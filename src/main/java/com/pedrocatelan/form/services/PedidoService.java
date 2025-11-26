package com.pedrocatelan.form.services;

import com.pedrocatelan.form.dtos.FuncionarioDTO;
import com.pedrocatelan.form.dtos.PedidoDTO;
import com.pedrocatelan.form.entities.Pedido;
import com.pedrocatelan.form.repositories.interfaces.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    @Transactional //mantém a integridade dos dados, evita que modificações parciais sejam salvas em caso de falhas. Exemplo:
                   //uma transferência bancária que envolve debitar uma conta e creditar outra, @Transactional garante que ambas as operações sejam bem-sucedidas ou que nenhuma delas seja efetivada.
                   //Remove a necessidade de escrever manualmente os blocos try-catch com commit e rollback. O Spring gerencia esse processo automaticamente, reduzindo a complexidade e a chance de erros.
    public Pedido salvarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<PedidoDTO> listarPedido(PedidoDTO pedidoDTO) {
        var pedidos = pedidoRepository.filtrar(pedidoDTO.nomeCliente(),
            pedidoDTO.funcionario().getId(), pedidoDTO.dataPedido().split("T")[0]);

        return pedidos.stream()
                .map(ped -> PedidoDTO.builder()
                        .id(ped.getId())
                        .nomeCliente(ped.getNomeCliente())
                        .funcionario(ped.getFuncionario())
                        .dataPedido(ped.getDataPedido())
                        .telefone(ped.getTelefone())
                        .bairro(ped.getBairro())
                        .rua(ped.getRua())
                        .numero(ped.getNumero())
                        .complemento(ped.getComplemento())
                        .observacao(ped.getObservacao())
                        .isEntregue(ped.isEntregue())
                        .build())
                .toList();
    }

    public void alterarStatusEntregue(BigInteger id) {
        pedidoRepository.alterarStatusEntregue(id);
    }
}
