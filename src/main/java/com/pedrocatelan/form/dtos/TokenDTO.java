package com.pedrocatelan.form.dtos;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String username;
    private String fullname;
    private Boolean authenticated; // informa se o usuário está autenticado
    private Date created; // informa se o token está criado
    private Date expiration; // informa a data de expiração do token
    private String password; // informa a senha do usuário
    private String accessToken; // informa o token de acesso
    private String refreshToken; // informa o token de refresh caso o de acesso seja expirado

    public TokenDTO(String username, String fullname, Boolean authenticated, Date created, Date expiration,
                    String accessToken, String refreshToken) {
        this.username = username;
        this.fullname = fullname;
        this.authenticated = authenticated;
        this.created = created;
        this.expiration = expiration;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
