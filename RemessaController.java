package com.folhear.controller;

import com.folhear.entity.Remessa;
import com.folhear.service.RemessaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/remessas")
public class RemessaController {

    @Autowired
    private RemessaService remessaService;

    @GetMapping
    public List<Remessa> findAll() {
        return remessaService.findAll();
    }

    @GetMapping("/{id}")
    public Remessa findById(@PathVariable Long id) {
        return remessaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Remessa create(@RequestBody Remessa remessa) {
        return remessaService.create(remessa);
    }

    @PutMapping("/{id}")
    public Remessa update(@PathVariable Long id, @RequestBody Remessa remessa) {
        return remessaService.update(id, remessa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        remessaService.delete(id);
    }
}
