package com.isaque.assistente_pessoal.domain.ai_provider;

import java.util.List;

public record RespostaAssistente(
        String texto,
        List<ChamadaFerramenta> chamadasFerramenta
) {
    public static RespostaAssistente apenasTexto(String texto) {
        return new RespostaAssistente(texto, List.of());
    }

    public boolean solicitouFerramenta() {
        return !chamadasFerramenta.isEmpty();
    }
}