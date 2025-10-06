package com.estudando.spring.Screenmatch.service;

import com.estudando.spring.Screenmatch.dto.SerieDTO;
import com.estudando.spring.Screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;


    public List<SerieDTO> obterTodasSeries(){
        //Converter um obj para um objDTO e passar esses dados para outra lista.
        return serieRepository.findAll().stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }
}
