import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 1. MEMENTO (El Recuerdo)
 * Representa el estado guardado. Es inmutable.
 */
class Memento {
    private final String estado;

    public Memento(String estado) {
        this.estado = estado;
    }

    // Solo el originador debería usar este método idealmente
    public String getEstadoGuardado() {
        return estado;
    }
}

/**
 * 2. ORIGINADOR (Originator)
 * El objeto que cambia y que sabe cómo crear/restaurar su propio memento.
 */
class EditorTexto {
    private String contenido;

    public void escribir(String nuevoTexto) {
        this.contenido = nuevoTexto;
        System.out.println("Editor: Contenido actual -> \"" + contenido + "\"");
    }

    // Crea la "foto" del estado actual
    public Memento guardar() {
        System.out.println("Editor: Creando memento...");
        return new Memento(contenido);
    }

    // Restaura el estado desde una "foto"
    public void restaurar(Memento memento) {
        this.contenido = memento.getEstadoGuardado();
        System.out.println("Editor: Estado restaurado -> \"" + contenido + "\"");
    }
}

/**
 * 3. CUIDADOR (Caretaker)
 * Administra el historial de mementos. No conoce el interior del memento.
 */
class Historial {
    // Usamos Deque (ArrayDeque) como pila moderna LIFO
    private Deque<Memento> versiones = new ArrayDeque<>();

    public void salvar(Memento m) {
        versiones.push(m);
    }

    public Memento deshacer() {
        if (!versiones.isEmpty()) {
            return versiones.pop();
        }
        return null;
    }
}

/**
 * 4. CLIENTE (Main)
 */
public class MementoPatternExample {
    public static void main(String[] args) {
        EditorTexto miEditor = new EditorTexto();
        Historial historial = new Historial();

        // Primera acción y guardado
        miEditor.escribir("Versión 1: El inicio");
        historial.salvar(miEditor.guardar());

        // Segunda acción y guardado
        miEditor.escribir("Versión 2: Agregando más texto");
        historial.salvar(miEditor.guardar());

        // Tercera acción (sin guardar aún)
        miEditor.escribir("Versión 3: Texto con errores...");

        System.out.println("\n--- Iniciando proceso de restauración ---");

        // El cliente decide deshacer
        Memento versionAnterior = historial.deshacer(); // Sacamos la Versión 2
        if (versionAnterior != null) {
            miEditor.restaurar(versionAnterior);
        }

        // Deshacer otra vez
        versionAnterior = historial.deshacer(); // Sacamos la Versión 1
        if (versionAnterior != null) {
            miEditor.restaurar(versionAnterior);
        }
    }
}