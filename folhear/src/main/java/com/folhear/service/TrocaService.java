package com.folhear.service;

import com.folhear.entity.PontoEncontro;
import com.folhear.entity.Troca;
import com.folhear.entity.Usuario;
import com.folhear.exception.ResourceNotFoundException;
import com.folhear.repository.PontoEncontroRepository;
import com.folhear.repository.TrocaRepository;
import com.folhear.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrocaService {

    @Autowired
    private TrocaRepository trocaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PontoEncontroRepository pontoEncontroRepository;

    public Troca create(Troca troca) {
        resolverReferencias(troca);
        return trocaRepository.save(troca);
    }

    public Troca findById(UUID id) {
        return trocaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Troca", id));
    }

    public List<Troca> findAll() {
        return trocaRepository.findAll();
    }

    public Troca update(UUID id, Troca dadosAtualizados) {
        findById(id);
        dadosAtualizados.setId(id);
        resolverReferencias(dadosAtualizados);
        return trocaRepository.save(dadosAtualizados);
    }

    public void delete(UUID id) {
        findById(id);
        trocaRepository.deleteById(id);
    }

    /**
     * Garante que "proponente", "receptor" e "pontoEncontro" enviados no JSON
     * (apenas com id) virem referências gerenciadas de verdade, evitando
     * TransientPropertyValueException.
     */
    private void resolverReferencias(Troca troca) {
        if (troca.getProponente() != null && troca.getProponente().getId() != null) {
            Usuario proponenteRef = usuarioRepository.getReferenceById(troca.getProponente().getId());
            troca.setProponente(proponenteRef);
        }
        if (troca.getReceptor() != null && troca.getReceptor().getId() != null) {
            Usuario receptorRef = usuarioRepository.getReferenceById(troca.getReceptor().getId());
            troca.setReceptor(receptorRef);
        }
        if (troca.getPontoEncontro() != null && troca.getPontoEncontro().getId() != null) {
            PontoEncontro pontoRef = pontoEncontroRepository.getReferenceById(troca.getPontoEncontro().getId());
            troca.setPontoEncontro(pontoRef);
        }
    }
}
