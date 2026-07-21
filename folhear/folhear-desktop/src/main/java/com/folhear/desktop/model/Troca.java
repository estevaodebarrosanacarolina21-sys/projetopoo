package com.folhear.desktop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Troca {

    public static final String[] STATUS = {"PROPOSTA", "ACEITA", "RECUSADA", "CONTRAPROPOSTA", "CONCLUIDA"};

    private String id;
    private Usuario proponente;
    private Usuario receptor;
    private String dataAgendamento;
    private String qrCodeCheckin;
    private Float complemento = 0f;
    private String status = "PROPOSTA";
    private String criadoEm;
    private List<TrocaItem> itens = new ArrayList<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Usuario getProponente() { return proponente; }
    public void setProponente(Usuario proponente) { this.proponente = proponente; }

    public Usuario getReceptor() { return receptor; }
    public void setReceptor(Usuario receptor) { this.receptor = receptor; }

    public String getDataAgendamento() { return dataAgendamento; }
    public void setDataAgendamento(String dataAgendamento) { this.dataAgendamento = dataAgendamento; }

    public String getQrCodeCheckin() { return qrCodeCheckin; }
    public void setQrCodeCheckin(String qrCodeCheckin) { this.qrCodeCheckin = qrCodeCheckin; }

    public Float getComplemento() { return complemento; }
    public void setComplemento(Float complemento) { this.complemento = complemento; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCriadoEm() { return criadoEm; }
    public void setCriadoEm(String criadoEm) { this.criadoEm = criadoEm; }

    public List<TrocaItem> getItens() { return itens; }
    public void setItens(List<TrocaItem> itens) { this.itens = itens; }
}
