package com.folhear.desktop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Clube {

    private String id;
    private String livroDoMes;
    private String anunciadoEm;
    private List<String> eventos = new ArrayList<>();
    private List<Usuario> membros = new ArrayList<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLivroDoMes() { return livroDoMes; }
    public void setLivroDoMes(String livroDoMes) { this.livroDoMes = livroDoMes; }

    public String getAnunciadoEm() { return anunciadoEm; }
    public void setAnunciadoEm(String anunciadoEm) { this.anunciadoEm = anunciadoEm; }

    public List<String> getEventos() { return eventos; }
    public void setEventos(List<String> eventos) { this.eventos = eventos; }

    public List<Usuario> getMembros() { return membros; }
    public void setMembros(List<Usuario> membros) { this.membros = membros; }

    @Override
    public String toString() {
        return (livroDoMes != null && !livroDoMes.isBlank()) ? "Clube — " + livroDoMes : "Clube de Leitura";
    }
}
