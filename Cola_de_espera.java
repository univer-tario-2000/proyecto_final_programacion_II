public class Cola_de_espera<T> {


        private static class Nodo<T>{
            T dato;
            Nodo<T> siguiente;

            Nodo(T dato){
                this.dato = dato;
                this.siguiente = null;
            }
        }

        private Nodo<T> cabeza;
        private Nodo<T> cola;
        private int size;

        public Cola_de_espera(){
            this.cabeza = null;
            this.cola = null;
            this.size = 0;
        }

        public void encolar(T valor){
            if (valor == null){
                throw new IllegalStateException("No se permite encolar null");
            }
            Nodo<T> nuevoNodo = new Nodo<>(valor);
            if (size == 0){
                cabeza = nuevoNodo;
                cola = nuevoNodo;
            }else{
                cola.siguiente = nuevoNodo;
                cola = nuevoNodo;
            }
            size ++;
        }

        public T dequeue(){
            if(isEmpty()){
                throw new IllegalStateException("No se puede desencolar, la cola esta vacia");
            }
            T valor = cabeza.dato;
            cabeza = cabeza.siguiente;
            size --;
            if(isEmpty()){
                cola = null;
            }
            return valor;
        }

        public T peek(){
            if(isEmpty()){
                throw new IllegalStateException("Cola vacia");
            }
            return cabeza.dato;
        }

        public boolean isEmpty(){
            return size == 0;
        }

        public int size(){
            return size;
        }

        public String imprimir(){
            StringBuilder sb = new StringBuilder();
            sb.append("Frente -> ");
            Nodo<T> actual = cabeza;
            while (actual != null){
                sb.append(actual.dato.toString());
                if(actual.siguiente != null) sb.append(" -> ");
                actual = actual.siguiente;
            }
            sb.append(" <- Fin ");
            sb.append(" | tamaño= ").append(size);
            return sb.toString();
        }

}
