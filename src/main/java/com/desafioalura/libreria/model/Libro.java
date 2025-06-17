package com.desafioalura.libreria.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;

//Le indica a la DB que trabajaría con una tabla
@Entity
//Indico el nomobre de la tabla
@Table(name = "libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String titulo;
    private List<String> idiomas;
    private String numeroDeDescargas;

    @ManyToOne
    private Autor autor;

    public Libro() {
    }

    public Libro(DatosLibros datosLibro){
        this.titulo = datosLibro.titulo();
        this.idiomas = datosLibro.idiomas();
        this.numeroDeDescargas = String.valueOf(datosLibro.numeroDeDescargas());

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public String getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(String numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

}
