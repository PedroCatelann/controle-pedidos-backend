package com.pedrocatelan.form.dtos;

import lombok.*;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetricasFuncionarioPedidoDTO {

    private Map<String, Long> pedidosPorDia;
    private Double mediaMinutos;
    private EntregaMaisDemoradaDTO entregaMaisDemoradaDTO;
}
