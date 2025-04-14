package com.jogodaforca.forca.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jogodaforca.forca.model.LetraTentada;
import com.jogodaforca.forca.model.Palavra;
import com.jogodaforca.forca.model.Partida;
import com.jogodaforca.forca.model.StatusPartida;
import com.jogodaforca.forca.model.Usuario;
import com.jogodaforca.forca.repository.PartidaRepository;
import com.jogodaforca.forca.repository.UsuarioRepository;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PalavraService palavraService;
    
    @Transactional
    public Partida iniciarPartida(Long usuarioId, String palavraCustomizada, String dica) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        Palavra palavra;
        if (palavraCustomizada != null && !palavraCustomizada.trim().isEmpty()) {
            // Usar palavra customizada
            palavra = palavraService.criarPalavraCustomizada(palavraCustomizada, dica);
        } else {
            // Buscar palavra aleatória que o usuário ainda não jogou
            palavra = palavraService.obterPalavraAleatoria(usuario);
        }
        
        Partida partida = new Partida();
        partida.setUsuario(usuario);
        partida.setPalavra(palavra);
        partida.setDataInicio(LocalDateTime.now());
        partida.setStatus(StatusPartida.EM_ANDAMENTO);
        partida.setTentativasRestantes(6); // Padrão: 6 tentativas
        
        return partidaRepository.save(partida);
    }
    
    @Transactional
    public Partida tentarLetra(Long partidaId, char letra) {
        letra = Character.toLowerCase(letra);
        
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new IllegalArgumentException("Partida não encontrada"));
        
        // Verificar se a partida ainda está em andamento
        if (partida.getStatus() != StatusPartida.EM_ANDAMENTO) {
            throw new IllegalArgumentException("Esta partida já foi finalizada");
        }
        
        // Verificar se a letra já foi tentada
        if (partida.contemLetraTentada(letra)) {
            throw new IllegalArgumentException("Esta letra já foi tentada");
        }
        
        // Verificar se a letra está na palavra secreta
        boolean acerto = partida.getPalavra().getPalavraSecreta().indexOf(letra) >= 0;
        
        // Criar nova tentativa
        LetraTentada letraTentada = new LetraTentada();
        letraTentada.setLetra(letra);
        letraTentada.setAcerto(acerto);
        
        // Adicionar à partida e atualizar estado
        partida.adicionarLetraTentada(letraTentada);
        
        return partidaRepository.save(partida);
    }
    
    public Partida buscarPartida(Long partidaId) {
        return partidaRepository.findById(partidaId)
                .orElseThrow(() -> new IllegalArgumentException("Partida não encontrada"));
    }
    
    public List<Partida> listarPartidasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        return partidaRepository.findByUsuarioOrderByDataInicioDesc(usuario);
    }
    
    @Transactional
    public Partida desistirPartida(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new IllegalArgumentException("Partida não encontrada"));
        
        if (partida.getStatus() != StatusPartida.EM_ANDAMENTO) {
            throw new IllegalArgumentException("Esta partida já foi finalizada");
        }
        
        partida.setStatus(StatusPartida.DESISTIU);
        partida.setDataFim(LocalDateTime.now());
        partida.getUsuario().setDerrotas(partida.getUsuario().getDerrotas() + 1);
        
        return partidaRepository.save(partida);
    }
}