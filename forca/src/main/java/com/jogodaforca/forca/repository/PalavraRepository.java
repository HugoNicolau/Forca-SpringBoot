package com.jogodaforca.forca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jogodaforca.forca.model.Palavra;

@Repository
public interface PalavraRepository extends JpaRepository<Palavra, Long> {
    @Query(value = "SELECT * FROM palavra ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Palavra> findRandomPalavra();
}