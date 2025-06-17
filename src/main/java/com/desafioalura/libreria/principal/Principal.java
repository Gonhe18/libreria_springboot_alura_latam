package com.desafioalura.libreria.principal;

import ch.qos.logback.core.encoder.JsonEscapeUtil;
import com.desafioalura.libreria.model.*;
import com.desafioalura.libreria.repository.AutorRepository;
import com.desafioalura.libreria.repository.LibroRepository;
import com.desafioalura.libreria.service.ConsumoAPI;
import com.desafioalura.libreria.service.ConvierteDatos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Principal {
    private static final String URL = "https://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;

    private Scanner teclado = new Scanner(System.in);

    public Principal(LibroRepository repositorioLibro, AutorRepository repositorioAutor) {
        this.repositorioLibro = repositorioLibro;
        this.repositorioAutor = repositorioAutor;
    }

    public void seleccionMenu() {
        int opcion = -1;

        while (opcion != 0) {
            var menu = """
                    \n1 - Buscar libro por título 
                    2 - Mostrar libros registrados
                    3 - Mostrar autores registrados
                    4 - Mostrar autores vivos en determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            System.out.print("Ingrese una opción: \n");

            String entrada = teclado.nextLine(); // Siempre leemos como String

            try {
                opcion = Integer.parseInt(entrada); // Intentamos convertir
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número.\n");
                continue; // Volvemos a mostrar el menú
            }

            switch (opcion) {
                case 1:
                    bucarLibroPorTitulo();
                    break;
                case 2:
                    librosRegistrados();
                    break;
                case 3:
                    autoresRegistrados();
                    break;
                case 4:
                    autoresVivosEnDeterminadoAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Sesión finalizada.");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private DatosLibros getDatosLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var fragmentoTitulo = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL + "?search=" + fragmentoTitulo.replace(" ", "+"));

        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(fragmentoTitulo.toUpperCase()))
                .findFirst();

        var datosLibro = libroBuscado.orElse(null);

        if (datosLibro != null) {
            Autor autor = null;
            DatosAutor datosAutor = datosLibro.autor() != null && !datosLibro.autor().isEmpty()
                    ? datosLibro.autor().getFirst()
                    : null;

            // Verificar si el autor ya existe, para evitar duplicarlo
            Optional<Autor> autorExistente = repositorioAutor.buscarSiExisteElAutor(datosAutor.nombre());

            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            } else {
                var nuevoAutor = new Autor(datosAutor);
                autor = repositorioAutor.save(nuevoAutor);
            }
            Libro libro = new Libro(datosLibro);
            // Validar que el libro no exista ya con ese título y autor
            Optional<Libro> libroExistente = repositorioLibro.findByTituloContainingIgnoreCaseAndAutor(
                    libro.getTitulo(), autor);

            if (libroExistente.isEmpty()) {
                libro.setAutor(autor);
                repositorioLibro.save(libro);
            }
        } else {
            System.out.println("\nLibro no encontrado\n");
        }
        return datosLibro;
    }

    private void bucarLibroPorTitulo() {
        DatosLibros datos = getDatosLibro();
        if (datos != null) {
            System.out.println(datos.toString());
        }
    }

    private void librosRegistrados() {
        List<Libro> listarLibros = repositorioLibro.findAll();
        System.out.println("\n");
        listarLibros.forEach(l -> System.out.println("Libro: " + l.getTitulo() +
                " - Autor: " + l.getAutor().getNombre()));
        System.out.println("\n");
    }

    private void autoresRegistrados() {
        List<Autor> listarAutores = repositorioAutor.findAll();
        System.out.println("\nLista de autores:");
        listarAutores.forEach(a -> System.out.println(a.getNombre()));
        System.out.println("\n");
    }

    private void autoresVivosEnDeterminadoAnio() {
        System.out.println("Ingrese el año a consultar");

        String anioElegido = teclado.nextLine();
        List<Autor> listarAutores = repositorioAutor.autoresVivosEnDeterminadoAnio(anioElegido);

        if (listarAutores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anioElegido);
        } else {
            System.out.println("\nAutores vivos en el año " + anioElegido);
            listarAutores.forEach(a -> System.out.println("- " + a.getNombre()));
        }
    }

    private void listarLibrosPorIdioma() {
        List<String> listaIdiomas = repositorioLibro.findAllIdiomasUnicos();

        if (listaIdiomas.isEmpty()) {
            System.out.println("No hay idiomas disponibles.");
            return;
        }

        System.out.println("\nSeleccione un idioma:");
        for (int i = 0; i < listaIdiomas.size(); i++) {
            var idioma = idiomaSeleccionado(listaIdiomas.get(i));
            System.out.println((i + 1) + ". " + idioma);
        }

        System.out.print("\nIngrese una opción: ");

        int opcion = Integer.parseInt(teclado.nextLine());
        try {
            if (opcion < 1 || opcion > listaIdiomas.size()) {
                System.out.println("\nOpción inválida.");
                return;
            }

            String idiomaSeleccionado = listaIdiomas.get(opcion - 1);
            String idiomaCompleto = idiomaSeleccionado(idiomaSeleccionado);

            List<Libro> libros = repositorioLibro.findAll();

            long cantidad = libros.stream()
                    .filter(l -> l.getIdiomas() != null && l.getIdiomas().contains(idiomaSeleccionado))
                    .count();

            System.out.println("\nTotal de libros en " + idiomaCompleto + ": " + cantidad);
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida. Por favor debe ingresar un número.\n");
        }
    }

    public String idiomaSeleccionado(String opcion) {
        var idioma = "";
        switch (opcion) {
            case "es":
                idioma = "Español";
                break;
            case "en":
                idioma = "Inglés";
                break;
            case "de":
                idioma = "Alemán";
                break;
            default:
                idioma = "";
                break;
        }
        return idioma;
    }

}
