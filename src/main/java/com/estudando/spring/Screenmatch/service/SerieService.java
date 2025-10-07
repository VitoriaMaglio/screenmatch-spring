package com.estudando.spring.Screenmatch.service;

import com.estudando.spring.Screenmatch.dto.EpisodioDTO;
import com.estudando.spring.Screenmatch.dto.SerieDTO;
import com.estudando.spring.Screenmatch.entities.Episodio;
import com.estudando.spring.Screenmatch.entities.Serie;
import com.estudando.spring.Screenmatch.enums.Categoria;
import com.estudando.spring.Screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    //Camada de regras de negócio e lógica da aplicação
    //Chamam os repositories
    @Autowired
    private SerieRepository serieRepository;


    public List<SerieDTO> obterTodasSeries() {
        //Converter um obj para um objDTO e passar esses dados para outra lista.
        return converterDados(serieRepository.findAll());
    }

    public List<SerieDTO> obterSeriesTop5() {
        return converterDados(serieRepository.findTop5ByOrderByAvaliacaoDesc());

    }


    //Método específico que converte Serie para SerieDTO para chamar ele nos métodos de busca
    private List<SerieDTO> converterDados(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterLancamentos() {
        return converterDados(serieRepository.lancamentoRecentes());
    }

    //Método para buscar característias específicas
    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);
        //Código omitido
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse());
        }
        return null;
    }

    //Método para carregar temporadas

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);
        //Código omitido
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodioList().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
        return serieRepository.obterEpisodiosPorTemporada(id, numero)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                .collect(Collectors.toList());

    }

    public List<SerieDTO> obterSeriesPorCategoria(String nomeGenero) {
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        return converterDados(serieRepository.findByGenero(categoria));
    }

    //Método para mostrar top5 epiódios de uma temporada
    public List<EpisodioDTO> obterTopEpisodios(Long id) {
        var serie = serieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Série não encontrada"));

        // Pega todos os episódios ordenados, depois limita a 5
        return serieRepository.topEpisodiosPorSerie(serie)
                .stream()
                .sorted((a, b) -> b.getAvaliacao().compareTo(a.getAvaliacao())) // garante ordem decrescente
                .limit(5) // pega apenas os top 5
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                .collect(Collectors.toList());
    }
}

    //Para vc criar um método para conectar ao front:
    //Repository -> query
    //Depois em controller cria a requisição http
    //Service cria o retorno 

