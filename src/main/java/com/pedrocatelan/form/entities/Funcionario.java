package com.pedrocatelan.form.entities;

import com.pedrocatelan.form.enums.RolesEnum;
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
@Table(name = "funcionario")
@EntityListeners(AuditingEntityListener.class) // Chama automaticamente eventos de criado em e alterado em
@Data // gera automaticamente métodos @Getter e @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger Id;

    @Column
    private String nome;

    @CreatedDate
    @Column(name = "datahorainclui")
    private LocalDateTime dataHoraInclui;

    @Column(name = "datahoraaltera")
    private LocalDateTime dataHoraAltera;

    @Column
    private RolesEnum roles;



}
