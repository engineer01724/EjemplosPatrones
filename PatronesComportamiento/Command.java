import java.util.Stack;

/**
 * 1. COMMAND (Interfaz)
 * Define el contrato para todas las acciones.
 */
interface Comando {
    void ejecutar();
    void deshacer();
}

/**
 * 2. RECEIVER (El que sabe hacer el trabajo real)
 */
class Luz {
    public void encender() { System.out.println("Luz: Encendida."); }
    public void apagar() { System.out.println("Luz: Apagada."); }
}

class Ventilador {
    public void arrancar() { System.out.println("Ventilador: Girando."); }
    public void detener() { System.out.println("Ventilador: Detenido."); }
}

/**
 * 3. CONCRETE COMMANDS
 * Encapsulan la relación entre el Receptor y la acción.
 */
class ComandoEncenderLuz implements Comando {
    private Luz luz;

    public ComandoEncenderLuz(Luz luz) { this.luz = luz; }

    @Override
    public void ejecutar() { luz.encender(); }

    @Override
    public void deshacer() { luz.apagar(); }
}

class ComandoArrancarVentilador implements Comando {
    private Ventilador ventilador;

    public ComandoArrancarVentilador(Ventilador v) { this.ventilador = v; }

    @Override
    public void ejecutar() { ventilador.arrancar(); }

    @Override
    public void deshacer() { ventilador.detener(); }
}

/**
 * 4. INVOKER (El que dispara el comando)
 * No sabe qué hace el comando, solo que tiene un botón.
 */
class ControlRemoto {
    private Stack<Comando> historial = new Stack<>();

    public void presionarBoton(Comando comando) {
        comando.ejecutar();
        historial.push(comando); // Guardamos para poder deshacer
    }

    public void presionarBotonDeshacer() {
        if (!historial.isEmpty()) {
            Comando ultimoComando = historial.pop();
            System.out.print("Deshaciendo: ");
            ultimoComando.deshacer();
        } else {
            System.out.println("Nada que deshacer.");
        }
    }
}

/**
 * 5. CLIENTE (Main)
 */
public class CommandPatternExample {
    public static void main(String[] args) {
        // Configuramos los receptores
        Luz sala = new Luz();
        Ventilador techo = new Ventilador();

        // Creamos los comandos
        Comando luzOn = new ComandoEncenderLuz(sala);
        Comando ventiladorOn = new ComandoArrancarVentilador(techo);

        // El invocador
        ControlRemoto control = new ControlRemoto();

        // Ejecución de acciones
        System.out.println("--- Interactuando con el sistema ---");
        control.presionarBoton(luzOn);
        control.presionarBoton(ventiladorOn);

        // Deshaciendo
        System.out.println("\n--- Arrepentimiento ---");
        control.presionarBotonDeshacer();
        control.presionarBotonDeshacer();
        control.presionarBotonDeshacer(); // Intento extra
    }
}