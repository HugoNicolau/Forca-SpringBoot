package com.jogodaforca.forca.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jogodaforca.forca.dto.RankingDTO;
import com.jogodaforca.forca.model.Usuario;
import com.jogodaforca.forca.repository.UsuarioRepository;

@Service
public class RankingService {
    
    private final UsuarioRepository usuarioRepository;
    
    public RankingService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    public List<RankingDTO> obterRanking() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        
        return usuarios.stream()
            .map(RankingDTO::fromUsuario)
            .sorted(Comparator.comparing(RankingDTO::getWinRate).reversed())
            .collect(Collectors.toList());
    }
}