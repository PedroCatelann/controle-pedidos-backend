package com.pedrocatelan.form.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PedidoPorDiaDTO {

    private final String dataPedido;
    private final Long quantidade;

}
