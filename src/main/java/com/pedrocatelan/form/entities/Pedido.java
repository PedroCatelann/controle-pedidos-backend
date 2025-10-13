package com.pedrocatelan.form.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "PEDIDO")
@EntityListeners(AuditingEntityListener.class) // Chama automaticamente eventos de criado em e alterado em
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger Id;
    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;
    @Column
    private String telefone;
    @Column
    private String bairro;
    @Column
    private String rua;
    @Column
    private String numero;
    @Column
    private String complemento;
    @Column
    private String observacao;
    @Column
    private String nomeCliente;
    @CreatedDate
    @Column(name = "datahorainclui")
    private LocalDateTime dataHoraInclui;
}
