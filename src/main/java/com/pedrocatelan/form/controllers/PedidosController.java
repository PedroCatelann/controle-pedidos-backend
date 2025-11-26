package com.pedrocatelan.form.controllers;

import com.pedrocatelan.form.dtos.PedidoDTO;
import com.pedrocatelan.form.entities.Funcionario;
import com.pedrocatelan.form.entities.Pedido;
import com.pedrocatelan.form.services.FuncionarioService;
import com.pedrocatelan.form.services.PedidoService;
import lombok.RequiredArgsConstructor;
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
                .funcionario(funcionario)
                .nomeCliente(order.nomeCliente())
                .build();

        pedidoService.salvarPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PedidoDTO>> listarPedidos(@RequestParam("nomeCliente") String nomeCliente, @RequestParam("funcionario") String funcionario_id,
                                                         @RequestParam("dataPedido") String dataPedido) {


        var func = funcionarioService.findFuncById(new BigInteger(funcionario_id));

        var pedido = PedidoDTO.builder()
                .nomeCliente(nomeCliente)
                .funcionario(func)
                .dataPedido(dataPedido).build();

        var pedidos = pedidoService.listarPedido(pedido);
        return ResponseEntity.ok(pedidos);
    }
}
