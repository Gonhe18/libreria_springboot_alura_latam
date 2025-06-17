package com.desafioalura.libreria;

import com.desafioalura.libreria.principal.Principal;
import com.desafioalura.libreria.repository.AutorRepository;
import com.desafioalura.libreria.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibreriaApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repositorioLibro;
	@Autowired
	private AutorRepository repositorioAutor;

	public static void main(String[] args) {
		SpringApplication.run(LibreriaApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorioLibro,repositorioAutor);
		principal.seleccionMenu();
	}
}
