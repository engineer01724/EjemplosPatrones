import java.util.HashMap;
import java.util.Map;

/**
 * 1. CONTEXTO
 * Contiene información global necesaria para la interpretación.
 * En este caso, mapea nombres de variables a sus valores reales.
 */
class Contexto {
    private Map<String, Integer> variables = new HashMap<>();

    public void asignar(String variable, int valor) {
        variables.put(variable, valor);
    }

    public int obtener(String variable) {
        return variables.getOrDefault(variable, 0);
    }
}

/**
 * 2. EXPRESIÓN (Interfaz)
 * El nodo base de todos los elementos de la gramática.
 */
interface Expresion {
    int interpretar(Contexto contexto);
}

/**
 * 3. EXPRESIONES TERMINALES
 * Representan los valores finales (átomos) de la expresión.
 */
class ExpresionNumero implements Expresion {
    private int numero;
    public ExpresionNumero(int numero) { this.numero = numero; }

    @Override
    public int interpretar(Contexto contexto) {
        return numero;
    }
}

class ExpresionVariable implements Expresion {
    private String nombre;
    public ExpresionVariable(String nombre) { this.nombre = nombre; }

    @Override
    public int interpretar(Contexto contexto) {
        return contexto.obtener(nombre);
    }
}

/**
 * 4. EXPRESIONES NO TERMINALES
 * Definen las reglas de combinación (operaciones).
 */
class Suma implements Expresion {
    private Expresion izquierda;
    private Expresion derecha;

    public Suma(Expresion izq, Expresion der) {
        this.izquierda = izq;
        this.derecha = der;
    }

    @Override
    public int interpretar(Contexto contexto) {
        return izquierda.interpretar(contexto) + derecha.interpretar(contexto);
    }
}

class Resta implements Expresion {
    private Expresion izquierda;
    private Expresion derecha;

    public Resta(Expresion izq, Expresion der) {
        this.izquierda = izq;
        this.derecha = der;
    }

    @Override
    public int interpretar(Contexto contexto) {
        return izquierda.interpretar(contexto) - derecha.interpretar(contexto);
    }
}

/**
 * 5. CLIENTE
 */
public class InterpreterPatternExample {
    public static void main(String[] args) {
        // Configuramos el contexto con valores para nuestras variables
        Contexto contexto = new Contexto();
        contexto.asignar("A", 20);
        contexto.asignar("B", 10);
        contexto.asignar("C", 5);

        // Sentencia: (A + B) - C
        // Nota: En la vida real, un "Parser" leería un String y crearía estos objetos.
        Expresion operacion = new Resta(
                new Suma(new ExpresionVariable("A"), new ExpresionVariable("B")),
                new ExpresionVariable("C")
        );

        // Interpretación
        int resultado = operacion.interpretar(contexto);

        System.out.println("Interpretando la expresión: (A + B) - C");
        System.out.println("Valores en el Contexto: A=20, B=10, C=5");
        System.out.println("Resultado final calculado: " + resultado); // 25
    }
}