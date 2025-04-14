package com.jogodaforca.forca.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jogodaforca.forca.dto.UsuarioLoginDTO;
import com.jogodaforca.forca.model.Usuario;
import com.jogodaforca.forca.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.ok(novoUsuario);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensagem", e.getMessage());
            return ResponseEntity.badRequest().body(error); // 400 Bad Request com mensagem
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginDTO loginRequest) {
        try {
            Usuario usuario = usuarioService.autenticar(loginRequest.getLogin(), loginRequest.getSenha());
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensagem", e.getMessage());
            return ResponseEntity.status(401).body(error); // 401 Unauthorized com mensagem
        }
    }

    @DeleteMapping("/{usuarioId}/historico-palavras")
    public ResponseEntity<?> apagarHistoricoPalavras(@PathVariable Long usuarioId) {
        try {
            usuarioService.apagarHistoricoPalavras(usuarioId);
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Histórico de palavras apagado com sucesso");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensagem", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
