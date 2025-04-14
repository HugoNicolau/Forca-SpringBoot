package com.jogodaforca.forca.model;

import com.jogodaforca.forca.dto.PalavraDTO;

import jakarta.persistence.Entity;

/**
 * Classe que representa uma palavra do jogo da forca.
 * 
 * CONCEITO: HERANÇA (ESPECIALIZAÇÃO)
 * - Palavra estende BaseEntity, herdando atributos e comportamentos comuns a todas as entidades
 * - Exemplo de especialização, onde a subclasse adiciona atributos e comportamentos específicos
 */
@Entity
public class Palavra extends BaseEntity {
    
    private String palavraSecreta;
    private String dica;
    
    /**
     * CONCEITO: CONSTRUTOR PADRÃO
     * - Necessário para o JPA criar instâncias da entidade
     */
    public Palavra() {
    }
    
    /**
     * CONCEITO: SOBRECARGA DE CONSTRUTORES
     * - Permite inicializar o objeto de diferentes maneiras
     */
    public Palavra(String palavraSecreta, String dica) {
        this.palavraSecreta = palavraSecreta.toLowerCase();
        this.dica = dica;
    }
    
    // Getters e Setters
    
    public String getPalavraSecreta() {
        return palavraSecreta;
    }
    
    public void setPalavraSecreta(String palavraSecreta) {
        this.palavraSecreta = palavraSecreta.toLowerCase();
    }
    
    public String getDica() {
        return dica;
    }
    
    public void setDica(String dica) {
        this.dica = dica;
    }
    
    /**
     * CONCEITO: IMPLEMENTAÇÃO DE MÉTODO ABSTRATO
     * - Implementação do método abstrato definido na classe BaseEntity
     * - Converte a entidade Palavra para um objeto DTO
     */
    @Override
    public Object toDTO() {
        PalavraDTO dto = new PalavraDTO();
        dto.setId(this.getId());
        dto.setPalavraSecreta(this.palavraSecreta);
        dto.setDica(this.dica);
        return dto;
    }
    
    @Override
    public String toString() {
        return "Palavra [id=" + getId() + ", palavraSecreta=" + palavraSecreta + ", dica=" + dica + "]";
    }
}