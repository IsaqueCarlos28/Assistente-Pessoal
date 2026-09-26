package com.isaque.assistente_pessoal.domain.ai_provider;

import java.util.List;

public interface AiService {

    /**
     * Gera a próxima resposta do assistente a partir do histórico de
     * mensagens da conversa (a mensagem mais recente do usuário já
     * incluída no final da lista).
     *
     * @throws AiServiceException se o provedor de IA falhar (timeout,
     *         resposta malformada, indisponibilidade, etc.)
     */
    RespostaAssistente gerarResposta(List<MensagemIA> historico);
}