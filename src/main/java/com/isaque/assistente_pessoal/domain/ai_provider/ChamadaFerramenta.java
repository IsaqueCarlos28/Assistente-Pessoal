package com.isaque.assistente_pessoal.domain.ai_provider;

import java.util.Map;

public record ChamadaFerramenta(
        String id,
        String nome,
        Map<String, Object> argumentos
) {
}