/**
 * 1. TARGET (Interfaz Objetivo)
 * Representa el estándar de enchufe de una región (ej. Reino Unido).
 * Cualquier dispositivo que quiera conectarse aquí debe tener 3 patas planas.
 */
interface EnchufeBritanico {
    void flujoElectricoTresPatasPlanas();
}

/**
 * 2. ADAPTEE (Clase a Adaptar)
 * Este es un dispositivo que traemos de otra región (ej. España/Europa).
 * Su conector es de 2 patas redondas y no encaja en la pared británica.
 */
class SecadorEspañol {
    public void encenderConDosPatasRedondas() {
        System.out.println("Secador: Recibiendo energía por 2 patas redondas...");
        System.out.println("Secador: ¡Funcionando a máxima potencia!");
    }
}

/**
 * 3. ADAPTER (El Adaptador Físico)
 * Esta clase "se hace pasar" por un enchufe británico ante la pared,
 * pero por dentro contiene el dispositivo español.
 */
class AdaptadorUniversal implements EnchufeBritanico {
    private SecadorEspañol secador;

    public AdaptadorUniversal(SecadorEspañol secador) {
        this.secador = secador;
    }

    @Override
    public void flujoElectricoTresPatasPlanas() {
        System.out.println("--- Adaptador: Convirtiendo señal de 3 patas planas a 2 patas redondas ---");
        // Delegamos la acción al dispositivo original
        secador.encenderConDosPatasRedondas();
    }
}

/**
 * 4. CLIENTE (La Pared / El Usuario)
 * La pared solo acepta dispositivos que cumplan con la interfaz 'EnchufeBritanico'.
 */
public class AdapterPatternExample {
    public static void main(String[] args) {
        // Tenemos el dispositivo incompatible
        SecadorEspañol miSecador = new SecadorEspañol();

        // No podemos hacer esto:
        // EnchufeBritanico pared = miSecador; // ERROR DE COMPILACIÓN

        // Usamos el adaptador para que sea compatible
        EnchufeBritanico adaptador = new AdaptadorUniversal(miSecador);

        System.out.println("Cliente: Conectando el secador en Londres...");
        
        // La pared (cliente) llama al método que conoce
        adaptador.flujoElectricoTresPatasPlanas();

        System.out.println("\nCliente: El secador funciona perfectamente gracias al adaptador.");
    }
}
