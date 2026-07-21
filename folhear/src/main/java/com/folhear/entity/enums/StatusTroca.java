package com.folhear.entity.enums;

public enum StatusTroca {
    PROPOSTA("Proposta"),
    ACEITA("Aceita"),
    RECUSADA("Recusada"),
    CONTRAPROPOSTA("Contraproposta"),
    CONCLUIDA("Concluída");

    private final String descricao;

    StatusTroca(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
