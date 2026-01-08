package com.pedrocatelan.form.dtos;

import com.pedrocatelan.form.entities.Funcionario;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
public record PedidoDTO(
                        BigInteger id,
                        String telefone,
                        String bairro,
                        String rua,
                        String numero,
                        String complemento,
                        String observacao,
                        String nomeCliente,
                        Funcionario funcionario,
                        BigInteger funcionario_id,
                        String dataPedido,
                        LocalDateTime dataHoraInclui,
                        boolean isEntregue,
                        LocalDateTime dataHoraEntregue,
                        boolean passouEntregador
                        ) {
}
