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


    //Busca episódios que contenham parte do título
    @Query("SELECT e FROM Serie s JOIN s.episodioList e WHERE LOWER(e.titulo) LIKE LOWER(CONCAT('%', :trecho, '%'))")
    List<Episodio> episodioTrecho(@Param("trecho") String trecho);

    //Top 5 séries por avaliação
    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    //Séries filtradas por temporadas e avaliação mínima
    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :avaliacao")
    List<Serie> seriesPorTemporadaEAValiacao(@Param("totalTemporadas") int totalTemporadas,
                                             @Param("avaliacao") double avaliacao);

    // Top episódios de uma série, ordenados por avaliação
    @Query("SELECT e FROM Serie s JOIN s.episodioList e WHERE s = :serie ORDER BY e.avaliacao DESC")
    List<Episodio> topEpisodiosPorSerie(@Param("serie") Serie serie);

    //Episódios lançados após determinado ano
    @Query("SELECT e FROM Serie s JOIN s.episodioList e " +
            "WHERE s = :serie AND EXTRACT(YEAR FROM e.dataLancamento) >= :anoLancamento")
    List<Episodio> episodiosPorSerieEAno(@Param("serie") Serie serie,
                                         @Param("anoLancamento") int anoLancamento);
    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int totalTemporadas, double avaliacao);

    //Método para conexão com front
    @Query(value = """
    SELECT s.* FROM tb_series s
    JOIN tb_episodio e ON e.serie_id = s.id
    GROUP BY s.id
    ORDER BY MAX(e.data_lancamento) DESC
    LIMIT 5
    """, nativeQuery = true)
    List<Serie> lancamentoRecentes();

    @Query("SELECT e FROM Serie s JOIN s.episodioList e WHERE s.id = :id AND e.temporada = :numero")
    List<Episodio> obterEpisodiosPorTemporada(Long id, Long numero);



    @Query("SELECT e FROM Serie s JOIN s.episodioList e WHERE s.id = :serieId ORDER BY e.avaliacao DESC")
    List<Episodio> top5EpisodiosPorSerie(@Param("serieId") Long serieId);

    //JPQL->ava Persistence Query Language, ou seja, Linguagem de Consulta de Persistência Java. Portanto, é uma linguagem de consulta própria do JPA, do controle de persistência do Java.
    //trabalha com objetos Java, não com tabelas diretamente.

    //Em vez de dizer “SELECT * FROM serie”, você diz “SELECT s FROM Serie s” — ou seja, seleciona objetos Serie.
    //Query é uma pergunta que vc faz ao banco, um comando para ser executado no banco

    //Se o método não tiver já um nome de uma query ele precisa declarar a query com a ação se não o JPQL tenta fazer e como n é ujm método dentro dos seus padrões dá erro

}