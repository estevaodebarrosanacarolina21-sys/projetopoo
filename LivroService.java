package com.folhear.service;

import com.folhear.entity.Livro;
import com.folhear.entity.Usuario;
import com.folhear.exception.ResourceNotFoundException;
import com.folhear.repository.LivroRepository;
import com.folhear.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Livro create(Livro livro) {
        resolverReferencias(livro);
        return livroRepository.save(livro);
    }

    public Livro findById(UUID id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro", id));
    }

    public List<Livro> findAll() {
        return livroRepository.findAll();
    }

    public Livro update(UUID id, Livro dadosAtualizados) {
        findById(id);
        dadosAtualizados.setId(id);
        resolverReferencias(dadosAtualizados);
        return livroRepository.save(dadosAtualizados);
    }

    public void delete(UUID id) {
        findById(id);
        livroRepository.deleteById(id);
    }

    /**
     * Garante que o "vendedor" enviado no JSON (apenas com id) vire uma
     * referência gerenciada de verdade, evitando TransientPropertyValueException.
     */
    private void resolverReferencias(Livro livro) {
        if (livro.getVendedor() != null && livro.getVendedor().getId() != null) {
            Usuario vendedorRef = usuarioRepository.getReferenceById(livro.getVendedor().getId());
            livro.setVendedor(vendedorRef);
        }
    }
}
