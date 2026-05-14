/**
 * 1. SUJETO (Interfaz)
 * Define las operaciones que el sistema puede realizar.
 */
interface AccesoServidor {
    void descargarArchivo(String nombre, String usuario, String rol);
    void eliminarArchivo(String nombre, String usuario, String rol);
}

/**
 * 2. SUJETO REAL
 * El sistema real que contiene los archivos y realiza las operaciones.
 * No tiene lógica de seguridad; confía en que quien lo llama tiene permiso.
 */
class ServidorArchivosReal implements AccesoServidor {
    @Override
    public void descargarArchivo(String nombre, String usuario, String rol) {
        System.out.println("Servidor Real: Entregando '" + nombre + "' al usuario " + usuario);
    }

    @Override
    public void eliminarArchivo(String nombre, String usuario, String rol) {
        System.out.println("Servidor Real: Archivo '" + nombre + "' eliminado por " + usuario);
    }
}

/**
 * 3. PROXY (Proxy de Protección)
 * Actúa como un guardaespaldas. Verifica el rol antes de delegar al Servidor Real.
 */
class ProxySeguridad implements AccesoServidor {
    private ServidorArchivosReal servidorReal;

    public ProxySeguridad() {
        this.servidorReal = new ServidorArchivosReal();
    }

    @Override
    public void descargarArchivo(String nombre, String usuario, String rol) {
        // Cualquier usuario puede descargar archivos
        servidorReal.descargarArchivo(nombre, usuario, rol);
    }

    @Override
    public void eliminarArchivo(String nombre, String usuario, String rol) {
        // Solo el administrador puede eliminar
        if ("ADMIN".equalsIgnoreCase(rol)) {
            servidorReal.eliminarArchivo(nombre, usuario, rol);
        } else {
            System.err.println("ERROR DE SEGURIDAD: El usuario " + usuario + 
                               " con rol [" + rol + "] no tiene permiso para eliminar '" + nombre + "'.");
        }
    }
}

/**
 * CLIENTE
 */
public class ProxyPatternExample {
    public static void main(String[] args) {
        AccesoServidor sistema = new ProxySeguridad();

        System.out.println("--- Intento 1: Usuario Estándar ---");
        sistema.descargarArchivo("Manual_Usuario.pdf", "Juan123", "USER");
        sistema.eliminarArchivo("Config_Sistema.conf", "Juan123", "USER");

        System.out.println("\n--- Intento 2: Administrador ---");
        sistema.descargarArchivo("Base_Datos.sql", "Admin_Sara", "ADMIN");
        sistema.eliminarArchivo("Virus_Detectado.exe", "Admin_Sara", "ADMIN");
    }
}
