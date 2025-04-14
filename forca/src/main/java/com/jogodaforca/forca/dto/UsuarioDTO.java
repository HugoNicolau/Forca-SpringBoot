package com.jogodaforca.forca.dto;

/**
 * DTO para transferência de dados do usuário.
 * 
 * CONCEITO: DATA TRANSFER OBJECT (PADRÃO DE PROJETO)
 * - Objeto usado para transferir dados entre subsistemas
 * - Separa a representação de dados da lógica de negócios
 */
public class UsuarioDTO extends BaseDTO {
    private String nome;
    private String login;
    private int vitorias;
    private int derrotas;
    private double winRate;
    private int totalPartidas;
    
    // Getters e Setters
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
    
    public double getWinRate() {
        return winRate;
    }
    
    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }
    
    public int getTotalPartidas() {
        return totalPartidas;
    }
    
    public void setTotalPartidas(int totalPartidas) {
        this.totalPartidas = totalPartidas;
    }
}