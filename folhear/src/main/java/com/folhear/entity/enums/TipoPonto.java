package com.folhear.entity.enums;

public enum TipoPonto {
    BIBLIOTECA("Biblioteca"),
    LIVRARIA("Livraria"),
    CAFE("Café"),
    OUTRO("Outro");

    private final String descricao;

    TipoPonto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
