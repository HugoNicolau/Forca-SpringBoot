package com.jogodaforca.forca.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jogodaforca.forca.model.Jogador;
import com.jogodaforca.forca.model.JogadorBot;
import com.jogodaforca.forca.model.JogadorHumano;
import com.jogodaforca.forca.model.Palavra;
import com.jogodaforca.forca.model.Usuario;
import com.jogodaforca.forca.repository.PalavraRepository;

/**
 * CONCEITO: SERVIÇO
 * - Encapsula a lógica de negócio relacionada aos jogadores
 * - Acessa repositórios e interage com entidades
 * - Separa responsabilidades de acesso a dados (repositórios) da lógica de negócio
 */
@Service
public class JogadorService {
    
    @Autowired
    private PalavraRepository palavraRepository;
    
    @Autowired
    private PalavraService palavraService;
    
    /**
     * Escolhe uma palavra para um jogador Bot.
     * 
     * CONCEITO: MÉTODO ESPECÍFICO
     * - Implementa a lógica de escolha de palavra para um tipo específico de jogador
     * - Demonstra como a lógica de negócio fica no serviço, não na entidade
     * 
     * @param bot O jogador Bot para quem escolher a palavra
     * @return A palavra escolhida, ou null se não houver palavras disponíveis
     */
    public Palavra escolherPalavraPara(JogadorBot bot) {
        // Lógica de negócio específica para bots
        Random random = new Random();
        var palavras = palavraRepository.findAll();
        if (!palavras.isEmpty()) {
            return palavras.get(random.nextInt(palavras.size()));
        }
        return null;
    }
    
    /**
     * Escolhe uma palavra para um jogador humano, considerando o histórico do usuário.
     * 
     * CONCEITO: SOBRECARGA DE MÉTODOS
     * - Mesmo nome de método, mas com parâmetros diferentes
     * - Comportamento específico para cada tipo de jogador
     * 
     * @param humano O jogador humano para quem escolher a palavra
     * @param usuario O usuário associado ao jogador humano
     * @return A palavra escolhida, ou null se não houver palavras disponíveis
     */
    public Palavra escolherPalavraPara(JogadorHumano humano, Usuario usuario) {
        // Lógica de negócio específica para humanos
        // Delega para o PalavraService, que tem a lógica para considerar o histórico do usuário
        if (usuario != null) {
            return palavraService.obterPalavraAleatoria(usuario);
        } else {
            // Fallback se não tiver usuário associado
            var palavras = palavraRepository.findAll();
            if (!palavras.isEmpty()) {
                return palavras.get(new Random().nextInt(palavras.size()));
            }
        }
        return null;
    }
    
    /**
     * Calcula a pontuação final para um jogador específico.
     * 
     * CONCEITO: POLIMORFISMO
     * - Usa o tipo abstrato Jogador como parâmetro
     * - Funciona com qualquer tipo concreto de Jogador
     * - Demonstra como usar o método polimórfico do jogador
     * 
     * @param jogador O jogador para calcular a pontuação
     * @param tentativasRestantes Número de tentativas restantes na partida
     * @param tamanhoPalavra Tamanho da palavra da partida
     * @return A pontuação calculada
     */
    public int calcularPontuacaoFinal(Jogador jogador, int tentativasRestantes, int tamanhoPalavra) {
        // Usa o método polimórfico do jogador, que pode ser diferente para cada tipo
        int pontuacaoBase = jogador.calcularPontuacao(tentativasRestantes, tamanhoPalavra);
        
        // Adiciona lógica adicional específica do serviço
        return pontuacaoBase * 10; // Multiplicador para pontuação final
    }
}