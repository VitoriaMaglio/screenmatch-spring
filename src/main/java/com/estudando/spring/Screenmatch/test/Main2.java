package com.estudando.spring.Screenmatch.test;

import com.estudando.spring.Screenmatch.entities.DadosSerie;
import com.estudando.spring.Screenmatch.entities.DadosTemporada;
import com.estudando.spring.Screenmatch.entities.Episodio;
import com.estudando.spring.Screenmatch.entities.Serie;
import com.estudando.spring.Screenmatch.enums.Categoria;
import com.estudando.spring.Screenmatch.repository.SerieRepository;
import com.estudando.spring.Screenmatch.service.ConsumoApi;
import com.estudando.spring.Screenmatch.service.ConverteDados;
import jakarta.transaction.Transactional;

import java.util.*;
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
    // Recebe o repositório injetado pela classe principal
    public Main2(SerieRepository repository) {
        this.repository = repository;
    }

    private List<Serie> series = new ArrayList<>();

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
            1 - Buscar séries
            2 - Buscar episódios
            3 - Listar séries buscadas
            4 - Buscar série por título
            5-  Buscar série pelo nome do ator
            6-  Buscar série por categoria
            7-  Buscar episódio por trecho
            8-  Top 5 Séries
            9-  Filtrar séries
            10 - Top 5 episódios por série
            11- Buscar episódios a partir de uma data\s

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
                case 4:
                    buscarSerieTitulo();
                    break;
                case 5:
                    buscarSerieAtor();
                    break;
                case 6:
                    buscarSerieCategoria();
                    break;
                case 7:
                    buscarEpisodioTrecho();
                    break;
                case 8:
                    buscarTop5Series();
                    break;
                case 9:
                    filtrarSeriesPorTemporadaEAvaliacao();
                    break;
                case 10:
                    topEpisodiosPorSerie();
                    break;
                case 11:
                    findTop5ByOrderByEpisodioListDataLancamentoDesc();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");

            }
        }
    }

    private void filtrarSeriesPorTemporadaEAvaliacao() {
        System.out.println("Filtrar séries até quantas temporadas? ");
        var totalTemporadas = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Com avaliação a partir de que valor? ");
        var avaliacao = leitura.nextDouble();
        leitura.nextLine();
        List<Serie> filtroSeries = repository.seriesPorTemporadaEAValiacao(totalTemporadas, avaliacao);
        System.out.println("*** Séries filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - avaliação: " + s.getAvaliacao()));

    }

    private void topEpisodiosPorSerie() {
        Optional<Serie> serieBuscada = buscarSerieTitulo();
        if (serieBuscada.isPresent()) {
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = repository.topEpisodiosPorSerie(serie)
                    .stream()
                    .limit(5) // 🔥 Mostra só os 5 melhores
                    .toList();

            topEpisodios.forEach(e ->
                    System.out.printf("Série: %s | Temporada %s | Episódio %s - %s | Avaliação: %.1f\n",
                            e.getSerie().getTitulo(), e.getTemporada(),
                            e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()));
        } else {
            System.out.println("Série não encontrada!");
        }
    }



    private void buscarTop5Series() {
        List<Serie> serieTop = repository.findTop5ByOrderByAvaliacaoDesc();
        serieTop.forEach(s ->
                System.out.println(s.getTitulo() + " avaliação: " + s.getAvaliacao()));
    }


    private void buscarEpisodioTrecho() {
        System.out.println("Qual nome do episódio para busca?");
        String trecho = leitura.nextLine();
        List<Episodio> episodiosEncontrados = repository.episodioTrecho(trecho);
        episodiosEncontrados.forEach(e ->
                System.out.printf("Série: %s Temporada %s - Episódio %s - %s\n",
                        e.getSerie().getTitulo(), e.getTemporada(),
                        e.getNumeroEpisodio(), e.getTitulo()));;
    }

    private void buscarSerieCategoria() {
        System.out.println("Deseja buscar séries de que categoria/gênero? ");
        var nomeGenero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Serie> serieCategoria = repository.findByGenero(categoria);
        System.out.println("Séries da categoria " + nomeGenero);
        serieCategoria.forEach(System.out::println);
    }

    private void buscarSerieAtor() {
        //Para criar um método de busca:
        //Colocar opção no menu, na interface criar o metodo querie e instanciar nessa classe o método
        System.out.println("Escolha uma série pelo nome");
        String nomeAtor = leitura.nextLine();
        //Quando digitar nome do ator aparecer uma lista com as séries
        List<Serie> seriesEncontradas = repository.findByAtoresContainingIgnoreCase(nomeAtor);
        System.out.println("Séries em que " + nomeAtor + "trabalhou!");
        //imprimir uma lista -> usar forEach
        seriesEncontradas.forEach(s-> System.out.println(s.getTitulo()));
        System.out.println(seriesEncontradas);
    }

    private Optional<Serie> buscarSerieTitulo() {
        System.out.println("Escolha um série pelo nome: ");
        var nomeSerie = leitura.nextLine();
        Optional<Serie> serieBusca = repository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()) {
            System.out.println("Dados da série: " + serieBusca.get());
        } else {
            System.out.println("Série não encontrada!");
        }
        return serieBusca;
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
    @Transactional
    private void buscarEpisodioPorSerie(){

        //DadosSerie dadosSerie = getDadosSerie();
        //Buscar no banco a série
        System.out.println("Escolha uma série pelo nome");
        listarSeriesBuscadas();
        String nomeSerie = leitura.nextLine();
        //Cria um obj local de serie para aplicar a função lambda que filtra títulos que contenham 'nomeSerie' e pega a primeira ocorrência
//        Optional<Serie> serie = series.stream()
//                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
//                .findFirst();

        Optional<Serie> serie =repository.findByTituloContainingIgnoreCase(nomeSerie);
        //Verifica se a série está no banc
        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + APIKEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }

            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            // Adiciona os episódios à série garantindo o relacionamento bidirecional
            episodios.forEach(serieEncontrada::addEpisodio);

            // Salva série e episódios no banco
            repository.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada!");
        }

//        List<DadosTemporada> temporadas = new ArrayList<>();
//
//        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
//            String json = consumo.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + APIKEY);
//            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
//            temporadas.add(dadosTemporada);
//        }
//        temporadas.forEach(System.out::println);
    }
    //Método que imprima a lista de séries buscadas-> cria uma lista de séries e no método de buscar séries
    //colocar essa lista .add(série inserida pelo use) pq ai são armazenadas na lista e conseguiremos retornar depois

    //Método que de acorod com as séries digitadas pelo user, vai imprimir uma lista indicando as categorias das séries que ele digitou

    private void listarSeriesBuscadas() {
      // List<Serie> series = new ArrayList<>();
//        series = dadosSeries.stream()
//                        .map(d ->new Serie(d))
//                                .collect(Collectors.toList());

        series = repository.findAll();//->aqui buscads os dados não de uma lista criada a partir das iterações do usuário e sim do banco
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
    //Imprimir dados de Serie ordenados pela categoria-> Criar lista de series; atribuir essa lista a dadosseries
    //e fazer lambda para cada elemento d em dadosSeries, cria um novo objeto Serie usando d
    //transforma o Stream de Serie de volta em uma lista.
    //.sorted(Comparator.comparing(Serie::getGenero)) → ordena as séries alfabeticamente pelo gênero (getGenero()

    private void findTop5ByOrderByEpisodioListDataLancamentoDesc() {
        Optional<Serie> serieBuscada = buscarSerieTitulo();

        if (serieBuscada.isPresent()) {
            Serie serie = serieBuscada.get();
            System.out.println("Digite o ano limite de lançamento");
            var anoLancamento = leitura.nextInt();
            leitura.nextLine();

            List<Episodio> episodiosAno = repository.episodiosPorSerieEAno(serie, anoLancamento);
            episodiosAno.forEach(System.out::println);
        }
    }


}

