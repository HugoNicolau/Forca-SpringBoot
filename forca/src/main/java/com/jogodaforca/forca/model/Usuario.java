package com.jogodaforca.forca.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String login;
    private String senha;
    private int vitorias;
    private int derrotas;
    
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Partida> partidas = new ArrayList<>();
    
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
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public int getVitorias() {
        return vitorias;
    }
    
    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }
    
    public int getDerrotas() {
        return derrotas;
    }
    
    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }
    
    public List<Partida> getPartidas() {
        return partidas;
    }
    
    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }
    
    // Método para calcular taxa de vitórias
    public double getWinRate() {
        int total = vitorias + derrotas;
        return total == 0 ? 0 : (double) vitorias / total;
    }
}
