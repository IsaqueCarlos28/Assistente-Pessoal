package com.isaque.assistente_pessoal.domain;

/**
 * Estado da conversa entre o usuário e o assistente.
 * Usado principalmente para controlar fluxos de confirmação
 * (ex.: "Deseja realmente excluir?").
 */
public enum EstadoConversa {
    IDLE,
    WAITING_FOR_INPUT,
    WAITING_CONFIRMATION,
    PROCESSING,
    COMPLETED,
    FAILED
}
