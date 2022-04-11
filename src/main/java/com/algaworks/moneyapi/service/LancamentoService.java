package com.algaworks.moneyapi.service;

import com.algaworks.moneyapi.model.Lancamento;
import com.algaworks.moneyapi.model.Pessoa;
import com.algaworks.moneyapi.repository.LancamentoRepository;
import com.algaworks.moneyapi.repository.PessoaRepository;
import com.algaworks.moneyapi.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    private final PessoaRepository pessoaRepository;
    private final LancamentoRepository lancamentoRepository;

    public LancamentoService(PessoaRepository pessoaRepository, LancamentoRepository lancamentoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.lancamentoRepository = lancamentoRepository;
    }

    public Lancamento salvar(Lancamento lancamento) {
        Pessoa pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo()).orElseThrow(() -> new EmptyResultDataAccessException(1));
        if (pessoa == null || pessoa.isInativo()) {
            try {
                throw new PessoaInexistenteOuInativaException();
            } catch (PessoaInexistenteOuInativaException e) {
                e.printStackTrace();
            }
        }
        return lancamentoRepository.save(lancamento);
    }
}
