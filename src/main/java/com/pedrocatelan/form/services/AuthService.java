package com.pedrocatelan.form.services;

import com.pedrocatelan.form.dtos.AccountCredentialsDTO;
import com.pedrocatelan.form.dtos.TokenDTO;
import com.pedrocatelan.form.repositories.interfaces.UserRepository;
import com.pedrocatelan.form.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
                new UsernamePasswordAuthenticationToken(credentialsDTO.getUsername(), credentialsDTO.getPassword())
        );

        var user = userRepository.findByUsername(credentialsDTO.getUsername());
        if(user == null) {
            throw new UsernameNotFoundException("Username" + credentialsDTO.getUsername() + "not found!");
        }

        var tokenResponse = jwtTokenProvider.createAccessToken(credentialsDTO.getUsername(), user.getRoles());

        return ResponseEntity.ok(tokenResponse);
    }

    public ResponseEntity<TokenDTO> refreshToken (String username, String refreshToken) {
        var user = userRepository.findByUsername(username);
        TokenDTO token;

        if(user != null) {
            token = jwtTokenProvider.refreshToken(refreshToken);
        }
        else {
            throw new UsernameNotFoundException("Username" + username + "not found!");
        }

        return ResponseEntity.ok(token);

    }


}