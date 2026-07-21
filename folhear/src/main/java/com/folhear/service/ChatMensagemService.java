package com.folhear.service;

import com.folhear.entity.ChatMensagem;
import com.folhear.entity.Usuario;
import com.folhear.exception.ResourceNotFoundException;
import com.folhear.repository.ChatMensagemRepository;
import com.folhear.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMensagemService {

    @Autowired
    private ChatMensagemRepository chatMensagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ChatMensagem enviarMensagem(ChatMensagem mensagem) {
        resolverReferencias(mensagem);
        return chatMensagemRepository.save(mensagem);
    }

    public ChatMensagem findById(Long id) {
        return chatMensagemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mensagem", id));
    }

    public List<ChatMensagem> findAll() {
        return chatMensagemRepository.findAll();
    }

    public ChatMensagem marcarComoLida(Long id) {
        ChatMensagem mensagem = findById(id);
        mensagem.setLida(true);
        mensagem.setDataLeitura(LocalDateTime.now());
        return chatMensagemRepository.save(mensagem);
    }

    public void delete(Long id) {
        findById(id);
        chatMensagemRepository.deleteById(id);
    }

    /**
     * Garante que "remetente" e "destinatario" enviados no JSON (apenas com
     * id) virem referências gerenciadas de verdade, evitando
     * TransientPropertyValueException.
     */
    private void resolverReferencias(ChatMensagem mensagem) {
        if (mensagem.getRemetente() != null && mensagem.getRemetente().getId() != null) {
            Usuario remetenteRef = usuarioRepository.getReferenceById(mensagem.getRemetente().getId());
            mensagem.setRemetente(remetenteRef);
        }
        if (mensagem.getDestinatario() != null && mensagem.getDestinatario().getId() != null) {
            Usuario destinatarioRef = usuarioRepository.getReferenceById(mensagem.getDestinatario().getId());
            mensagem.setDestinatario(destinatarioRef);
        }
    }
}
