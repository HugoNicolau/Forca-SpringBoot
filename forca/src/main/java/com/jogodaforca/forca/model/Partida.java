package com.jogodaforca.forca.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Partida {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties("partidas")
    private Usuario usuario;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "palavra_id")
    private Palavra palavra;
    
    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL)
    private List<LetraTentada> letrasTentadas = new ArrayList<>();
    
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    
    private int tentativasRestantes;
    
    @Enumerated(EnumType.STRING)
    private StatusPartida status;
    
    // Construtor padrão
    public Partida() {
        this.dataInicio = LocalDateTime.now();
        this.tentativasRestantes = 6; // Número padrão de tentativas no jogo da forca
        this.status = StatusPartida.EM_ANDAMENTO;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    // Método para adicionar uma letra tentada
    public void adicionarLetraTentada(LetraTentada letraTentada) {
        letraTentada.setPartida(this);
        this.letrasTentadas.add(letraTentada);
        
        // Se a letra não estiver na palavra, reduz as tentativas restantes
        if (!letraTentada.isAcerto()) {
            this.tentativasRestantes--;
            
            // Verifica se o jogador perdeu
            if (this.tentativasRestantes <= 0) {
                this.status = StatusPartida.PERDEU;
                this.dataFim = LocalDateTime.now();
                // Atualiza estatísticas do usuário
                this.usuario.setDerrotas(this.usuario.getDerrotas() + 1);
            }
        } else {
            // Verifica se o jogador ganhou (todas as letras da palavra foram descobertas)
            boolean todasLetrasDescobertas = true;
            for (char letra : this.palavra.getPalavraSecreta().toCharArray()) {
                if (letra != ' ' && !this.contemLetraTentada(letra)) {
                    todasLetrasDescobertas = false;
                    break;
                }
            }
            
            if (todasLetrasDescobertas) {
                this.status = StatusPartida.VENCEU;
                this.dataFim = LocalDateTime.now();
                // Atualiza estatísticas do usuário
                this.usuario.setVitorias(this.usuario.getVitorias() + 1);
            }
        }
    }
    
    // Verifica se uma letra já foi tentada
    public boolean contemLetraTentada(char letra) {
        return this.letrasTentadas.stream()
                .anyMatch(lt -> lt.getLetra() == Character.toLowerCase(letra));
    }
    
    // Retorna o estado atual da palavra com letras descobertas
    public String getPalavraAtual() {
        StringBuilder sb = new StringBuilder();
        for (char letra : this.palavra.getPalavraSecreta().toCharArray()) {
            if (letra == ' ') {
                sb.append(' ');
            } else if (this.contemLetraTentada(letra)) {
                sb.append(letra);
            } else {
                sb.append('_');
            }
        }
        return sb.toString();
    }
}