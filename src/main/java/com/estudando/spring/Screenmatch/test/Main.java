package com.estudando.spring.Screenmatch.test;

import com.estudando.spring.Screenmatch.entities.DadosEpisodio;
import com.estudando.spring.Screenmatch.entities.DadosSerie;
import com.estudando.spring.Screenmatch.entities.DadosTemporada;
import com.estudando.spring.Screenmatch.entities.Episodio;
import com.estudando.spring.Screenmatch.service.ConsumoApi;
import com.estudando.spring.Screenmatch.service.ConverteDados;

import javax.sound.midi.Soundbank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

       //Aqui estou fazendo uma iteração para buscar os episódios das temporadas e o título

//       for (int i = 0; i < dadosSerie.totalTemporadas(); i++){
//           List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//           for(int j = 0; j< episodiosTemporada.size(); j++){
//               System.out.println(episodiosTemporada.get(j).titulo());
//           }
//       }


        //LAMBDA  versão do Java 8.
       //API STREAMS  encadear operações
       //  stream é uma sequência de elementos que pode ser processada em paralelo ou em série.
       // As operações intermediárias são aquelas que podem ser aplicadas em uma stream e retornam uma nova stream como resultado.
       // .sorted() ordenar .limit(número) imprime od dados q vc colocar com número 3 - vai impirmir os 3 primeios dados da lista
       //.filter() imprime o q entra no filtro
       // Map: permite transformar cada elemento da stream em outro tipo de dado. Collect: permite coletar os elementos da stream em uma coleção ou em outro tipo de dado.
        //Collectors é uma classe utilitária da API de Streams
       //depois de processar os dados com .map(), .filter(), etc., você usa .collect() com um Collector para transformar o fluxo em algo utilizável.
       temporadas.forEach(t-> t.episodios().forEach(e-> System.out.println(e.titulo())));
        //Pegar os top 5 episódios de toda série, transformar em uma lista que tenha todos os episódios

       List<DadosEpisodio> dadosEpisodios = temporadas.stream()
               .flatMap(t -> t.episodios().stream())
                       .collect(Collectors.toList());
       dadosEpisodios.stream()
               .filter(e-> !e.avaliacao().equalsIgnoreCase("N;A"))
               .peek(e-> System.out.println("Primeiro filtro (N/A)" + e))
               //função para conseguir olhar cada resposta de cada função
               .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
               .limit(5)
               .forEach(System.out::println);
       //juntando listas




        //Classe de episodios com seus atributos e estou criando uma lista atribuida a temporadas
       //flatmaptransforma cada elemento em uma coleção/stream e depois “achata” tudo em um único stream contínuo.
       List<Episodio> episodios =  temporadas.stream()
               .flatMap(t -> t.episodios().stream()
                       .map(d -> new Episodio(t.numero(), d))
               ).collect(Collectors.toList());
       episodios.forEach(System.out::println);

       //Encontrando a primeira ocorrência de uma busca
       System.out.println("Digite um trecho do título do episódio");
       String trechoTitulo = scanner.nextLine();

       //Ele pode conter um valor único ou nenhum valor.
       Optional<Episodio> episodioBuscado = episodios.stream()//Optional é um objeto container que pode ou n ter valor não nulo, retorno opcional
               .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))// a primeira referência que contém o q o user digitar vai imprimir
               .findFirst();//aqui vai encontrar e precisa tratar esse retorno atribuindo ele a uma var episodioBucado
       if(episodioBuscado.isPresent()){
           System.out.println("Episódio encontrado!");
           System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
       } else {
           System.out.println("Episódio não encontrado!");
       }


       //ver os episódios a partir de uma data
       System.out.println("A partir de que ano você deseja ver os episódios?");
       int ano = scanner.nextInt(); scanner.nextLine();
       LocalDate dataBusca = LocalDate.of(ano,1,1);//formada uma data a partir de 1º de janeiro do ano q a pessoa digitar

       System.out.println("A partir de que ano você deseja ver os episódios? ");
       DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

       episodios.stream()
               .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
               .forEach(e -> System.out.println(
                       "Temporada: " + e.getTemporada() +
                               " Episódio: " + e.getTitulo() +
                               " Data lançamento: " + e.getDataLancamento().format(formatador)
               ));

       //criando um mapa com dados por temporada
       //média de avaliações de uma temporada
       Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
               .filter(e -> e.getAvaliacao() > 0.0)
               .collect(Collectors.groupingBy(Episodio::getTemporada,
                       Collectors.averagingDouble(Episodio::getAvaliacao)));
       System.out.println(avaliacoesPorTemporada);

       //buscar outras estatísticas -> classe DoubleSummaryStatistics
       DoubleSummaryStatistics est = episodios.stream()
               .filter(e -> e.getAvaliacao() > 0.0)
               .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));//cletando dados e atribuindo para Double summary
       //essa classe já imprime alguns estatísticas, pois ela já tem funções:
       System.out.println("Média: " + est.getAverage());
       System.out.println("Melhor episódio: " + est.getMax());
       System.out.println("Pior episódio: " + est.getMin());
       System.out.println("Quantidade: " + est.getCount());







   }





}
