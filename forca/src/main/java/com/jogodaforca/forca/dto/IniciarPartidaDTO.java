package com.jogodaforca.forca.dto;

public class IniciarPartidaDTO {
    private Long usuarioId;
    private String palavraCustomizada;
    private String dica;
    
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
}