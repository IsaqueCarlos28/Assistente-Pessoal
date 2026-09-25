package com.isaque.assistente_pessoal.domain.usuario;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    // Identificação do chat no Telegram. String porque é um
    // identificador, não um valor usado em cálculo.
    @Column(name = "telegram_chat_id", nullable = false, unique = true)
    private String telegramChatId;

    // Fixo em America/Sao_Paulo por enquanto — sem seleção dinâmica
    // ainda, mas já mantido como campo próprio do usuário (item 9 da
    // spec: timezone nunca deve ser assumido a partir do servidor).
    @Column(nullable = false)
    private String timezone = "America/Sao_Paulo";

    @Column(name = "criado_em", nullable = false, updatable = false)
    private ZonedDateTime criadoEm;

    protected Usuario() {
        // exigido pelo JPA
    }

    public Usuario(String telegramChatId) {
        this.id = UuidCreator.getTimeOrderedEpoch(); // UUIDv7
        this.telegramChatId = telegramChatId;
    }

    @PrePersist
    protected void onCreate() {
        this.criadoEm = ZonedDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getTelegramChatId() {
        return telegramChatId;
    }

    public void setTelegramChatId(String telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public ZonedDateTime getCriadoEm() {
        return criadoEm;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", telegramChatId='" + telegramChatId + '\'' +
                ", timezone='" + timezone + '\'' +
                ", criadoEm=" + criadoEm +
                '}';
    }
}