package com.isaque.assistente_pessoal.repository;

import com.isaque.assistente_pessoal.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    // Base da associação Telegram -> Usuario (item 11 da spec).
    Optional<Usuario> findByTelegramChatId(String telegramChatId);
}
