package com.folhear.entity.enums;

public enum TipoAvaliacao {
    LIVRO("Livro"),
    USUARIO("Usuário"),
    PONTO_ENCONTRO("Ponto de Encontro");

    private final String descricao;

    TipoAvaliacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
