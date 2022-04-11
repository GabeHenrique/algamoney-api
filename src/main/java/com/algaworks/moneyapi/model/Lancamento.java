package com.algaworks.moneyapi.model;

import com.algaworks.moneyapi.enums.TipoLancamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lancamento")
public class Lancamento {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @NotNull
    private String descricao;
    @NotNull
    @Column(name = "data_vencimento")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVencimento;
    @Column(name = "data_pagamento")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPagamento;
    @NotNull
    private Double valor;
    private String observacao;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoLancamento tipo;
    @ManyToOne
    @JoinColumn(name = "codigo_categoria")
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "codigo_pessoa")
    private Pessoa pessoa;

}
