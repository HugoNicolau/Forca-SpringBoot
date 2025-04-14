package com.jogodaforca.forca.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jogodaforca.forca.dto.IniciarPartidaDTO;
import com.jogodaforca.forca.dto.PartidaDTO;
import com.jogodaforca.forca.dto.TentativaDTO;
import com.jogodaforca.forca.model.Partida;
import com.jogodaforca.forca.service.PartidaService;

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarPartida(@RequestBody IniciarPartidaDTO iniciarPartidaDTO) {
        try {
            Partida partida = partidaService.iniciarPartida(
                    iniciarPartidaDTO.getUsuarioId(), 
                    iniciarPartidaDTO.getPalavraCustomizada(),
                    iniciarPartidaDTO.getDica());
            return ResponseEntity.ok(PartidaDTO.fromPartida(partida));
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensagem", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/{partidaId}/tentar")
    public ResponseEntity<?> tentarLetra(@PathVariable Long partidaId, @RequestBody TentativaDTO tentativa) {
        try {
            Partida partida = partidaService.tentarLetra(partidaId, tentativa.getLetra());
            return ResponseEntity.ok(PartidaDTO.fromPartida(partida));
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensagem", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PartidaDTO>> listarPartidasUsuario(@PathVariable Long usuarioId) {
        List<Partida> partidas = partidaService.listarPartidasPorUsuario(usuarioId);
        List<PartidaDTO> partidasDTO = partidas.stream()
                .map(PartidaDTO::fromPartida)
                .collect(Collectors.toList());
        return ResponseEntity.ok(partidasDTO);
    }
    
    @GetMapping("/{partidaId}")
    public ResponseEntity<?> getPartida(@PathVariable Long partidaId) {
        try {
            Partida partida = partidaService.buscarPartida(partidaId);
            return ResponseEntity.ok(PartidaDTO.fromPartida(partida));
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensagem", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/{partidaId}/desistir")
    public ResponseEntity<?> desistirPartida(@PathVariable Long partidaId) {
        try {
            Partida partida = partidaService.desistirPartida(partidaId);
            return ResponseEntity.ok(PartidaDTO.fromPartida(partida));
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensagem", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}