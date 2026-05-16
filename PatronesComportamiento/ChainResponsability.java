/**
 * 1. MANEJADOR (Abstract Class)
 * Define la interfaz para manejar las peticiones y el enlace al siguiente.
 */
abstract class Aprobador {
    protected Aprobador siguiente;

    public void setSiguiente(Aprobador siguiente) {
        this.siguiente = siguiente;
    }

    public abstract void procesarSolicitud(double monto);
}

/**
 * 2. MANEJADORES CONCRETOS
 */

class Gerente extends Aprobador {
    @Override
    public void procesarSolicitud(double monto) {
        if (monto <= 1000) {
            System.out.println("Gerente: Yo apruebo este gasto de $" + monto);
        } else if (siguiente != null) {
            System.out.println("Gerente: Demasiado dinero. Pasando al Director...");
            siguiente.procesarSolicitud(monto);
        }
    }
}

class Director extends Aprobador {
    @Override
    public void procesarSolicitud(double monto) {
        if (monto <= 5000) {
            System.out.println("Director: Yo apruebo este gasto de $" + monto);
        } else if (siguiente != null) {
            System.out.println("Director: Esto requiere aprobación del CEO. Pasando...");
            siguiente.procesarSolicitud(monto);
        }
    }
}

class CEO extends Aprobador {
    @Override
    public void procesarSolicitud(double monto) {
        if (monto <= 50000) {
            System.out.println("CEO: Apruebo el gasto de $" + monto + ". ¡Gastadlo bien!");
        } else {
            System.out.println("CEO: $" + monto + " es excesivo. Solicitud DENEGADA.");
        }
    }
}

/**
 * 3. CLIENTE
 */
public class ChainPatternExample {
    public static void main(String[] args) {
        // Configuramos la cadena: Gerente -> Director -> CEO
        Aprobador gerente = new Gerente();
        Aprobador director = new Director();
        Aprobador ceo = new CEO();

        gerente.setSiguiente(director);
        director.setSiguiente(ceo);

        // Enviamos peticiones a la cabeza de la cadena
        System.out.println("--- Petición de $500 ---");
        gerente.procesarSolicitud(500);

        System.out.println("\n--- Petición de $2500 ---");
        gerente.procesarSolicitud(2500);

        System.out.println("\n--- Petición de $15000 ---");
        gerente.procesarSolicitud(15000);

        System.out.println("\n--- Petición de $100000 ---");
        gerente.procesarSolicitud(100000);
    }
}