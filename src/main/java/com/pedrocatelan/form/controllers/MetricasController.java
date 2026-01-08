package com.pedrocatelan.form.controllers;

import com.pedrocatelan.form.dtos.MetricasFuncionarioPedidoDTO;
import com.pedrocatelan.form.services.MetricasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metricas")
@RequiredArgsConstructor
public class MetricasController {

    private final MetricasService metricasService;

    @GetMapping("/funcionario-pedido")
    public ResponseEntity<MetricasFuncionarioPedidoDTO> metricaFuncionarioPedido(@RequestParam("idFuncionario") String idFuncionario, @RequestParam("ano") String ano,
                                                   @RequestParam("mes") String mes) throws Exception {

        var metricas = metricasService.buscarMetricasFuncionarioPorAnoMes(idFuncionario, ano, mes);

        return ResponseEntity.ok(metricas);

    }
}
