/**
 * SUBSISTEMA COMPLEJO
 * Estas clases representan la complejidad interna que el cliente NO debería gestionar manualmente.
 */

class Luces {
    public void atenuar(int nivel) { System.out.println("Luces: Ajustadas al " + nivel + "%."); }
    public void encender() { System.out.println("Luces: Encendidas."); }
}

class Proyector {
    public void encender() { System.out.println("Proyector: Encendido."); }
    public void modoPanoramico() { System.out.println("Proyector: Configurado en 16:9."); }
    public void apagar() { System.out.println("Proyector: Apagado."); }
}

class Sonido {
    public void encender() { System.out.println("Sonido: Sistema de audio activo."); }
    public void setVolumen(int nivel) { System.out.println("Sonido: Volumen en " + nivel + "."); }
    public void apagar() { System.out.println("Sonido: Audio desactivado."); }
}

class ReproductorStreaming {
    public void encender() { System.out.println("Streaming: App abierta."); }
    public void reproducir(String pelicula) { System.out.println("Streaming: Reproduciendo '" + pelicula + "'."); }
    public void detener() { System.out.println("Streaming: Video detenido."); }
}

/**
 * LA FACHADA (FACADE)
 * Esta clase coordina el subsistema y ofrece una interfaz simple al programador.
 */
class CineEnCasaFacade {
    private Luces luces;
    private Proyector proyector;
    private Sonido sonido;
    private ReproductorStreaming streaming;

    public CineEnCasaFacade(Luces l, Proyector p, Sonido s, ReproductorStreaming rs) {
        this.luces = l;
        this.proyector = p;
        this.sonido = s;
        this.streaming = rs;
    }

    // El método "mágico" que oculta toda la complejidad
    public void verPelicula(String nombrePelicula) {
        System.out.println("\n--- Preparando todo para la película ---");
        luces.atenuar(10);
        proyector.encender();
        proyector.modoPanoramico();
        sonido.encender();
        sonido.setVolumen(20);
        streaming.encender();
        streaming.reproducir(nombrePelicula);
        System.out.println("--- ¡A disfrutar! ---\n");
    }

    public void terminarPelicula() {
        System.out.println("\n--- Apagando el sistema ---");
        streaming.detener();
        sonido.apagar();
        proyector.apagar();
        luces.encender();
        System.out.println("--- Sistema en reposo ---\n");
    }
}

/**
 * CLIENTE
 */
public class FacadePatternExample {
    public static void main(String[] args) {
        // Instanciamos los componentes del subsistema (esto podría venir de un Factory)
        Luces luces = new Luces();
        Proyector proyector = new Proyector();
        Sonido sonido = new Sonido();
        ReproductorStreaming streaming = new ReproductorStreaming();

        // Creamos la Fachada
        CineEnCasaFacade cine = new CineEnCasaFacade(luces, proyector, sonido, streaming);

        // El cliente solo interactúa con la fachada mediante comandos simples
        cine.verPelicula("Mad Max: Fury Road");

        // ... tiempo después ...

        cine.terminarPelicula();
    }
}