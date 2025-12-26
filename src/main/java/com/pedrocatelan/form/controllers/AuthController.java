package com.pedrocatelan.form.controllers;

import com.pedrocatelan.form.dtos.AccountCredentialsDTO;
import com.pedrocatelan.form.dtos.FuncionarioDTO;
import com.pedrocatelan.form.entities.Funcionario;
import com.pedrocatelan.form.security.jwt.JwtTokenProvider;
import com.pedrocatelan.form.services.AuthService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService service;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentialsDTO) {

        if(credentialValidation(credentialsDTO)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        var token = service.signIn(credentialsDTO);

        if(token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        return ResponseEntity.ok().body(token);
    }

    private static boolean credentialValidation(AccountCredentialsDTO credentialsDTO) {
        return credentialsDTO == null || StringUtils.isBlank(credentialsDTO.getPassword()) || StringUtils.isBlank(credentialsDTO.getUsername());
    }

    @PutMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Invalid client request!");
        }

        String refreshToken = authorizationHeader.substring("Bearer ".length());

        var token = jwtTokenProvider.refreshToken(refreshToken);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Invalid client request!");
        }

        return ResponseEntity.ok(token);
    }

    @PostMapping(value = "/createUser")
    public ResponseEntity<AccountCredentialsDTO> salvarUsuario (@RequestBody AccountCredentialsDTO accountCredentialsDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(service.create(accountCredentialsDTO));
    }

    private boolean parametersAreInvalid(String username, String refreshToken) {
        return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
    }
}
