package com.jogodaforca.forca.dto;

import com.jogodaforca.forca.model.Palavra;

/**
 * DTO para transferência de dados de palavras.
 * 
 * CONCEITO: DATA TRANSFER OBJECT (PADRÃO DE PROJETO)
 * - Objeto usado para transferir dados entre subsistemas
 * - Separa a representação de dados da lógica de negócios
 * - Contém apenas os dados necessários para a camada de apresentação
 */
public class PalavraDTO {
    private Long id;
    private String palavraSecreta;
    private String dica;
    
    /**
     * CONCEITO: CONSTRUTOR PADRÃO
     * - Necessário para deserialização JSON
     */
    public PalavraDTO() {
    }
    
    /**
     * CONCEITO: MÉTODO DE FÁBRICA ESTÁTICO
     * - Facilita a criação de DTOs a partir de entidades
     * - Implementa o padrão Factory Method
     * 
     * @param palavra A entidade a ser convertida
     * @return DTO contendo os dados da entidade
     */
    public static PalavraDTO fromPalavra(Palavra palavra) {
        PalavraDTO dto = new PalavraDTO();
        dto.setId(palavra.getId());
        dto.setPalavraSecreta(palavra.getPalavraSecreta());
        dto.setDica(palavra.getDica());
        return dto;
    }
    
    // Getters e Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPalavraSecreta() {
        return palavraSecreta;
    }
    
    public void setPalavraSecreta(String palavraSecreta) {
        this.palavraSecreta = palavraSecreta;
    }
    
    public String getDica() {
        return dica;
    }
    
    public void setDica(String dica) {
        this.dica = dica;
    }
}