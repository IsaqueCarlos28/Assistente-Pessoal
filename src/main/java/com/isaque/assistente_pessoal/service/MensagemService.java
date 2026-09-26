package com.isaque.assistente_pessoal.service;

import com.isaque.assistente_pessoal.domain.ai_provider.AiService;
import com.isaque.assistente_pessoal.domain.ai_provider.AiServiceException;
import com.isaque.assistente_pessoal.domain.ai_provider.MensagemIA;
import com.isaque.assistente_pessoal.domain.ai_provider.PapelMensagem;
import com.isaque.assistente_pessoal.domain.conversa.Conversa;
import com.isaque.assistente_pessoal.domain.conversa.ConversaRepository;
import com.isaque.assistente_pessoal.domain.mensagem.Mensagem;
import com.isaque.assistente_pessoal.domain.mensagem.MensagemRepository;
import com.isaque.assistente_pessoal.domain.mensagem.RemetenteTipo;
import com.isaque.assistente_pessoal.domain.usuario.Usuario;
import com.isaque.assistente_pessoal.domain.usuario.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MensagemService {

    private static final Logger log = LoggerFactory.getLogger(MensagemService.class);

    private final UsuarioRepository usuarioRepository;
    private final ConversaRepository conversaRepository;
    private final MensagemRepository mensagemRepository;
    private final AiService aiService;

    public MensagemService(UsuarioRepository usuarioRepository,
                           ConversaRepository conversaRepository,
                           MensagemRepository mensagemRepository,
                           AiService aiService) {
        this.usuarioRepository = usuarioRepository;
        this.conversaRepository = conversaRepository;
        this.mensagemRepository = mensagemRepository;
        this.aiService = aiService;
    }

    @Transactional
    public String processarMensagemRecebida(String identificadorExterno, String texto) {
        Usuario usuario = usuarioRepository.findByTelegramChatId(identificadorExterno)
                .orElseGet(() -> usuarioRepository.save(new Usuario(identificadorExterno)));

        Conversa conversa = conversaRepository.findFirstByUsuarioOrderByCriadoEmDesc(usuario)
                .orElseGet(() -> conversaRepository.save(new Conversa(usuario)));

        mensagemRepository.save(new Mensagem(conversa, RemetenteTipo.USUARIO, texto));

        List<MensagemIA> historico = mensagemRepository.findByConversaOrderByCriadoEmAsc(conversa).stream()
                .map(this::paraMensagemIA)
                .toList();

        String resposta;
        try {
            resposta = aiService.gerarResposta(historico).texto();
        } catch (AiServiceException e) {
            log.error("Falha ao gerar resposta com o provedor de IA", e);
            resposta = "Desculpe, não consegui processar sua mensagem agora. Tente novamente em instantes.";
        }

        mensagemRepository.save(new Mensagem(conversa, RemetenteTipo.ASSISTENTE, resposta));

        return resposta;
    }

    private MensagemIA paraMensagemIA(Mensagem mensagem) {
        PapelMensagem papel = mensagem.getRemetente() == RemetenteTipo.USUARIO
                ? PapelMensagem.USUARIO
                : PapelMensagem.ASSISTENTE;
        return new MensagemIA(papel, mensagem.getConteudo());
    }
}