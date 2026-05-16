import java.util.ArrayList;
import java.util.List;

/**
 * 1. INTERFAZ ITERADOR
 * Define las operaciones básicas para recorrer cualquier colección.
 */
interface Iterador<T> {
    boolean tieneSiguiente();
    T siguiente();
}

/**
 * 2. INTERFAZ COLECCIÓN (Aggregate)
 * Define que la colección debe ser capaz de entregar un iterador.
 */
interface ColeccionCanales {
    Iterador<String> crearIterador();
}

/**
 * 3. COLECCIÓN CONCRETA
 * Mantiene la lista de canales. Nota que la estructura interna (ArrayList)
 * es privada y no se expone.
 */
class ListaCanalesTV implements ColeccionCanales {
    private List<String> canales = new ArrayList<>();

    public void agregarCanal(String canal) {
        canales.add(canal);
    }

    @Override
    public Iterador<String> crearIterador() {
        return new IteradorCanalesConcreto(this.canales);
    }
}

/**
 * 4. ITERADOR CONCRETO
 * Mantiene el estado del recorrido (el índice actual).
 */
class IteradorCanalesConcreto implements Iterador<String> {
    private List<String> canales;
    private int posicion = 0;

    public IteradorCanalesConcreto(List<String> canales) {
        this.canales = canales;
    }

    @Override
    public boolean tieneSiguiente() {
        return posicion < canales.size();
    }

    @Override
    public String siguiente() {
        if (tieneSiguiente()) {
            return canales.get(posicion++);
        }
        return null;
    }
}

/**
 * 5. CLIENTE (Main)
 */
public class IteratorPatternExample {
    public static void main(String[] args) {
        // Creamos la colección y la llenamos
        ListaCanalesTV miTv = new ListaCanalesTV();
        miTv.agregarCanal("HBO");
        miTv.agregarCanal("Netflix");
        miTv.agregarCanal("Disney+");
        miTv.agregarCanal("Eurosport");

        // Obtenemos el iterador
        Iterador<String> it = miTv.crearIterador();

        System.out.println("Zappeando por los canales:");

        // Recorremos de forma agnóstica a la estructura
        while (it.tieneSiguiente()) {
            String canal = it.siguiente();
            System.out.println("Sintonizando: " + canal);
        }

        // El cliente nunca supo que por dentro había un ArrayList.
        // Podríamos cambiarlo por un Array fijo o una base de datos y este main no cambiaría.
    }
}