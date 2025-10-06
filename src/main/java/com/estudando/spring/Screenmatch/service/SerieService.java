package com.estudando.spring.Screenmatch.service;

import com.estudando.spring.Screenmatch.dto.SerieDTO;
import com.estudando.spring.Screenmatch.entities.Serie;
import com.estudando.spring.Screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
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
    private List<SerieDTO> converterDados(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }
}
