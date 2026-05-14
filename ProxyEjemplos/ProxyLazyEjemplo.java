/**
 * 1. SUJETO (Interfaz)
 * Define el contrato que ambos (Real y Proxy) deben cumplir.
 */
interface Imagen {
    void mostrar();
}

/**
 * 2. SUJETO REAL
 * Un objeto que es costoso de crear (ej. carga un archivo de 100MB del disco).
 */
class ImagenReal implements Imagen {
    private String nombreArchivo;

    public ImagenReal(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        cargarDesdeDisco();
    }

    private void cargarDesdeDisco() {
        System.out.println("=> Cargando archivo pesado: " + nombreArchivo + " (Esto tarda mucho...)");
        try { Thread.sleep(2000); } catch (InterruptedException e) {} // Simula retraso
    }

    @Override
    public void mostrar() {
        System.out.println("Mostrando imagen: " + nombreArchivo);
    }
}

/**
 * 3. PROXY (Proxy Virtual)
 * Controla la creación de ImagenReal. No la instancia hasta que se llama a mostrar().
 */
class ProxyImagen implements Imagen {
    private String nombreArchivo;
    private ImagenReal imagenReal; // Referencia al sujeto real

    public ProxyImagen(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        // NOTA: Aquí NO creamos la ImagenReal todavía.
    }

    @Override
    public void mostrar() {
        // Carga perezosa (Lazy Loading)
        if (imagenReal == null) {
            imagenReal = new ImagenReal(nombreArchivo);
        }
        imagenReal.mostrar();
    }
}

/**
 * CLIENTE
 */
public class ProxyPatternExample {
    public static void main(String[] args) {
        System.out.println("Cliente: Creando objetos Proxy (esto es instantáneo)...");
        Imagen foto1 = new ProxyImagen("vacaciones_hd.png");
        Imagen foto2 = new ProxyImagen("proyecto_final_ultra_res.jpg");

        System.out.println("\nCliente: Los objetos ya están creados en el programa, pero no en RAM.");
        
        System.out.println("\nCliente: Ahora necesito ver la foto 1:");
        // Aquí es donde el Proxy realmente instancia el objeto pesado
        foto1.mostrar();

        System.out.println("\nCliente: Necesito ver la foto 1 de nuevo (ya debería estar en caché):");
        // El Proxy ya tiene la instancia, no la vuelve a cargar
        foto1.mostrar();

        System.out.println("\nCliente: La foto 2 nunca se mostró, por lo tanto, nunca gastó memoria.");
    }
}
