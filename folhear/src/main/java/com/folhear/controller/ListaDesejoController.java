package com.folhear.controller;

import com.folhear.entity.ListaDesejo;
import com.folhear.service.ListaDesejoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/listas-desejo")
public class ListaDesejoController {

    @Autowired
    private ListaDesejoService listaDesejoService;

    @GetMapping
    public List<ListaDesejo> findAll() {
        return listaDesejoService.findAll();
    }

    @GetMapping("/{id}")
    public ListaDesejo findById(@PathVariable UUID id) {
        return listaDesejoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ListaDesejo create(@RequestBody ListaDesejo lista) {
        return listaDesejoService.create(lista);
    }

    @PutMapping("/{id}")
    public ListaDesejo update(@PathVariable UUID id, @RequestBody ListaDesejo lista) {
        return listaDesejoService.update(id, lista);
    }

    @PostMapping("/{id}/livros/{livroId}")
    public ListaDesejo adicionarLivro(@PathVariable UUID id, @PathVariable UUID livroId) {
        return listaDesejoService.adicionarLivro(id, livroId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        listaDesejoService.delete(id);
    }
}
