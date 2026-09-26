package com.isaque.assistente_pessoal.domain.ai_provider;

import com.isaque.assistente_pessoal.exception.AplicacaoException;
import org.springframework.http.HttpStatus;

public class AiServiceException extends AplicacaoException {

    public AiServiceException(String message) {
        super(message);
    }

    public AiServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus status() {
        // BAD_GATEWAY: a aplicação está íntegra, quem falhou foi o
        // provedor de IA externo (Ollama/Gemini) — sinaliza como
        // falha de "upstream", não erro interno nosso.
        return HttpStatus.BAD_GATEWAY;
    }
}