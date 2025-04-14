package com.jogodaforca.forca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jogodaforca.forca.model.JogadorHumano;

/**
 * Repositório para operações de acesso a dados relacionados a jogadores humanos.
 */
@Repository
public interface JogadorHumanoRepository extends JpaRepository<JogadorHumano, Long> {
    // Métodos de consulta customizados podem ser adicionados aqui
}