package com.estudando.spring.Screenmatch.test;

import com.estudando.spring.Screenmatch.entities.DadosSerie;
import com.estudando.spring.Screenmatch.entities.DadosTemporada;
import com.estudando.spring.Screenmatch.service.ConsumoApi;
import com.estudando.spring.Screenmatch.service.ConverteDados;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);

    //tornando atributos pois vão ser utilizados várias vezes
    private ConverteDados converteDados = new ConverteDados();
    private ConsumoApi consumoApi = new ConsumoApi();

    //constantes valores fixos e imutáveis sejam armazenados e utilizados ao longo do código.
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=604e9a2f";

    //Adicionar final para indicar que esses "atributos" não vão ser modificados, e não vão mudar pois são as partes da url fixas
   public void exibirMenu(){
       System.out.println("Digite o nome da série que você deseja acessar dados: ");
       String nomeSerie = scanner.nextLine();

       String json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + APIKEY);
       //"https://www.omdbapi.com/?t=" Gilmore+girls" " &apikey=604e9a2f"
       //TROCAR O ESPAÇO ENTRE OS NOMES DA SÉRIE

       //Buscando dadosSerie

       DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
       System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();

       for(int i =1; i<dadosSerie.totalTemporadas(); i++){
        json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i +  APIKEY);
        DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
        temporadas.add(dadosTemporada);
       }
       temporadas.forEach(System.out::println);


   }
}
