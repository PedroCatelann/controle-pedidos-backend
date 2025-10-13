package com.pedrocatelan.form.dtos;

import com.pedrocatelan.form.enums.RolesEnum;
import lombok.Builder;

@Builder
public record FuncionarioDTO(String nome, RolesEnum roles) {
}
