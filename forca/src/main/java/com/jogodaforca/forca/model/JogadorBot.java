package com.jogodaforca.forca.model;

import java.util.Random;

import com.jogodaforca.forca.dto.JogadorDTO;

import jakarta.persistence.Entity;

/**
 * CONCEITO: ESPECIALIZAÇÃO DE CLASSE ABSTRATA
 * - Outra implementação concreta da classe abstrata Jogador
 * - Demonstra como diferentes subclasses podem implementar o mesmo contrato
 */
@Entity
public class JogadorBot extends Jogador {
    
    /**
     * CONCEITO: ATRIBUTO ESPECÍFICO DE SUBCLASSE
     * - Cada tipo de jogador pode ter atributos específicos
     * - Em uma implementação real, seria usado para escolher palavras aleatoriamente
     * - Não utilizado nesta implementação simplificada
     */
    private final Random random = new Random();
    
    /**
     * CONCEITO: IMPLEMENTAÇÃO DE MÉTODO ABSTRATO
     * - Fornece implementação específica para o método abstrato da superclasse
     * - Bots sempre escolhem palavras aleatórias
     */
    @Override
    public Palavra escolherPalavra() {
        // Implementação simplificada para fins didáticos
        // Em uma implementação real, usaríamos o random para escolher palavras
        // Exemplo: return palavras.get(random.nextInt(palavras.size()));
        return null; // Para fins didáticos apenas
    }

    /**
     * CONCEITO: IMPLEMENTAÇÃO DE MÉTODO ABSTRATO
     * - Implementação do método abstrato definido em BaseEntity
     * - Converte a entidade JogadorBot para um objeto DTO
     * - Necessário porque JogadorBot é uma subclasse concreta (não abstrata)
     */
    @Override
    public Object toDTO() {
        JogadorDTO dto = new JogadorDTO();
        dto.setId(this.getId());
        dto.setNome(this.getNome());
        dto.setTipo("Bot");
        return dto;
    }
}