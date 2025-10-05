package com.estudando.spring.Screenmatch;

import com.estudando.spring.Screenmatch.repository.SerieRepository;
import com.estudando.spring.Screenmatch.test.Main1;
import com.estudando.spring.Screenmatch.test.Main2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
//injeção de dependência por construtor1

    private final SerieRepository repository;

    public ScreenmatchApplication(SerieRepository repository) {
        this.repository = repository;
    }


    public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Primeiro projeto Spring sem web!");
        //Agora o usuário vai poder escolher qual série ele quer ver os dados, então criei uma classe Main para criar método menu e chamar tudo por lá
        //Main1 main = new Main1();
        //main.exibirMenu();
        Main2 main2 = new Main2(repository);
        main2.exibeMenu();
    }
}
