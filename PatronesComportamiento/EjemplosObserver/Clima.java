import java.util.ArrayList;
import java.util.List;

/**
 * 1. INTERFAZ OBSERVADOR
 * El contrato que deben cumplir todos los interesados.
 */
interface Observador {
    void actualizar(float temperatura, float humedad);
}

/**
 * 2. SUJETO (Subject)
 * La estación meteorológica que genera los datos.
 */
class EstacionClima {
    private List<Observador> observadores = new ArrayList<>();
    private float temperatura;
    private float humedad;

    public void registrarObservador(Observador o) {
        observadores.add(o);
    }

    public void eliminarObservador(Observador o) {
        observadores.remove(o);
    }

    // El corazón del patrón: Notificar a todos los interesados
    private void notificarObservadores() {
        for (Observador observador : observadores) {
            observador.actualizar(temperatura, humedad);
        }
    }

    // Método que simula un cambio de estado (ej. lectura de sensores)
    public void setMediciones(float temperatura, float humedad) {
        this.temperatura = temperatura;
        this.humedad = humedad;
        System.out.println("\nEstación: Nuevas mediciones recibidas.");
        notificarObservadores();
    }
}

/**
 * 3. OBSERVADORES CONCRETOS
 * Cada uno reacciona de forma distinta a la misma notificación.
 */

class DisplayTelefono implements Observador {
    @Override
    public void actualizar(float temp, float hum) {
        System.out.println("[App Teléfono] Notificación: Temp=" + temp + "°C, Humedad=" + hum + "%");
    }
}

class PantallaLCDLocal implements Observador {
    @Override
    public void actualizar(float temp, float hum) {
        System.out.println("[LCD Local] Actualizando panel: " + temp + " grados.");
    }
}

class SistemaAlertas implements Observador {
    @Override
    public void actualizar(float temp, float hum) {
        if (temp > 35) {
            System.out.println("[ALERTA] ¡Peligro de Ola de Calor detectado!");
        }
    }
}

/**
 * 4. CLIENTE (Main)
 */
public class ObserverPatternExample {
    public static void main(String[] args) {
        // Creamos el sujeto
        EstacionClima estacion = new EstacionClima();

        // Creamos los observadores
        Observador app = new DisplayTelefono();
        Observador lcd = new PantallaLCDLocal();
        Observador alertas = new SistemaAlertas();

        // Suscripción dinámica
        estacion.registrarObservador(app);
        estacion.registrarObservador(lcd);
        estacion.registrarObservador(alertas);

        // Cambia el clima -> Todos se enteran automáticamente
        estacion.setMediciones(25.5f, 60.0f);

        System.out.println("\n--- Pasa el tiempo... ---");

        // El LCD se rompe (lo desuscribimos)
        estacion.eliminarObservador(lcd);

        // Ola de calor detectada
        estacion.setMediciones(40.2f, 20.0f);
    }
}