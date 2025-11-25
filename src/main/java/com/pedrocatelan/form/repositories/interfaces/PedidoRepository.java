package com.pedrocatelan.form.repositories.interfaces;

import com.pedrocatelan.form.dtos.PedidoDTO;
import com.pedrocatelan.form.entities.Funcionario;
import com.pedrocatelan.form.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, BigInteger> {

    @Query(
            value = """
    SELECT *
    FROM PEDIDO p
    WHERE 
        -- filtro por nome (opcional)
        (
            :nomeCliente IS NULL 
            OR :nomeCliente = '' 
            OR LOWER(p.nome_cliente) LIKE LOWER(CONCAT('%', :nomeCliente, '%'))
        )

        -- filtro por funcionário (opcional)
        AND (
            :funcionarioId IS NULL
            OR p.funcionario_id = :funcionarioId
        )

        -- filtro por data início (opcional)
        AND (
            :dataPedido IS NULL
            OR p.data_pedido >= :dataPedido
        )        

    ORDER BY p.datahorainclui DESC
  """,
            nativeQuery = true
    )
    List<Pedido> filtrar(
            @Param("nomeCliente") String nomeCliente,
            @Param("funcionarioId") BigInteger funcionarioId,
            @Param("dataPedido") String dataPedido);
}
