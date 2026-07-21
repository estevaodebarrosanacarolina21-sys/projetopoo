package com.folhear.exception;

/**
 * Lançada pela camada de serviço quando uma regra de negócio é violada
 * (ex: estado inválido, dado duplicado, operação não permitida).
 * Traduzida para HTTP 400 pelo GlobalExceptionHandler.
 */
public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String mensagem) {
        super(mensagem);
    }
}
