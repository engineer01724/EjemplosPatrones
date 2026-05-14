import java.util.HashMap;
import java.util.Map;

/**
 * Clase base que define el contrato para el prototipo.
 * Implementamos Cloneable para usar el método clone() de Object.
 */
abstract class Figura implements Cloneable {
    private String id;
    protected String tipo;
    private String color = "Blanco"; // Color por defecto

    abstract void dibujar();

    public String getTipo() {
        return tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            // super.clone() realiza una copia superficial (shallow copy)
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}

class Circulo extends Figura {
    public Circulo() {
        tipo = "Círculo";
    }

    @Override
    public void dibujar() {
        System.out.println("Dibujando un " + tipo + " [ID: " + getId() + " | Color: " + getColor() + "]");
    }
}

class Rectangulo extends Figura {
    public Rectangulo() {
        tipo = "Rectángulo";
    }

    @Override
    public void dibujar() {
        System.out.println("Dibujando un " + tipo + " [ID: " + getId() + " | Color: " + getColor() + "]");
    }
}

/**
 * Esta clase actúa como un registro de objetos prototipo.
 * En lugar de crear nuevas instancias pesadas, clonamos las guardadas aquí.
 */
class FiguraCache {
    private static Map<String, Figura> mapaFiguras = new HashMap<>();

    public static Figura getFigura(String figuraId) {
        Figura figuraCacheada = mapaFiguras.get(figuraId);
        // Retornamos una CLON del objeto original
        return (Figura) figuraCacheada.clone();
    }

    public static void cargarCache() {
        Circulo circulo = new Circulo();
        circulo.setId("1");
        mapaFiguras.put(circulo.getId(), circulo);

        Rectangulo rectangulo = new Rectangulo();
        rectangulo.setId("2");
        mapaFiguras.put(rectangulo.getId(), rectangulo);

        System.out.println("-> Cache de prototipos cargada.");
    }
}

class PrototypePattern {

    public static void main(String[] args) {
        // Inicializamos los prototipos (todos nacen "Blancos")
        FiguraCache.cargarCache();

        System.out.println("--- Personalizando Clones ---");

        // Clonamos el círculo original y lo hacemos ROJO
        Figura circuloRojo = FiguraCache.getFigura("1");
        circuloRojo.setColor("Rojo");
        circuloRojo.dibujar();

        // Clonamos OTRA VEZ el círculo original (que sigue siendo blanco en el cache) y lo hacemos VERDE
        Figura circuloVerde = FiguraCache.getFigura("1");
        circuloVerde.setColor("Verde");
        circuloVerde.dibujar();

        // Verificamos que el original no se "ensució"
        Figura circuloNuevo = FiguraCache.getFigura("1");
        System.out.println("\nUn nuevo clon del cache tiene color: " + circuloNuevo.getColor());

        System.out.println("\n¿Son el mismo objeto? " + (circuloRojo == circuloVerde));
    }
}