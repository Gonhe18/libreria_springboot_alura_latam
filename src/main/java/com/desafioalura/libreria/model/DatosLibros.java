package com.desafioalura.libreria.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Double numeroDeDescargas,
        @JsonAlias("authors") List<DatosAutor> autor
) {
    @Override
    public String toString() {
        return "\nTítulo: " + titulo +
                "\nAutor(es): " + (autor != null && !autor.isEmpty()
                    ? autor.stream().map(DatosAutor::nombre).collect(Collectors.joining(", "))
                    : "Desconocido") +
                "\nIdioma(s): " + idiomas +
                "\nDescargas: " + numeroDeDescargas + "\n";
    }
}


