package com.folhear.desktop.api;

import com.folhear.desktop.model.Usuario;

/**
 * Guarda o estado da sessão atual: usuário logado e cliente de API
 * compartilhado por todas as telas.
 */
public class Session {

    private static final Session INSTANCE = new Session();

    public static Session get() {
        return INSTANCE;
    }

    public static final String DEFAULT_BASE_URL = "http://localhost:8080/api";

    private final ApiClient apiClient = new ApiClient(DEFAULT_BASE_URL);
    private Usuario usuarioLogado;

    private Session() {}

    public ApiClient api() {
        return apiClient;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public boolean isLogado() {
        return usuarioLogado != null;
    }

    public void logout() {
        this.usuarioLogado = null;
    }
}
