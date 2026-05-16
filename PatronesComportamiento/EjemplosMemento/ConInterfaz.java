import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 1. INTERFAZ MEMENTO (Ancha para el mundo, vacía de métodos)
 * El Cuidador recibirá esto. Como no tiene métodos, el Cuidador
 * no puede extraer ninguna información.
 */
interface Memento {
    // Interfaz de marcador (Marker Interface)
}

/**
 * 2. ORIGINADOR (EditorTexto)
 * Contiene la lógica y la clase interna privada del Memento.
 */
class EditorTexto {
    private String contenido;

    public void escribir(String nuevoTexto) {
        this.contenido = nuevoTexto;
        System.out.println("Editor: Contenido actual -> \"" + contenido + "\"");
    }

    /**
     * Crea un memento. Notar que devuelve la interfaz pública,
     * ocultando la implementación interna.
     */
    public Memento guardar() {
        System.out.println("Editor: Guardando estado internamente...");
        return new TextoMemento(contenido);
    }

    /**
     * Restaura el estado. Aquí es donde ocurre la magia:
     * El editor sabe que el Memento que recibe es en realidad un TextoMemento.
     */
    public void restaurar(Memento memento) {
        if (memento instanceof TextoMemento) {
            TextoMemento m = (TextoMemento) memento;
            this.contenido = m.getEstado();
            System.out.println("Editor: Estado restaurado -> \"" + contenido + "\"");
        }
    }

    /**
     * CLASE INTERNA PRIVADA (La solución real)
     * Solo EditorTexto puede ver y usar esta clase.
     */
    private class TextoMemento implements Memento {
        private final String estado;

        private TextoMemento(String estado) {
            this.estado = estado;
        }

        private String getEstado() {
            return estado;
        }
    }
}

/**
 * 3. CUIDADOR (Historial)
 * Guarda los mementos pero no puede hacer NADA con ellos.
 */
class Historial {
    private Deque<Memento> versiones = new ArrayDeque<>();

    public void salvar(Memento m) {
        versiones.push(m);
    }

    public Memento deshacer() {
        return versiones.poll(); // Usamos poll por seguridad si está vacío
    }
}

/**
 * 4. CLIENTE (Main)
 */
public class MementoPatternExample {
    public static void main(String[] args) {
        EditorTexto miEditor = new EditorTexto();
        Historial historial = new Historial();

        miEditor.escribir("Versión 1");
        historial.salvar(miEditor.guardar());

        miEditor.escribir("Versión 2");
        historial.salvar(miEditor.guardar());

        miEditor.escribir("Versión con errores");

        // Intento de "hackeo" del Cuidador:
        Memento m = historial.deshacer();
        // m.getEstado(); // ERROR DE COMPILACIÓN: La interfaz Memento no tiene ese método.

        System.out.println("\n--- Restauración segura ---");
        miEditor.restaurar(m);
    }
}