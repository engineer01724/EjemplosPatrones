import java.util.ArrayList;
import java.util.List;

/**
 * 1. VISITANTE (Interfaz)
 * Debe conocer todos los tipos concretos de la estructura.
 */
interface VisitanteDocumento {
    void visitar(Texto texto);
    void visitar(Imagen imagen);
    void visitar(Tabla tabla);
}

/**
 * 2. ELEMENTO (Interfaz)
 */
interface ElementoDocumento {
    void aceptar(VisitanteDocumento v);
}

/**
 * 3. ELEMENTOS CONCRETOS
 */
class Texto implements ElementoDocumento {
    private String contenido;
    public Texto(String c) { this.contenido = c; }
    public String getContenido() { return contenido; }

    @Override
    public void aceptar(VisitanteDocumento v) {
        v.visitar(this); // El elemento se identifica ante el visitante
    }
}

class Imagen implements ElementoDocumento {
    private String ruta;
    public Imagen(String r) { this.ruta = r; }
    public String getRuta() { return ruta; }

    @Override
    public void aceptar(VisitanteDocumento v) {
        v.visitar(this);
    }
}

class Tabla implements ElementoDocumento {
    private int filas;
    public Tabla(int f) { this.filas = f; }
    public int getFilas() { return filas; }

    @Override
    public void aceptar(VisitanteDocumento v) {
        v.visitar(this);
    }
}

/**
 * 4. VISITANTES CONCRETOS (Las Operaciones)
 */

// Operación 1: Exportar a HTML
class ExportarHTML implements VisitanteDocumento {
    @Override
    public void visitar(Texto t) {
        System.out.println("<p>" + t.getContenido() + "</p>");
    }

    @Override
    public void visitar(Imagen i) {
        System.out.println("<img src='" + i.getRuta() + "' />");
    }

    @Override
    public void visitar(Tabla t) {
        System.out.println("<table>Filas: " + t.getFilas() + "</table>");
    }
}

// Operación 2: Contar recursos (Estadísticas)
class ContadorRecursos implements VisitanteDocumento {
    private int palabras = 0;
    private int imagenes = 0;

    @Override
    public void visitar(Texto t) {
        palabras += t.getContenido().split("\\s+").length;
    }

    @Override
    public void visitar(Imagen i) {
        imagenes++;
    }

    @Override
    public void visitar(Tabla t) {
        // Ignoramos tablas en este conteo
    }

    public void mostrarReporte() {
        System.out.println("Reporte: " + palabras + " palabras y " + imagenes + " imágenes.");
    }
}

/**
 * 5. CLIENTE
 */
public class VisitorPatternExample {
    public static void main(String[] args) {
        // Estructura de objetos
        List<ElementoDocumento> documento = new ArrayList<>();
        documento.add(new Texto("Hola mundo desde el patron Visitor"));
        documento.add(new Imagen("logo.png"));
        documento.add(new Tabla(5));
        documento.add(new Texto("Fin del documento"));

        // Aplicamos operación 1: Exportar
        System.out.println("--- Generando HTML ---");
        ExportarHTML exportador = new ExportarHTML();
        for (ElementoDocumento e : documento) {
            e.aceptar(exportador);
        }

        // Aplicamos operación 2: Estadísticas
        System.out.println("\n--- Calculando Estadísticas ---");
        ContadorRecursos contador = new ContadorRecursos();
        for (ElementoDocumento e : documento) {
            e.aceptar(contador);
        }
        contador.mostrarReporte();
    }
}