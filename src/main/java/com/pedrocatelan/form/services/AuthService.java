package com.pedrocatelan.form.services;

import com.pedrocatelan.form.dtos.AccountCredentialsDTO;
import com.pedrocatelan.form.dtos.TokenDTO;
import com.pedrocatelan.form.entities.User;
import com.pedrocatelan.form.exceptions.RequiredObjectIsNullException;
import com.pedrocatelan.form.repositories.interfaces.UserRepository;
import com.pedrocatelan.form.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<TokenDTO> signIn(AccountCredentialsDTO credentialsDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentialsDTO.getUsername(),
                        credentialsDTO.getPassword()
                )
        );

        var user = userRepository.findByUsername(credentialsDTO.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException(
                    "Username " + credentialsDTO.getUsername() + " not found!"
            );
        }

        // 🔥 CONVERSÃO CORRETA
        List<String> roles = user.getPermissions()
                .stream()
                .map(role -> "ROLE_" + role.getDescription())
                .toList();// 👈 FUNDAMENTAL

        return ResponseEntity.ok(
                jwtTokenProvider.createAccessToken(
                        user.getUsername(),
                        roles
                )
        );
    }

    public AccountCredentialsDTO create (AccountCredentialsDTO accountCredentialsDTO) {

        if(accountCredentialsDTO == null) throw new RequiredObjectIsNullException();

        var user = User.builder()
                .username(accountCredentialsDTO.getUsername())
                .fullname(accountCredentialsDTO.getFullname())
                .password(generateHashdPassword(accountCredentialsDTO.getPassword()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();


        var savedUser = userRepository.save(user);

        return AccountCredentialsDTO.builder()
                .username(savedUser.getUsername())
                .fullname(savedUser.getFullname())
                .password(savedUser.getPassword())
                .build();

    }

    private static String generateHashdPassword(String password) {
        PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 185000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pbkdf2Encoder);

        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);

        return passwordEncoder.encode(password);
    }


}