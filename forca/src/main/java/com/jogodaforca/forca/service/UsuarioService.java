package com.jogodaforca.forca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jogodaforca.forca.model.Usuario;
import com.jogodaforca.forca.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Usuario cadastrarUsuario(Usuario usuario) {
        // Verificar se login já existe
        if (usuarioRepository.findByLogin(usuario.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Login já existe");
        }
        
        // Validar dados
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        
        if (usuario.getLogin() == null || usuario.getLogin().trim().isEmpty()) {
            throw new IllegalArgumentException("Login é obrigatório");
        }
        
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        
        // Inicializa estatísticas
        usuario.setVitorias(0);
        usuario.setDerrotas(0);
        
        // Salvar no banco de dados
        return usuarioRepository.save(usuario);
    }
    
    // Método para autenticação de usuário
    public Usuario autenticar(String login, String senha) {
        return usuarioRepository.findByLogin(login)
                .filter(usuario -> usuario.getSenha().equals(senha))
                .orElseThrow(() -> new IllegalArgumentException("Login ou senha inválidos"));
    }
}
