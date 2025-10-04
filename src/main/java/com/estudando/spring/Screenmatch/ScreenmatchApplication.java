package com.estudando.spring.Screenmatch;

import com.estudando.spring.Screenmatch.entities.DadosEpisodio;
import com.estudando.spring.Screenmatch.entities.DadosSerie;
import com.estudando.spring.Screenmatch.entities.DadosTemporada;
import com.estudando.spring.Screenmatch.service.ConsumoApi;
import com.estudando.spring.Screenmatch.service.ConverteDados;
import com.estudando.spring.Screenmatch.test.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Primeiro projeto Spring sem web!");
        //Agora o usuário vai poder escolher qual série ele quer ver os dados, então criei uma classe Main para criar método menu e chamar tudo por lá
        Main main = new Main();
        main.exibirMenu();
    }
}
