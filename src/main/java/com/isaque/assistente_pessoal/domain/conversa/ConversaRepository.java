package com.isaque.assistente_pessoal.domain.conversa;

import com.isaque.assistente_pessoal.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConversaRepository extends JpaRepository<Conversa, UUID> {

    // Retorna a conversa mais recente do usuário — usada para
    // recuperar o estado (ex.: WAITING_CONFIRMATION) entre mensagens.
    // Casa com o índice (usuario_id, criado_em DESC) criado no SQL.
    Optional<Conversa> findFirstByUsuarioOrderByCriadoEmDesc(Usuario usuario);
}
