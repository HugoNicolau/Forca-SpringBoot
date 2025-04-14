package com.jogodaforca.forca.model;

import java.time.LocalDateTime;

import com.jogodaforca.forca.dto.TentativaDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Classe que representa uma letra tentada pelo jogador em uma partida.
 * 
 * CONCEITO: HERANÇA (ESPECIALIZAÇÃO)
 * - LetraTentada estende BaseEntity, herdando atributos e comportamentos comuns a todas as entidades
 * - Exemplo de especialização, onde a subclasse adiciona atributos e comportamentos específicos
 */
@Entity
public class LetraTentada extends BaseEntity {
    
    // Remove a declaração duplicada do id que já está na classe pai
    // private Long id;
    
    private char letra;
    private boolean acerto;
    
    @ManyToOne
    @JoinColumn(name = "partida_id")
    private Partida partida;
    
    @Column(name = "data_hora")
    private LocalDateTime dataHora;
    
    /**
     * CONCEITO: CONSTRUTORES
     * - Inicializa o objeto com valores padrão
     */
    public LetraTentada() {
        this.dataHora = LocalDateTime.now(); // Inicializa com a data/hora atual
    }
    
    /**
     * CONCEITO: SOBRECARGA DE CONSTRUTORES
     * - Permite inicializar o objeto de diferentes maneiras
     */
    public LetraTentada(char letra, boolean acerto) {
        this();
        this.letra = Character.toLowerCase(letra);
        this.acerto = acerto;
    }
    
    // Getters e Setters
    // Remove os métodos getter e setter para id que já estão na classe pai
    
    /**
     * CONCEITO: ENCAPSULAMENTO
     * - Métodos públicos que controlam o acesso aos atributos privados
     */
    public char getLetra() {
        return letra;
    }
    
    public void setLetra(char letra) {
        this.letra = Character.toLowerCase(letra);
    }
    
    public boolean isAcerto() {
        return acerto;
    }
    
    public void setAcerto(boolean acerto) {
        this.acerto = acerto;
    }
    
    public Partida getPartida() {
        return partida;
    }
    
    public void setPartida(Partida partida) {
        this.partida = partida;
    }
    
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    /**
     * CONCEITO: IMPLEMENTAÇÃO DE MÉTODO ABSTRATO
     * - Implementação do método abstrato definido na classe BaseEntity
     * - Converte a entidade LetraTentada para um objeto DTO
     * - Exemplo de polimorfismo, onde cada subclasse implementa o mesmo método de forma específica
     */
    @Override
    public Object toDTO() {
        TentativaDTO dto = new TentativaDTO();
        dto.setLetra(this.letra);
        dto.setAcerto(this.acerto);
        dto.setPartidaId(this.partida != null ? this.partida.getId() : null);
        dto.setDataHora(this.dataHora != null ? this.dataHora.toString() : null);
        return dto;
    }
}