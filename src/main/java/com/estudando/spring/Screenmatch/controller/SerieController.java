package com.estudando.spring.Screenmatch.controller;
//Para que o Spring mapeie os pcotes, o que for rodar na aplicação deve derivar do mesmo pacote.
import com.estudando.spring.Screenmatch.dto.EpisodioDTO;
import com.estudando.spring.Screenmatch.dto.SerieDTO;
import com.estudando.spring.Screenmatch.entities.Serie;
import com.estudando.spring.Screenmatch.repository.SerieRepository;
import com.estudando.spring.Screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController//indicando que é uma classe controller-> recebe requisições http
@RequestMapping("/series")
public class SerieController {
//requisições http

//Iniciando o uso de DTO-> DATA TRANSFER OBJ

//Testando Devtools e Live running
//    @GetMapping("/inicio")
//    public String retornarInicio(){
//        return "Bem vindo!";
//    }

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieDTO> obterSeries(){
        return serieService.obterTodasSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterSeriesTpo5(){
        return serieService.obterSeriesTop5();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return serieService.obterLancamentos();
    }

    //Código omitido

    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id) {
        return serieService.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id) {
    return serieService.obterTodasTemporadas(id);
    }


    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterTemporadasPorNumero(@PathVariable Long id, @PathVariable Long numero){
        return serieService.obterTemporadasPorNumero(id, numero);
    }

    @GetMapping("/categoria/{nomeGenero}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String nomeGenero){
        return serieService.obterSeriesPorCategoria(nomeGenero);
    }
    }








