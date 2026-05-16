/**
 * 1. STRATEGY (Interfaz)
 * Define el contrato para todos los algoritmos de la familia.
 */
interface EstrategiaEnvio {
    double calcularCosto(double peso);
}

/**
 * 2. ESTRATEGIAS CONCRETAS
 * Cada clase encapsula un algoritmo de cálculo diferente.
 */

class EnvioEstandar implements EstrategiaEnvio {
    @Override
    public double calcularCosto(double peso) {
        return peso * 5.0; // Tarifa base
    }
}

class EnvioExpress implements EstrategiaEnvio {
    @Override
    public double calcularCosto(double peso) {
        return (peso * 5.0) + 20.0; // Tarifa base + recargo por velocidad
    }
}

class EnvioInternacional implements EstrategiaEnvio {
    @Override
    public double calcularCosto(double peso) {
        return peso * 15.0; // Tarifa elevada por aduanas
    }
}

/**
 * 3. CONTEXTO
 * Mantiene una referencia a la estrategia y la usa para ejecutar el algoritmo.
 * Nota que el contexto NO sabe cómo se calcula el envío, solo sabe que
 * la estrategia tiene un método para hacerlo.
 */
class Pedido {
    private double peso;
    private EstrategiaEnvio estrategia;

    public Pedido(double peso) {
        this.peso = peso;
    }

    // Permite cambiar la estrategia en tiempo de ejecución
    public void setEstrategiaEnvio(EstrategiaEnvio estrategia) {
        this.estrategia = estrategia;
    }

    public void procesarPedido() {
        if (estrategia == null) {
            System.err.println("ERROR: No se ha seleccionado un método de envío.");
            return;
        }
        double costo = estrategia.calcularCosto(peso);
        System.out.println("Procesando pedido de " + peso + "kg. Costo de envío: $" + costo);
    }
}

/**
 * 4. CLIENTE (Main)
 * El cliente es quien elige la estrategia adecuada según la situación.
 */
public class StrategyPatternExample {
    public static void main(String[] args) {
        Pedido miPedido = new Pedido(10.5);

        System.out.println("--- Usuario elige Envío Estándar ---");
        miPedido.setEstrategiaEnvio(new EnvioEstandar());
        miPedido.procesarPedido();

        System.out.println("\n--- Usuario cambia a Envío Express ---");
        miPedido.setEstrategiaEnvio(new EnvioExpress());
        miPedido.procesarPedido();

        System.out.println("\n--- Envío a otro país ---");
        miPedido.setEstrategiaEnvio(new EnvioInternacional());
        miPedido.procesarPedido();
    }
}