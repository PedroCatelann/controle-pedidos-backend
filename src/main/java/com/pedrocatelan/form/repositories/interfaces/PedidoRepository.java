package com.pedrocatelan.form.repositories.interfaces;

import com.pedrocatelan.form.dtos.PedidoDTO;
import com.pedrocatelan.form.dtos.PedidoPorDiaDTO;
import com.pedrocatelan.form.entities.Funcionario;
import com.pedrocatelan.form.entities.Pedido;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDate;
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

    @Modifying // Sem usar Modifying o Spring iria entender que a query é um select. Ele é necessário para queries no Spring Data JPA
    @Transactional // Garante que a operação seja executada dentro de uma transação do banco de dados.
    @Query(
            value = """
            UPDATE pedido
            SET is_entregue = CASE WHEN is_entregue = 1 THEN 0 ELSE 1 END, datahoraentregue = GETDATE() WHERE id = :id            
            """,
            nativeQuery = true
    )
    void alterarStatusEntregue(@Param("id") BigInteger id);

    @Modifying
    @Transactional
    @Query(value = """
            update pedido 
            set bairro = :bairro,
            complemento = :complemento,
            nome_cliente = :nomeCliente,
            numero = :numero,
            observacao = :observacao,
            rua = :rua,
            telefone = :telefone,
            funcionario_id = :funcionarioId,
            datahoraaltera = :dataHoraAltera
            where id = :id
            """,
            nativeQuery = true
    )
    void alterarPedido(@Param("bairro") String bairro,
                       @Param("complemento") String complemento,
                       @Param("nomeCliente") String nomeCliente,
                       @Param("numero") String numero,
                       @Param("observacao") String observacao,
                       @Param("rua") String rua,
                       @Param("telefone") String telefone,
                       @Param("funcionarioId") BigInteger funcionarioId,
                       @Param("dataHoraAltera") LocalDateTime dataHoraAltera,
                       @Param("id") BigInteger id
                       );

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
        
        AND p.is_entregue = 1

    ORDER BY p.datahorainclui DESC
  """,
            nativeQuery = true
    )
    List<Pedido> filtrarPedidoEntregue(
            @Param("nomeCliente") String nomeCliente,
            @Param("funcionarioId") BigInteger funcionarioId,
            @Param("dataPedido") String dataPedido);


    @Query("SELECT p FROM Pedido p WHERE p.funcionario.id = :idFuncionario " +
            "AND p.dataHoraPassouEntrega BETWEEN :primeiroDia AND :ultimoDia AND p.isEntregue = true")
    List<Pedido> buscarPedidoPorFuncionarioAnoMes(@Param("idFuncionario") String idFuncionario,
                                                  @Param("primeiroDia") String primeiroDia,
                                                  @Param("ultimoDia") String ultimoDia);

    @Query("""
        SELECT new com.pedrocatelan.form.dtos.PedidoPorDiaDTO(p.dataPedido, COUNT(p))
        FROM Pedido p
        WHERE p.funcionario.id = :idFuncionario
          AND p.dataPedido BETWEEN :primeiroDia AND :ultimoDia
          AND p.isEntregue = true
        GROUP BY p.dataPedido
        ORDER BY p.dataPedido
    """)
    List<PedidoPorDiaDTO> buscarQuantidadePedidosPorDiaPorFuncionarioAnoMesAgrupado(@Param("idFuncionario") String idFuncionario,
                                                                                    @Param("primeiroDia") String primeiroDia,
                                                                                    @Param("ultimoDia") String ultimoDia);

    @Modifying // Sem usar Modifying o Spring iria entender que a query é um select. Ele é necessário para queries no Spring Data JPA
    @Transactional // Garante que a operação seja executada dentro de uma transação do banco de dados.
    @Query(
            value = """
            UPDATE pedido
            SET datahorapassouentrega = GETDATE(), passou_entrega = 1 WHERE id = :id            
            """,
            nativeQuery = true
    )
    void passouEntrega(@Param("id") BigInteger id);
}
