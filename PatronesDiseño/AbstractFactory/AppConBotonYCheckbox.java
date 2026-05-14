/**
 * 1. FAMILIA DE PRODUCTOS A: Botones
 */
interface Boton {
    void renderizar();
}

class BotonWindows implements Boton {
    @Override
    public void renderizar() {
        System.out.println("[Boton] Renderizando estilo cuadrado clásico de Windows.");
    }
}

class BotonMac implements Boton {
    @Override
    public void renderizar() {
        System.out.println("[Boton] Renderizando estilo redondeado y elegante de Mac.");
    }
}


/**
 * 2. FAMILIA DE PRODUCTOS B: Checkboxes
 */
interface Checkbox {
    void marcar();
}

class CheckboxWindows implements Checkbox {
    @Override
    public void marcar() {
        System.out.println("[Checkbox] Marcado con una 'X' de Windows.");
    }
}

class CheckboxMac implements Checkbox {
    @Override
    public void marcar() {
        System.out.println("[Checkbox] Marcado con un 'Tick' azul de Mac.");
    }
}


/**
 * 3. LA FÁBRICA ABSTRACTA
 * Define qué productos puede crear la familia, pero no sabe CÓMO.
 */
interface GUIFactory {
    Boton crearBoton();
    Checkbox crearCheckbox();
}

/**
 * 4. FÁBRICAS CONCRETAS
 * Cada fábrica se encarga de una "familia" específica.
 */
class WindowsFactory implements GUIFactory {
    @Override
    public Boton crearBoton() {
        return new BotonWindows();
    }
    @Override
    public Checkbox crearCheckbox() {
        return new CheckboxWindows();
    }
}

class MacFactory implements GUIFactory {
    @Override
    public Boton crearBoton() {
        return new BotonMac();
    }
    @Override
    public Checkbox crearCheckbox() {
        return new CheckboxMac();
    }
}


/**
 * 5. EL CLIENTE
 * El cliente no sabe qué fábrica está usando, solo sabe que recibe
 * productos que combinan entre sí.
 */
class Aplicacion {
    private Boton boton;
    private Checkbox checkbox;

    public Aplicacion(GUIFactory fabrica) {
        // La aplicación no sabe si es Windows o Mac
        this.boton = fabrica.crearBoton();
        this.checkbox = fabrica.crearCheckbox();
    }

    public void pintar() {
        boton.renderizar();
        checkbox.marcar();
    }
}


class AbstractFactoryPattern {
    public static void main(String[] args) {
        Aplicacion app;
        GUIFactory fabrica;

        // Simulamos la detección del Sistema Operativo
        String os = "mac"; // Esto podría venir de un archivo de configuración

        System.out.println("=== SISTEMA DE UI MULTIPLATAFORMA ===");

        if (os.equalsIgnoreCase("windows")) {
            fabrica = new WindowsFactory();
        } else {
            fabrica = new MacFactory();
        }

        // El cliente (Aplicacion) se configura con la fábrica detectada
        app = new Aplicacion(fabrica);

        // El cliente usa los objetos sin saber sus clases reales
        app.pintar();

        System.out.println("\n¡Todo el sistema se mantiene consistente!");
    }
}