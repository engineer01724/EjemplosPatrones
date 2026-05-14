
/**
 * STREAMING_CHUNK: Definiendo la clase Singleton simplificada...
 * En este ejemplo, simulamos un Gestor de Configuración único para la App.
 */
class GestorConfiguracion {

    private String temaVisual;

    /**
     * 1. CONSTRUCTOR PRIVADO:
     * Nadie fuera de aquí puede hacer 'new GestorConfiguracion()'.
     */
    private GestorConfiguracion() {
        this.temaVisual = "Modo Oscuro"; // Configuración por defecto
        System.out.println("-> [Sistema] Cargando configuración desde el disco (operación única)...");
    }

    /**
     * STREAMING_CHUNK: Implementando el Holder Estático...
     * Esta clase interna no se carga en memoria hasta que se llama a getInstancia().
     * Java garantiza que la creación de INSTANCIA es segura para hilos sin usar 'synchronized'.
     */
    private static class SoporteSingleton {
        private static final GestorConfiguracion INSTANCIA = new GestorConfiguracion();
    }

    /**
     * 2. PUNTO DE ACCESO GLOBAL:
     * Retorna la única instancia existente.
     */
    public static GestorConfiguracion getInstancia() {
        return SoporteSingleton.INSTANCIA;
    }

    // Métodos de utilidad
    public String getTemaVisual() { return temaVisual; }
    public void setTemaVisual(String tema) { this.temaVisual = tema; }
}

/**
 * STREAMING_CHUNK: Clase de prueba para verificar la unicidad...
 */
class SingletonSimplificado {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA SINGLETON SIMPLIFICADO ===\n");

        // Obtenemos la instancia por primera vez
        GestorConfiguracion configA = GestorConfiguracion.getInstancia();
        System.out.println("Tema A: " + configA.getTemaVisual());

        // Cambiamos el valor desde la variable A
        configA.setTemaVisual("Modo Alto Contraste");

        // Obtenemos la instancia de nuevo en otra variable
        GestorConfiguracion configB = GestorConfiguracion.getInstancia();
        System.out.println("Tema B: " + configB.getTemaVisual());

        System.out.println("\n--- VERIFICACIÓN ---");
        if (configA == configB) {
            System.out.println("✅ OK: configA y configB son exactamente EL MISMO objeto.");
            System.out.println("El cambio hecho en 'A' se refleja en 'B' porque no hay dos objetos.");
        }
    }
}