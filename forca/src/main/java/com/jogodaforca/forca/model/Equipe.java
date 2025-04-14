package com.jogodaforca.forca.model;

import java.util.ArrayList;
import java.util.List;

import com.jogodaforca.forca.dto.EquipeDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

/**
 * CONCEITO: ASSOCIAÇÃO BIDIRECIONAL
 * - Demonstra como duas classes mantêm referências uma à outra
 * - Equipe conhece seus JogadoresHumanos e cada JogadorHumano conhece sua Equipe
 */
@Entity
public class Equipe extends BaseEntity {
    
    private String nome;
    private int pontuacaoTotal;
    
    /**
     * CONCEITO: ASSOCIAÇÃO ONE-TO-MANY
     * - Uma equipe tem vários jogadores humanos
     * - Associação bidirecional (jogadores conhecem sua equipe)
     */
    @OneToMany(mappedBy = "equipe", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<JogadorHumano> jogadores = new ArrayList<>();
    
    /**
     * Adiciona um jogador à equipe e estabelece a associação bidirecional.
     * 
     * CONCEITO: MANUTENÇÃO DA CONSISTÊNCIA DA ASSOCIAÇÃO BIDIRECIONAL
     * - Responsabilidade da classe controlar ambos os lados da associação
     * 
     * @param jogador o jogador a ser adicionado à equipe
     */
    public void adicionarJogador(JogadorHumano jogador) {
        jogadores.add(jogador);
        jogador.setEquipe(this); // Mantém a consistência bidirecional
    }
    
    /**
     * Remove um jogador da equipe e desfaz a associação bidirecional.
     * 
     * @param jogador o jogador a ser removido da equipe
     */
    public void removerJogador(JogadorHumano jogador) {
        if (jogadores.remove(jogador)) {
            jogador.setEquipe(null); // Mantém a consistência bidirecional
        }
    }
    
    /**
     * Atualiza a pontuação total da equipe somando as pontuações dos jogadores.
     * 
     * CONCEITO: OPERAÇÃO QUE UTILIZA A ASSOCIAÇÃO
     * - Usa a associação para realizar uma operação que envolve as partes
     */
    public void atualizarPontuacaoTotal() {
        this.pontuacaoTotal = jogadores.stream()
                .mapToInt(j -> j.nivel * 100 + j.experiencia)
                .sum();
    }
    
    /**
     * CONCEITO: IMPLEMENTAÇÃO DE MÉTODO ABSTRATO
     * - Implementação do método abstrato definido em BaseEntity
     */
    @Override
    public Object toDTO() {
        EquipeDTO dto = new EquipeDTO();
        dto.setId(this.getId());
        dto.setNome(this.nome);
        dto.setPontuacaoTotal(this.pontuacaoTotal);
        dto.setQuantidadeJogadores(this.jogadores.size());
        return dto;
    }
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }
    
    public List<JogadorHumano> getJogadores() {
        return jogadores;
    }
}