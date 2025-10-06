package com.estudando.spring.Screenmatch.repository;

import com.estudando.spring.Screenmatch.entities.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {
    //Usando queries methods para fazer buscar no banco de dado: declarar apenas o método na interface que a JPA realiza sua função
    //Método para encontrar uma Serie pelo seu nome
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
}
