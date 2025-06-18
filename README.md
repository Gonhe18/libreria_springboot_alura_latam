# ** DESAFIO LIBRERIA **
## Challenge incluido en el curso de Java del grupo G8 - ONE. Alura Latam
#### Se utilizó la API de [Gutendex](https://gutendex.com/ "Gutendex")

El desafío cuenta con varias opciones para interactuar

![libreria](https://github.com/user-attachments/assets/5240a5cf-2af5-44bb-8430-205bbbc6365f)

### 1) Buscar libro por título
Aquí nos permitirá buscar un libro en la API de [Gutendex](https://gutendex.com/ "Gutendex"), en caso de encontrarlo lo guardará automáticamente en la base de datos, también registrando el autor del mismo. El proceso cuenta con las validaciones correspondientes para que no se dupliquen los datos del libro o autor.
Al encontrar y registrar el libro en la base de datos, el sistema mostrará algunos datos relevantes del libro buscado.

![libreria1](https://github.com/user-attachments/assets/24a2009a-0081-4822-9c7e-cdd3836ae208)

### 2) Mostrar libros registrados
En esta opción ya interactuamos directamente con la base de datos y la información que se muestre depende de la opción n° 1, ya que de acuerdo a los libros que hayamos buscado, y encontrado en la API de [Gutendex](https://gutendex.com/ "Gutendex"), será la lista que nos aparecerá. En la misma se muestra el título del libro y su autor.

![libreria2](https://github.com/user-attachments/assets/32726095-dc00-4269-b721-8072d1cc932a)

### 3) Mostrar autores registrados
La consulta aquí será solo por los autores que tengamos registrados en la base de datos, similar a lo que sucede en el punto 2, la información que nos aparezca depende de la cantidad de libros que se hayan registrado en la base de datos.

![libreria3](https://github.com/user-attachments/assets/da733529-6227-4ed0-be27-6a1f94a53f87)

### 4) Mostrar autores vivos en determinado año
Aquí se vuelven a mostrar los autores registrados en la base de datos, pero con una distinción, serán solo aquellos que no hayan fallecido al año ingresado, pero también se tendrá en cuenta que debieron haber nacido en una fecha anterior a dicho año.

![libreria4](https://github.com/user-attachments/assets/211e292b-fee3-4b3d-b00e-a3301e700ee4)

### 5) Listar libros por idioma
Esta opción es más estadística, primero debemos elegir un idioma, dentro de las distintas traducciones que puede tener un libro registrado. Luego mostrará la totalidad de libros que hay registrados en ese idioma dentro de la base de datos

![libreria5](https://github.com/user-attachments/assets/17333d22-867f-4bc9-8da9-a0f1ff6154e6)

