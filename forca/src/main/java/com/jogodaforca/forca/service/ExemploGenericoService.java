package com.jogodaforca.forca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jogodaforca.forca.model.Palavra;
import com.jogodaforca.forca.model.Usuario;
import com.jogodaforca.forca.util.Repositorio;

/**
 * Serviço de exemplo para demonstrar o uso de classes genéricas.
 * 
 * CONCEITO: USO DE CLASSES GENÉRICAS
 * - Demonstra como instanciar e utilizar classes genéricas
 * - Mostra como o tipo concreto substitui o parâmetro de tipo
 */
@Service
public class ExemploGenericoService {
    
    // Instâncias de repositório com diferentes tipos concretos
    private final Repositorio<Usuario, Long> repositorioUsuarios = new Repositorio<>();
    private final Repositorio<Palavra, Long> repositorioPalavras = new Repositorio<>();
    
    /**
     * Demonstra o uso de um repositório genérico de usuários.
     * 
     * @param usuario O usuário a ser salvo
     * @param id O id do usuário
     * @return O próprio usuário
     */
    public Usuario salvarUsuario(Usuario usuario, Long id) {
        return repositorioUsuarios.salvar(id, usuario);
    }
    
    /**
     * Obtém o nome de um usuário pelo id.
     * 
     * CONCEITO: USO DE MÉTODO GENÉRICO
     * - Demonstra como usar o método genérico 'processar'
     * - Extrair apenas uma propriedade do objeto
     * 
     * @param id O id do usuário
     * @return O nome do usuário, se encontrado
     */
    public String obterNomeUsuario(Long id) {
        return repositorioUsuarios.processar(id, Usuario::getNome).orElse("Usuário não encontrado");
    }
    
    /**
     * Salva várias palavras no repositório.
     * 
     * @param palavras Lista de palavras
     */
    public void salvarPalavras(List<Palavra> palavras) {
        for (int i = 0; i < palavras.size(); i++) {
            repositorioPalavras.salvar((long) i, palavras.get(i));
        }
        
        System.out.println("Total de palavras salvas: " + repositorioPalavras.tamanho());
    }
    
    /**
     * Obtém apenas as dicas das palavras.
     * 
     * @return Lista de dicas
     */
    public List<String> obterTodasAsDicas() {
        return repositorioPalavras.listarTodos().stream()
                .map(Palavra::getDica)
                .toList();
    }
}