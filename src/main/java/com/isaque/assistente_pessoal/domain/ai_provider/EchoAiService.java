package com.isaque.assistente_pessoal.domain.ai_provider;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(prefix = "ai", name = "provider", havingValue = "echo", matchIfMissing = true)
public class EchoAiService implements AiService {

    @Override
    public RespostaAssistente gerarResposta(List<MensagemIA> historico) {
        String ultimaMensagemUsuario = historico.stream()
                .filter(m -> m.papel() == PapelMensagem.USUARIO)
                .reduce((primeira, ultima) -> ultima)
                .map(MensagemIA::conteudo)
                .orElse("");

        return RespostaAssistente.apenasTexto("Recebi sua mensagem: " + ultimaMensagemUsuario);
    }
}