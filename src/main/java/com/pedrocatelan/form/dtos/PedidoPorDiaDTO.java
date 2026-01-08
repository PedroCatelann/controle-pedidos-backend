package com.pedrocatelan.form.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class PedidoPorDiaDTO {

    private final String dataPedido;
    private final Long quantidade;

    public PedidoPorDiaDTO(String dataPedido, Long quantidade) {
        this.dataPedido = dataPedido;
        this.quantidade = quantidade;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public Long getQuantidade() {
        return quantidade;
    }
}
