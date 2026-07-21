package com.folhear.desktop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notificacao {

    private Long id;
    private Usuario usuario;
    private String tipo; // TROCA, VENDA, CHAT, SISTEMA, CLUBE
    private String mensagem;
    private Boolean lida = false;
    private String dataCriacao;
    private String dataLeitura;
    private Long trocaId;
    private Long transacaoId;
    private Long chatMensagemId;
    private Long clubeId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public Boolean getLida() { return lida; }
    public void setLida(Boolean lida) { this.lida = lida; }

    public String getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(String dataCriacao) { this.dataCriacao = dataCriacao; }

    public String getDataLeitura() { return dataLeitura; }
    public void setDataLeitura(String dataLeitura) { this.dataLeitura = dataLeitura; }

    public Long getTrocaId() { return trocaId; }
    public void setTrocaId(Long trocaId) { this.trocaId = trocaId; }

    public Long getTransacaoId() { return transacaoId; }
    public void setTransacaoId(Long transacaoId) { this.transacaoId = transacaoId; }

    public Long getChatMensagemId() { return chatMensagemId; }
    public void setChatMensagemId(Long chatMensagemId) { this.chatMensagemId = chatMensagemId; }

    public Long getClubeId() { return clubeId; }
    public void setClubeId(Long clubeId) { this.clubeId = clubeId; }
}
