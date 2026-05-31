public class Historial_Pila<T> {

        private static class Nodo<T> {
            T dato;
            Nodo<T> siguiente;

            Nodo(T dato) {
                this.dato = dato;
                this.siguiente = null;
            }
        }

        private Nodo<T> cima;
        private int size;

        public Historial_Pila() {
            this.cima = null;
            this.size = 0;
        }

        public void push(T valor) {
            if (valor == null) {
                throw new IllegalStateException("No se permite apilar null");
            }
            Nodo<T> nuevoNodo = new Nodo<>(valor);

            nuevoNodo.siguiente = cima;
            cima = nuevoNodo;
            size++;
        }

        public T pop() {
            if (isEmpty()) {
                throw new IllegalStateException("No se puede desapilar, la pila esta vacia");
            }
            T valor = cima.dato;
            cima = cima.siguiente;
            size--;
            return valor;
        }

        public T peek() {
            if (isEmpty()) {
                throw new IllegalStateException("La pila esta vacia");
            }
            return cima.dato;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public String imprimir() {
            StringBuilder sb = new StringBuilder();
            sb.append("Historial.. \n");

            Nodo<T> actual = cima;
            while (actual != null) {
                sb.append("> ").append(actual.dato.toString()).append("\n");
                actual = actual.siguiente;
            }
            sb.append("--------------------------\n");
            sb.append("Total de registros: ").append(size);

            return sb.toString();
        }

}
