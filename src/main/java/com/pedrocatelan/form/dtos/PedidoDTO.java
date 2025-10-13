package com.pedrocatelan.form.dtos;

public record PedidoDTO(FuncionarioDTO deliveryManDTO,
                        String telefone,
                        String bairro,
                        String rua,
                        String numero,
                        String complemento,
                        String observacao,
                        String nomeCliente
                        ) {
}
