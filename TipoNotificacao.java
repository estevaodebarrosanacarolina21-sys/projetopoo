package com.folhear.entity.enums;

public enum TipoNotificacao {
    TROCA("Troca"),
    VENDA("Venda"),
    CHAT("Chat"),
    SISTEMA("Sistema"),
    CLUBE("Clube");

    private final String descricao;

    TipoNotificacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
