package com.pedrocatelan.form.services;

import com.pedrocatelan.form.dtos.FuncionarioDTO;
import com.pedrocatelan.form.dtos.PedidoDTO;
import com.pedrocatelan.form.entities.Pedido;
import com.pedrocatelan.form.exceptions.RequiredObjectIsNullException;
import com.pedrocatelan.form.exceptions.ResourceNotFoundException;
import com.pedrocatelan.form.repositories.interfaces.FuncionarioRepository;
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
    private final FuncionarioService funcionarioService;

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
                        .dataHoraInclui(ped.getDataHoraInclui())
                        .build())
                .toList();
    }

    public List<PedidoDTO> listarPedidoEntregue(PedidoDTO pedidoDTO) {
        var pedidos = pedidoRepository.filtrarPedidoEntregue(pedidoDTO.nomeCliente(),
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
                        .dataHoraInclui(ped.getDataHoraInclui())
                        .dataHoraEntregue(ped.getDataHoraEntregue())
                        .build())
                .toList();
    }

    public void alterarStatusEntregue(BigInteger id) {
        pedidoRepository.alterarStatusEntregue(id);
    }

    public PedidoDTO obterPedido(BigInteger id) {
        var pedido = pedidoRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Pedido não encontrado"));
        var funcionario = funcionarioService.findFuncById(pedido.getFuncionario().getId());

        return PedidoDTO.builder()
                .id(pedido.getId())
                .telefone(pedido.getTelefone())
                .bairro(pedido.getBairro())
                .rua(pedido.getRua())
                .numero(pedido.getNumero())
                .complemento(pedido.getComplemento())
                .observacao(pedido.getObservacao())
                .nomeCliente(pedido.getNomeCliente())
                .funcionario(funcionario)
                .dataPedido(pedido.getDataPedido())
                .isEntregue(pedido.isEntregue())
                .build();

    }
    @Transactional
    public void alterarPedido(Pedido pedido) {
        if(pedido == null) throw new RequiredObjectIsNullException();

        pedidoRepository.findById(pedido.getId()).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        pedidoRepository.alterarPedido(pedido.getBairro(), pedido.getComplemento(), pedido.getNomeCliente(), pedido.getNumero(), pedido.getObservacao(), pedido.getRua(), pedido.getTelefone(),
                pedido.getFuncionario().getId(), pedido.getDataHoraAltera(), pedido.getId());
    }
}
