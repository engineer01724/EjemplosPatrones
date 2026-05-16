/**
 * 1. CLASE ABSTRACTA (Template)
 * Define el esqueleto del algoritmo.
 */
abstract class ProcesadorDeDocumentos {

    // El método plantilla es FINAL: NADIE puede cambiar el orden de los pasos.
    public final void procesarDocumento() {
        abrirArchivo();
        extraerDatos();
        analizarDatos();
        if (necesitaResumen()) { // Este es un Hook (Gancho)
            generarResumen();
        }
        cerrarArchivo();
        System.out.println("--- Proceso Finalizado ---\n");
    }

    // Pasos comunes (Implementación concreta en la base)
    private void abrirArchivo() {
        System.out.println("Sistema: Abriendo archivo...");
    }

    private void cerrarArchivo() {
        System.out.println("Sistema: Cerrando archivo y liberando recursos.");
    }

    // Pasos que varían (Abstractos: las subclases deciden)
    protected abstract void extraerDatos();
    protected abstract void analizarDatos();

    // Hook: Paso opcional (Implementación por defecto)
    protected boolean necesitaResumen() {
        return false;
    }

    protected void generarResumen() {
        // Por defecto no hace nada
    }
}

/**
 * 2. IMPLEMENTACIONES CONCRETAS
 */

class ProcesadorPDF extends ProcesadorDeDocumentos {
    @Override
    protected void extraerDatos() {
        System.out.println("PDF: Extrayendo texto usando motor de OCR...");
    }

    @Override
    protected void analizarDatos() {
        System.out.println("PDF: Analizando estructura de párrafos y metadatos.");
    }

    // El PDF decide usar el hook de resumen
    @Override
    protected boolean necesitaResumen() {
        return true;
    }

    @Override
    protected void generarResumen() {
        System.out.println("PDF: Creando vista previa de la primera página.");
    }
}

class ProcesadorCSV extends ProcesadorDeDocumentos {
    @Override
    protected void extraerDatos() {
        System.out.println("CSV: Leyendo líneas separadas por comas...");
    }

    @Override
    protected void analizarDatos() {
        System.out.println("CSV: Mapeando columnas a objetos de base de datos.");
    }
    // No sobrescribe el hook, usa el comportamiento por defecto (falso)
}

/**
 * 3. CLIENTE
 */
public class TemplateMethodExample {
    public static void main(String[] args) {
        System.out.println("=== Iniciando procesamiento de PDF ===");
        ProcesadorDeDocumentos procPdf = new ProcesadorPDF();
        procPdf.procesarDocumento();

        System.out.println("=== Iniciando procesamiento de CSV ===");
        ProcesadorDeDocumentos procCsv = new ProcesadorCSV();
        procCsv.procesarDocumento();
    }
}