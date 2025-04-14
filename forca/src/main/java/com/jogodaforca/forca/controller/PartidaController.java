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

import com.jogodaforca.forca.dto.IniciarPartidaComDificuldadeDTO;
import com.jogodaforca.forca.dto.IniciarPartidaDTO;
import com.jogodaforca.forca.dto.PartidaDTO;
import com.jogodaforca.forca.dto.PartidaDetalhadaDTO;
import com.jogodaforca.forca.dto.TentativaDTO;
import com.jogodaforca.forca.model.Partida;
import com.jogodaforca.forca.service.PartidaService;
import com.jogodaforca.forca.util.Resultado;

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarPartida(@RequestBody IniciarPartidaDTO iniciarPartidaDTO) {
        Resultado<Partida> resultado = partidaService.iniciarPartida(
                iniciarPartidaDTO.getUsuarioId(),
                iniciarPartidaDTO.getPalavraCustomizada(),
                iniciarPartidaDTO.getDica());
        
        if (resultado.isSucesso()) {
            return ResponseEntity.ok(PartidaDTO.fromPartida(resultado.getDados()));
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensagem", resultado.getMensagem());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/{partidaId}/tentar")
    public ResponseEntity<?> tentarLetra(@PathVariable Long partidaId, @RequestBody TentativaDTO tentativa) {
        Resultado<Partida> resultado = partidaService.tentarLetra(partidaId, tentativa.getLetra());
        
        if (resultado.isSucesso()) {
            return ResponseEntity.ok(PartidaDTO.fromPartida(resultado.getDados()));
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensagem", resultado.getMensagem());
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
        Resultado<Partida> resultado = partidaService.buscarPartida(partidaId);
        
        if (resultado.isSucesso()) {
            return ResponseEntity.ok(PartidaDTO.fromPartida(resultado.getDados()));
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensagem", resultado.getMensagem());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/{partidaId}/desistir")
    public ResponseEntity<?> desistirPartida(@PathVariable Long partidaId) {
        Resultado<Partida> resultado = partidaService.desistirPartida(partidaId);
        
        if (resultado.isSucesso()) {
            return ResponseEntity.ok(PartidaDTO.fromPartida(resultado.getDados()));
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensagem", resultado.getMensagem());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    // Adicionar endpoints para diferentes dificuldades
    @PostMapping("/iniciar/dificuldade")
    public ResponseEntity<?> iniciarPartidaComDificuldade(
            @RequestBody IniciarPartidaComDificuldadeDTO dto) {
        Resultado<Partida> resultado;
        
        if (dto.getPalavraCustomizada() != null && !dto.getPalavraCustomizada().isEmpty()) {
            resultado = partidaService.iniciarPartida(
                    dto.getUsuarioId(), 
                    dto.getPalavraCustomizada(), 
                    dto.getDica(), 
                    dto.getDificuldade());
        } else {
            resultado = partidaService.iniciarPartida(dto.getUsuarioId(), dto.getDificuldade());
        }
        
        if (resultado.isSucesso()) {
            return ResponseEntity.ok(PartidaDTO.fromPartida(resultado.getDados()));
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensagem", resultado.getMensagem());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{partidaId}/detalhada")
    public ResponseEntity<?> getPartidaDetalhada(@PathVariable Long partidaId) {
        Resultado<Partida> resultado = partidaService.buscarPartida(partidaId);
        
        if (resultado.isSucesso()) {
            return ResponseEntity.ok(PartidaDetalhadaDTO.fromPartida(resultado.getDados()));
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensagem", resultado.getMensagem());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/usuario/{usuarioId}/detalhadas")
    public ResponseEntity<List<PartidaDetalhadaDTO>> listarPartidasDetalhadasUsuario(@PathVariable Long usuarioId) {
        List<Partida> partidas = partidaService.listarPartidasPorUsuario(usuarioId);
        List<PartidaDetalhadaDTO> partidasDTO = partidas.stream()
                .map(PartidaDetalhadaDTO::fromPartida)
                .collect(Collectors.toList());
        return ResponseEntity.ok(partidasDTO);
    }
}