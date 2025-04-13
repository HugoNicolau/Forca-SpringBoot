package com.jogodaforca.forca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Palavra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String palavraSecreta;
    private String dica;
    
    // Construtores
    public Palavra() {
    }
    
    public Palavra(String palavraSecreta, String dica) {
        this.palavraSecreta = palavraSecreta.toLowerCase();
        this.dica = dica;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
}