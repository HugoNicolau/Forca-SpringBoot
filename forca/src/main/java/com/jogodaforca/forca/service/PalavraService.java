package com.jogodaforca.forca.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.jogodaforca.forca.model.Palavra;
import com.jogodaforca.forca.model.Partida;
import com.jogodaforca.forca.model.Usuario;
import com.jogodaforca.forca.repository.PalavraRepository;
import com.jogodaforca.forca.repository.PartidaRepository;

import jakarta.annotation.PostConstruct;

@Service
public class PalavraService {

    @Autowired
    private PalavraRepository palavraRepository;
    
    @Autowired
    private PartidaRepository partidaRepository;
    
    private final Random random = new Random();
    private List<Palavra> palavrasEmCache = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        carregarPalavrasDoArquivo();
    }
    
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
        } catch (Exception e) {
            System.err.println("Erro ao carregar palavras do arquivo: " + e.getMessage());
        }
    }
    
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
    
    public Palavra criarPalavraCustomizada(String palavraSecreta, String dica) {
        Palavra palavra = new Palavra(palavraSecreta, dica);
        return palavraRepository.save(palavra);
    }
}