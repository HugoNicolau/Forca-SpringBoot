package com.jogodaforca.forca.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

/**
 * CONCEITO: ABSTRAÇÃO - CLASSE ABSTRATA
 * - Classe base que não pode ser instanciada diretamente
 * - Define estrutura comum a todas as entidades
 * - Força subclasses a herdar comportamentos e atributos comuns
 * 
 * CONCEITO: HERANÇA (GENERALIZAÇÃO)
 * - Classe base para todas as entidades JPA
 * - Fornece atributos comuns como id, timestamps de criação e atualização
 * - Usa @MappedSuperclass para permitir herança em entidades JPA
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;
    
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;
    
    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    
    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
    
    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
    
    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
    
    /**
     * CONCEITO: MÉTODO ABSTRATO
     * - Declara assinatura sem implementação
     * - Força subclasses a fornecerem sua própria implementação
     * - Garante que cada entidade implemente seu próprio método toDTO
     */
    public abstract Object toDTO();
}