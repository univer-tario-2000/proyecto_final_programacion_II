public class AVL {
    private NodoAVL raiz;

    public void insertar(Libro libro){
        raiz = insertarRecursivo(raiz, libro);
    }

    private NodoAVL insertarRecursivo(NodoAVL nodo, Libro libro){
        if (nodo == null){
            return new NodoAVL(libro);
        }

        int comparacion = libro.getIsbn().compareTo(nodo.libro.getIsbn());

        if(comparacion < 0){
            nodo.izquierda = insertarRecursivo(nodo.izquierda, libro);
        } else if (comparacion > 0){
            nodo.derecha = insertarRecursivo(nodo.derecha, libro);
        } else {
            return nodo;
        }

        nodo.altura = 1 + Math.max(obtenerAltura(nodo.izquierda), obtenerAltura(nodo.derecha));

        int balance = obtenerBalance(nodo);

        if(balance > 1 && libro.getIsbn().compareTo(nodo.izquierda.libro.getIsbn()) < 0){
            return rotarDerecha(nodo);
        }

        if(balance < -1 && libro.getIsbn().compareTo(nodo.derecha.libro.getIsbn()) > 0){
            return rotarIzquierda(nodo);
        }

        if(balance > 1 && libro.getIsbn().compareTo(nodo.izquierda.libro.getIsbn()) > 0){
            nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }

        if(balance < -1 && libro.getIsbn().compareTo(nodo.derecha.libro.getIsbn()) < 0){
            nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    public int obtenerAltura(NodoAVL nodo){
        if(nodo == null){
            return 0;
        }
        return nodo.altura;
    }

    public int obtenerBalance(NodoAVL nodo){
        if(nodo == null){
            return 0;
        }
        return obtenerAltura(nodo.izquierda) - obtenerAltura(nodo.derecha);
    }

    public NodoAVL rotarDerecha(NodoAVL y){
        NodoAVL x = y.izquierda;
        NodoAVL T2 = x.derecha;

        x.derecha = y;
        y.izquierda = T2;

        y.altura = 1 + Math.max(obtenerAltura(y.izquierda), obtenerAltura(y.derecha));
        x.altura = 1 + Math.max(obtenerAltura(x.izquierda), obtenerAltura(x.derecha));

        return x;
    }

    public NodoAVL rotarIzquierda(NodoAVL x){
        NodoAVL y = x.derecha;
        NodoAVL T2 = y.izquierda;

        y.izquierda = x;
        x.derecha = T2;

        x.altura = 1 + Math.max(obtenerAltura(x.izquierda), obtenerAltura(x.derecha));
        y.altura = 1 + Math.max(obtenerAltura(y.izquierda), obtenerAltura(y.derecha));

        return y;
    }

    public void InOrden(){
        inOrdenRecursivo(raiz);
        System.out.println();
    }

    private void inOrdenRecursivo(NodoAVL nodo){
        if (nodo != null){
            inOrdenRecursivo(nodo.izquierda);
            System.out.println("ISBN: " + nodo.libro.getIsbn() + " | Titulo: " + nodo.libro.getTitulo() + " | Disponible: " + nodo.libro.isDisponible());
            inOrdenRecursivo(nodo.derecha);
        }
    }

    public Libro buscarLibro(String isbn) {
        return buscarRecursivo(raiz, isbn);
    }

    private Libro buscarRecursivo(NodoAVL nodo, String isbn) {
        if (nodo == null) {
            return null;
        }
        int comparacion = isbn.compareTo(nodo.libro.getIsbn());
        if (comparacion == 0) {
            return nodo.libro;
        } else if (comparacion < 0) {
            return buscarRecursivo(nodo.izquierda, isbn);
        } else {
            return buscarRecursivo(nodo.derecha, isbn);
        }
    }


    private void enlazarPorCategoriaRec(NodoAVL nodo, Libro nuevoLibro, GrafoRelaciones grafo) {
        if (nodo != null) {
            enlazarPorCategoriaRec(nodo.izquierda, nuevoLibro, grafo);

            if (nodo.libro.getCategoria().equalsIgnoreCase(nuevoLibro.getCategoria())
                    && !nodo.libro.getTitulo().equalsIgnoreCase(nuevoLibro.getTitulo())) {

                grafo.agregarRelacion(nuevoLibro.getTitulo(), nodo.libro.getTitulo());
            }

            enlazarPorCategoriaRec(nodo.derecha, nuevoLibro, grafo);
        }
    }
}