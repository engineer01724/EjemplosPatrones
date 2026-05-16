/**
 * Interfaz común para todos los productos que la fábrica puede crear.
 * El cliente solo conocerá esta interfaz.
 */
interface Documento {
    void abrir();
    void cerrar();
}

/**
 * Implementación concreta para archivos PDF.
 */
class DocumentoPDF implements Documento {
    @Override
    public void abrir() {
        System.out.println("[PDF] Abriendo documento con Adobe Reader...");
    }

    @Override
    public void cerrar() {
        System.out.println("[PDF] Cerrando archivo y liberando memoria.");
    }
}

/**
 * Implementación concreta para archivos de Word.
 */
class DocumentoWord implements Documento {
    @Override
    public void abrir() {
        System.out.println("[Word] Abriendo documento con Microsoft Word...");
    }

    @Override
    public void cerrar() {
        System.out.println("[Word] Guardando cambios y cerrando.");
    }
}

/**
 * Esta es la Clase Fábrica. Centraliza la lógica de creación.
 * Evita que el cliente tenga que usar el operador 'new' directamente
 * con clases concretas.
 */
class DocumentoFactory {

    /**
     * El "Método Fábrica".
     * @param tipo El tipo de documento que necesitamos.
     * @return Una instancia de una clase que implementa Documento.
     */
    public Documento crearDocumento(String tipo) {
        if (tipo == null || tipo.isEmpty()) {
            return null;
        }

        // Aquí centralizamos la decisión de qué objeto crear
        switch (tipo.toLowerCase()) {
            case "pdf":
                return new DocumentoPDF();
            case "word":
                return new DocumentoWord();
            default:
                throw new IllegalArgumentException("Formato de documento no soportado: " + tipo);
        }
    }
}

public class FactoryPattern {

    public static void main(String[] args) {
        DocumentoFactory fabrica = new DocumentoFactory();

        System.out.println("=== SISTEMA DE GESTIÓN DE DOCUMENTOS ===");

        // 1. Pedimos un PDF
        Documento doc1 = fabrica.crearDocumento("pdf");
        doc1.abrir();

        // 2. Pedimos un Word
        Documento doc2 = fabrica.crearDocumento("word");
        doc2.abrir();

        // 3. ¿Qué pasa si intentamos crear algo que no existe?
        try {
            Documento doc3 = fabrica.crearDocumento("excel");
        } catch (IllegalArgumentException e) {
            System.out.println("\nError esperado: " + e.getMessage());
        }

        System.out.println("\n=== Tareas finalizadas ===");
        doc1.cerrar();
        doc2.cerrar();
    }
}
