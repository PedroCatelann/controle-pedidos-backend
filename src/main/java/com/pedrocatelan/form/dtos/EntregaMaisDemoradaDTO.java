package com.pedrocatelan.form.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntregaMaisDemoradaDTO {

    private String endereco;
    private String nomeCliente;
    private String data;
    private Double tempoParaEntrega;
}
