package com.estudando.spring.Screenmatch.controller;
//Para que o Spring mapeie os pcotes, o que for rodar na aplicação deve derivar do mesmo pacote.
import com.estudando.spring.Screenmatch.dto.SerieDTO;
import com.estudando.spring.Screenmatch.entities.Serie;
import com.estudando.spring.Screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController//indicando que é uma classe controller-> recebe requisições http
public class SerieController {

    @Autowired
    private SerieRepository serieRepository;

    //requisições http

    @GetMapping("/series")
    public List<SerieDTO> obterSeries(){
    //Converter um obj para um objDTO e passar esses dados para outra lista.
    return serieRepository.findAll().stream()
            .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
            .collect(Collectors.toList());
    }
    //Iniciando o uso de DTO-> DATA TRANSFER OBJ


}
