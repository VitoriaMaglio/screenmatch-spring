package com.estudando.spring.Screenmatch.repository;

import com.estudando.spring.Screenmatch.entities.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {
}
