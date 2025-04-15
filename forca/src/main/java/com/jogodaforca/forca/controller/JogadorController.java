package com.jogodaforca.forca.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jogodaforca.forca.dto.JogadorDTO;
import com.jogodaforca.forca.service.JogadorService;
import com.jogodaforca.forca.util.Resultado;

/**
 * Controller para operações relacionadas a jogadores.
 */
@RestController
@RequestMapping("/api/jogadores")
public class JogadorController {
    
    @Autowired
    private JogadorService jogadorService;
    
    /**
     * Busca um jogador pelo ID do usuário associado.
     * 
     * @param usuarioId ID do usuário
     * @return DTO do jogador humano ou erro 404 se não encontrado
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obterJogadorPorUsuario(@PathVariable Long usuarioId) {
        Resultado<JogadorDTO> resultado = jogadorService.obterJogadorPorUsuario(usuarioId);
        
        if (resultado.isSucesso()) {
            return ResponseEntity.ok(resultado.getDados());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensagem", resultado.getMensagem());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}