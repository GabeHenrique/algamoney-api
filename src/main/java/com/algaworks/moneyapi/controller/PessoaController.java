package com.algaworks.moneyapi.controller;

import com.algaworks.moneyapi.event.RecursoCriadoEvent;
import com.algaworks.moneyapi.model.Pessoa;
import com.algaworks.moneyapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    public PessoaController(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return !pessoas.isEmpty() ? ResponseEntity.ok(pessoas) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> buscarPelaId(@PathVariable Long codigo) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
        return pessoa.isPresent() ? ResponseEntity.ok(pessoa) : ResponseEntity.noContent().build();
    }
    private final ApplicationEventPublisher publisher;

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }


}
