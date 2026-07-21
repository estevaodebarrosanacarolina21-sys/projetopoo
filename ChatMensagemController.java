package com.folhear.controller;

import com.folhear.entity.ChatMensagem;
import com.folhear.service.ChatMensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagens")
public class ChatMensagemController {

    @Autowired
    private ChatMensagemService chatMensagemService;

    @GetMapping
    public List<ChatMensagem> findAll() {
        return chatMensagemService.findAll();
    }

    @GetMapping("/{id}")
    public ChatMensagem findById(@PathVariable Long id) {
        return chatMensagemService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChatMensagem enviar(@RequestBody ChatMensagem mensagem) {
        return chatMensagemService.enviarMensagem(mensagem);
    }

    @PatchMapping("/{id}/lida")
    public ChatMensagem marcarComoLida(@PathVariable Long id) {
        return chatMensagemService.marcarComoLida(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        chatMensagemService.delete(id);
    }
}
