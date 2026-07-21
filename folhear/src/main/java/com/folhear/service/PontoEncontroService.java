package com.folhear.service;

import com.folhear.entity.PontoEncontro;
import com.folhear.exception.ResourceNotFoundException;
import com.folhear.repository.PontoEncontroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PontoEncontroService {

    @Autowired
    private PontoEncontroRepository pontoEncontroRepository;

    public PontoEncontro create(PontoEncontro ponto) {
        return pontoEncontroRepository.save(ponto);
    }

    public PontoEncontro findById(UUID id) {
        return pontoEncontroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de encontro", id));
    }

    public List<PontoEncontro> findAll() {
        return pontoEncontroRepository.findAll();
    }

    public PontoEncontro update(UUID id, PontoEncontro dadosAtualizados) {
        findById(id);
        dadosAtualizados.setId(id);
        return pontoEncontroRepository.save(dadosAtualizados);
    }

    public void delete(UUID id) {
        findById(id);
        pontoEncontroRepository.deleteById(id);
    }
}
