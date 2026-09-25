package com.isaque.assistente_pessoal.domain.mensagem;

import com.github.f4b6a3.uuid.UuidCreator;
import com.isaque.assistente_pessoal.domain.conversa.Conversa;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "mensagem")
public class Mensagem {

    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "conversa_id", nullable = false)
    private Conversa conversa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private RemetenteTipo remetente;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private ZonedDateTime criadoEm;

    protected Mensagem() {
        // exigido pelo JPA
    }

    public Mensagem(Conversa conversa, RemetenteTipo remetente, String conteudo) {
        this.id = UuidCreator.getTimeOrderedEpoch(); // UUIDv7
        this.conversa = conversa;
        this.remetente = remetente;
        this.conteudo = conteudo;
    }

    @PrePersist
    protected void onCreate() {
        this.criadoEm = ZonedDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public Conversa getConversa() {
        return conversa;
    }

    public RemetenteTipo getRemetente() {
        return remetente;
    }

    public String getConteudo() {
        return conteudo;
    }

    public ZonedDateTime getCriadoEm() {
        return criadoEm;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mensagem other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Mensagem{" +
                "id=" + id +
                ", conversaId=" + (conversa != null ? conversa.getId() : null) +
                ", remetente=" + remetente +
                ", criadoEm=" + criadoEm +
                '}';
    }
}
