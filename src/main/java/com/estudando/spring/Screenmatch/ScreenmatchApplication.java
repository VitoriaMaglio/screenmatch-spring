package com.estudando.spring.Screenmatch;

import com.estudando.spring.Screenmatch.entities.DadosSerie;
import com.estudando.spring.Screenmatch.service.ConsumoApi;
import com.estudando.spring.Screenmatch.service.ConverteDados;
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
        String json = consumoApi.obterDados("https://www.omdbapi.com/?t=Gilmore+girls&apikey=604e9a2f");
        System.out.println(json);
        //Aqui recebemos a API, porém quando rodamos aparece no console os dados em JSON e queremos converter esses dados
        //Assim precisamos desserializá-los e transformar em objetos java:
            //Criar uma classe entidade que conecta atributos java com o nome dos campos da API
            //Cria uma interface com a regra genérica de converter
            //Instanciar essa interface com a conversão

        //desserializar os dados da api e transformar em classes e obj java -> usando Jackson
        //colar dependência do jackson em pom.xml
        //Jackson, uma biblioteca Java para processar JSON, que ajudam a mapear propriedades de classe para campos JSON.

        ConverteDados converteDados = new ConverteDados();//instancia um conversor
        DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
        //receber dados do tipo json e vai converter para DadosSerie.class
        System.out.println(dadosSerie);

        //Deu erro pq trouxe dados que não mapeamos, por isso usamos a anotação JsonIgnoreProperties na classe de mapear dados

    }
}
