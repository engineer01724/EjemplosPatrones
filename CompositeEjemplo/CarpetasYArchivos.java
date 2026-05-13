import java.util.ArrayList;
import java.util.List;

/**
 * 1. COMPONENTE
 */
interface ElementoSistema {
    void mostrarDetalles();
    long obtenerTamaño();
}

/**
 * 2. HOJA (Leaf)
 */
class Archivo implements ElementoSistema {
    private String nombre;
    private long tamaño;

    public Archivo(String nombre, long tamaño) {
        this.nombre = nombre;
        this.tamaño = tamaño;
    }

    public void mostrarDetalles() {
        System.out.println("Archivo: " + nombre + " (" + tamaño + " KB)");
    }

    public long obtenerTamaño() {
        return tamaño;
    }
}

/**
 * 3. COMPUESTO (Composite)
 */
class Carpeta implements ElementoSistema {
    private String nombre;
    private List<ElementoSistema> hijos = new ArrayList<>();

    public Carpeta(String nombre) {
        this.nombre = nombre;
    }

    public void agregar(ElementoSistema elemento) {
        hijos.add(elemento);
    }

    public void mostrarDetalles() {
        System.out.println("Carpeta: [" + nombre + "]");
        for (ElementoSistema hijo : hijos) {
            hijo.mostrarDetalles(); // Delegación recursiva
        }
    }

    public long obtenerTamaño() {
        long total = 0;
        for (ElementoSistema hijo : hijos) {
            total += hijo.obtenerTamaño();
        }
        return total;
    }
}

/**
 * USO DEL PATRÓN
 */
class CompositePatternExample {
    public static void main(String[] args) {
        // Hojas
        Archivo a1 = new Archivo("foto.jpg", 500);
        Archivo a2 = new Archivo("tesis.pdf", 2000);
        Archivo a3 = new Archivo("notas.txt", 10);

        // Compuesto (Subcarpeta)
        Carpeta subCarpeta = new Carpeta("Mis Documentos");
        subCarpeta.agregar(a2);
        subCarpeta.agregar(a3);

        // Compuesto Principal (Raíz)
        Carpeta raiz = new Carpeta("C:");
        raiz.agregar(a1);
        raiz.agregar(subCarpeta);

        // El cliente trata todo igual
        raiz.mostrarDetalles();
        System.out.println("Tamaño total del disco: " + raiz.obtenerTamaño() + " KB");
    }
}
