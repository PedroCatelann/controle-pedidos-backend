package com.pedrocatelan.form.services;

import com.pedrocatelan.form.entities.Pedido;
import com.pedrocatelan.form.repositories.interfaces.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
