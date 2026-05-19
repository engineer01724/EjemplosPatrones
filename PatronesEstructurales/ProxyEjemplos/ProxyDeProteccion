// ==========================================
// 1. LA INTERFAZ COMÚN (El Sujeto)
// ==========================================
// Define el contrato que deben seguir tanto el objeto real 
// como el proxy. El cliente siempre interactuará a través de ella.
interface Reporte {
    void mostrarContenido(Usuario usuario);
}

// ==========================================
// 2. EL OBJETO REAL (El Sujeto Real)
// ==========================================
// Es el objeto que contiene la información sensible o realiza la 
// operación costosa. Debe ser protegido de accesos no autorizados.
class ReporteConfidencial implements Reporte {
    private String datosConfidenciales;

    public ReporteConfidencial(String datosConfidenciales) {
        this.datosConfidenciales = datosConfidenciales;
    }

    @Override
    public void mostrarContenido(Usuario usuario) {
        // Esta línea solo debería ejecutarse si el usuario pasó la validación del Proxy
        System.out.println(">>> [ACCESO AUTORIZADO] Datos del reporte: " + datosConfidenciales);
    }
}

// ==========================================
// 3. EL PROXY DE PROTECCIÓN
// ==========================================
// Controla el acceso al objeto real. Implementa la misma interfaz 
// para hacerse pasar por el objeto original frente al cliente.
class ProxyReporte implements Reporte {
    // Mantiene una referencia interna al objeto real que está protegiendo
    private ReporteConfidencial reporteReal;
    private String contenidoReporte;

    public ProxyReporte(String datosConfidenciales) {
        this.contenidoReporte = datosConfidenciales;
        // Nota: El objeto real podría no instanciarse aquí, sino retrasarse (Lazy Initialization)
        // hasta que un usuario con los permisos correctos realmente lo solicite.
    }

    @Override
    public void mostrarContenido(Usuario usuario) {
        System.out.println("[Proxy] Evaluando solicitud de acceso para: " + usuario.getNombre());

        // REGLA DE PROTECCIÓN: Solo los usuarios con rol ADMIN pueden pasar
        if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
            
            // Si es la primera vez que se accede con éxito, creamos el objeto real
            if (reporteReal == null) {
                reporteReal = new ReporteConfidencial(contenidoReporte);
            }
            
            // Delegamos la ejecución al objeto real protegido
            reporteReal.mostrarContenido(usuario);
            
        } else {
            // Si no tiene permisos, el Proxy frena la ejecución AQUÍ y nunca toca el objeto real
            System.out.println("[Proxy] ACCESO DENEGADO: El rol '" + usuario.getRol() + 
                               "' no tiene los privilegios suficientes.");
        }
    }
}

// ==========================================
// 4. CLASE AUXILIAR (El Contexto del Cliente)
// ==========================================
class Usuario {
    private String nombre;
    private String rol;

    public Usuario(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getNombre() { return nombre; }
    public String getRol() { return rol; }
}

// ==========================================
// 5. CLASE PRINCIPAL (Demostración)
// ==========================================
public class Main {
    public static void main(String[] args) {
        // El cliente crea el Proxy, tratándolo como si fuera el Reporte real
        Reporte reporteConfidencial = new ProxyReporte("Balances Financieros Consolidados Q1 2026");

        // Creamos dos usuarios con diferentes niveles de acceso
        Usuario empleadoComun = new Usuario("Carlos", "EMPLEADO");
        Usuario administrador = new Usuario("Ana", "ADMIN");

        System.out.println("--- Intento 1: Usuario sin permisos obligatorios ---");
        // El proxy interceptará esto y bloqueará la llamada antes de que llegue al objeto real
        reporteConfidencial.mostrarContenido(empleadoComun);

        System.out.println("\n--- Intento 2: Usuario con permisos de Administrador ---");
        // El proxy validará el rol exitosamente y delegará el comportamiento al objeto real
        reporteConfidencial.mostrarContenido(administrador);
    }
}
