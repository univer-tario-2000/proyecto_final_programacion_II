import java.time.LocalDate;
import java.util.Scanner;

public class Prestamo {
    private String idPrestamo;
    private Usuario usuario;
    private Libro libro;
    private String fechaPrestamo;
    private String fechaDevolucion;

    public Prestamo(String idPrestamo, Usuario usuario, Libro libro, String fechaPrestamo) {
        this.idPrestamo = idPrestamo;
        this.usuario = usuario;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = null;
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public void registrarDevolucion(String fechaDevolucion){
        this.fechaDevolucion = fechaDevolucion;
    }

    @Override
    public String toString() {

        String estado = (fechaDevolucion == null) ? "Pendiente" : fechaDevolucion;

        return "ID: " + idPrestamo +
                " | Usuario: " + usuario.getNombre() +
                " | Libro: " + libro.getTitulo() +
                " | Fecha: " + fechaPrestamo +
                " | Devolución: " + estado;
    } //esto sirve para que java pueda imprimir de manera correcta los prestamos, nose porque

}