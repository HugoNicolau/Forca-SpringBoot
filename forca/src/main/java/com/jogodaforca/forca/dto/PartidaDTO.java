package com.jogodaforca.forca.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.jogodaforca.forca.model.Partida;
import com.jogodaforca.forca.model.StatusPartida;

/**
 * Classe DTO (Data Transfer Object) para representar uma Partida na camada de apresentação.
 * 
 * CONCEITO: CLASSES E OBJETOS
 * - Esta classe representa o conceito de DTO, um objeto usado para transferir dados
 *   entre subsistemas da aplicação, separando as regras de negócio da apresentação.
 * 
 * CONCEITO: HERANÇA (ESPECIALIZAÇÃO)
 * - PartidaDTO estende BaseDTO, herdando seus atributos e comportamentos
 * - Exemplo de especialização, onde a subclasse adiciona atributos e comportamentos específicos
 */
public class PartidaDTO extends BaseDTO {
    // Remove o id que já está na classe pai
    // private Long id;
    
    private Long usuarioId;
    private String nomeUsuario;
    private String palavraAtual; // palavra com letras descobertas
    private String dica;
    private int tentativasRestantes;
    private Set<Character> letrasCorretas = new HashSet<>(); // CONCEITO: OBJETOS - utilização de objetos Java padrão
    private Set<Character> letrasErradas = new HashSet<>();
    private StatusPartida status;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    
    /**
     * Método estático que cria um objeto DTO a partir de uma entidade Partida.
     * 
     * CONCEITO: CLASSES E OBJETOS
     * - Método de fábrica que demonstra a criação de objetos a partir de outros objetos
     * - Transformação de uma entidade de domínio em um objeto de transferência
     */
    public static PartidaDTO fromPartida(Partida partida) {
        PartidaDTO dto = new PartidaDTO();
        dto.setId(partida.getId()); // Método herdado da classe BaseDTO
        dto.setUsuarioId(partida.getUsuario().getId());
        dto.setNomeUsuario(partida.getUsuario().getNome());
        dto.setPalavraAtual(partida.getPalavraAtual());
        dto.setDica(partida.getPalavra().getDica());
        dto.setTentativasRestantes(partida.getEstado().getTentativasRestantes());
        
        // Obter letras corretas e erradas
        dto.setLetrasCorretas(partida.getEstado().getLetrasCorretasSet());
        dto.setLetrasErradas(partida.getEstado().getLetrasErradasSet());
        
        dto.setStatus(partida.getStatus());
        dto.setDataInicio(partida.getDataInicio());
        dto.setDataFim(partida.getDataFim());
        return dto;
    }
    
    /**
     * CONCEITO: ENCAPSULAMENTO
     * - Métodos getters e setters controlam o acesso aos atributos privados
     * - Permitem validações ou transformações antes de modificar ou retornar valores
     */
    // Getters e Setters
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
    
    public Set<Character> getLetrasCorretas() {
        return letrasCorretas;
    }
    
    public void setLetrasCorretas(Set<Character> letrasCorretas) {
        this.letrasCorretas = letrasCorretas;
    }
    
    public Set<Character> getLetrasErradas() {
        return letrasErradas;
    }
    
    public void setLetrasErradas(Set<Character> letrasErradas) {
        this.letrasErradas = letrasErradas;
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