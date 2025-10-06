package com.estudando.spring.Screenmatch.repository;

import com.estudando.spring.Screenmatch.entities.Episodio;
import com.estudando.spring.Screenmatch.entities.Serie;
import com.estudando.spring.Screenmatch.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {

    //Usando queries methods para fazer buscar no banco de dado: declarar apenas o método na interface que a JPA realiza sua função
    //Método para encontrar uma Serie pelo seu nome
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
    List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);
    List<Serie> findByGenero(Categoria categoria);

    @Query("SELECT e FROM Serie s JOIN s.episodioList e WHERE LOWER(e.titulo) LIKE LOWER(CONCAT('%', :trecho, '%'))")
    List<Episodio> episodioTrecho(@Param("trecho") String trecho);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();
    //JPQL->ava Persistence Query Language, ou seja, Linguagem de Consulta de Persistência Java. Portanto, é uma linguagem de consulta própria do JPA, do controle de persistência do Java.
    //trabalha com objetos Java, não com tabelas diretamente.

    //Em vez de dizer “SELECT * FROM serie”, você diz “SELECT s FROM Serie s” — ou seja, seleciona objetos Serie.
    //Query é uma pergunta que vc faz ao banco, um comando para ser executado no banco

}
