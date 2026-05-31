import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrafoRelaciones {
    private Map<String, List<String>> listaAdyacencia;

    public GrafoRelaciones(){
        listaAdyacencia = new HashMap<>();
    }

    public void agregarLibro(String libro){
        listaAdyacencia.putIfAbsent(libro, new ArrayList<>());
    }

    public void agregarRelacion(String libro1, String libro2){ //esta parte me falta agregarla
        if(!listaAdyacencia.containsKey(libro1) || !listaAdyacencia.containsKey(libro2)){
            System.out.println("Uno de los libros no esta registrado en el grafo de relaciones.");
            return;
        }
        listaAdyacencia.get(libro1).add(libro2);
        listaAdyacencia.get(libro2).add(libro1);
    }


    public void recomendar(String libro){
        if(!listaAdyacencia.containsKey(libro)){
            System.out.println("Este libro no existe en el sistema.");
            return;
        }
        List<String> relacionados = listaAdyacencia.get(libro);
        if(relacionados.isEmpty()){
            System.out.println("aun no hay recomendaciones conectadas para este libro.");
        } else {
            System.out.print("recomendacionde libro.... '" + libro + "', te recomendamos: ");
            for(String sugerencia : relacionados){
                System.out.print("[" + sugerencia + "] ");
            }
            System.out.println();
        }
    }

    public void imprimir(){
        System.out.println("\n mapa relaciones de libros");
        for(String libro : listaAdyacencia.keySet()){
            System.out.print(libro + " -> ");
            for(String vecino : listaAdyacencia.get(libro)){
                System.out.print(vecino + " | ");
            }
            System.out.println();
        }
    }
}