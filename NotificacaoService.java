package com.folhear.service;

import com.folhear.entity.Notificacao;
import com.folhear.entity.Usuario;
import com.folhear.exception.ResourceNotFoundException;
import com.folhear.repository.NotificacaoRepository;
import com.folhear.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Notificacao create(Notificacao notificacao) {
        resolverReferencias(notificacao);
        return notificacaoRepository.save(notificacao);
    }

    public Notificacao findById(Long id) {
        return notificacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificação", id));
    }

    public List<Notificacao> findAll() {
        return notificacaoRepository.findAll();
    }

    public Notificacao marcarComoLida(Long id) {
        Notificacao notificacao = findById(id);
        notificacao.setLida(true);
        notificacao.setDataLeitura(LocalDateTime.now());
        return notificacaoRepository.save(notificacao);
    }

    public void delete(Long id) {
        findById(id);
        notificacaoRepository.deleteById(id);
    }

    /**
     * Garante que "usuario" enviado no JSON (apenas com id) vire uma
     * referência gerenciada de verdade, evitando TransientPropertyValueException.
     */
    private void resolverReferencias(Notificacao notificacao) {
        if (notificacao.getUsuario() != null && notificacao.getUsuario().getId() != null) {
            Usuario usuarioRef = usuarioRepository.getReferenceById(notificacao.getUsuario().getId());
            notificacao.setUsuario(usuarioRef);
        }
    }
}
