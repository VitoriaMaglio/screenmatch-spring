package com.estudando.spring.Screenmatch.enums;

public enum Categoria {

    ACAO("Action","Ação"),
    ROMANCE("Romance","Romance"),
    COMEDIA("Comedy","Comédia"),
    DRAMA("Drama","Drama"),
    CRIME("Crime", "Crime");

    private String categoriaOmdb;
    private String categoriaPortugues;

    Categoria(String categoriaOmdb, String categoriaPortugues){ //construtor que adiciona as categorias no atributo
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortugues = categoriaPortugues;
    }

    //Método static pertence a classe e não ao objeto
    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
    //Interpretar os valores da API e transforma no valor correspondente da Enum, percorrendo a lista e fazendo a associação


    public static Categoria fromPortugues(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPortugues.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }



}
