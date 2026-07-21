package com.folhear.controller;

import com.folhear.entity.Clube;
import com.folhear.service.ClubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clubes")
public class ClubeController {

    @Autowired
    private ClubeService clubeService;

    @GetMapping
    public List<Clube> findAll() {
        return clubeService.findAll();
    }

    @GetMapping("/{id}")
    public Clube findById(@PathVariable UUID id) {
        return clubeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Clube create(@RequestBody Clube clube) {
        return clubeService.create(clube);
    }

    @PutMapping("/{id}")
    public Clube update(@PathVariable UUID id, @RequestBody Clube clube) {
        return clubeService.update(id, clube);
    }

    @PostMapping("/{id}/membros/{usuarioId}")
    public Clube adicionarMembro(@PathVariable UUID id, @PathVariable UUID usuarioId) {
        return clubeService.adicionarMembro(id, usuarioId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        clubeService.delete(id);
    }
}
