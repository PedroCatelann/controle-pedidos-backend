package com.pedrocatelan.form.repositories.interfaces;

import com.pedrocatelan.form.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, BigInteger> {

    @Query("SELECT f FROM Funcionario f WHERE (:nome IS NULL OR LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%')))")
    List<Funcionario> findByNomeContainingIgnoreCaseOptional(@Param("nome") String nome);
}
