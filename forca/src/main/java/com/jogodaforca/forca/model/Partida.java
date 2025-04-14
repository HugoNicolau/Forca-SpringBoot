package com.jogodaforca.forca.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jogodaforca.forca.dto.PartidaDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

/**
 * Classe que representa uma partida do jogo da forca.
 * 
 * CONCEITO: HERANÇA (ESPECIALIZAÇÃO)
 * - Partida estende BaseEntity, herdando atributos e comportamentos comuns a todas as entidades
 * - Exemplo de especialização, onde a subclasse adiciona atributos e comportamentos específicos
 */
@Entity
public class Partida extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties("partidas")
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "palavra_id")
    private Palavra palavra;

    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL)
    private List<LetraTentada> letrasTentadas = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estado_id")
    private EstadoDaForca estado;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    @Enumerated(EnumType.STRING)
    private StatusPartida status;

    /**
     * CONCEITO: SOBRECARGA DE CONSTRUTORES
     * - Múltiplos construtores com diferentes parâmetros
     * - Permite inicializar o objeto de diferentes maneiras
     */
    public Partida() {
        this.dataInicio = LocalDateTime.now();
        this.status = StatusPartida.EM_ANDAMENTO;
        this.estado = new EstadoDaForca();
    }

    // Sobrecarga de construtores
    public Partida(Usuario usuario, Palavra palavra) {
        this(); // Chama o construtor sem parâmetros
        this.usuario = usuario;
        this.palavra = palavra;
    }

    public Partida(Usuario usuario, Palavra palavra, String dificuldade) {
        this(usuario, palavra); // Chama o construtor com dois parâmetros
        // Ajuste o número de tentativas com base na dificuldade
        if ("facil".equalsIgnoreCase(dificuldade)) {
            this.estado.setTentativasRestantes(8);
        } else if ("dificil".equalsIgnoreCase(dificuldade)) {
            this.estado.setTentativasRestantes(4);
        }
    }

    // Getters e Setters

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Palavra getPalavra() {
        return palavra;
    }

    public void setPalavra(Palavra palavra) {
        this.palavra = palavra;
    }

    public List<LetraTentada> getLetrasTentadas() {
        return letrasTentadas;
    }

    public void setLetrasTentadas(List<LetraTentada> letrasTentadas) {
        this.letrasTentadas = letrasTentadas;
    }

    /**
     * CONCEITO: COMPOSIÇÃO
     * - A classe Partida contém uma instância de EstadoDaForca
     * - EstadoDaForca não existe sem uma Partida (relação forte)
     * - O estado da forca é parte integrante da partida
     */
    public EstadoDaForca getEstado() {
        return estado;
    }

    public void setEstado(EstadoDaForca estado) {
        this.estado = estado;
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

    public StatusPartida getStatus() {
        return status;
    }

    public void setStatus(StatusPartida status) {
        this.status = status;
    }

    // Método para adicionar uma letra tentada
    public boolean tentarLetra(char letra) {
        if (status != StatusPartida.EM_ANDAMENTO) {
            throw new IllegalStateException("Esta partida já foi finalizada");
        }

        // Verificar se a letra já foi tentada
        if (contemLetraTentada(letra)) {
            throw new IllegalStateException("Esta letra já foi tentada");
        }

        boolean acerto = estado.tentarLetra(letra, palavra.getPalavraSecreta());

        // Cria nova tentativa e a adiciona à lista
        LetraTentada letraTentada = new LetraTentada(letra, acerto);
        letraTentada.setPartida(this);
        letrasTentadas.add(letraTentada);

        // Verificar se o jogador ganhou
        if (estado.verificarVitoria(palavra.getPalavraSecreta())) {
            finalizarComoVitoria();
            return true;
        }

        // Verificar se o jogador perdeu
        if (estado.verificarDerrota()) {
            finalizarComoDerrota();
            return false;
        }

        return acerto;
    }

    // Verifica se uma letra já foi tentada
    public boolean contemLetraTentada(char letra) {
        return letrasTentadas.stream()
                .anyMatch(lt -> lt.getLetra() == Character.toLowerCase(letra));
    }

    // Retorna o estado atual da palavra com letras descobertas
    public String getPalavraAtual() {
        return estado.getPalavraAtual(palavra.getPalavraSecreta());
    }

    // Métodos para finalizar a partida
    private void finalizarComoVitoria() {
        this.status = StatusPartida.VENCEU;
        this.dataFim = LocalDateTime.now();
        this.usuario.setVitorias(this.usuario.getVitorias() + 1);
    }

    private void finalizarComoDerrota() {
        this.status = StatusPartida.PERDEU;
        this.dataFim = LocalDateTime.now();
        this.usuario.setDerrotas(this.usuario.getDerrotas() + 1);
    }

    public void desistir() {
        if (status != StatusPartida.EM_ANDAMENTO) {
            throw new IllegalStateException("Esta partida já foi finalizada");
        }

        this.status = StatusPartida.DESISTIU;
        this.dataFim = LocalDateTime.now();
        this.usuario.setDerrotas(this.usuario.getDerrotas() + 1);
    }

    /**
     * CONCEITO: POLIMORFISMO
     * - Sobrescrita do método toString() da classe Object
     * - Implementação específica para representar uma Partida como string
     */
    @Override
    public String toString() {
        return "Partida [id=" + getId() + ", palavra=" + palavra.getPalavraSecreta() +
               ", estado=" + estado + ", status=" + status + "]";
    }

    /**
     * CONCEITO: IMPLEMENTAÇÃO DE MÉTODO ABSTRATO
     * - Implementação do método abstrato definido na classe BaseEntity
     * - Converte a entidade Partida para um objeto DTO
     * - Demonstra como diferentes subclasses podem implementar o mesmo método abstrato
     */
    @Override
    public Object toDTO() {
        PartidaDTO dto = new PartidaDTO();
        dto.setId(this.getId());
        dto.setUsuarioId(this.usuario.getId());
        dto.setNomeUsuario(this.usuario.getNome());
        dto.setPalavraAtual(this.getPalavraAtual());
        dto.setDica(this.palavra.getDica());
        dto.setTentativasRestantes(this.estado.getTentativasRestantes());
        dto.setLetrasCorretas(this.estado.getLetrasCorretasSet());
        dto.setLetrasErradas(this.estado.getLetrasErradasSet());
        dto.setStatus(this.status);
        dto.setDataInicio(this.dataInicio);
        dto.setDataFim(this.dataFim);
        return dto;
    }
}