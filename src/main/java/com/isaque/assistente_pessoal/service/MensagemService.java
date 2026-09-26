package com.isaque.assistente_pessoal.service;

import com.isaque.assistente_pessoal.domain.conversa.Conversa;
import com.isaque.assistente_pessoal.domain.conversa.ConversaRepository;
import com.isaque.assistente_pessoal.domain.mensagem.Mensagem;
import com.isaque.assistente_pessoal.domain.mensagem.MensagemRepository;
import com.isaque.assistente_pessoal.domain.mensagem.RemetenteTipo;
import com.isaque.assistente_pessoal.domain.usuario.Usuario;
import com.isaque.assistente_pessoal.domain.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MensagemService {

    private final UsuarioRepository usuarioRepository;
    private final ConversaRepository conversaRepository;
    private final MensagemRepository mensagemRepository;

    public MensagemService(UsuarioRepository usuarioRepository,
                           ConversaRepository conversaRepository,
                           MensagemRepository mensagemRepository) {
        this.usuarioRepository = usuarioRepository;
        this.conversaRepository = conversaRepository;
        this.mensagemRepository = mensagemRepository;
    }

    @Transactional
    public String processarMensagemRecebida(String identificadorExterno, String texto) {
        Usuario usuario = usuarioRepository.findByTelegramChatId(identificadorExterno)
                .orElseGet(() -> usuarioRepository.save(new Usuario(identificadorExterno)));

        Conversa conversa = conversaRepository.findFirstByUsuarioOrderByCriadoEmDesc(usuario)
                .orElseGet(() -> conversaRepository.save(new Conversa(usuario)));

        mensagemRepository.save(new Mensagem(conversa, RemetenteTipo.USUARIO, texto));

        // Placeholder até a Fase 3/4 (AiService de verdade)
        String resposta = "Recebi sua mensagem: " + texto;

        mensagemRepository.save(new Mensagem(conversa, RemetenteTipo.ASSISTENTE, resposta));

        return resposta;
    }
}
