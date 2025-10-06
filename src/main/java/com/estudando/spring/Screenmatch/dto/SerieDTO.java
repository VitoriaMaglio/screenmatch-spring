package com.estudando.spring.Screenmatch.dto;

import com.estudando.spring.Screenmatch.enums.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SerieDTO(
        Long id,
        String titulo,
        Integer totalTemporadas,
        Double avaliacao,
        Categoria genero, //listar categorias->enum
        String atores,
        String poster,
        String sinopse) {

    //declarar atributos que serão devolvidos


}
