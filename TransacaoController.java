package com.folhear.controller;

import com.folhear.entity.Transacao;
import com.folhear.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping
    public List<Transacao> findAll() {
        return transacaoService.findAll();
    }

    @GetMapping("/{id}")
    public Transacao findById(@PathVariable UUID id) {
        return transacaoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transacao create(@RequestBody Transacao transacao) {
        return transacaoService.create(transacao);
    }

    @PutMapping("/{id}")
    public Transacao update(@PathVariable UUID id, @RequestBody Transacao transacao) {
        return transacaoService.update(id, transacao);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        transacaoService.delete(id);
    }
}
