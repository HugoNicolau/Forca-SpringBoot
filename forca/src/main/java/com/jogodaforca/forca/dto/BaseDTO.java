package com.jogodaforca.forca.dto;

import java.time.LocalDateTime;

/**
 * CONCEITO: HERANÇA (GENERALIZAÇÃO)
 * - Classe base abstrata que define comportamentos e atributos comuns a todos os DTOs
 * - Exemplo de generalização, onde características comuns são elevadas para uma superclasse
 */
public abstract class BaseDTO {
    private Long id;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    
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
}