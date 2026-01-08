package com.pedrocatelan.form.services;

import com.pedrocatelan.form.dtos.EntregaMaisDemoradaDTO;
import com.pedrocatelan.form.dtos.MetricasFuncionarioPedidoDTO;
import com.pedrocatelan.form.dtos.PedidoPorDiaDTO;
import com.pedrocatelan.form.entities.Pedido;
import com.pedrocatelan.form.repositories.interfaces.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.webresources.EmptyResource;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetricasService {

    private final PedidoRepository pedidoRepository;

    public MetricasFuncionarioPedidoDTO buscarMetricasFuncionarioPorAnoMes(String idFuncionario, String ano, String mes) throws Exception {
        EntregaMaisDemoradaDTO entregaMaisDemorada = null;

        YearMonth yearMonth = YearMonth.of(Integer.parseInt(ano), Integer.parseInt(mes));

        LocalDate primeiroDia = yearMonth.atDay(1);
        LocalDate ultimoDia = yearMonth.atEndOfMonth();

        var pedidos = pedidoRepository.buscarPedidoPorFuncionarioAnoMes(idFuncionario, primeiroDia.toString(), ultimoDia.toString());
        Map<String, Long> pedidosPorDia = pedidoRepository.buscarQuantidadePedidosPorDiaPorFuncionarioAnoMesAgrupado(idFuncionario, primeiroDia.toString(), ultimoDia.toString())
                .stream()
                .collect(Collectors.toMap(
                        PedidoPorDiaDTO::getDataPedido,
                        PedidoPorDiaDTO::getQuantidade

        ));

        double mediaSegundos = pedidos.stream()
                .filter(p -> p.getDataHoraEntregue() != null)
                .mapToLong(p -> Duration.between(
                        p.getDataHoraInclui(),
                        p.getDataHoraEntregue()
                ).getSeconds())
                .average()
                .orElse(0);

        Double mediaMinutos = mediaSegundos / 60.0;

        Pedido pedidoMaiorTempo = pedidos.stream()
                .filter(p -> p.getDataHoraInclui() != null)
                .filter(p -> p.getDataHoraEntregue() != null)
                .max(Comparator.comparingLong(p ->
                        Duration.between(
                                p.getDataHoraInclui(),
                                p.getDataHoraEntregue()
                        ).getSeconds()
                )).orElse(null);

        if(pedidoMaiorTempo != null) {
            var rua = pedidoMaiorTempo.getRua().isBlank() ? "" : pedidoMaiorTempo.getRua();
            var numero = pedidoMaiorTempo.getNumero().isBlank() ? "" : pedidoMaiorTempo.getNumero();
            var bairro = pedidoMaiorTempo.getBairro().isBlank() ? "" : pedidoMaiorTempo.getBairro();

            double mediaSegundosMaximo = pedidos.stream()
                    .filter(p -> p.getDataHoraEntregue() != null)
                    .mapToLong(p ->
                            Duration.between( p.getDataHoraInclui(), p.getDataHoraEntregue()
                            ).getSeconds())
                    .max()
                    .orElse(0);

            Double mediaMinutosMaximo = mediaSegundosMaximo / 60.0;

            entregaMaisDemorada = EntregaMaisDemoradaDTO.builder()
                    .endereco(
                            rua + ", " + numero + ", " + bairro
                    )
                    .nomeCliente(pedidoMaiorTempo.getNomeCliente())
                    .data(pedidoMaiorTempo.getDataPedido())
                    .tempoParaEntrega(mediaMinutosMaximo)
                    .build();
        } else {
            throw new Exception("Não foi possível obter o pedido com maior tempo");
        }

         return MetricasFuncionarioPedidoDTO.builder()
                .pedidosPorDia(pedidosPorDia)
                .mediaMinutos(mediaMinutos)
                .entregaMaisDemoradaDTO(entregaMaisDemorada)
                .build();
    }
}
