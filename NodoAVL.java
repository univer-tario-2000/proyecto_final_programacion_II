public class NodoAVL {
    Libro libro;
    int altura;
    NodoAVL derecha;
    NodoAVL izquierda;

    public NodoAVL(Libro libro){
        this.libro = libro;
        this.altura = 1;
    }
}