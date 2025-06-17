package com.desafioalura.libreria.repository;

import com.desafioalura.libreria.model.Autor;
import com.desafioalura.libreria.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    Optional<Libro> findByTituloContainingIgnoreCaseAndAutor(String titulo, Autor autor);

    @Query("SELECT DISTINCT l.idiomas FROM Libro l WHERE l.idiomas IS NOT NULL")
    List<String> findAllIdiomasUnicos();


}
