package com.pedrocatelan.form.controllers;

import com.pedrocatelan.form.dtos.PedidoDTO;
import com.pedrocatelan.form.entities.Funcionario;
import com.pedrocatelan.form.entities.Pedido;
import com.pedrocatelan.form.services.FuncionarioService;
import com.pedrocatelan.form.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidosController {

    private final PedidoService pedidoService;
    private final FuncionarioService funcionarioService;
    @PostMapping
    public ResponseEntity salvarPedido (@RequestBody PedidoDTO order) {

        var funcionario = funcionarioService.findFuncById(order.funcionario_id());


        Pedido pedido = Pedido.builder()
                .telefone(order.telefone())
                .bairro(order.bairro())
                .rua(order.rua())
                .numero(order.numero())
                .complemento(order.complemento())
                .observacao(order.observacao())
                .dataPedido(LocalDate.now().toString())
                .dataHoraInclui(LocalDateTime.now())
                .dataHoraAltera(LocalDateTime.now())
                .funcionario(funcionario)
                .nomeCliente(order.nomeCliente())
                .build();

        pedidoService.salvarPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obterPedido(@PathVariable("id") BigInteger id) {
        return ResponseEntity.ok(pedidoService.obterPedido(id));
    }

    @PutMapping()
    public ResponseEntity<PedidoDTO> alterarPedido(@RequestBody PedidoDTO pedidoDTO) {
        var funcionario = funcionarioService.findFuncById(pedidoDTO.funcionario_id());

        var pedido = Pedido.builder()
                .Id(pedidoDTO.id())
                .telefone(pedidoDTO.telefone())
                .bairro(pedidoDTO.bairro())
                .rua(pedidoDTO.rua())
                .numero(pedidoDTO.numero())
                .complemento(pedidoDTO.complemento())
                .observacao(pedidoDTO.observacao())
                .nomeCliente(pedidoDTO.nomeCliente())
                .dataHoraAltera(LocalDateTime.now())
                .funcionario(funcionario)
                .build();

        pedidoService.alterarPedido(pedido);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PedidoDTO>> listarPedidos(@Param("nomeCliente") String nomeCliente, @Param("funcionario") String funcionario,
                                                         @RequestParam("dataPedido") String dataPedido) {

        Funcionario func = new Funcionario();

        if(funcionario != null && !funcionario.isEmpty())
            func = funcionarioService.findFuncById(new BigInteger(funcionario));

        var pedido = PedidoDTO.builder()
                .nomeCliente(nomeCliente)
                .funcionario(func)
                .dataPedido(dataPedido).build();

        var pedidos = pedidoService.listarPedido(pedido);
        return ResponseEntity.ok(pedidos);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> alterarStatusEntregue(@PathVariable("id") BigInteger id) {
        pedidoService.alterarStatusEntregue(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/passouEntrega/{id}")
    public ResponseEntity<Void> passouEntrega(@PathVariable("id") BigInteger id) {
        pedidoService.passouEntrega(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listarPedidosEntregues")
    public ResponseEntity<List<PedidoDTO>> listarPedidosEntregues(@Param("nomeCliente") String nomeCliente, @Param("funcionario") String funcionario,
                                                         @RequestParam("dataPedido") String dataPedido) {

        Funcionario func = new Funcionario();

        if(funcionario != null && !funcionario.isEmpty())
            func = funcionarioService.findFuncById(new BigInteger(funcionario));

        var pedido = PedidoDTO.builder()
                .nomeCliente(nomeCliente)
                .funcionario(func)
                .dataPedido(dataPedido).build();

        var pedidos = pedidoService.listarPedidoEntregue(pedido);
        return ResponseEntity.ok(pedidos);
    }
}
