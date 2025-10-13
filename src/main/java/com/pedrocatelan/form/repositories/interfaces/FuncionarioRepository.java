package com.pedrocatelan.form.repositories.interfaces;

import com.pedrocatelan.form.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
public interface FuncionarioRepository extends JpaRepository<Funcionario, BigInteger> {
}
