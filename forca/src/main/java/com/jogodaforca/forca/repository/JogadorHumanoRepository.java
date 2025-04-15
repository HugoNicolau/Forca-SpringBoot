package com.jogodaforca.forca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jogodaforca.forca.model.JogadorHumano;
import com.jogodaforca.forca.model.Usuario;

/**
 * Repositório para operações de acesso a dados relacionados a jogadores humanos.
 */
@Repository
public interface JogadorHumanoRepository extends JpaRepository<JogadorHumano, Long> {
    Optional<JogadorHumano> findByUsuario(Usuario usuario);
}