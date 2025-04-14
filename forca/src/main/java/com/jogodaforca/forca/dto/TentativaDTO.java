package com.jogodaforca.forca.dto;

import com.jogodaforca.forca.model.LetraTentada;

/**
 * DTO para representar uma tentativa de letra no jogo da forca.
 * 
 * CONCEITO: CLASSES E OBJETOS
 * - Representa um objeto de transferência de dados para uma tentativa de letra
 * - Separa a camada de apresentação da camada de modelo
 */
public class TentativaDTO {
    private char letra;
    private boolean acerto;
    private Long partidaId;
    private String dataHora;
    
    /**
     * CONCEITO: CONSTRUTOR PADRÃO
     * - Necessário para serialização/deserialização JSON
     */
    public TentativaDTO() {
    }
    
    /**
     * CONCEITO: SOBRECARGA DE CONSTRUTORES
     * - Construtor com parâmetros para facilitar a criação de objetos
     */
    public TentativaDTO(char letra) {
        this.letra = letra;
    }
    
    /**
     * Método estático de fábrica que cria um DTO a partir da entidade LetraTentada.
     * 
     * CONCEITO: MÉTODOS ESTÁTICOS
     * - Método de classe que pode ser chamado sem instanciar a classe
     * - Padrão de projeto Factory Method para criar objetos
     * 
     * @param letraTentada A entidade modelo a ser convertida em DTO
     * @return Um novo DTO com os dados da entidade
     */
    public static TentativaDTO fromLetraTentada(LetraTentada letraTentada) {
        TentativaDTO dto = new TentativaDTO();
        dto.setLetra(letraTentada.getLetra());
        dto.setAcerto(letraTentada.isAcerto());
        dto.setPartidaId(letraTentada.getPartida().getId());
        // Formatar data/hora se necessário
        if (letraTentada.getDataHora() != null) {
            dto.setDataHora(letraTentada.getDataHora().toString());
        }
        return dto;
    }
    
    // Getters e Setters
    
    /**
     * CONCEITO: ENCAPSULAMENTO
     * - Métodos públicos que controlam o acesso aos atributos privados
     */
    public char getLetra() {
        return letra;
    }
    
    public void setLetra(char letra) {
        this.letra = letra;
    }
    
    public boolean isAcerto() {
        return acerto;
    }
    
    public void setAcerto(boolean acerto) {
        this.acerto = acerto;
    }
    
    public Long getPartidaId() {
        return partidaId;
    }
    
    public void setPartidaId(Long partidaId) {
        this.partidaId = partidaId;
    }
    
    public String getDataHora() {
        return dataHora;
    }
    
    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }
}