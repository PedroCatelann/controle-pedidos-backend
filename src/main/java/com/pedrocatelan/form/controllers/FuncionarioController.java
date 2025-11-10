package com.pedrocatelan.form.controllers;

import com.pedrocatelan.form.dtos.FuncionarioDTO;
import com.pedrocatelan.form.entities.Funcionario;
import com.pedrocatelan.form.services.FuncionarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    @PostMapping
    public ResponseEntity salvarFuncionario (@RequestBody FuncionarioDTO funcionarioDTO) {

        Funcionario funcionario = Funcionario.builder()
                .nome(funcionarioDTO.nome())
                .roles(funcionarioDTO.roles())
                .build();

        funcionarioService.salvarEntregador(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> obterFuncionario(@PathVariable("id") String id) {
        var funcionarioOpt = funcionarioService.getById(new BigInteger(id));
        if(funcionarioOpt.isEmpty())
            return ResponseEntity.notFound().build();
        var funcionario = funcionarioOpt.get();

        var funcionarioDto = FuncionarioDTO.builder()
                .nome(funcionario.getNome())
                .roles(funcionario.getRoles())
                .build();

        return ResponseEntity.ok(funcionarioDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarFuncionario(@PathVariable("id") String id) {
        funcionarioService.delete(new BigInteger(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping()
    public ResponseEntity<FuncionarioDTO> obterFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = Funcionario.builder()
                .nome(funcionarioDTO.nome())
                .roles(funcionarioDTO.roles())
                .build();
        funcionarioService.alterarEntregador(funcionario);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<FuncionarioDTO>> listarFuncionarios(@PathParam("nome") String nome) {
        var funcionarios = funcionarioService.listarFuncionarios();

        return ResponseEntity.ok(funcionarios);
    }
}
