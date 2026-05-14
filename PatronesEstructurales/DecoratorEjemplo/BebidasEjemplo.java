/**
 * 1. COMPONENTE (Interfaz)
 * Define el contrato para el café y sus decoradores.
 */
interface Bebida {
    String getDescripcion();
    double getPrecio();
}

/**
 * 2. COMPONENTE CONCRETO
 * El objeto base que recibirá las decoraciones.
 */
class CafeBasico implements Bebida {
    @Override
    public String getDescripcion() {
        return "Café básico";
    }

    @Override
    public double getPrecio() {
        return 50.0;
    }
}

/**
 * 3. DECORADOR BASE (Abstracto)
 * Implementa la interfaz y mantiene una referencia al objeto envuelto.
 */
abstract class AgregadoDecorator implements Bebida {
    protected Bebida bebidaEnvoltorio;

    public AgregadoDecorator(Bebida bebida) {
        this.bebidaEnvoltorio = bebida;
    }

    @Override
    public String getDescripcion() {
        return bebidaEnvoltorio.getDescripcion();
    }

    @Override
    public double getPrecio() {
        return bebidaEnvoltorio.getPrecio();
    }
}

/**
 * 4. DECORADORES CONCRETOS
 */
class Leche extends AgregadoDecorator {
    public Leche(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String getDescripcion() {
        return bebidaEnvoltorio.getDescripcion() + ", con Leche";
    }

    @Override
    public double getPrecio() {
        return bebidaEnvoltorio.getPrecio() + 15.0; // Añade costo de leche
    }
}

class Azucar extends AgregadoDecorator {
    public Azucar(Bebida bebida) {
        super(bebida);
    }

    @Override
    public String getDescripcion() {
        return bebidaEnvoltorio.getDescripcion() + ", con Azúcar";
    }

    @Override
    public double getPrecio() {
        return bebidaEnvoltorio.getPrecio() + 5.0;
    }
}

/**
 * USO DEL PATRÓN
 */
public class DecoratorPatternExample {
    public static void main(String[] args) {
        // 1. Empezamos con un café simple
        Bebida miCafe = new CafeBasico();
        System.out.println(miCafe.getDescripcion() + " -> $" + miCafe.getPrecio());

        // 2. Lo decoramos con leche (en tiempo de ejecución)
        miCafe = new Leche(miCafe);
        System.out.println(miCafe.getDescripcion() + " -> $" + miCafe.getPrecio());

        // 3. Lo decoramos con azúcar sobre la leche
        miCafe = new Azucar(miCafe);
        System.out.println(miCafe.getDescripcion() + " -> $" + miCafe.getPrecio());

        // 4. ¡Podemos añadir doble leche si queremos!
        Bebida superCafe = new Leche(new Leche(new CafeBasico()));
        System.out.println("\nPedido especial:");
        System.out.println(superCafe.getDescripcion() + " -> $" + superCafe.getPrecio());
    }
}