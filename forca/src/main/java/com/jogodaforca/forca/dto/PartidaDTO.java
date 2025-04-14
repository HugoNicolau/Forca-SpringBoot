package com.jogodaforca.forca.dto;

import java.time.LocalDateTime;

import com.jogodaforca.forca.model.Partida;
import com.jogodaforca.forca.model.StatusPartida;

public class PartidaDTO {
    private Long id;
    private Long usuarioId;
    private String nomeUsuario;
    private String palavraAtual; // palavra com letras descobertas
    private String dica;
    private int tentativasRestantes;
    private StatusPartida status;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    
    public static PartidaDTO fromPartida(Partida partida) {
        PartidaDTO dto = new PartidaDTO();
        dto.setId(partida.getId());
        dto.setUsuarioId(partida.getUsuario().getId());
        dto.setNomeUsuario(partida.getUsuario().getNome());
        dto.setPalavraAtual(partida.getPalavraAtual());
        dto.setDica(partida.getPalavra().getDica());
        dto.setTentativasRestantes(partida.getTentativasRestantes());
        dto.setStatus(partida.getStatus());
        dto.setDataInicio(partida.getDataInicio());
        dto.setDataFim(partida.getDataFim());
        return dto;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
    
    public String getPalavraAtual() {
        return palavraAtual;
    }
    
    public void setPalavraAtual(String palavraAtual) {
        this.palavraAtual = palavraAtual;
    }
    
    public String getDica() {
        return dica;
    }
    
    public void setDica(String dica) {
        this.dica = dica;
    }
    
    public int getTentativasRestantes() {
        return tentativasRestantes;
    }
    
    public void setTentativasRestantes(int tentativasRestantes) {
        this.tentativasRestantes = tentativasRestantes;
    }
    
    public StatusPartida getStatus() {
        return status;
    }
    
    public void setStatus(StatusPartida status) {
        this.status = status;
    }
    
    public LocalDateTime getDataInicio() {
        return dataInicio;
    }
    
    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }
    
    public LocalDateTime getDataFim() {
        return dataFim;
    }
    
    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }
}