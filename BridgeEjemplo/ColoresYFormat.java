/**
 * 1. IMPLEMENTADOR (La dimensión que varía)
 */
interface Color {
    void aplicarColor();
}

/**
 * 2. IMPLEMENTACIONES CONCRETAS
 */
class Rojo implements Color {
    public void aplicarColor() {
        System.out.print(" de color Rojo");
    }
}

class Azul implements Color {
    public void aplicarColor() {
        System.out.print(" de color Azul");
    }
}

/**
 * 3. ABSTRACCIÓN (El "Puente")
 * Contiene una referencia a la interfaz Color.
 */
abstract class Forma {
    protected Color color; // Este es el "Bridge"

    protected Forma(Color color) {
        this.color = color;
    }

    abstract void dibujar();
}

/**
 * 4. ABSTRACCIONES REFINADAS
 */
class Circulo extends Forma {
    public Circulo(Color color) {
        super(color);
    }

    @Override
    void dibujar() {
        System.out.print("Dibujando Círculo");
        color.aplicarColor();
        System.out.println();
    }
}

class Cuadrado extends Forma {
    public Cuadrado(Color color) {
        super(color);
    }

    @Override
    void dibujar() {
        System.out.print("Dibujando Cuadrado");
        color.aplicarColor();
        System.out.println();
    }
}

/**
 * USO DEL PATRÓN
 */
public class BridgePatternExample {
    public static void main(String[] args) {
        Forma circuloRojo = new Circulo(new Rojo());
        Forma cuadradoAzul = new Cuadrado(new Azul());

        circuloRojo.dibujar();
        cuadradoAzul.dibujar();
        
        // Podemos crear nuevas combinaciones sin crear nuevas clases
        Forma circuloAzul = new Circulo(new Azul());
        circuloAzul.dibujar();
    }
}
