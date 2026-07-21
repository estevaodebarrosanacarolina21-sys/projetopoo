package com.folhear.exception;

/**
 * Lançada pela camada de serviço quando um recurso solicitado não existe.
 * Traduzida para HTTP 404 pelo GlobalExceptionHandler.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }

    public ResourceNotFoundException(String recurso, Object id) {
        super(recurso + " não encontrado(a) para o id: " + id);
    }
}
