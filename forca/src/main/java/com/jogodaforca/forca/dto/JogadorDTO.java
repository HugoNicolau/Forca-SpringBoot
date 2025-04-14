package com.jogodaforca.forca.dto;

/**
 * DTO para transferência de dados de jogadores.
 * 
 * CONCEITO: DATA TRANSFER OBJECT (PADRÃO DE PROJETO)
 * - Objeto usado para transferir dados entre subsistemas
 * - Contém apenas os dados necessários para a camada de apresentação
 */
public class JogadorDTO {
    private Long id;
    private String nome;
    private String tipo;
    
    /**
     * CONCEITO: CONSTRUTOR PADRÃO
     * - Necessário para deserialização JSON
     */
    public JogadorDTO() {
    }
    
    // Getters e Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}