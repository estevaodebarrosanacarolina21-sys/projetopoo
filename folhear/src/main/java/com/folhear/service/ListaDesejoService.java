package com.folhear.service;

import com.folhear.entity.ListaDesejo;
import com.folhear.entity.ListaDesejoItem;
import com.folhear.entity.Livro;
import com.folhear.entity.Usuario;
import com.folhear.exception.ResourceNotFoundException;
import com.folhear.repository.ListaDesejoRepository;
import com.folhear.repository.LivroRepository;
import com.folhear.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListaDesejoService {

    @Autowired
    private ListaDesejoRepository listaDesejoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    public ListaDesejo create(ListaDesejo lista) {
        resolverReferencias(lista);
        return listaDesejoRepository.save(lista);
    }

    public ListaDesejo findById(UUID id) {
        return listaDesejoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista de desejo", id));
    }

    public List<ListaDesejo> findAll() {
        return listaDesejoRepository.findAll();
    }

    public ListaDesejo update(UUID id, ListaDesejo dadosAtualizados) {
        findById(id);
        dadosAtualizados.setId(id);
        resolverReferencias(dadosAtualizados);
        return listaDesejoRepository.save(dadosAtualizados);
    }

    public void delete(UUID id) {
        findById(id);
        listaDesejoRepository.deleteById(id);
    }

    public ListaDesejo adicionarLivro(UUID listaId, UUID livroId) {
        ListaDesejo lista = findById(listaId);
        if (!livroRepository.existsById(livroId)) {
            throw new ResourceNotFoundException("Livro", livroId);
        }
        Livro livroRef = livroRepository.getReferenceById(livroId);
        ListaDesejoItem item = new ListaDesejoItem();
        item.setListaDesejo(lista);
        item.setLivro(livroRef);
        lista.getItens().add(item);
        return listaDesejoRepository.save(lista);
    }

    /**
     * Garante que "usuario" enviado no JSON (apenas com id) vire uma
     * referência gerenciada de verdade, evitando TransientPropertyValueException.
     */
    private void resolverReferencias(ListaDesejo lista) {
        if (lista.getUsuario() != null && lista.getUsuario().getId() != null) {
            Usuario usuarioRef = usuarioRepository.getReferenceById(lista.getUsuario().getId());
            lista.setUsuario(usuarioRef);
        }
    }
}
