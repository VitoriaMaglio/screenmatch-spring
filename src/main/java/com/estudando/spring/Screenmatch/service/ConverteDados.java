package com.estudando.spring.Screenmatch.service;

import com.estudando.spring.Screenmatch.entities.DadosSerie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados{

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    //public DadosSerie obterDados(String json){}
    //Se criarmos um método de converter dados, ele teria que ser um método específico
    //Ai se precisar converter outros dados teríamos que criar mais métodos, por isso é importante criar uma interface e aplicar essa regra
    //implementar o método da interface e usar mapper para conversão
}
