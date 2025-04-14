package com.jogodaforca.forca.dto;

/**
 * DTO para transferência de dados de equipes.
 */
public class EquipeDTO extends BaseDTO {
    private String nome;
    private int pontuacaoTotal;
    private int quantidadeJogadores;
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }
    
    public void setPontuacaoTotal(int pontuacaoTotal) {
        this.pontuacaoTotal = pontuacaoTotal;
    }
    
    public int getQuantidadeJogadores() {
        return quantidadeJogadores;
    }
    
    public void setQuantidadeJogadores(int quantidadeJogadores) {
        this.quantidadeJogadores = quantidadeJogadores;
    }
}