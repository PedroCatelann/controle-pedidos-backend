package com.pedrocatelan.form.controllers;

import com.pedrocatelan.form.dtos.PedidoDTO;
import com.pedrocatelan.form.entities.Funcionario;
import com.pedrocatelan.form.entities.Pedido;
import com.pedrocatelan.form.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidosController {

    private PedidoService pedidoService;

    public ResponseEntity salvarPedido (@RequestBody PedidoDTO order) {

        Funcionario funcionario = Funcionario.builder()
                .nome(order.deliveryManDTO().nome())
                .build();

        Pedido pedido = Pedido.builder()
                .funcionario(funcionario)
                .telefone(order.telefone())
                .bairro(order.bairro())
                .rua(order.rua())
                .numero(order.numero())
                .complemento(order.complemento())
                .observacao(order.observacao())
                .nomeCliente(order.nomeCliente())
                .build();

        pedidoService.salvarPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
