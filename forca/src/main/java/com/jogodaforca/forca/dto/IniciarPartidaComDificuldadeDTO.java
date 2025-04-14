package com.jogodaforca.forca.dto;

public class IniciarPartidaComDificuldadeDTO {
    private Long usuarioId;
    private String palavraCustomizada;
    private String dica;
    private String dificuldade;
    
    // Getters e Setters
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getPalavraCustomizada() {
        return palavraCustomizada;
    }
    
    public void setPalavraCustomizada(String palavraCustomizada) {
        this.palavraCustomizada = palavraCustomizada;
    }
    
    public String getDica() {
        return dica;
    }
    
    public void setDica(String dica) {
        this.dica = dica;
    }
    
    public String getDificuldade() {
        return dificuldade;
    }
    
    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }
}