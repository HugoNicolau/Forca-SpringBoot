package com.jogodaforca.forca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class LetraTentada {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private char letra;
    private boolean acerto;
    
    @ManyToOne
    @JoinColumn(name = "partida_id")
    private Partida partida;
    
    // Construtores
    public LetraTentada() {
    }
    
    public LetraTentada(char letra, boolean acerto) {
        this.letra = Character.toLowerCase(letra);
        this.acerto = acerto;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public char getLetra() {
        return letra;
    }
    
    public void setLetra(char letra) {
        this.letra = Character.toLowerCase(letra);
    }
    
    public boolean isAcerto() {
        return acerto;
    }
    
    public void setAcerto(boolean acerto) {
        this.acerto = acerto;
    }
    
    public Partida getPartida() {
        return partida;
    }
    
    public void setPartida(Partida partida) {
        this.partida = partida;
    }
}