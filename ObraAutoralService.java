package com.folhear.service;

import com.folhear.entity.ObraAutoral;
import com.folhear.entity.Usuario;
import com.folhear.exception.ResourceNotFoundException;
import com.folhear.repository.ObraAutoralRepository;
import com.folhear.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ObraAutoralService {

    @Autowired
    private ObraAutoralRepository obraAutoralRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ObraAutoral create(ObraAutoral obra) {
        resolverReferencias(obra);
        return obraAutoralRepository.save(obra);
    }

    public ObraAutoral findById(UUID id) {
        return obraAutoralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Obra autoral", id));
    }

    public List<ObraAutoral> findAll() {
        return obraAutoralRepository.findAll();
    }

    public ObraAutoral update(UUID id, ObraAutoral dadosAtualizados) {
        findById(id);
        dadosAtualizados.setId(id);
        resolverReferencias(dadosAtualizados);
        return obraAutoralRepository.save(dadosAtualizados);
    }

    public void delete(UUID id) {
        findById(id);
        obraAutoralRepository.deleteById(id);
    }

    /**
     * Garante que "autor" enviado no JSON (apenas com id) vire uma
     * referência gerenciada de verdade, evitando TransientPropertyValueException.
     */
    private void resolverReferencias(ObraAutoral obra) {
        if (obra.getAutor() != null && obra.getAutor().getId() != null) {
            Usuario autorRef = usuarioRepository.getReferenceById(obra.getAutor().getId());
            obra.setAutor(autorRef);
        }
    }
}
