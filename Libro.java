import java.time.DateTimeException;
import java.time.LocalDate;

public class Libro {


    private String isbn;
    private String titulo;
    private boolean disponible;
    private String categoria;
    private Cola_de_espera<Usuario> colaEspera;

    public Libro(String isbn, String titulo, String categoria) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.disponible = true;
        this.categoria = categoria;
        this.colaEspera = new Cola_de_espera<>();
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getCategoria() {
        return categoria;
    }

    public Cola_de_espera<Usuario> getColaEspera() {
        return colaEspera;
    }

}
