package com.jogodaforca.forca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jogodaforca.forca.model.Partida;
import com.jogodaforca.forca.model.Usuario;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findByUsuarioOrderByDataInicioDesc(Usuario usuario);
}