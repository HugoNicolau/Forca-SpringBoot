package com.jogodaforca.forca.model;

import com.jogodaforca.forca.dto.PalavraDTO;

import jakarta.persistence.Entity;

/**
 * Classe que representa uma palavra do jogo da forca.
 * 
 * CONCEITO: HERANÇA (ESPECIALIZAÇÃO)
 * - Palavra estende BaseEntity, herdando atributos e comportamentos comuns a todas as entidades
 * - Exemplo de especialização, onde a subclasse adiciona atributos e comportamentos específicos
 * 
 * CONCEITO: SOBRECARGA
 * - Esta classe demonstra sobrecarga de construtores e métodos
 * - A sobrecarga permite múltiplas versões de um método com mesmo nome mas parâmetros diferentes
 * - É uma forma de polimorfismo em tempo de compilação (static binding)
 * - Aumenta a flexibilidade e legibilidade da API
 */
@Entity
public class Palavra extends BaseEntity {
    
    private String palavraSecreta;
    private String dica;
    
    /**
     * CONCEITO: CONSTRUTOR PADRÃO
     * - Necessário para o JPA criar instâncias da entidade
     */
    public Palavra() {
    }
    
    /**
     * CONCEITO: SOBRECARGA DE CONSTRUTORES
     * - Permite inicializar o objeto de diferentes maneiras
     * - Mesmo nome da classe, mas com parâmetros diferentes
     * - Uma forma de polimorfismo em tempo de compilação (static binding)
     * - Proporciona diferentes opções de criação do objeto
     */
    public Palavra(String palavraSecreta, String dica) {
        this.palavraSecreta = palavraSecreta.toLowerCase();
        this.dica = dica;
    }
    
    /**
     * CONCEITO: SOBRECARGA DE CONSTRUTORES (exemplo adicional)
     * - Outro construtor com conjunto diferente de parâmetros
     * - Demonstra como podemos ter múltiplas formas de criar o mesmo objeto
     * 
     * @param palavraSecreta a palavra a ser adivinhada
     * @param dica dica para ajudar a adivinhar a palavra
     * @param id identificador da palavra
     */
    public Palavra(String palavraSecreta, String dica, Long id) {
        this(palavraSecreta, dica); // Chama o outro construtor sobrecarregado
        this.setId(id);             // Adiciona funcionalidade específica
    }
    
    // Getters e Setters
    
    public String getPalavraSecreta() {
        return palavraSecreta;
    }
    
    public void setPalavraSecreta(String palavraSecreta) {
        this.palavraSecreta = palavraSecreta.toLowerCase();
    }
    
    public String getDica() {
        return dica;
    }
    
    public void setDica(String dica) {
        this.dica = dica;
    }
    
    /**
     * CONCEITO: IMPLEMENTAÇÃO DE MÉTODO ABSTRATO
     * - Implementação do método abstrato definido na classe BaseEntity
     * - Converte a entidade Palavra para um objeto DTO
     */
    @Override
    public Object toDTO() {
        PalavraDTO dto = new PalavraDTO();
        dto.setId(this.getId());
        dto.setPalavraSecreta(this.palavraSecreta);
        dto.setDica(this.dica);
        return dto;
    }
    
    @Override
    public String toString() {
        return "Palavra [id=" + getId() + ", palavraSecreta=" + palavraSecreta + ", dica=" + dica + "]";
    }

    /**
     * Verifica se a palavra contém a letra especificada.
     * 
     * CONCEITO: SOBRECARGA DE MÉTODOS
     * - Mesmo nome de método, mas parâmetros diferentes
     * - Esta versão aceita um parâmetro char
     * 
     * @param letra a letra a ser verificada
     * @return true se a palavra contém a letra, false caso contrário
     */
    public boolean contemLetra(char letra) {
        return palavraSecreta.indexOf(Character.toLowerCase(letra)) >= 0;
    }

    /**
     * Verifica se a palavra contém a letra especificada, com opção de ignorar acentos.
     * 
     * CONCEITO: SOBRECARGA DE MÉTODOS
     * - Mesmo nome, diferentes parâmetros (número e tipo)
     * - Esta versão aceita um parâmetro char e um boolean
     * 
     * @param letra a letra a ser verificada
     * @param ignorarAcentos se true, ignora acentuação na comparação
     * @return true se a palavra contém a letra, false caso contrário
     */
    public boolean contemLetra(char letra, boolean ignorarAcentos) {
        if (!ignorarAcentos) {
            return contemLetra(letra); // Reutiliza o método sobrecarregado
        }
        
        // Versão que ignora acentos
        String letraStr = String.valueOf(Character.toLowerCase(letra));
        String palavraNormalizada = palavraSecreta
            .replaceAll("[áàãâä]", "a")
            .replaceAll("[éèêë]", "e")
            .replaceAll("[íìîï]", "i")
            .replaceAll("[óòõôö]", "o")
            .replaceAll("[úùûü]", "u")
            .replaceAll("[ç]", "c");
        
        return palavraNormalizada.indexOf(letraStr) >= 0;
    }

    /**
     * Verifica se a palavra contém a string especificada.
     * 
     * CONCEITO: SOBRECARGA DE MÉTODOS
     * - Mesmo nome, mas tipo de parâmetro diferente (String em vez de char)
     * - Demonstra sobrecarga com tipos de parâmetros diferentes
     * 
     * @param trecho o trecho a ser verificado
     * @return true se a palavra contém o trecho, false caso contrário
     */
    public boolean contemLetra(String trecho) {
        if (trecho == null || trecho.isEmpty()) {
            return false;
        }
        return palavraSecreta.contains(trecho.toLowerCase());
    }

    /**
     * Formata a palavra secreta para exibição, ocultando letras não descobertas.
     * 
     * CONCEITO: SOBRECARGA DE MÉTODOS
     * - Método com mesmo nome, mas sem parâmetros
     * - Usa comportamento padrão (ocultar todas as letras)
     * 
     * @return a palavra formatada com letras ocultas
     */
    public String formatarPalavra() {
        return formatarPalavra(new char[0]);  // Chama a versão sobrecarregada sem letras descobertas
    }

    /**
     * Formata a palavra secreta para exibição, mostrando apenas letras descobertas.
     * 
     * CONCEITO: SOBRECARGA DE MÉTODOS
     * - Mesmo nome, parâmetros diferentes
     * - Esta versão aceita um array de letras descobertas
     * 
     * @param letrasDescobertas array de letras já descobertas pelo jogador
     * @return a palavra formatada, com letras não descobertas substituídas por underscores
     */
    public String formatarPalavra(char[] letrasDescobertas) {
        StringBuilder resultado = new StringBuilder();
        
        for (char c : palavraSecreta.toCharArray()) {
            boolean descoberta = false;
            for (char letraDescobertas : letrasDescobertas) {
                if (c == letraDescobertas) {
                    descoberta = true;
                    break;
                }
            }
            
            if (descoberta) {
                resultado.append(c);
            } else if (c == ' ') {
                resultado.append(' ');
            } else {
                resultado.append('_');
            }
            resultado.append(' ');
        }
        
        return resultado.toString().trim();
    }
}