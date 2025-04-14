package com.jogodaforca.forca.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jogodaforca.forca.model.Palavra;
import com.jogodaforca.forca.model.Partida;
import com.jogodaforca.forca.model.Usuario;
import com.jogodaforca.forca.repository.PalavraRepository;
import com.jogodaforca.forca.repository.PartidaRepository;

import jakarta.annotation.PostConstruct;

/**
 * Serviço responsável pelo gerenciamento de palavras do jogo.
 * 
 * CONCEITO: ABSTRAÇÃO
 * - Encapsula a lógica de negócio relacionada às palavras do jogo
 * - Separa a implementação do acesso e manipulação de palavras da camada de controle
 * 
 * CONCEITO: HERANÇA
 * - Estende AbstractService para herdar operações CRUD básicas
 * - Exemplo de especialização, onde a subclasse adiciona comportamentos específicos
 */
@Service
public class PalavraService extends AbstractService<Palavra, Long> {

    @Autowired
    private PalavraRepository palavraRepository;
    
    @Autowired
    private PartidaRepository partidaRepository;
    
    /**
     * CONCEITO: ENCAPSULAMENTO
     * - Atributos privados acessíveis apenas dentro desta classe
     */
    private final Random random = new Random();
    private final List<Palavra> palavrasEmCache = new ArrayList<>();
    
    /**
     * Inicializa o serviço carregando palavras do arquivo.
     * 
     * CONCEITO: CICLO DE VIDA
     * - @PostConstruct é executado após a injeção de dependências
     * - Demonstra o ciclo de vida gerenciado pelo framework Spring
     */
    @PostConstruct
    public void init() {
        carregarPalavrasDoArquivo();
    }
    
    /**
     * Carrega palavras de um arquivo de texto e as salva no banco de dados.
     * 
     * CONCEITO: ENCAPSULAMENTO
     * - Método privado usado apenas internamente pela classe
     * - Esconde a implementação de como as palavras são carregadas
     * 
     * CONCEITO: TRATAMENTO DE EXCEÇÕES ESPECÍFICAS
     * - Captura e trata diferentes tipos de exceções de forma adequada
     * - Demonstra melhor prática de programação defensiva
     */
    private void carregarPalavrasDoArquivo() {
        try {
            ClassPathResource resource = new ClassPathResource("palavras.txt");
            InputStream inputStream = resource.getInputStream();
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    String[] partes = linha.split(",");
                    if (partes.length >= 2) {
                        String palavraSecreta = partes[0].trim();
                        String dica = partes[1].trim();
                        
                        Palavra palavra = new Palavra(palavraSecreta, dica);
                        palavrasEmCache.add(palavra);
                    }
                }
            }
            
            // Salvar no banco de dados
            if (!palavrasEmCache.isEmpty()) {
                palavraRepository.saveAll(palavrasEmCache);
                System.out.println("Carregadas " + palavrasEmCache.size() + " palavras do arquivo.");
            }
        } catch (IOException e) {
            // Exceção específica para erros de leitura de arquivo
            System.err.println("Erro ao ler o arquivo de palavras: " + e.getMessage());
        } catch (DataAccessException e) {
            // Exceção específica do Spring para problemas de acesso ao banco de dados
            System.err.println("Erro ao salvar palavras no banco de dados: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Exceção para argumentos inválidos
            System.err.println("Argumento inválido ao processar palavras: " + e.getMessage());
        } catch (Exception e) {
            // Mantém um catch genérico no final para qualquer outra exceção não prevista
            // Mas agora é menos abrangente, pois as exceções mais comuns já foram tratadas acima
            System.err.println("Erro inesperado ao carregar palavras: " + e.getMessage());
            // Em ambiente de produção, seria ideal logar o stack trace completo
            // e.printStackTrace();
        }
    }
    
    /**
     * Obtém uma palavra aleatória que o usuário ainda não jogou.
     * 
     * CONCEITO: RESPONSABILIDADE ÚNICA
     * - Método tem responsabilidade bem definida: obter uma palavra não jogada
     * - Implementa lógica de negócio específica para este requisito
     * 
     * @param usuario O usuário para o qual será selecionada uma palavra
     * @return Uma palavra aleatória ainda não jogada pelo usuário
     */
    public Palavra obterPalavraAleatoria(Usuario usuario) {
        // Obter palavras já jogadas pelo usuário
        List<Partida> partidasDoUsuario = partidaRepository.findByUsuarioOrderByDataInicioDesc(usuario);
        Set<String> palavrasJaJogadas = partidasDoUsuario.stream()
                .map(p -> p.getPalavra().getPalavraSecreta().toLowerCase())
                .collect(Collectors.toSet());
        
        // Filtrar palavras que o usuário ainda não jogou
        List<Palavra> palavrasDisponiveis = palavraRepository.findAll().stream()
                .filter(p -> !palavrasJaJogadas.contains(p.getPalavraSecreta().toLowerCase()))
                .collect(Collectors.toList());
        
        if (palavrasDisponiveis.isEmpty()) {
            // Se todas as palavras já foram jogadas, podemos retornar qualquer palavra
            List<Palavra> todasPalavras = palavraRepository.findAll();
            if (todasPalavras.isEmpty()) {
                throw new IllegalStateException("Não há palavras disponíveis no sistema");
            }
            return todasPalavras.get(random.nextInt(todasPalavras.size()));
        }
        
        // Escolher uma palavra aleatória dentre as disponíveis
        return palavrasDisponiveis.get(random.nextInt(palavrasDisponiveis.size()));
    }
    
    /**
     * Cria uma nova palavra customizada e a salva no banco de dados.
     * 
     * CONCEITO: SOBRECARGA DE MÉTODOS (COMO PARTE DO SISTEMA)
     * - Trabalha em conjunto com o método obterPalavraAleatoria()
     * - Oferece diferentes formas de obter uma palavra para o jogo
     * 
     * @param palavraSecreta A palavra a ser adivinhada
     * @param dica Uma dica para ajudar a adivinhar a palavra
     * @return A palavra criada e salva no banco de dados
     */
    public Palavra criarPalavraCustomizada(String palavraSecreta, String dica) {
        // Validação
        if (palavraSecreta == null || palavraSecreta.trim().isEmpty()) {
            throw new IllegalArgumentException("A palavra não pode ser vazia");
        }
        
        Palavra palavra = new Palavra(palavraSecreta, dica);
        return palavraRepository.save(palavra);
    }
    
    /**
     * CONCEITO: SOBRESCRITA (OVERRIDE)
     * - Implementa método abstrato definido na superclasse
     * - Fornece o repositório específico para operações com Palavra
     */
    @Override
    protected JpaRepository<Palavra, Long> getRepository() {
        return palavraRepository;
    }

    // Adicionar à classe PalavraService ou criar uma classe utilitária StringUtils
    /**
     * CONCEITO: MÉTODO ESTÁTICO DE UTILIDADE
     * - Método que não depende de estado de objeto
     * - Pode ser chamado sem instância (diretamente pela classe)
     * - Adequado para operações de transformação puras
     * 
     * @param input String para normalizar
     * @return String sem acentos e em minúsculas
     */
    public static String normalizarTexto(String input) {
        if (input == null) return null;
        return input.toLowerCase()
            .replaceAll("[áàãâä]", "a")
            .replaceAll("[éèêë]", "e")
            .replaceAll("[íìîï]", "i")
            .replaceAll("[óòõôö]", "o")
            .replaceAll("[úùûü]", "u")
            .replaceAll("[ç]", "c");
    }
}