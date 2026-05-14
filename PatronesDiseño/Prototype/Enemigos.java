/**
 * Interfaz que define el comportamiento de clonación.
 * En Java podemos usar Cloneable, pero una interfaz personalizada
 * nos da más control sobre el proceso de clonación.
 */
interface PrototipoEnemigo extends Cloneable {
    PrototipoEnemigo clonar();
    void setPosicion(int x, int y);
    void mostrarEstado();
}

/**
 * Clase concreta que representa un tipo de enemigo "Orco".
 */
class Orco implements PrototipoEnemigo {
    private String arma;
    private int salud;
    private int fuerza;
    private int x, y;

    public Orco(String arma, int salud, int fuerza) {
        this.arma = arma;
        this.salud = salud;
        this.fuerza = fuerza;
        System.out.println("-> [Sistema] Orco original creado con gran coste de recursos.");
    }

    // Constructor de copia para facilitar la clonación manual si fuera necesario
    private Orco(Orco prototipo) {
        this.arma = prototipo.arma;
        this.salud = prototipo.salud;
        this.fuerza = prototipo.fuerza;
    }

    @Override
    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void mostrarEstado() {
        System.out.println("Orco [Pos: " + x + "," + y + " | Arma: " + arma + " | Vida: " + salud + " | Fuerza: " + fuerza + "]");
    }

    @Override
    public PrototipoEnemigo clonar() {
        // En una implementación real, aquí se haría una copia profunda (Deep Copy)
        // para asegurar que los objetos internos también se dupliquen.
        return new Orco(this);
    }
}

/**
 * Clase concreta que representa un "Mago Oscuro".
 */
class MagoOscuro implements PrototipoEnemigo {
    private String hechizo;
    private int mana;
    private int x, y;

    public MagoOscuro(String hechizo, int mana) {
        this.hechizo = hechizo;
        this.mana = mana;
        System.out.println("-> [Sistema] Mago Oscuro original creado con gran coste de recursos.");
    }

    private MagoOscuro(MagoOscuro prototipo) {
        this.hechizo = prototipo.hechizo;
        this.mana = prototipo.mana;
    }

    @Override
    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void mostrarEstado() {
        System.out.println("Mago [Pos: " + x + "," + y + " | Hechizo: " + hechizo + " | Maná: " + mana + "]");
    }

    @Override
    public PrototipoEnemigo clonar() {
        return new MagoOscuro(this);
    }
}

/**
 * El Spawner actúa como el registro que guarda los prototipos "maestros".
 */
class SpawnerEnemigos {
    private PrototipoEnemigo orcoBase;
    private PrototipoEnemigo magoBase;

    public SpawnerEnemigos() {
        // Creamos los objetos originales una sola vez
        orcoBase = new Orco("Hacha de Hierro", 100, 25);
        magoBase = new MagoOscuro("Bola de Fuego", 150);
    }

    public PrototipoEnemigo spawnOrco(int x, int y) {
        PrototipoEnemigo clon = orcoBase.clonar();
        clon.setPosicion(x, y);
        return clon;
    }

    public PrototipoEnemigo spawnMago(int x, int y) {
        PrototipoEnemigo clon = magoBase.clonar();
        clon.setPosicion(x, y);
        return clon;
    }
}

class PrototypeGame {
    public static void main(String[] args) {
        System.out.println("=== Cargando Motor de Juego ===");
        SpawnerEnemigos spawner = new SpawnerEnemigos();

        System.out.println("\n--- Generando Horda de Enemigos (Clonación) ---");
        
        // Generamos múltiples enemigos rápidamente clonando el prototipo
        PrototipoEnemigo e1 = spawner.spawnOrco(10, 5);
        PrototipoEnemigo e2 = spawner.spawnOrco(20, 15);
        PrototipoEnemigo e3 = spawner.spawnMago(50, 50);

        e1.mostrarEstado();
        e2.mostrarEstado();
        e3.mostrarEstado();

        System.out.println("\n¿Es el enemigo 1 el mismo objeto que el enemigo 2? " + (e1 == e2));
        System.out.println("¡El sistema ha poblado el mapa sin re-instanciar los atributos base!");
    }
}