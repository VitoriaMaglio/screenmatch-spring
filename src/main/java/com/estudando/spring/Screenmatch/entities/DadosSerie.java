package com.estudando.spring.Screenmatch.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao,
                         @JsonAlias("Genre") String genero,
                         @JsonAlias("Actors") String atores,
                         @JsonAlias("Poster") String poster,
                         @JsonAlias("Plot") String sinopse) {

    //Record pois só vamos pegar dados da API, não vamos construir métodos
    //Precisamos dar um "apelido" para os dados que vamos trabalhar para que eles se conectam com os campos da API,
    //para isso podemos usar a anotação @JsonAlias(ler json, tbm aceita array) ou @JsonProperty(serve para serializar e desserializar)
    //Adicionando mais dados da API sobre as séries


















}
