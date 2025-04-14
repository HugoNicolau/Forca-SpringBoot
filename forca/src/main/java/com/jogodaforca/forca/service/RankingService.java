package com.jogodaforca.forca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jogodaforca.forca.dto.RankingDTO;
import com.jogodaforca.forca.model.Usuario;
import com.jogodaforca.forca.repository.UsuarioRepository;

@Service
public class RankingService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<RankingDTO> obterRanking() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        
        // Ordenar por taxa de vitória (decrescente) e depois por número de vitórias (decrescente)
        return usuarios.stream()
                .filter(u -> u.getTotalPartidas() > 0) // Filtra apenas usuários que jogaram pelo menos uma partida
                .sorted((u1, u2) -> {
                    int compareWinRate = Double.compare(u2.calcularWinRate(), u1.calcularWinRate());
                    if (compareWinRate != 0) {
                        return compareWinRate;
                    }
                    return Integer.compare(u2.getVitorias(), u1.getVitorias());
                })
                .map(RankingDTO::fromUsuario)
                .collect(Collectors.toList());
    }
}