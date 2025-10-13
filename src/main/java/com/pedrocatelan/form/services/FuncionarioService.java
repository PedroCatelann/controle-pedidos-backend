package com.pedrocatelan.form.services;

import com.pedrocatelan.form.dtos.FuncionarioDTO;
import com.pedrocatelan.form.entities.Funcionario;
import com.pedrocatelan.form.exceptions.RequiredObjectIsNullException;
import com.pedrocatelan.form.exceptions.ResourceNotFoundException;
import com.pedrocatelan.form.repositories.interfaces.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    @Transactional
    public Funcionario salvarEntregador(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    @Transactional
    public Funcionario alterarEntregador(Funcionario funcionario) {
        if(funcionario == null) throw new RequiredObjectIsNullException();

        var entity = funcionarioRepository.findById(funcionario.getId()).orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado!"));

        return funcionarioRepository.save(entity);
    }

    public Optional<Funcionario> getById(BigInteger id) {
        return funcionarioRepository.findById(id);
    }

    public void delete(BigInteger id) {
        var funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado!"));
        funcionarioRepository.delete(funcionario);
    }

    public List<FuncionarioDTO> listarFuncionarios() {
        var funcionarios = funcionarioRepository.findAll();

        return funcionarios.stream()
                .map(func -> FuncionarioDTO.builder().nome(func.getNome()).roles(func.getRoles()).build())
                .toList();
    }
}
