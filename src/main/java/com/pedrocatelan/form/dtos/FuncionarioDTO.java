package com.pedrocatelan.form.dtos;

import com.pedrocatelan.form.enums.RolesEnum;
import lombok.Builder;

import java.math.BigInteger;

@Builder
public record FuncionarioDTO(BigInteger id, String nome, RolesEnum roles) {
}
