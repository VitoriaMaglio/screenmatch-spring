package com.estudando.spring.Screenmatch.service;

public interface IConverteDados {

    <T> T obterDados(String json, Class<T> classe);
    //Aqui usamos Generics-> uma classe Genérica que garante type safety
    //Não sabemos o que a pessoa vai informar paa o programa, por isso não sbemos o retorno
    //
}
