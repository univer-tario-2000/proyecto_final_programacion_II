public class lista_enlazada<T> {


        private Nodo<T> cabeza;
        private Nodo<T> cola;
        private int size;

        public lista_enlazada(){
            this.cabeza = null;
            this.cola = null;
            this.size = 0;
        }

        public Nodo<T> getCabeza() {
            return cabeza;
        }

        public void agregarInicio(T valor){
            Nodo<T> nuevoNodo = new Nodo<>(valor);
            if (size == 0){
                cabeza = nuevoNodo;
                cola = nuevoNodo;
            } else {
                nuevoNodo.siguiente = cabeza;
                cabeza = nuevoNodo;
            }
            size++;
        }

        public void agregarFinal(T valor){
            Nodo<T> nuevoNodo = new Nodo<>(valor);
            if(size == 0){
                cabeza = nuevoNodo;
                cola = nuevoNodo;
            } else {
                cola.siguiente = nuevoNodo;
                cola = nuevoNodo;
            }
            size++;
        }


        public int size(){
            return size;
        }

        public String imprimir(){
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            Nodo<T> actual = cabeza;
            while(actual != null){
                sb.append(actual.dato);

                if(actual.siguiente !=null)
                {
                    sb.append(", ");
                }
                actual = actual.siguiente;
            }
            sb.append("]");
            return sb.toString();
        }
    }
