package com.isaque.assistente_pessoal.domain.mensagem;

import com.isaque.assistente_pessoal.domain.conversa.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MensagemRepository extends JpaRepository<Mensagem, UUID> {

    // Casa com o índice (conversa_id, criado_em ASC) criado no SQL.
    List<Mensagem> findByConversaOrderByCriadoEmAsc(Conversa conversa);
}
