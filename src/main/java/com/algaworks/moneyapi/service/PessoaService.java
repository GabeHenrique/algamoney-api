package com.algaworks.moneyapi.service;

import com.algaworks.moneyapi.model.Pessoa;
import com.algaworks.moneyapi.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;


@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    private Pessoa getPessoa(Long codigo) {
        Pessoa pessoaSalva = pessoaRepository.findById(codigo).orElse(null);
        if (pessoaSalva == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return pessoaSalva;
    }

    public Pessoa atualizar(Long codigo, Pessoa pessoa) {
        Pessoa pessoaSalva = getPessoa(codigo);
        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
        return pessoaRepository.save(pessoaSalva);
    }

    public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
        Pessoa pessoaSalva = getPessoa(codigo);
        pessoaSalva.setAtivo(ativo);
        pessoaRepository.save(pessoaSalva);
    }

    public Pessoa buscarPessoaPeloCodigo(Long codigo) {
        Pessoa pessoaSalva =  pessoaRepository.findById(codigo)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        return pessoaSalva;
    }
}
