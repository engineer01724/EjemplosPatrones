import java.util.ArrayList;
import java.util.List;

/**
 * 1. MEDIATOR (Interfaz)
 * Define el contrato para la comunicación entre los objetos "Colega".
 */
interface ChatMediador {
    void enviarMensaje(String mensaje, Usuario usuario);
    void agregarUsuario(Usuario usuario);
}

/**
 * 2. COLLEAGUE (Clase Abstracta)
 * Representa a los participantes que se comunicarán a través del mediador.
 * No se conocen entre sí, solo conocen al mediador.
 */
abstract class Usuario {
    protected ChatMediador mediador;
    protected String nombre;

    public Usuario(ChatMediador mediador, String nombre) {
        this.mediador = mediador;
        this.nombre = nombre;
    }

    public abstract void enviar(String mensaje);
    public abstract void recibir(String mensaje);
}

/**
 * 3. CONCRETE MEDIATOR
 * Implementa la lógica de coordinación. Sabe quiénes son los participantes
 * y cómo deben interactuar.
 */
class ChatMediadorConcreto implements ChatMediador {
    private List<Usuario> usuarios;

    public ChatMediadorConcreto() {
        this.usuarios = new ArrayList<>();
    }

    @Override
    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    @Override
    public void enviarMensaje(String mensaje, Usuario origen) {
        // La lógica de difusión: enviar a todos excepto al que originó el mensaje
        for (Usuario u : usuarios) {
            if (u != origen) {
                u.recibir(mensaje);
            }
        }
    }
}

/**
 * 4. CONCRETE COLLEAGUE
 */
class UsuarioConcreto extends Usuario {
    public UsuarioConcreto(ChatMediador mediador, String nombre) {
        super(mediador, nombre);
    }

    @Override
    public void enviar(String mensaje) {
        System.out.println(this.nombre + " envía: " + mensaje);
        mediador.enviarMensaje(mensaje, this);
    }

    @Override
    public void recibir(String mensaje) {
        System.out.println(this.nombre + " recibe: " + mensaje);
    }
}

/**
 * 5. CLIENTE (Main)
 */
public class MediatorPatternExample {
    public static void main(String[] args) {
        // Creamos el mediador (la torre de control/servidor de chat)
        ChatMediador mediador = new ChatMediadorConcreto();

        // Creamos los colegas
        Usuario u1 = new UsuarioConcreto(mediador, "Juan");
        Usuario u2 = new UsuarioConcreto(mediador, "Sara");
        Usuario u3 = new UsuarioConcreto(mediador, "Pedro");
        Usuario u4 = new UsuarioConcreto(mediador, "Maria");

        // Registramos los usuarios en el mediador
        mediador.agregarUsuario(u1);
        mediador.agregarUsuario(u2);
        mediador.agregarUsuario(u3);
        mediador.agregarUsuario(u4);

        // La comunicación es indirecta
        u1.enviar("Hola a todos, ¿cómo están?");

        System.out.println();
        u3.enviar("Todo bien por aquí.");
    }
}