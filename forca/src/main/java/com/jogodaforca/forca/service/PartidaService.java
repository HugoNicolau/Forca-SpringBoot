package com.jogodaforca.forca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jogodaforca.forca.model.Palavra;
import com.jogodaforca.forca.model.Partida;
import com.jogodaforca.forca.model.Usuario;
import com.jogodaforca.forca.repository.PartidaRepository;
import com.jogodaforca.forca.repository.UsuarioRepository;
import com.jogodaforca.forca.util.Resultado;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PalavraService palavraService;
    
    // Método principal para iniciar partida
    @Transactional
    public Resultado<Partida> iniciarPartida(Long usuarioId, String palavraCustomizada, String dica) {
        try {
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
            
            Partida partida = new Partida(usuario, palavra);
            return Resultado.sucesso(partidaRepository.save(partida));
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
    // Sobrecarga para iniciar partida com dificuldade
    @Transactional
    public Resultado<Partida> iniciarPartida(Long usuarioId, String dificuldade) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            
            // Buscar palavra aleatória que o usuário ainda não jogou
            Palavra palavra = palavraService.obterPalavraAleatoria(usuario);
            
            Partida partida = new Partida(usuario, palavra, dificuldade);
            return Resultado.sucesso(partidaRepository.save(partida));
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
    // Sobrecarga para iniciar partida com palavra específica e dificuldade
    @Transactional
    public Resultado<Partida> iniciarPartida(Long usuarioId, String palavraCustomizada, String dica, String dificuldade) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
            
            Palavra palavra = palavraService.criarPalavraCustomizada(palavraCustomizada, dica);
            
            Partida partida = new Partida(usuario, palavra, dificuldade);
            return Resultado.sucesso(partidaRepository.save(partida));
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
    @Transactional
    public Resultado<Partida> tentarLetra(Long partidaId, char letra) {
        try {
            letra = Character.toLowerCase(letra);
            
            Partida partida = partidaRepository.findById(partidaId)
                    .orElseThrow(() -> new IllegalArgumentException("Partida não encontrada"));
            
            boolean acerto = partida.tentarLetra(letra);
            
            partidaRepository.save(partida);
            
            // Incluir informação de acerto/erro na mensagem de retorno
            String mensagem = acerto ? 
                    "Letra correta! Você acertou." : 
                    "Letra incorreta! Tente novamente.";
            
            return Resultado.sucesso(mensagem, partida);
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
    public Resultado<Partida> buscarPartida(Long partidaId) {
        try {
            Partida partida = partidaRepository.findById(partidaId)
                    .orElseThrow(() -> new IllegalArgumentException("Partida não encontrada"));
            return Resultado.sucesso(partida);
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
    public List<Partida> listarPartidasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        return partidaRepository.findByUsuarioOrderByDataInicioDesc(usuario);
    }
    
    @Transactional
    public Resultado<Partida> desistirPartida(Long partidaId) {
        try {
            Partida partida = partidaRepository.findById(partidaId)
                    .orElseThrow(() -> new IllegalArgumentException("Partida não encontrada"));
            
            partida.desistir();
            partidaRepository.save(partida);
            
            return Resultado.sucesso(partida);
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }
}