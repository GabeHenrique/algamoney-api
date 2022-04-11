package com.algaworks.moneyapi.filter;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class LancamentoFilter {

    private String descricao;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVencimentoDe;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVencimentoAte;

}
