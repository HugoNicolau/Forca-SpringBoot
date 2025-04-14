package com.jogodaforca.forca.model;

import jakarta.persistence.MappedSuperclass;

/**
 * CONCEITO: CLASSE ABSTRATA
 * - Define o comportamento comum a todos os tipos de jogador
 * - Não pode ser instanciada diretamente
 * - Serve como base para especialização (tipos específicos de jogador)
 */
@MappedSuperclass
public abstract class Jogador extends BaseEntity {
    
    private String nome;
    
    /**
     * CONCEITO: MÉTODO ABSTRATO
     * - Declara comportamento sem implementação
     * - Força subclasses a fornecerem implementação específica
     * - Cada tipo de jogador escolhe palavra de forma diferente
     */
    public abstract Palavra escolherPalavra();
    
    /**
     * Calcula pontuação do jogador.
     * Implementação padrão que pode ser sobrescrita por subclasses.
     * 
     * CONCEITO: MÉTODO CONCRETO EM CLASSE ABSTRATA
     * - Fornece implementação padrão que pode ser herdada ou sobrescrita
     * - Permite comportamento comum com opção de especialização
     */
    public int calcularPontuacao(int tentativasRestantes, int tamanhopalavra) {
        return tentativasRestantes * tamanhopalavra;
    }
    
    // Getters e setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
}