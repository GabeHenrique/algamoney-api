package com.algaworks.moneyapi.controller;

import com.algaworks.moneyapi.event.RecursoCriadoEvent;
import com.algaworks.moneyapi.exceptionhandler.AlgamoneyExceptionHandler;
import com.algaworks.moneyapi.filter.LancamentoFilter;
import com.algaworks.moneyapi.model.Lancamento;
import com.algaworks.moneyapi.repository.LancamentoRepository;
import com.algaworks.moneyapi.service.LancamentoService;
import com.algaworks.moneyapi.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

    private final LancamentoRepository lancamentoRepository;
    private final ApplicationEventPublisher publisher;
    private final LancamentoService lancamentoService;
    private final MessageSource messageSource;

    public LancamentoController(ApplicationEventPublisher publisher, LancamentoRepository lancamentoRepository, LancamentoService lancamentoService, MessageSource messageSource) {
        this.publisher = publisher;
        this.lancamentoRepository = lancamentoRepository;
        this.lancamentoService = lancamentoService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepository.filtrar(lancamentoFilter, pageable);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
        Optional<Lancamento> lancamento = lancamentoRepository.findById(codigo);
        return lancamento.isPresent() ? ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long codigo) {
        lancamentoRepository.deleteById(codigo);
    }

    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaException (PessoaInexistenteOuInativaException ex) {
        String mensagemUsuario = messageSource.getMessage("'pessoa.inexistente-ou-inativa'", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<AlgamoneyExceptionHandler.Erro> erros = List.of(new AlgamoneyExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }
}
