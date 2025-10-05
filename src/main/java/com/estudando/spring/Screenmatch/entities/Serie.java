package com.estudando.spring.Screenmatch.entities;

import com.estudando.spring.Screenmatch.enums.Categoria;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.OptionalDouble;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private Categoria genero; //listar categorias->enum
    private String atores;
    private String poster;
    private String sinopse;

    //Construtor que faz os atributos dessa classe serem correspondentes aos campos da API
    public Serie(DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        //transformando avaliação em double com Optinal lançando uma opção que se não obter o valor double ele é colocado como 0
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        //pegando a primeira categoria da série disponível na API; trim elimina espaços em branco e \n
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        this.sinopse = dadosSerie.sinopse();
}}
