package com.estudando.spring.Screenmatch.entities;

import com.estudando.spring.Screenmatch.enums.Categoria;
import com.estudando.spring.Screenmatch.service.ConsultaTranslation;
import com.theokanning.openai.OpenAiHttpException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
//adicionando anotações JPA = mapeando objetos relacionais
@Entity
@Table(name = "tb_series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto incremento do id
    private Long id;

    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;

    @Enumerated(EnumType.STRING)
    private Categoria genero; //listar categorias->enum

    private String atores;
    private String poster;
    private String sinopse;//Essa sinopse está em inglês, precisamos converter para português
    //Complicado por que precisamos traduzir todas as sinopses de séries para outra língua ->  consumindo API externa

    //Atributo que relaciona Serie tem episódios
    //@Transient //obj que não vai ser salvo
    //indicar para jpa como acontece o relacionamento -> 1 Série tme N episódios
    //mapear o relacionamento indicando o atributo da outra classe com a relação para não confundir
    @OneToMany( mappedBy = "serie")
    private List<Episodio> episodioList = new ArrayList<>();

    //Construtor padrão exigido pela JPA
    public Serie(){

    }


    //Construtor que faz os atributos dessa classe serem correspondentes aos campos da API
    public Serie(DadosSerie dadosSerie){
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        //transformando avaliação em double com Optinal lançando uma opção que se não obter o valor double ele é colocado como 0
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        //pegando a primeira categoria da série disponível na API; trim elimina espaços em branco e \n
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        try {
            this.sinopse = ConsultaTranslation.obterTraducao(dadosSerie.sinopse()).trim();
        } catch (OpenAiHttpException e) {
            System.out.println("Não foi possível traduzir a sinopse: " + e.getMessage());
            this.sinopse = dadosSerie.sinopse(); // mantém a sinopse original
        }


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }



    @Override
    public String toString() {
        return "Serie{" +
                ", genero=" + genero +
                "titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", avaliacao=" + avaliacao +
                ", atores='" + atores + '\'' +
                ", poster='" + poster + '\'' +
                ", sinopse='" + sinopse + '\'' +
                '}';
    }
}
