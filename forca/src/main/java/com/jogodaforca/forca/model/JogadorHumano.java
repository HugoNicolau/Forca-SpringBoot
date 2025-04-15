package com.jogodaforca.forca.model;

import com.jogodaforca.forca.dto.JogadorDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 * CONCEITO: ESPECIALIZAÇÃO DE CLASSE ABSTRATA
 * - Implementação concreta da classe abstrata Jogador
 * - Fornece implementação específica para os métodos abstratos
 */
@Entity
public class JogadorHumano extends Jogador {
    
    /**
     * CONCEITO: ASSOCIAÇÃO BIDIRECIONAL (MANY-TO-ONE)
     * - Muitos jogadores podem pertencer a uma equipe
     * - O lado "muitos" da associação bidirecional com Equipe
     */
    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    @OneToOne
    private Usuario usuario;

    // Getters e Setters para a associação
    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * CONCEITO: IMPLEMENTAÇÃO DE MÉTODO ABSTRATO
     * - Fornece uma implementação concreta para o método abstrato definido na classe pai
     * - Utiliza o serviço injetado para obter uma palavra do sistema
     */
    @Override
    public Palavra escolherPalavra() {
        // Implementação simplificada
        return null; // Para fins didáticos apenas
    }
    
    /**
     * CONCEITO: SOBRESCRITA DE MÉTODO
     * - Implementa uma versão especializada do método da classe pai
     * - Altera o comportamento herdado para atender às necessidades específicas
     */
    @Override
    public int calcularPontuacao(int tentativasRestantes, int tamanhoPalavra) {
        // Jogadores humanos recebem pontuação bônus
        return super.calcularPontuacao(tentativasRestantes, tamanhoPalavra) + 10;
    }

    /**
     * CONCEITO: IMPLEMENTAÇÃO DE MÉTODO ABSTRATO
     * - Implementação do método abstrato definido em BaseEntity
     * - Converte a entidade JogadorHumano para um objeto DTO
     */
    @Override
    public Object toDTO() {
        JogadorDTO dto = new JogadorDTO();
        dto.setId(this.getId());
        dto.setNome(this.getNome());
        dto.setTipo("Humano");
        return dto;
    }
}