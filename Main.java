import java.util.Scanner;

public class Main {

//siento como que alguien anda tocando el programa o como que estoy tocando mal
    static MapaUsuarios mapaUsuarios = new MapaUsuarios();
    static GrafoRelaciones recomendaciones = new GrafoRelaciones();
    static Historial_Pila<String> historial = new Historial_Pila<>();
    static AVL arbolLibros = new AVL();
    static lista_enlazada<Prestamo> listaPrestamos = new lista_enlazada<>();
    static int contadorPrestamos = 1;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        mapaUsuarios.registrarUsuario(new Usuario("U1", "Carlos"));
        arbolLibros.insertar(new Libro("1300", "El Señor de los Anillos","metal"));
        recomendaciones.agregarLibro("El Señor de los Anillos");
        historial.push("Sistema iniciado y datos de prueba cargados.");

        int opcion;
        do {
            System.out.println("\n Menu Principal..");
            System.out.println("1. Gestion de Libros");
            System.out.println("2. Gestion de Usuarios");
            System.out.println("3. Prestamo y Devoluciones");
            System.out.println("4. Ver Historial de Acciones");
            System.out.println("5. Ver Recomendaciones de Libros");
            System.out.println("6. Ver grafo completo de relaciones");
            System.out.println("0. Salir del Sistema");
            System.out.print("Seleccione un módulo: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> menuLibros();
                case 2 -> menuUsuarios();
                case 3 -> menuPrestamos();
                case 4 -> System.out.println(historial.imprimir());
                case 5 -> {
                    System.out.print("Ingrese el título del libro para ver recomendaciones: ");
                    String titulo = sc.nextLine();
                    recomendaciones.recomendar(titulo);
                }
                case 6 -> recomendaciones.imprimir();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // --- MÓDULO 1: LIBROS ---
    private static void menuLibros() {
        System.out.println("\n Gestion de libros..");
        System.out.println("1. Registrar nuevo libro");
        System.out.println("2. Mostrar libros registrados (InOrden)");
        System.out.println("3. Buscar libro por ISBN");
        System.out.print("Seleccione opción: ");
        int op = sc.nextInt();
        sc.nextLine();

        if (op == 1) {
            System.out.print("Ingrese ISBN: ");
            String isbn = sc.nextLine();
            System.out.print("Ingrese Titulo: ");
            String titulo = sc.nextLine();
            System.out.print("Ingrese Categoria: ");
            String categoria = sc.nextLine();


            Libro nuevoLibro = new Libro(isbn, titulo, categoria);
            arbolLibros.insertar(nuevoLibro);
            recomendaciones.agregarLibro(titulo);
            historial.push("Libro registrado: " + titulo+" categoria: "+categoria);
            System.out.println("Libro guardado en AVL");

        } else if (op == 2) {
            System.out.println("Catalogo de libros:");
            arbolLibros.InOrden();
        } else if (op == 3) {
            System.out.print("Ingrese ISBN a buscar: ");
            String isbn = sc.nextLine();
            Libro encontrado = arbolLibros.buscarLibro(isbn);
            if (encontrado != null) {
                System.out.println("Encontrado: " + encontrado.getTitulo() + " Disponible: " + encontrado.isDisponible());
            } else {
                System.out.println("Libro no encontrado en el árbol.");
            }
        }
    }

    private static void menuUsuarios() {
        System.out.println("\n Gestion de usuario..");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Mostrar usuarios");
        System.out.print("Seleccione opción: ");
        int op = sc.nextInt();
        sc.nextLine();

        if (op == 1) {
            System.out.print("Ingrese ID del usuario: ");
            String id = sc.nextLine();
            System.out.print("Ingrese Nombre del usuario: ");
            String nombre = sc.nextLine();

            mapaUsuarios.registrarUsuario(new Usuario(id, nombre));
            historial.push("Usuario registrado: " + nombre);
        } else if (op == 2) {
            mapaUsuarios.mostrarUsuarios();
        }
    }

    private static void menuPrestamos() {
        System.out.println("\n Prestamos y devoluciones..");
        System.out.println("1. Registrar prestamos");
        System.out.println("2. Registrar devolucion");
        System.out.println("3. Mostrar Lista de prestamos");
        System.out.print("Seleccione opción: ");
        int op = sc.nextInt();
        sc.nextLine();

        if (op == 1) {
            System.out.print("Ingrese ID del usuario: ");
            String idUser = sc.nextLine();
            Usuario user = mapaUsuarios.buscarUsuario(idUser);

            if (user == null) {
                System.out.println("Usuario no encontrado.");
                return;
            }

            System.out.print("Ingrese ISBN del libro a prestar: ");
            String isbn = sc.nextLine();
            Libro libro = arbolLibros.buscarLibro(isbn);

            if (libro == null) {
                System.out.println("Libro no encontrado.");
                return;
            }

            if (libro.isDisponible()) {
                Prestamo nuevoPrestamo = new Prestamo("P" + contadorPrestamos++, user, libro, "Hoy");
                listaPrestamos.agregarFinal(nuevoPrestamo);
                libro.setDisponible(false);
                historial.push("Prestamo realizado: " + user.getNombre() + " se llevaron: " + libro.getTitulo());
                System.out.println("prestamo registrado exitosamente");
            } else {
                libro.getColaEspera().encolar(user);
                historial.push("Usuario " + user.getNombre() + " entro a cola de espera para " + libro.getTitulo());
                System.out.println("El libro esta ocupado. Usuario agregado a la cola de espera.");
            }

        } else if (op == 2) {
            System.out.print("Ingrese ID del prestamo a devolver: ");
            String idPrestamo = sc.nextLine();

            boolean devuelto = false;
            var actual = listaPrestamos.getCabeza();
            while (actual != null) {
                Prestamo p = actual.dato;
                if (p.getIdPrestamo().equals(idPrestamo) && p.toString().contains("Pendiente")) {
                    p.registrarDevolucion("Hoy");
                    Libro libroDevuelto = p.getLibro();
                    historial.push("Devolucion registrada: prestamo " + idPrestamo);
                    System.out.println("Devolucion exitosa");
                    devuelto = true;

                    if (!libroDevuelto.getColaEspera().isEmpty()) {
                        Usuario afortunado = libroDevuelto.getColaEspera().dequeue();
                        Prestamo nuevoPrestamo = new Prestamo("P" + contadorPrestamos++, afortunado, libroDevuelto, "Hoy");
                        listaPrestamos.agregarFinal(nuevoPrestamo);
                        historial.push("Libro asignado a " + afortunado.getNombre() + " desde la cola de espera");
                        System.out.println("El libro fue asignado automáticamente a " + afortunado.getNombre() + " que estaba en espera.");
                    } else {
                        libroDevuelto.setDisponible(true); // Vuelve a estar disponible
                    }
                    break;
                }
                actual = actual.siguiente;
            }
            if (!devuelto) {
                System.out.println("Prestamo no encontrado o ya devuelto.");
            }

        } else if (op == 3) {
            System.out.println(listaPrestamos.imprimir());
        }
    }

    private static void registrarPrestamo() {
        System.out.print("Ingrese el ID del usuario que solicita el prestamo: ");
        String idUsuario = sc.nextLine();

        // 1. En lugar de crear un usuario nuevo, lo buscamos en el HashMap
        Usuario usuario = mapaUsuarios.buscarUsuario(idUsuario);
        if (usuario == null) {
            System.out.println("Error: El usuario no existe. Registrelo primero en el menu de usuarios.");
            return;
        }

        System.out.print("Ingrese el ISBN del libro a prestar: ");
        String isbn = sc.nextLine();

        Libro libro = arbolLibros.buscarLibro(isbn);
        if (libro == null) {
            System.out.println("Error: El libro no existe en el catálogo.");
            return;
        }


        if (libro.isDisponible()) {
            System.out.print("Ingrese un ID para este nuevo préstamo (ej. P001): ");
            String idPrestamo = sc.nextLine();

            Prestamo prestamo = new Prestamo(idPrestamo, usuario, libro, "2026-05-29");
            listaPrestamos.agregarFinal(prestamo);
            libro.setDisponible(false);

            historial.push("Prestamo registrado: " + usuario.getNombre() + " se llevo " + libro.getTitulo());
            System.out.println("Prestamo registrado correctamente.");

        } else {
            libro.getColaEspera().encolar(usuario);
            historial.push("Usuario " + usuario.getNombre() + " entro a cola de espera para " + libro.getTitulo());
            System.out.println("El libro esta ocupado en este momento. El usuario ha sido agregado a la cola de espera.");
        }
    }

    private static void mostrarPrestamos() {
        System.out.println("Lista de prestamo:");
        System.out.println(listaPrestamos.imprimir());
    }

    private static void registrarDevolucion() {
        System.out.print("Ingrese ID del préstamo a devolver: ");
        String id = sc.nextLine();

        Nodo<Prestamo> actual = listaPrestamos.getCabeza();
        while (actual != null) {
            if (actual.dato.getIdPrestamo().equals(id)) {
                actual.dato.registrarDevolucion("2026-05-29");
                System.out.println("Devolucion registrada.");
                return;
            }
            actual = actual.siguiente;
        }
        System.out.println("Préstamo no encontrado.");
    }
}