package com.folhear.service;

import com.folhear.entity.Livro;
import com.folhear.entity.Transacao;
import com.folhear.entity.Usuario;
import com.folhear.exception.ResourceNotFoundException;
import com.folhear.repository.LivroRepository;
import com.folhear.repository.TransacaoRepository;
import com.folhear.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Transacao create(Transacao transacao) {
        resolverReferencias(transacao);
        return transacaoRepository.save(transacao);
    }

    public Transacao findById(UUID id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transação", id));
    }

    public List<Transacao> findAll() {
        return transacaoRepository.findAll();
    }

    public Transacao update(UUID id, Transacao dadosAtualizados) {
        findById(id);
        dadosAtualizados.setId(id);
        resolverReferencias(dadosAtualizados);
        return transacaoRepository.save(dadosAtualizados);
    }

    public void delete(UUID id) {
        findById(id);
        transacaoRepository.deleteById(id);
    }

    /**
     * Garante que "livro", "comprador" e "vendedor" enviados no JSON (apenas
     * com id) virem referências gerenciadas de verdade, evitando
     * TransientPropertyValueException.
     */
    private void resolverReferencias(Transacao transacao) {
        if (transacao.getLivro() != null && transacao.getLivro().getId() != null) {
            Livro livroRef = livroRepository.getReferenceById(transacao.getLivro().getId());
            transacao.setLivro(livroRef);
        }
        if (transacao.getComprador() != null && transacao.getComprador().getId() != null) {
            Usuario compradorRef = usuarioRepository.getReferenceById(transacao.getComprador().getId());
            transacao.setComprador(compradorRef);
        }
        if (transacao.getVendedor() != null && transacao.getVendedor().getId() != null) {
            Usuario vendedorRef = usuarioRepository.getReferenceById(transacao.getVendedor().getId());
            transacao.setVendedor(vendedorRef);
        }
    }
}
