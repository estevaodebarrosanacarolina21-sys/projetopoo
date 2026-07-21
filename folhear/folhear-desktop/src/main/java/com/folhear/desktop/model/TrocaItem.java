package com.folhear.desktop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrocaItem {

    private String id;
    private Livro livro;
    private String lado; // "O" = Oferecido, "S" = Solicitado

    public TrocaItem() {}

    public TrocaItem(Livro livro, String lado) {
        this.livro = livro;
        this.lado = lado;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    public String getLado() { return lado; }
    public void setLado(String lado) { this.lado = lado; }
}
