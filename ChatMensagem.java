package com.folhear.desktop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMensagem {

    private Long id;
    private Usuario remetente;
    private Usuario destinatario;
    private String conteudo;
    private String dataEnvio;
    private Boolean lida = false;
    private String dataLeitura;
    private Long trocaId;
    private Long transacaoId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getRemetente() { return remetente; }
    public void setRemetente(Usuario remetente) { this.remetente = remetente; }

    public Usuario getDestinatario() { return destinatario; }
    public void setDestinatario(Usuario destinatario) { this.destinatario = destinatario; }

    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    public String getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(String dataEnvio) { this.dataEnvio = dataEnvio; }

    public Boolean getLida() { return lida; }
    public void setLida(Boolean lida) { this.lida = lida; }

    public String getDataLeitura() { return dataLeitura; }
    public void setDataLeitura(String dataLeitura) { this.dataLeitura = dataLeitura; }

    public Long getTrocaId() { return trocaId; }
    public void setTrocaId(Long trocaId) { this.trocaId = trocaId; }

    public Long getTransacaoId() { return transacaoId; }
    public void setTransacaoId(Long transacaoId) { this.transacaoId = transacaoId; }
}
