package com.pedrocatelan.form.repositories.interfaces;

import com.pedrocatelan.form.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, BigInteger> {
}
