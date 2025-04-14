package com.jogodaforca.forca.model;

import jakarta.persistence.Embeddable;

/**
 * CONCEITO: COMPOSIÇÃO
 * - Classe componente que não existe independentemente do Jogador
 * - Representa configurações específicas de um jogador
 * - Usando @Embeddable para incorporação na entidade Jogador
 */
@Embeddable
public class ConfiguracaoJogador {
    private String tema;
    private String dificuldade;
    private boolean notificacoesAtivadas;
    
    // Construtor padrão necessário para JPA
    public ConfiguracaoJogador() {
        this.tema = "claro";
        this.dificuldade = "normal";
        this.notificacoesAtivadas = true;
    }
    
    // Construtor com parâmetros
    public ConfiguracaoJogador(String tema, String dificuldade, boolean notificacoesAtivadas) {
        this.tema = tema;
        this.dificuldade = dificuldade;
        this.notificacoesAtivadas = notificacoesAtivadas;
    }
    
    // Getters e Setters
    public String getTema() {
        return tema;
    }
    
    public void setTema(String tema) {
        this.tema = tema;
    }
    
    public String getDificuldade() {
        return dificuldade;
    }
    
    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }
    
    public boolean isNotificacoesAtivadas() {
        return notificacoesAtivadas;
    }
    
    public void setNotificacoesAtivadas(boolean notificacoesAtivadas) {
        this.notificacoesAtivadas = notificacoesAtivadas;
    }
    
    @Override
    public String toString() {
        return "ConfiguracaoJogador [tema=" + tema + 
               ", dificuldade=" + dificuldade + 
               ", notificacoesAtivadas=" + notificacoesAtivadas + "]";
    }
}