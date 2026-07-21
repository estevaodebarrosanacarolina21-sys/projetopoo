package com.folhear.controller;

import com.folhear.entity.PontoEncontro;
import com.folhear.service.PontoEncontroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pontos-encontro")
public class PontoEncontroController {

    @Autowired
    private PontoEncontroService pontoEncontroService;

    @GetMapping
    public List<PontoEncontro> findAll() {
        return pontoEncontroService.findAll();
    }

    @GetMapping("/{id}")
    public PontoEncontro findById(@PathVariable UUID id) {
        return pontoEncontroService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PontoEncontro create(@RequestBody PontoEncontro ponto) {
        return pontoEncontroService.create(ponto);
    }

    @PutMapping("/{id}")
    public PontoEncontro update(@PathVariable UUID id, @RequestBody PontoEncontro ponto) {
        return pontoEncontroService.update(id, ponto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        pontoEncontroService.delete(id);
    }
}
