package com.folhear.service;

import com.folhear.entity.Clube;
import com.folhear.entity.Usuario;
import com.folhear.exception.ResourceNotFoundException;
import com.folhear.repository.ClubeRepository;
import com.folhear.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClubeService {

    @Autowired
    private ClubeRepository clubeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Clube create(Clube clube) {
        resolverReferencias(clube);
        return clubeRepository.save(clube);
    }

    public Clube findById(UUID id) {
        return clubeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clube", id));
    }

    public List<Clube> findAll() {
        return clubeRepository.findAll();
    }

    public Clube update(UUID id, Clube dadosAtualizados) {
        findById(id);
        dadosAtualizados.setId(id);
        resolverReferencias(dadosAtualizados);
        return clubeRepository.save(dadosAtualizados);
    }

    public void delete(UUID id) {
        findById(id);
        clubeRepository.deleteById(id);
    }

    public Clube adicionarMembro(UUID clubeId, UUID usuarioId) {
        Clube clube = findById(clubeId);
        // valida que o usuário existe antes de vinculá-lo ao clube
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuário", usuarioId);
        }
        Usuario usuarioRef = usuarioRepository.getReferenceById(usuarioId);
        clube.getMembros().add(usuarioRef);
        return clubeRepository.save(clube);
    }

    /**
     * Garante que os membros enviados no JSON (apenas com id) virem
     * referências gerenciadas de verdade, evitando TransientPropertyValueException.
     */
    private void resolverReferencias(Clube clube) {
        if (clube.getMembros() != null && !clube.getMembros().isEmpty()) {
            List<Usuario> membrosResolvidos = new ArrayList<>();
            for (Usuario membro : clube.getMembros()) {
                if (membro.getId() != null) {
                    membrosResolvidos.add(usuarioRepository.getReferenceById(membro.getId()));
                }
            }
            clube.setMembros(membrosResolvidos);
        }
    }
}
