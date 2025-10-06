package com.estudando.spring.Screenmatch.test.controller;
//Para que o Spring mapeie os pcotes, o que for rodar na aplicação deve derivar do mesmo pacote.
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//indicando que é uma classe controller-> recebe requisições http
public class SerieController {

    //requisições http

    @GetMapping("/series")
    public String obterSeries(){
        return "Aqui vão ser listada as séries";
    }
}
