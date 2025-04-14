package com.jogodaforca.forca.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;

/**
 * CONCEITO: CLASSE ABSTRATA
 * - Define o comportamento comum a todos os tipos de jogador
 * - Não pode ser instanciada diretamente
 * - Serve como base para especialização (tipos específicos de jogador)
 */
@MappedSuperclass
public abstract class Jogador extends BaseEntity {
    
    private String nome;           // Visível apenas nesta classe
    protected int nivel;           // Visível nesta classe e nas subclasses
    int experiencia;               // Visível apenas no mesmo pacote (default)
    public String tipoJogador;     // Visível em todo lugar
    
    /**
     * CONCEITO: MODIFICADORES DE ACESSO
     * - private: visível apenas na própria classe
     * - protected: visível na classe e em subclasses
     * - (default/package): visível no mesmo pacote
     * - public: visível em todo lugar
     * 
     * Estes modificadores implementam diferentes níveis de encapsulamento
     * e controle de visibilidade no sistema.
     */
    
    // Adicionar este campo à classe Jogador
    /**
     * CONCEITO: COMPOSIÇÃO
     * - ConfiguracaoJogador é parte intrínseca do Jogador
     * - Não existe independentemente do Jogador
     * - Relação de vida toda-parte, onde a parte não sobrevive sem o todo
     */
    @Embedded
    private ConfiguracaoJogador configuracao = new ConfiguracaoJogador();
    
    // Métodos com diferentes modificadores
    private void metodoPrivado() {
        // Lógica interna para processamento privado
        experiencia += 5;  // Incrementa experiência internamente
        System.out.println("Processamento interno: atualizando experiência para " + experiencia);
    }
    
    protected void metodoProtegido() {
        // Lógica que será útil para subclasses
        nivel = experiencia / 100;  // Calcula nível baseado na experiência
        System.out.println("Atualizando nível para " + nivel + " baseado na experiência " + experiencia);
        // Chama o método privado, demonstrando encapsulamento
        metodoPrivado();
    }
    
    void metodoPackage() {
        // Método acessível apenas no mesmo pacote
        System.out.println("Jogador " + nome + " do tipo " + tipoJogador + " está sendo processado");
        
        // Simulação de operação específica ao pacote
        if (nivel < 5) {
            experiencia += 10;
            System.out.println("Bônus de experiência adicionado para jogador de baixo nível");
        }
    }
    
    public void metodoPublico() {
        // Interface pública que qualquer classe pode acessar
        System.out.println("Jogador: " + nome + " (Nível " + nivel + ", Experiência: " + experiencia + ")");
        
        // Demonstra como métodos públicos podem chamar outros métodos com diferentes visibilidades
        metodoProtegido();
        metodoPackage();
        
        // Não pode chamar metodoPrivado() diretamente - demonstração de encapsulamento
    }
    
    /**
     * CONCEITO: MÉTODO ABSTRATO
     * - Declara comportamento sem implementação
     * - Força subclasses a fornecerem implementação específica
     * - Cada tipo de jogador escolhe palavra de forma diferente
     */
    public abstract Palavra escolherPalavra();
    
    /**
     * Calcula pontuação do jogador baseado nas tentativas restantes e tamanho da palavra.
     * Implementação padrão que pode ser sobrescrita por subclasses.
     * 
     * CONCEITO: MÉTODO CONCRETO EM CLASSE ABSTRATA
     * - Fornece implementação padrão que pode ser herdada ou sobrescrita
     * - Permite comportamento comum com opção de especialização
     * 
     * @param tentativasRestantes Número de tentativas não utilizadas
     * @param tamanhoPalavra Tamanho da palavra adivinhada
     * @return Pontuação calculada
     */
    public int calcularPontuacao(int tentativasRestantes, int tamanhoPalavra) {
        // Pontuação base: tentativas restantes × tamanho da palavra
        int pontuacaoBase = tentativasRestantes * tamanhoPalavra;
        
        // Bônus baseado no nível do jogador
        int bonus = nivel * 10;
        
        // Bônus baseado na experiência (pequeno bônus percentual)
        double bonusExperiencia = pontuacaoBase * (experiencia / 1000.0);
        
        // Pontuação final combinando todos os fatores
        int pontuacaoFinal = (int)(pontuacaoBase + bonus + bonusExperiencia);
        
        System.out.println("Pontuação calculada: " + pontuacaoFinal + 
                           " (Base: " + pontuacaoBase + 
                           ", Bônus de nível: " + bonus + 
                           ", Bônus de experiência: " + (int)bonusExperiencia + ")");
        
        return pontuacaoFinal;
    }
    
    // Getters e setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    // Adicionar getter e setter
    public ConfiguracaoJogador getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(ConfiguracaoJogador configuracao) {
        this.configuracao = configuracao;
    }
}