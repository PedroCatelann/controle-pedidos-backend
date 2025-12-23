package com.pedrocatelan.form.controllers;

import com.pedrocatelan.form.dtos.AccountCredentialsDTO;
import com.pedrocatelan.form.dtos.FuncionarioDTO;
import com.pedrocatelan.form.entities.Funcionario;
import com.pedrocatelan.form.security.jwt.JwtTokenProvider;
import com.pedrocatelan.form.services.AuthService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService service;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentialsDTO,
                                    HttpServletResponse response) {

        if (credentialValidation(credentialsDTO)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Invalid client request!");
        }

        var token = service.signIn(credentialsDTO);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Invalid client request!");
        }

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", token.getAccessToken())
                .httpOnly(true)
                .secure(false) // true em produção (HTTPS)
                .path("/")
                .maxAge(60 * 60) // 1 hora
                .sameSite("Lax")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", token.getRefreshToken())
                .httpOnly(true)
                .secure(false) // true em produção
                .path("/auth/refresh")
                .maxAge(60 * 60 * 24 * 3) // 3 dias
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        // 🔐 NÃO retornar tokens no body
        return ResponseEntity.ok().body(Map.of(
                "authenticated", true,
                "username", token.getUsername()
        ));
    }

    private static boolean credentialValidation(AccountCredentialsDTO credentialsDTO) {
        return credentialsDTO == null || StringUtils.isBlank(credentialsDTO.getPassword()) || StringUtils.isBlank(credentialsDTO.getUsername());
    }

    @PutMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request,
                                     HttpServletResponse response) {

        Cookie refreshCookie = WebUtils.getCookie(request, "refreshToken");


        String newAccessToken = jwtTokenProvider.refreshAccessToken(refreshCookie.getValue());

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", newAccessToken)
                .httpOnly(true)
                .path("/")
                .maxAge(3600)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/createUser")
    public ResponseEntity<AccountCredentialsDTO> salvarUsuario (@RequestBody AccountCredentialsDTO accountCredentialsDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(service.create(accountCredentialsDTO));
    }

    private boolean parametersAreInvalid(String username, String refreshToken) {
        return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
    }
}
