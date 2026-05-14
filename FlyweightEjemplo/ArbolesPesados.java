import java.util.HashMap;
import java.util.Map;

/**
 * 1. FLYWEIGHT (Estado Intrínseco)
 * Este objeto contiene la parte pesada y común: el tipo de árbol y su color/textura.
 */
class TipoArbol {
    private String nombre;
    private String color;
    private String texturasPesadas; // Simulamos datos pesados

    public TipoArbol(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
        this.texturasPesadas = "Datos de 10MB para " + nombre;
    }

    // El estado extrínseco (x, y) se pasa por parámetro
    public void dibujar(int x, int y) {
        System.out.println("Dibujando " + nombre + " " + color + " en [" + x + "," + y + "]");
    }
}

/**
 * 2. FLYWEIGHT FACTORY
 * Se encarga de reciclar los objetos Flyweight existentes.
 */
class ArbolFactory {
    private static Map<String, TipoArbol> tipos = new HashMap<>();

    public static TipoArbol getTipoArbol(String nombre, String color) {
        String clave = nombre + "-" + color;
        if (!tipos.containsKey(clave)) {
            tipos.put(clave, new TipoArbol(nombre, color));
            System.out.println("=> Creando nuevo TipoArbol: " + clave);
        }
        return tipos.get(clave);
    }
}

/**
 * 3. CONTEXTO (Estado Extrínseco)
 * Clase liviana que solo guarda lo que varía: la posición y la referencia al Flyweight.
 */
class Arbol {
    private int x, y;
    private TipoArbol tipo;

    public Arbol(int x, int y, TipoArbol tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
    }

    public void mostrar() {
        tipo.dibujar(x, y);
    }
}

/**
 * USO DEL PATRÓN
 */
public class FlyweightPatternExample {
    public static void main(String[] args) {
        // Queremos 1.000.000 de árboles, pero solo 2 tipos de texturas
        TipoArbol roble = ArbolFactory.getTipoArbol("Roble", "Verde");
        TipoArbol pino = ArbolFactory.getTipoArbol("Pino", "Verde Oscuro");

        // Creamos muchos árboles compartiendo los tipos
        for (int i = 0; i < 5; i++) {
            new Arbol(i * 10, i * 20, roble).mostrar();
            new Arbol(i * 15, i * 25, pino).mostrar();
        }
        
        System.out.println("\nEn memoria solo existen 2 objetos de 'TipoArbol' con texturas pesadas,");
        System.out.println("independientemente de si dibujamos 10 o 1.000.000 de árboles.");
    }
}
