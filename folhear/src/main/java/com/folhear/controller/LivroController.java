package com.folhear.controller;

import com.folhear.entity.Livro;
import com.folhear.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<Livro> findAll() {
        return livroService.findAll();
    }

    @GetMapping("/{id}")
    public Livro findById(@PathVariable UUID id) {
        return livroService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro create(@RequestBody Livro livro) {
        return livroService.create(livro);
    }

    @PutMapping("/{id}")
    public Livro update(@PathVariable UUID id, @RequestBody Livro livro) {
        return livroService.update(id, livro);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        livroService.delete(id);
    }
}
