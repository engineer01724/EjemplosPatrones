/**
 * 1. STATE (Interfaz)
 * Define los métodos que dependen del estado.
 */
interface EstadoPedido {
    void pagar(Pedido contexto);
    void enviar(Pedido contexto);
    void cancelar(Pedido contexto);
}

/**
 * 2. CONTEXTO
 * Mantiene la referencia al estado actual y delega las acciones.
 */
class Pedido {
    private EstadoPedido estadoActual;

    public Pedido() {
        // Estado inicial
        this.estadoActual = new EstadoPendiente();
    }

    public void setEstado(EstadoPedido nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public void pagar() {
        estadoActual.pagar(this);
    }

    public void enviar() {
        estadoActual.enviar(this);
    }

    public void cancelar() {
        estadoActual.cancelar(this);
    }
}

/**
 * 3. ESTADOS CONCRETOS
 * Cada clase implementa la lógica específica y decide la transición.
 */

class EstadoPendiente implements EstadoPedido {
    @Override
    public void pagar(Pedido contexto) {
        System.out.println("Pago procesado con éxito.");
        contexto.setEstado(new EstadoPagado());
    }

    @Override
    public void enviar(Pedido contexto) {
        System.out.println("ERROR: No se puede enviar un pedido no pagado.");
    }

    @Override
    public void cancelar(Pedido contexto) {
        System.out.println("Pedido cancelado.");
        // Aquí podría ir a un estado 'Cancelado'
    }
}

class EstadoPagado implements EstadoPedido {
    @Override
    public void pagar(Pedido contexto) {
        System.out.println("AVISO: El pedido ya está pagado.");
    }

    @Override
    public void enviar(Pedido contexto) {
        System.out.println("Pedido enviado a logística.");
        contexto.setEstado(new EstadoEnviado());
    }

    @Override
    public void cancelar(Pedido contexto) {
        System.out.println("Reembolsando dinero y cancelando pedido...");
    }
}

class EstadoEnviado implements EstadoPedido {
    @Override
    public void pagar(Pedido contexto) {
        System.out.println("ERROR: El pedido ya fue pagado y enviado.");
    }

    @Override
    public void enviar(Pedido contexto) {
        System.out.println("AVISO: El pedido ya está en camino.");
    }

    @Override
    public void cancelar(Pedido contexto) {
        System.out.println("ERROR: No se puede cancelar un pedido que ya está en el camión.");
    }
}

/**
 * 4. CLIENTE (Main)
 */
public class StatePatternExample {
    public static void main(String[] args) {
        Pedido miPedido = new Pedido();

        System.out.println("--- Flujo Normal ---");
        miPedido.enviar(); // Debería fallar
        miPedido.pagar();  // Pasa a Pagado
        miPedido.enviar(); // Pasa a Enviado

        System.out.println("\n--- Intentos Inválidos ---");
        miPedido.pagar();    // Ya está pagado
        miPedido.cancelar(); // Ya fue enviado, no se puede cancelar
    }
}