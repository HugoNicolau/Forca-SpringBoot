package com.jogodaforca.forca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jogodaforca.forca.model.Equipe;

/**
 * Repositório para operações de acesso a dados relacionados a equipes.
 * 
 * CONCEITO: INTERFACE ESPECIALIZADA
 * - Estende JpaRepository para herdar operações básicas de CRUD
 * - Adiciona métodos específicos para consultas relacionadas a equipes
 * - Demonstra a especialização de interfaces em Spring Data JPA
 */
@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
    
    /**
     * Busca equipes pelo nome (correspondência parcial, ignorando maiúsculas/minúsculas).
     * 
     * CONCEITO: QUERY DERIVATION
     * - Spring Data JPA gera automaticamente a consulta com base no nome do método
     * 
     * @param nome Parte do nome a ser pesquisada
     * @return Lista de equipes correspondentes ao critério
     */
    List<Equipe> findByNomeContainingIgnoreCase(String nome);
    
    /**
     * Busca equipes com pontuação total maior que o valor especificado.
     * 
     * @param pontuacao Pontuação mínima para filtrar
     * @return Lista de equipes com pontuação superior
     */
    List<Equipe> findByPontuacaoTotalGreaterThan(int pontuacao);
    
    /**
     * Encontra equipe pelo nome exato.
     * 
     * @param nome Nome completo da equipe
     * @return Equipe encontrada ou Optional vazio
     */
    Optional<Equipe> findByNomeEquals(String nome);
    
    /**
     * Busca equipes que têm pelo menos um número específico de jogadores.
     * 
     * CONCEITO: JPQL QUERY CUSTOMIZADA
     * - Uso de @Query para definir consultas personalizadas em JPQL
     * 
     * @param quantidadeMinima Quantidade mínima de jogadores
     * @return Lista de equipes que atendem ao critério
     */
    @Query("SELECT e FROM Equipe e WHERE SIZE(e.jogadores) >= :quantidade")
    List<Equipe> findByMinimumNumberOfPlayers(@Param("quantidade") int quantidadeMinima);
    
    /**
     * Busca as N equipes com maior pontuação.
     * 
     * @param limit Número máximo de equipes a retornar
     * @return Lista das equipes melhor classificadas
     */
    @Query("SELECT e FROM Equipe e ORDER BY e.pontuacaoTotal DESC")
    List<Equipe> findTopByPontuacao(int limit);
}