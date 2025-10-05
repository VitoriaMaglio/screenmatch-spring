package com.estudando.spring.Screenmatch.test;

import com.estudando.spring.Screenmatch.entities.DadosSerie;
import com.estudando.spring.Screenmatch.entities.DadosTemporada;
import com.estudando.spring.Screenmatch.entities.Serie;
import com.estudando.spring.Screenmatch.repository.SerieRepository;
import com.estudando.spring.Screenmatch.service.ConsumoApi;
import com.estudando.spring.Screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main2 {
//Continuação do projeto->Inicinado jpa
    //Criei uma classe com um menu e métodos genéricos para consultas genéricas de APIS
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();//instancia a classe que chama a API Pública
    private ConverteDados conversor = new ConverteDados();//instancia a class que converte dadods json em objetos java
    private final String ENDERECO = "https://www.omdbapi.com/?t=";//constantes fixas da URL da API
    private final String APIKEY = "&apikey=604e9a2f";

    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private final SerieRepository repository;
    // 🔹 Recebe o repositório injetado pela classe principal
    public Main2(SerieRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
            1 - Buscar séries
            2 - Buscar episódios
            3 - Listar séries buscadas

            0 - Sair
            """;
            System.out.println(menu);
             opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");

            }
        }
    }
    //Método que busca uma série digitada pelo usuário e exibe os dados
    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();//chama o método getDadosSerie
        //dadosSeries.add(dados);
        Serie serie = new Serie(dados);
        repository.save(serie);
        System.out.println(dados);
    }
    //Método auxiliar que realmente faz a chamada na API e converte o retorno em um objeto Java.
    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        String nomeSerie = leitura.nextLine();
        String json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + APIKEY);//Fazer a requisição HTTP e retornar o JSON com as informações da série..
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);//converter esse JSON em um objeto Java
        return dados;
    }
    //Método todas as temporadas e episódios de uma série informada.
    private void buscarEpisodioPorSerie(){
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            String json = consumo.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + APIKEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }
    //Método que imprima a lista de séries buscadas-> cria uma lista de séries e no método de buscar séries
    //colocar essa lista .add(série inserida pelo use) pq ai são armazenadas na lista e conseguiremos retornar depois

    //Método que de acorod com as séries digitadas pelo user, vai imprimir uma lista indicando as categorias das séries que ele digitou

    private void listarSeriesBuscadas() {
        List<Serie> series = new ArrayList<>();
        series = dadosSeries.stream()
                        .map(d ->new Serie(d))
                                .collect(Collectors.toList());
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
    //Imprimir dados de Serie ordenados pela categoria-> Criar lista de series; atribuir essa lista a dadosseries
    //e fazer lambda para cada elemento d em dadosSeries, cria um novo objeto Serie usando d
    //transforma o Stream de Serie de volta em uma lista.
    //.sorted(Comparator.comparing(Serie::getGenero)) → ordena as séries alfabeticamente pelo gênero (getGenero()

}

