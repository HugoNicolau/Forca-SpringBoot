package com.jogodaforca.forca.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jogodaforca.forca.dto.UsuarioDTO;
import com.jogodaforca.forca.interfaces.EstatisticaCalculavel;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

/**
 * Classe que representa um usuário do sistema.
 * 
 * CONCEITO: HERANÇA (ESPECIALIZAÇÃO)
 * - Usuario estende BaseEntity, herdando atributos e comportamentos comuns a todas as entidades
 * 
 * CONCEITO: POLIMORFISMO (IMPLEMENTAÇÃO DE INTERFACE)
 * - Implementa a interface EstatisticaCalculavel, o que permite que objetos Usuario
 *   sejam tratados como EstatisticaCalculavel em contextos que trabalham com essa interface
 */
@Entity
public class Usuario extends BaseEntity implements EstatisticaCalculavel {

    /**
     * CONCEITO: ENCAPSULAMENTO
     * - Atributos privados que só podem ser acessados através de métodos getters e setters
     * - Protege os dados e permite controlar como eles são acessados e modificados
     */
    private String nome;
    private String login;
    private String senha;
    private int vitorias;
    private int derrotas;

    /**
     * CONCEITO: ASSOCIAÇÃO (COMPOSIÇÃO)
     * - Um Usuario pode ter várias Partidas (relacionamento 1:N)
     * - Exemplo de associação bidirecional, onde Partida também mantém referência ao Usuario
     * 
     * CONCEITO: ENCAPSULAMENTO
     * - @JsonIgnore evita recursão infinita na serialização JSON
     */
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Partida> partidas = new ArrayList<>();

    // Getters e Setters
    
    /**
     * CONCEITO: ENCAPSULAMENTO
     * - Métodos públicos que controlam o acesso aos atributos privados
     */
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * CONCEITO: POLIMORFISMO (SOBRESCRITA DE MÉTODO)
     * - Implementa métodos definidos na interface EstatisticaCalculavel
     * - A anotação @Override garante que estamos implementando corretamente os métodos da interface
     */
    @Override
    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    @Override
    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    /**
     * Método auxiliar para calcular taxa de vitórias.
     * 
     * CONCEITO: COESÃO
     * - Método com responsabilidade bem definida que complementa o comportamento da classe
     */
    public double getWinRate() {
        int total = vitorias + derrotas;
        return total == 0 ? 0 : (double) vitorias / total;
    }

    /**
     * CONCEITO: POLIMORFISMO
     * - Implementação do método da interface EstatisticaCalculavel
     * - Permite que a classe Usuario seja tratada polimorficamente
     */
    @Override
    public double calcularWinRate() {
        int total = vitorias + derrotas;
        return total == 0 ? 0.0 : (double) vitorias / total;
    }

    @Override
    public int getTotalPartidas() {
        return vitorias + derrotas;
    }

    /**
     * CONCEITO: POLIMORFISMO
     * - Sobrescrita do método toString() da classe Object
     * - Implementação específica para representar um Usuario como string
     */
    @Override
    public String toString() {
        return "Usuario [id=" + getId() + ", nome=" + nome + ", login=" + login +
                ", vitorias=" + vitorias + ", derrotas=" + derrotas + "]";
    }

    /**
     * CONCEITO: IMPLEMENTAÇÃO DE MÉTODO ABSTRATO
     * - Implementação do método abstrato definido na classe BaseEntity
     * - Converte a entidade para um objeto DTO para transferência de dados
     */
    @Override
    public Object toDTO() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(this.getId());
        dto.setNome(this.nome);
        dto.setLogin(this.login);
        dto.setVitorias(this.vitorias);
        dto.setDerrotas(this.derrotas);
        dto.setWinRate(this.calcularWinRate());
        dto.setTotalPartidas(this.getTotalPartidas());
        return dto;
    }
}
