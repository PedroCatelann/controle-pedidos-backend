package com.pedrocatelan.form.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
public record PedidoDTO(
                        String telefone,
                        String bairro,
                        String rua,
                        String numero,
                        String complemento,
                        String observacao,
                        String nomeCliente,
                        BigInteger funcionario,
                        String dataPedido
                        ) {
}
