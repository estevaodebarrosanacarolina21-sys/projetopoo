package com.folhear.controller;

import com.folhear.entity.Troca;
import com.folhear.service.TrocaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trocas")
public class TrocaController {

    @Autowired
    private TrocaService trocaService;

    @GetMapping
    public List<Troca> findAll() {
        return trocaService.findAll();
    }

    @GetMapping("/{id}")
    public Troca findById(@PathVariable UUID id) {
        return trocaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Troca create(@RequestBody Troca troca) {
        return trocaService.create(troca);
    }

    @PutMapping("/{id}")
    public Troca update(@PathVariable UUID id, @RequestBody Troca troca) {
        return trocaService.update(id, troca);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        trocaService.delete(id);
    }
}
