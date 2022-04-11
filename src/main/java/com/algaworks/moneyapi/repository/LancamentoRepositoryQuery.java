package com.algaworks.moneyapi.repository;

import com.algaworks.moneyapi.filter.LancamentoFilter;
import com.algaworks.moneyapi.model.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {

    Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);


}
