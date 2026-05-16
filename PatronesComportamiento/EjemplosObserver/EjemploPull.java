import java.util.ArrayList;
import java.util.List;

/**
 * 1. INTERFAZ OBSERVADOR (PULL)
 * El método actualizar no recibe los datos, solo la señal.
 * A veces se pasa el objeto Sujeto como parámetro para que el observador
 * sepa quién lo está llamando si está suscrito a varios.
 */
interface Observador {
    void actualizar();
}

/**
 * 2. SUJETO (Concrete Subject)
 */
class BlogTecnologia {
    private List<Observador> suscriptores = new ArrayList<>();
    private String ultimoArticulo;
    private int contadorVisitas;

    public void suscribir(Observador o) {
        suscriptores.add(o);
    }

    public void desuscribir(Observador o) {
        suscriptores.remove(o);
    }

    private void notificar() {
        for (Observador s : suscriptores) {
            s.actualizar(); // Solo avisamos
        }
    }

    public void publicar(String titulo) {
        this.ultimoArticulo = titulo;
        this.contadorVisitas = 0; // Reset para el nuevo artículo
        System.out.println("Blog: Nuevo artículo publicado -> " + titulo);
        notificar();
    }

    // Getters para que los observadores "tiren" (pull) de los datos
    public String getUltimoArticulo() {
        return ultimoArticulo;
    }

    public int getContadorVisitas() {
        return contadorVisitas;
    }
}

/**
 * 3. OBSERVADORES CONCRETOS (PULL)
 */

class SuscriptorEmail implements Observador {
    private String nombre;
    private BlogTecnologia blog; // Referencia para hacer el PULL

    public SuscriptorEmail(String nombre, BlogTecnologia blog) {
        this.nombre = nombre;
        this.blog = blog;
    }

    @Override
    public void actualizar() {
        // El observador decide QUÉ datos extraer
        String titulo = blog.getUltimoArticulo();
        System.out.println("[Email para " + nombre + "] ¡Nuevo post!: " + titulo);
    }
}

class PanelEstadisticas implements Observador {
    private BlogTecnologia blog;

    public PanelEstadisticas(BlogTecnologia blog) {
        this.blog = blog;
    }

    @Override
    public void actualizar() {
        // A este observador solo le interesan las estadísticas, no el título
        int visitas = blog.getContadorVisitas();
        System.out.println("[Estadísticas] El artículo inició con " + visitas + " visitas.");
    }
}

/**
 * 4. CLIENTE (Main)
 */
public class ObserverPullExample {
    public static void main(String[] args) {
        BlogTecnologia miBlog = new BlogTecnologia();

        // Los observadores se crean con una referencia al sujeto
        Observador juan = new SuscriptorEmail("Juan", miBlog);
        Observador analytics = new PanelEstadisticas(miBlog);

        miBlog.suscribir(juan);
        miBlog.suscribir(analytics);

        // Publicación
        miBlog.publicar("Patrones de Diseño: El modelo PULL");

        System.out.println("\n--- Otro artículo ---");
        miBlog.publicar("Java 21 y las Virtual Threads");
    }
}