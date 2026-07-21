package com.folhear.entity.enums;

public enum TipoObra {
    ROMANCE("Romance"),
    CONTO("Conto"),
    POESIA("Poesia"),
    CRONICA("Crônica"),
    OUTRO("Outro");

    private final String descricao;

    TipoObra(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
