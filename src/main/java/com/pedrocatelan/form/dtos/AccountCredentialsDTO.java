package com.pedrocatelan.form.dtos;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCredentialsDTO {
    private String username;
    private String password;
    private String fullname;
}
