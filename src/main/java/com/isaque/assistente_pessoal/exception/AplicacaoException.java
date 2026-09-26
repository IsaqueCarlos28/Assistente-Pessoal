package com.isaque.assistente_pessoal.exception;

import org.springframework.http.HttpStatus;

/**
 * Classe base para as exceções de negócio da aplicação.
 *
 * Centraliza o padrão de erro: cada subclasse só declara a mensagem
 * e o HttpStatus apropriado; o GlobalExceptionHandler trata qualquer
 * subclasse de forma genérica, sem precisar de um @ExceptionHandler
 * novo a cada exceção criada.
 */
public abstract class AplicacaoException extends RuntimeException {

    protected AplicacaoException(String message) {
        super(message);
    }

    protected AplicacaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus status();
}