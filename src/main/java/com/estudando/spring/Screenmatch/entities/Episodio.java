package com.estudando.spring.Screenmatch.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.time.DateTimeException;
import java.time.LocalDate;
@Entity
@Table( name = "tb_episodio")
public class Episodio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// auto incremento do id
    private Long id;

    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;

    @ManyToOne
    private Serie serie;

    public Episodio() {
    }

    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.numeroEpisodio = dadosEpisodio.numero();
        try{
            this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
        }catch (NumberFormatException e){
            this.avaliacao = 0.0;
        }
        try{
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento());
        }catch (DateTimeException e){
            this.dataLancamento = null;
        }

    }
    //Setar os atributos dessa classe com as referências da DadosEpisodio
    //reutilizar a lógica e os dados já existentes,
    //classe Episodio é responsável por representar um episódio de uma série, enquanto a classe DadosEpisodio pode ser vista como uma estrutura que contém dados brutos.

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Serie getSerie() {return serie;}
    public void setSerie(Serie serie) {this.serie = serie;}

    public Integer getTemporada() {return temporada;}
    public void setTemporada(Integer temporada) {this.temporada = temporada;}

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}

    public Integer getNumeroEpisodio() {return numeroEpisodio;}
    public void setNumeroEpisodio(Integer numeroEpisodio) {this.numeroEpisodio = numeroEpisodio;}

    public Double getAvaliacao() {return avaliacao;}
    public void setAvaliacao(Double avaliacao) {this.avaliacao = avaliacao;}

    public LocalDate getDataLancamento() {return dataLancamento;}
    public void setDataLancamento(LocalDate dataLancamento) {this.dataLancamento = dataLancamento;}

    @Override
    public String toString() {
        return "Episodio{" +
                "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento +
                '}';
    }
}
