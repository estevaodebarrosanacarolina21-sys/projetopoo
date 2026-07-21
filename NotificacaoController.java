package com.folhear.controller;

import com.folhear.entity.Notificacao;
import com.folhear.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping
    public List<Notificacao> findAll() {
        return notificacaoService.findAll();
    }

    @GetMapping("/{id}")
    public Notificacao findById(@PathVariable Long id) {
        return notificacaoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Notificacao create(@RequestBody Notificacao notificacao) {
        return notificacaoService.create(notificacao);
    }

    @PatchMapping("/{id}/lida")
    public Notificacao marcarComoLida(@PathVariable Long id) {
        return notificacaoService.marcarComoLida(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        notificacaoService.delete(id);
    }
}
