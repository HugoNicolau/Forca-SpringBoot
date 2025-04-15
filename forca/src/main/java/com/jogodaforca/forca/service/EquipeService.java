package com.jogodaforca.forca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jogodaforca.forca.dto.EquipeDTO;
import com.jogodaforca.forca.dto.JogadorDTO;
import com.jogodaforca.forca.model.Equipe;
import com.jogodaforca.forca.model.JogadorHumano;
import com.jogodaforca.forca.repository.EquipeRepository;
import com.jogodaforca.forca.repository.JogadorHumanoRepository;
import com.jogodaforca.forca.util.Resultado;

/**
 * Serviço para gerenciar operações relacionadas a equipes.
 */
@Service
public class EquipeService {

    @Autowired
    private EquipeRepository equipeRepository;
    
    @Autowired
    private JogadorHumanoRepository jogadorRepository;
    
    /**
     * Lista todas as equipes cadastradas.
     * 
     * @return Lista de DTOs de equipes
     */
    public List<EquipeDTO> listarEquipes() {
        return equipeRepository.findAll().stream()
                .map(equipe -> (EquipeDTO) equipe.toDTO())
                .collect(Collectors.toList());
    }
    
    /**
     * Busca uma equipe pelo ID.
     * 
     * @param id O ID da equipe
     * @return Um resultado contendo o DTO da equipe ou mensagem de erro
     */
    public Resultado<EquipeDTO> buscarPorId(Long id) {
        return equipeRepository.findById(id)
                .map(equipe -> Resultado.sucesso((EquipeDTO) equipe.toDTO()))
                .orElse(Resultado.falha("Equipe não encontrada"));
    }
    
    /**
     * Cria uma nova equipe.
     * 
     * @param equipeDTO DTO com dados da nova equipe
     * @return Um resultado contendo o DTO da equipe criada ou mensagem de erro
     */
    @Transactional
    public Resultado<EquipeDTO> criarEquipe(EquipeDTO equipeDTO) {
        try {
            // Verifica se já existe uma equipe com o mesmo nome
            if (existeEquipeComNome(equipeDTO.getNome())) {
                return Resultado.falha("Já existe uma equipe com este nome");
            }
            
            Equipe equipe = new Equipe();
            equipe.setNome(equipeDTO.getNome());
            
            Equipe equipeSalva = equipeRepository.save(equipe);
            return Resultado.sucesso("Equipe criada com sucesso", (EquipeDTO) equipeSalva.toDTO());
        } catch (Exception e) {
            return Resultado.falha("Erro ao criar equipe: " + e.getMessage());
        }
    }
    
    /**
     * Atualiza uma equipe existente.
     * 
     * @param id ID da equipe a ser atualizada
     * @param equipeDTO DTO com novos dados da equipe
     * @return Um resultado contendo o DTO da equipe atualizada ou mensagem de erro
     */
    @Transactional
    public Resultado<EquipeDTO> atualizarEquipe(Long id, EquipeDTO equipeDTO) {
        return equipeRepository.findById(id)
                .map(equipe -> {
                    equipe.setNome(equipeDTO.getNome());
                    Equipe equipeSalva = equipeRepository.save(equipe);
                    return Resultado.sucesso("Equipe atualizada com sucesso", (EquipeDTO) equipeSalva.toDTO());
                })
                .orElse(Resultado.falha("Equipe não encontrada"));
    }
    
    /**
     * Exclui uma equipe.
     * 
     * @param id ID da equipe a ser excluída
     * @return Um resultado indicando sucesso ou falha
     */
    @Transactional
    public Resultado<?> excluirEquipe(Long id) {
        return equipeRepository.findById(id)
                .map(equipe -> {
                    // Remover associação com jogadores antes de excluir
                    for (JogadorHumano jogador : equipe.getJogadores()) {
                        jogador.setEquipe(null);
                        jogadorRepository.save(jogador);
                    }
                    
                    equipeRepository.delete(equipe);
                    return Resultado.sucesso("Equipe excluída com sucesso");
                })
                .orElse(Resultado.falha("Equipe não encontrada"));
    }
    
    /**
     * Adiciona um jogador a uma equipe.
     * 
     * @param equipeId ID da equipe
     * @param jogadorId ID do jogador
     * @return Um resultado indicando sucesso ou falha
     */
    @Transactional
    public Resultado<?> adicionarJogador(Long equipeId, Long jogadorId) {
        try {
            Equipe equipe = equipeRepository.findById(equipeId)
                    .orElseThrow(() -> new IllegalArgumentException("Equipe não encontrada"));
            
            JogadorHumano jogador = jogadorRepository.findById(jogadorId)
                    .orElseThrow(() -> new IllegalArgumentException("Jogador não encontrado"));
            
            // Verificar se o jogador já está em outra equipe
            if (jogador.getEquipe() != null && !jogador.getEquipe().getId().equals(equipeId)) {
                return Resultado.falha("Jogador já pertence a outra equipe");
            }
            
            equipe.adicionarJogador(jogador);
            equipeRepository.save(equipe);
            
            return Resultado.sucesso("Jogador adicionado à equipe com sucesso");
        } catch (IllegalArgumentException e) {
            return Resultado.falha(e.getMessage());
        } catch (Exception e) {
            return Resultado.falha("Erro ao adicionar jogador à equipe: " + e.getMessage());
        }
    }
    
    /**
     * Remove um jogador de uma equipe.
     * 
     * @param equipeId ID da equipe
     * @param jogadorId ID do jogador
     * @return Um resultado indicando sucesso ou falha
     */
    @Transactional
    public Resultado<?> removerJogador(Long equipeId, Long jogadorId) {
        try {
            Equipe equipe = equipeRepository.findById(equipeId)
                    .orElseThrow(() -> new IllegalArgumentException("Equipe não encontrada"));
            
            JogadorHumano jogador = jogadorRepository.findById(jogadorId)
                    .orElseThrow(() -> new IllegalArgumentException("Jogador não encontrado"));
            
            // Verificar se o jogador pertence a esta equipe
            if (jogador.getEquipe() == null || !jogador.getEquipe().getId().equals(equipeId)) {
                return Resultado.falha("Jogador não pertence a esta equipe");
            }
            
            equipe.removerJogador(jogador);
            equipeRepository.save(equipe);
            
            return Resultado.sucesso("Jogador removido da equipe com sucesso");
        } catch (IllegalArgumentException e) {
            return Resultado.falha(e.getMessage());
        } catch (Exception e) {
            return Resultado.falha("Erro ao remover jogador da equipe: " + e.getMessage());
        }
    }
    
    /**
     * Obtém o ranking de equipes ordenado pela pontuação total.
     * 
     * @return Lista de DTOs de equipes ordenada por pontuação
     */
    public List<EquipeDTO> obterRankingEquipes() {
        List<Equipe> equipes = equipeRepository.findAll();
        
        // Atualizar pontuações antes de retornar
        for (Equipe equipe : equipes) {
            equipe.atualizarPontuacaoTotal();
            equipeRepository.save(equipe);
        }
        
        return equipes.stream()
                .sorted((e1, e2) -> Integer.compare(e2.getPontuacaoTotal(), e1.getPontuacaoTotal()))
                .map(equipe -> (EquipeDTO) equipe.toDTO())
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém estatísticas de uma equipe específica.
     * 
     * @param equipeId ID da equipe
     * @return Um resultado contendo o DTO da equipe com estatísticas ou mensagem de erro
     */
    public Resultado<EquipeDTO> obterEstatisticasEquipe(Long equipeId) {
        return equipeRepository.findById(equipeId)
                .map(equipe -> {
                    equipe.atualizarPontuacaoTotal();
                    equipeRepository.save(equipe);
                    return Resultado.sucesso((EquipeDTO) equipe.toDTO());
                })
                .orElse(Resultado.falha("Equipe não encontrada"));
    }

    /**
     * Busca equipes pelo nome (busca parcial).
     *
     * @param nomeParcial Parte do nome a ser pesquisada
     * @return Lista de DTOs de equipes encontradas
     */
    public List<EquipeDTO> buscarPorNomeParcial(String nomeParcial) {
        List<Equipe> equipes = equipeRepository.findByNomeContainingIgnoreCase(nomeParcial);
        return equipes.stream()
                .map(equipe -> (EquipeDTO) equipe.toDTO())
                .collect(Collectors.toList());
    }

    /**
     * Busca equipes com pontuação acima de um valor mínimo.
     *
     * @param pontuacaoMinima Pontuação mínima a considerar
     * @return Lista de DTOs de equipes com pontuação superior
     */
    public List<EquipeDTO> buscarEquipesComPontuacaoSuperior(int pontuacaoMinima) {
        List<Equipe> equipes = equipeRepository.findByPontuacaoTotalGreaterThan(pontuacaoMinima);
        return equipes.stream()
                .map(equipe -> (EquipeDTO) equipe.toDTO())
                .collect(Collectors.toList());
    }

    /**
     * Verifica se já existe uma equipe com o mesmo nome.
     *
     * @param nome Nome da equipe a verificar
     * @return true se o nome já existe, false caso contrário
     */
    public boolean existeEquipeComNome(String nome) {
        return equipeRepository.findByNomeEquals(nome).isPresent();
    }

    /**
     * Busca equipes com um número mínimo de jogadores.
     *
     * @param quantidadeMinima Número mínimo de jogadores
     * @return Lista de DTOs de equipes que atendem ao critério
     */
    public List<EquipeDTO> buscarEquipesComMinimoJogadores(int quantidadeMinima) {
        List<Equipe> equipes = equipeRepository.findByMinimumNumberOfPlayers(quantidadeMinima);
        return equipes.stream()
                .map(equipe -> (EquipeDTO) equipe.toDTO())
                .collect(Collectors.toList());
    }

    /**
     * Obtém o top N equipes por pontuação.
     *
     * @param quantidade Número de equipes a retornar
     * @return Lista das top N equipes
     */
    public List<EquipeDTO> obterTopEquipes(int quantidade) {
        List<Equipe> topEquipes = equipeRepository.findTopByPontuacao(quantidade);
        return topEquipes.stream()
                .map(equipe -> (EquipeDTO) equipe.toDTO())
                .collect(Collectors.toList());
    }

    /**
     * Lista todos os jogadores de uma equipe.
     * 
     * @param equipeId ID da equipe
     * @return Resultado contendo a lista de DTOs de jogadores ou mensagem de erro
     */
    public Resultado<List<JogadorDTO>> listarJogadoresEquipe(Long equipeId) {
        return equipeRepository.findById(equipeId)
                .map(equipe -> {
                    List<JogadorDTO> jogadoresDTOs = equipe.getJogadores().stream()
                            .map(jogador -> {
                                JogadorDTO dto = new JogadorDTO();
                                dto.setId(jogador.getId());
                                
                                // Verificar se o jogador tem usuário associado
                                if (jogador.getUsuario() != null) {
                                    dto.setNome(jogador.getUsuario().getNome());
                                    dto.setUsuarioId(jogador.getUsuario().getId());
                                } else {
                                    // Se não tiver, use o nome do jogador diretamente
                                    dto.setNome(jogador.getNome());
                                    // Não definir usuarioId, já que não há usuário
                                }
                                
                                return dto;
                            })
                            .collect(Collectors.toList());
                    return Resultado.sucesso(jogadoresDTOs);
                })
                .orElse(Resultado.falha("Equipe não encontrada"));
    }
}