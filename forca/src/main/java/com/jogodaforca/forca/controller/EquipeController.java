package com.jogodaforca.forca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jogodaforca.forca.dto.EquipeDTO;
import com.jogodaforca.forca.service.EquipeService;
import com.jogodaforca.forca.util.Resultado;

/**
 * Controller para endpoints relacionados a equipes.
 */
@RestController
@RequestMapping("/api/equipes")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;
    
    /**
     * Lista todas as equipes.
     * 
     * @return Lista de DTOs de equipes
     */
    @GetMapping
    public ResponseEntity<List<EquipeDTO>> listarEquipes() {
        List<EquipeDTO> equipes = equipeService.listarEquipes();
        return ResponseEntity.ok(equipes);
    }
    
    /**
     * Busca uma equipe pelo ID.
     * 
     * @param id ID da equipe
     * @return DTO da equipe ou erro 404 se não encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarEquipe(@PathVariable Long id) {
        Resultado<EquipeDTO> resultado = equipeService.buscarPorId(id);
        
        if (resultado.isSucesso()) {
            return ResponseEntity.ok(resultado.getDados());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Cria uma nova equipe.
     * 
     * @param equipeDTO DTO com dados da nova equipe
     * @return DTO da equipe criada ou erro 400 em caso de falha
     */
    @PostMapping
    public ResponseEntity<?> criarEquipe(@RequestBody EquipeDTO equipeDTO) {
        Resultado<EquipeDTO> resultado = equipeService.criarEquipe(equipeDTO);
        
        if (resultado.isSucesso()) {
            return ResponseEntity.ok(resultado.getDados());
        } else {
            return ResponseEntity.badRequest().body(resultado);
        }
    }
    
    /**
     * Atualiza uma equipe existente.
     * 
     * @param id ID da equipe a ser atualizada
     * @param equipeDTO DTO com novos dados da equipe
     * @return DTO da equipe atualizada ou erro 404/400 em caso de falha
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEquipe(@PathVariable Long id, @RequestBody EquipeDTO equipeDTO) {
        Resultado<EquipeDTO> resultado = equipeService.atualizarEquipe(id, equipeDTO);
        
        if (resultado.isSucesso()) {
            return ResponseEntity.ok(resultado.getDados());
        } else if (resultado.getMensagem().contains("não encontrada")) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.badRequest().body(resultado);
        }
    }
    
    /**
     * Exclui uma equipe.
     * 
     * @param id ID da equipe a ser excluída
     * @return Mensagem de sucesso ou erro 404 se não encontrada
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirEquipe(@PathVariable Long id) {
        Resultado<?> resultado = equipeService.excluirEquipe(id);
        
        if (resultado.isSucesso()) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Adiciona um jogador a uma equipe.
     * 
     * @param id ID da equipe
     * @param jogadorId ID do jogador
     * @return Mensagem de sucesso ou erro em caso de falha
     */
    @PostMapping("/{id}/jogadores/{jogadorId}")
    public ResponseEntity<?> adicionarJogador(@PathVariable("id") Long equipeId, @PathVariable Long jogadorId) {
        Resultado<?> resultado = equipeService.adicionarJogador(equipeId, jogadorId);
        
        if (resultado.isSucesso()) {
            return ResponseEntity.ok(resultado);
        } else if (resultado.getMensagem().contains("não encontrad")) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.badRequest().body(resultado);
        }
    }
    
    /**
     * Remove um jogador de uma equipe.
     * 
     * @param id ID da equipe
     * @param jogadorId ID do jogador
     * @return Mensagem de sucesso ou erro em caso de falha
     */
    @DeleteMapping("/{id}/jogadores/{jogadorId}")
    public ResponseEntity<?> removerJogador(@PathVariable("id") Long equipeId, @PathVariable Long jogadorId) {
        Resultado<?> resultado = equipeService.removerJogador(equipeId, jogadorId);
        
        if (resultado.isSucesso()) {
            return ResponseEntity.ok(resultado);
        } else if (resultado.getMensagem().contains("não encontrad")) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.badRequest().body(resultado);
        }
    }
    
    /**
     * Obtém o ranking de equipes ordenado por pontuação.
     * 
     * @return Lista de equipes ordenada por pontuação
     */
    @GetMapping("/ranking")
    public ResponseEntity<List<EquipeDTO>> obterRanking() {
        List<EquipeDTO> ranking = equipeService.obterRankingEquipes();
        return ResponseEntity.ok(ranking);
    }
    
    /**
     * Obtém estatísticas detalhadas de uma equipe.
     * 
     * @param id ID da equipe
     * @return Estatísticas da equipe ou erro 404 se não encontrada
     */
    @GetMapping("/{id}/estatisticas")
    public ResponseEntity<?> obterEstatisticas(@PathVariable Long id) {
        Resultado<EquipeDTO> resultado = equipeService.obterEstatisticasEquipe(id);
        
        if (resultado.isSucesso()) {
            return ResponseEntity.ok(resultado.getDados());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Busca equipes por nome parcial.
     *
     * @param nome Parte do nome a pesquisar
     * @return Lista de equipes encontradas
     */
    @GetMapping("/busca")
    public ResponseEntity<List<EquipeDTO>> buscarPorNome(@RequestParam String nome) {
        List<EquipeDTO> equipes = equipeService.buscarPorNomeParcial(nome);
        return ResponseEntity.ok(equipes);
    }

    /**
     * Busca equipes com pontuação mínima.
     *
     * @param pontuacao Pontuação mínima
     * @return Lista de equipes com pontuação superior
     */
    @GetMapping("/pontuacao")
    public ResponseEntity<List<EquipeDTO>> buscarPorPontuacao(@RequestParam int pontuacao) {
        List<EquipeDTO> equipes = equipeService.buscarEquipesComPontuacaoSuperior(pontuacao);
        return ResponseEntity.ok(equipes);
    }

    /**
     * Busca equipes com um número mínimo de jogadores.
     *
     * @param quantidade Número mínimo de jogadores
     * @return Lista de equipes que atendem ao critério
     */
    @GetMapping("/jogadores/minimo")
    public ResponseEntity<List<EquipeDTO>> buscarComMinimoJogadores(@RequestParam int quantidade) {
        List<EquipeDTO> equipes = equipeService.buscarEquipesComMinimoJogadores(quantidade);
        return ResponseEntity.ok(equipes);
    }

    /**
     * Obtém as top N equipes por pontuação.
     *
     * @param quantidade Número de equipes a retornar (padrão: 10)
     * @return Lista das top N equipes
     */
    @GetMapping("/top")
    public ResponseEntity<List<EquipeDTO>> obterTopEquipes(@RequestParam(defaultValue = "10") int quantidade) {
        List<EquipeDTO> equipes = equipeService.obterTopEquipes(quantidade);
        return ResponseEntity.ok(equipes);
    }
}