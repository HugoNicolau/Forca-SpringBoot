package com.jogodaforca.forca.dto;

import com.jogodaforca.forca.model.Usuario;

public class RankingDTO {
    private Long id;
    private String nome;
    private int vitorias;
    private int derrotas;
    private int totalPartidas;
    private double winRate;
    
    public static RankingDTO fromUsuario(Usuario usuario) {
        RankingDTO dto = new RankingDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setVitorias(usuario.getVitorias());
        dto.setDerrotas(usuario.getDerrotas());
        dto.setTotalPartidas(usuario.getTotalPartidas());
        dto.setWinRate(usuario.calcularWinRate());
        return dto;
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
    
    public int getTotalPartidas() {
        return totalPartidas;
    }
    
    public void setTotalPartidas(int totalPartidas) {
        this.totalPartidas = totalPartidas;
    }
    
    public double getWinRate() {
        return winRate;
    }
    
    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }
}