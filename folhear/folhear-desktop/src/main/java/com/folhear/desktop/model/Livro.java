package com.folhear.desktop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Livro {

    public static final String[] ESTADOS = {"OTIMO", "BOM", "REGULAR", "USADO"};
    public static final String[] TIPOS_ANUNCIO = {"VENDA", "TROCA", "AMBOS"};

    private String id;
    private String titulo;
    private String autor;
    private String isbn;
    private String categoria;
    private String estado;       // OTIMO, BOM, REGULAR, USADO
    private String tipoAnuncio;  // VENDA, TROCA, AMBOS
    private Float preco;
    private Boolean ativo = true;
    private String expiracaoEm;
    private Usuario vendedor;
    private String criadoEm;
    private List<String> fotos = new ArrayList<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getTipoAnuncio() { return tipoAnuncio; }
    public void setTipoAnuncio(String tipoAnuncio) { this.tipoAnuncio = tipoAnuncio; }

    public Float getPreco() { return preco; }
    public void setPreco(Float preco) { this.preco = preco; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public String getExpiracaoEm() { return expiracaoEm; }
    public void setExpiracaoEm(String expiracaoEm) { this.expiracaoEm = expiracaoEm; }

    public Usuario getVendedor() { return vendedor; }
    public void setVendedor(Usuario vendedor) { this.vendedor = vendedor; }

    public String getCriadoEm() { return criadoEm; }
    public void setCriadoEm(String criadoEm) { this.criadoEm = criadoEm; }

    public List<String> getFotos() { return fotos; }
    public void setFotos(List<String> fotos) { this.fotos = fotos; }

    @Override
    public String toString() {
        return titulo + " — " + autor;
    }
}
