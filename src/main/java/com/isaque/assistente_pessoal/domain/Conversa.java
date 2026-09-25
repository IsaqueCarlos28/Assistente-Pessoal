package com.isaque.assistente_pessoal.domain;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "conversa")
public class Conversa {

    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private EstadoConversa estado;

    // Guarda a operação aguardando confirmação (ex.: JSON serializado
    // com o tipo de ação e os argumentos), enquanto o estado for
    // WAITING_CONFIRMATION. Representação simples por enquanto;
    // pode evoluir para uma entidade própria depois.
    @Column(name = "operacao_pendente", columnDefinition = "TEXT")
    private String operacaoPendente;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private ZonedDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private ZonedDateTime atualizadoEm;

    protected Conversa() {
        // exigido pelo JPA
    }

    public Conversa(Usuario usuario) {
        this.id = UuidCreator.getTimeOrderedEpoch(); // UUIDv7
        this.usuario = usuario;
        this.estado = EstadoConversa.IDLE;
    }

    @PrePersist
    protected void onCreate() {
        ZonedDateTime agora = ZonedDateTime.now();
        this.criadoEm = agora;
        this.atualizadoEm = agora;
    }

    @PreUpdate
    protected void onUpdate() {
        this.atualizadoEm = ZonedDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public EstadoConversa getEstado() {
        return estado;
    }

    public void setEstado(EstadoConversa estado) {
        this.estado = estado;
    }

    public String getOperacaoPendente() {
        return operacaoPendente;
    }

    public void setOperacaoPendente(String operacaoPendente) {
        this.operacaoPendente = operacaoPendente;
    }

    public ZonedDateTime getCriadoEm() {
        return criadoEm;
    }

    public ZonedDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
}
