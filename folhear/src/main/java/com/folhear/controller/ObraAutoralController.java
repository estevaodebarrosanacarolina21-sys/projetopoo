package com.folhear.controller;

import com.folhear.entity.ObraAutoral;
import com.folhear.service.ObraAutoralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/obras")
public class ObraAutoralController {

    @Autowired
    private ObraAutoralService obraAutoralService;

    @GetMapping
    public List<ObraAutoral> findAll() {
        return obraAutoralService.findAll();
    }

    @GetMapping("/{id}")
    public ObraAutoral findById(@PathVariable UUID id) {
        return obraAutoralService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ObraAutoral create(@RequestBody ObraAutoral obra) {
        return obraAutoralService.create(obra);
    }

    @PutMapping("/{id}")
    public ObraAutoral update(@PathVariable UUID id, @RequestBody ObraAutoral obra) {
        return obraAutoralService.update(id, obra);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        obraAutoralService.delete(id);
    }
}
