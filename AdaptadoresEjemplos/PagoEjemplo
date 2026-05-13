/**
 * 1. TARGET (Interfaz Objetivo)
 * Esta es la interfaz que el cliente conoce y utiliza.
 * Representa el estándar moderno de nuestro sistema.
 */
interface ProcesadorPago {
    void procesar(String cuenta, double monto);
}

/**
 * 2. ADAPTEE (Clase a Adaptar)
 * Esta clase representa el sistema externo o antiguo.
 * Notarás que los nombres de los métodos y los parámetros son distintos.
 * No podemos modificar esta clase porque es de un tercero o código legacy.
 */
class SistemaPagoExterno {
    public void autorizarTransaccion(double cantidad) {
        System.out.println("Sistema Externo: Autorizando monto de $" + cantidad);
    }

    public void ejecutarCobro(long idCuenta) {
        System.out.println("Sistema Externo: Ejecutando cobro a la cuenta ID: " + idCuenta);
    }
}

/**
 * 3. ADAPTER (El Adaptador)
 * Esta clase implementa la interfaz moderna (Target) y envuelve 
 * la instancia del sistema viejo (Adaptee).
 */
class AdaptadorPagoExterno implements ProcesadorPago {
    private SistemaPagoExterno sistemaExterno;

    public AdaptadorPagoExterno(SistemaPagoExterno sistemaExterno) {
        this.sistemaExterno = sistemaExterno;
    }

    @Override
    public void procesar(String cuenta, double monto) {
        // El adaptador realiza la "traducción" de los datos
        // Por ejemplo, convierte el String de cuenta en el long que espera el sistema externo
        long idCuentaFormateada = Long.parseLong(cuenta.replaceAll("[^0-9]", ""));
        
        System.out.println("--- Adaptador en acción ---");
        sistemaExterno.autorizarTransaccion(monto);
        sistemaExterno.ejecutarCobro(idCuentaFormateada);
        System.out.println("--- Conversión y proceso finalizado ---");
    }
}

/**
 * 4. CLIENTE (Main)
 * El cliente solo interactúa con la interfaz ProcesadorPago.
 * No sabe (ni le importa) si el procesamiento lo hace un sistema nuevo o uno viejo adaptado.
 */
public class AdapterPatternExample {
    public static void main(String[] args) {
        // Escenario A: Usaríamos un procesador normal si existiera
        // ProcesadorPago procesadorNormal = new ProcesadorModerno(); 

        // Escenario B: Queremos usar el sistema externo usando el Adaptador
        SistemaPagoExterno sistemaViejo = new SistemaPagoExterno();
        ProcesadorPago miAdaptador = new AdaptadorPagoExterno(sistemaViejo);

        System.out.println("Cliente: Iniciando pago de suscripción...");
        
        // El cliente usa el método estándar 'procesar'
        miAdaptador.procesar("CTA-12345678", 1500.50);
        
        System.out.println("\nCliente: El pago se completó exitosamente a través del adaptador.");
    }
}
