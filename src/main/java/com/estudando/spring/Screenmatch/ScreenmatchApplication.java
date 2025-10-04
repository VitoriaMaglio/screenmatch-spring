package com.estudando.spring.Screenmatch;

import com.estudando.spring.Screenmatch.service.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Primeiro projeto Spring sem web!");
        //chamando API
        ConsumoApi consumoApi = new ConsumoApi();
        //declarando uma Strig json atribuída ao método da api com uma url de uma API Pública
        //Essa API retorna dados sobre filmes e séries: https://www.omdbapi.com/
        String json = consumoApi.obterDados("https://www.omdbapi.com/?t=Gilmore+girls&Season=1&apikey=604e9a2f");
        System.out.println(json);

        //desserializar os dados da api e transformar em classes e obj java -> usando Jackson
        //colar dependência do jackson em pom.xml
    }
}
