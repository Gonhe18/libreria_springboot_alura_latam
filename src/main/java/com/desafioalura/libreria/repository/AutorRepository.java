package com.desafioalura.libreria.repository;

import com.desafioalura.libreria.model.Autor;
import com.desafioalura.libreria.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {

    @Query("SELECT a FROM Autor a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Optional<Autor> buscarSiExisteElAutor(@Param("nombre") String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :anio AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento > :anio)")
    List<Autor> autoresVivosEnDeterminadoAnio(@Param("anio") String anio);
}
