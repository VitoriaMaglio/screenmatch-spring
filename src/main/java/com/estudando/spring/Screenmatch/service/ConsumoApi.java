package com.estudando.spring.Screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {
//Classe para consumir a API externa
//Ter uma classe específica separando o consumo da API é bom pois assim você pode consumir qual api pela url e ter as responsabilidades divididas
    //Método que devolve uma String com o json que corresponde a resposta da requisição
    //criar método do tipo String passando um endereço string como parâmetro
    //criar HtttpCliente e HttpRequest e HttpResponse
    //try para que se a response for igual a requisiçõa do cliente- executar a resposta da API
   //retornar ums String json atribuída a resposta da API

    public String obterDados (String endereco) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder() //cria uma URI para identificar o endereço que vai fazer a requisição
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String json = response.body();
        return json;
    }
}
