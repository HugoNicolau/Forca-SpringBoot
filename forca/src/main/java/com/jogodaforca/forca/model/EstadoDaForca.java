package com.jogodaforca.forca.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EstadoDaForca {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int tentativasRestantes;
    
    @Column(length = 100)
    private String letrasCorretas = "";
    
    @Column(length = 100)
    private String letrasErradas = "";
    
    // Construtor
    public EstadoDaForca() {
        this.tentativasRestantes = 6; // Padrão: 6 tentativas
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public int getTentativasRestantes() {
        return tentativasRestantes;
    }
    
    public void setTentativasRestantes(int tentativasRestantes) {
        this.tentativasRestantes = tentativasRestantes;
    }
    
    // Métodos para manipular as letras corretas como Set
    public Set<Character> getLetrasCorretasSet() {
        Set<Character> set = new HashSet<>();
        for (char c : letrasCorretas.toCharArray()) {
            set.add(c);
        }
        return set;
    }
    
    public void addLetraCorreta(char letra) {
        if (letrasCorretas.indexOf(letra) == -1) {
            letrasCorretas += letra;
        }
    }
    
    // Métodos para manipular as letras erradas como Set
    public Set<Character> getLetrasErradasSet() {
        Set<Character> set = new HashSet<>();
        for (char c : letrasErradas.toCharArray()) {
            set.add(c);
        }
        return set;
    }
    
    public void addLetraErrada(char letra) {
        if (letrasErradas.indexOf(letra) == -1) {
            letrasErradas += letra;
        }
    }
    
    // Getters e setters para as strings de letras
    public String getLetrasCorretas() {
        return letrasCorretas;
    }
    
    public void setLetrasCorretas(String letrasCorretas) {
        this.letrasCorretas = letrasCorretas;
    }
    
    public String getLetrasErradas() {
        return letrasErradas;
    }
    
    public void setLetrasErradas(String letrasErradas) {
        this.letrasErradas = letrasErradas;
    }
    
    // Método para tentar uma letra
    public boolean tentarLetra(char letra, String palavraSecreta) {
        letra = Character.toLowerCase(letra);
        boolean acertou = palavraSecreta.indexOf(letra) >= 0;
        
        if (acertou) {
            addLetraCorreta(letra);
        } else {
            addLetraErrada(letra);
            tentativasRestantes--;
        }
        
        return acertou;
    }
    
    // Método para verificar se já ganhou
    public boolean verificarVitoria(String palavraSecreta) {
        Set<Character> letrasNecessarias = new HashSet<>();
        for (char c : palavraSecreta.toLowerCase().toCharArray()) {
            if (c != ' ') {
                letrasNecessarias.add(c);
            }
        }
        
        Set<Character> letrasAcertadas = getLetrasCorretasSet();
        return letrasAcertadas.containsAll(letrasNecessarias);
    }
    
    // Método para verificar se já perdeu
    public boolean verificarDerrota() {
        return tentativasRestantes <= 0;
    }
    
    // Método para gerar representação da palavra com letras descobertas
    public String getPalavraAtual(String palavraSecreta) {
        Set<Character> corretas = getLetrasCorretasSet();
        StringBuilder sb = new StringBuilder();
        for (char letra : palavraSecreta.toCharArray()) {
            if (letra == ' ') {
                sb.append(' ');
            } else if (corretas.contains(Character.toLowerCase(letra))) {
                sb.append(letra);
            } else {
                sb.append('_');
            }
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return "EstadoDaForca [tentativasRestantes=" + tentativasRestantes + 
               ", letrasCorretas=" + letrasCorretas + 
               ", letrasErradas=" + letrasErradas + "]";
    }
}