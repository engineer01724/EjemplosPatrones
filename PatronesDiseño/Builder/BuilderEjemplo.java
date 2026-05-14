/**
 * Implementación del Patrón de Diseño Builder.
 * 
 * Este patrón es ideal para construir objetos complejos paso a paso,
 * especialmente cuando el objeto tiene muchos atributos opcionales.
 */
public class BuilderPattern {

    public static void main(String[] args) {
        
        // 1. Construcción de un objeto con todos los parámetros (PC Gamer)
        Computadora pcGamer = new Computadora.Builder("Intel i9", "32GB") // Obligatorios
                .setAlmacenamiento("2TB NVMe")                          // Opcional
                .setTarjetaGrafica("NVIDIA RTX 4090")                   // Opcional
                .setEsWifiHabilitado(true)                              // Opcional
                .build();

        // 2. Construcción de un objeto con solo lo básico (PC Oficina)
        Computadora pcOficina = new Computadora.Builder("Intel i3", "8GB")
                .build();

        System.out.println("Configuración PC Gamer:\n" + pcGamer);
        System.out.println("\nConfiguración PC Oficina:\n" + pcOficina);
    }
}

class Computadora {
    // Atributos finales para garantizar la inmutabilidad una vez creado el objeto
    private final String cpu;
    private final String ram;

    // Atributos opcionales
    private final String almacenamiento;
    private final String tarjetaGrafica;
    private final boolean esWifiHabilitado;

    // Constructor privado: Solo el Builder puede instanciar esta clase
    private Computadora(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.almacenamiento = builder.almacenamiento;
        this.tarjetaGrafica = builder.tarjetaGrafica;
        this.esWifiHabilitado = builder.esWifiHabilitado;
    }

    @Override
    public String toString() {
        return String.format(
            "[CPU: %s | RAM: %s | Disco: %s | GPU: %s | WiFi: %s]",
            cpu, ram, almacenamiento, tarjetaGrafica, (esWifiHabilitado ? "Sí" : "No")
        );
    }

    /**
     * Clase estática Builder interna.
     */
    public static class Builder {
        // Mismos atributos que la clase padre
        private final String cpu;
        private final String ram;

        // Valores por defecto para opcionales
        private String almacenamiento = "256GB SSD";
        private String tarjetaGrafica = "Integrada";
        private boolean esWifiHabilitado = false;

        /**
         * El constructor del Builder recibe solo los campos OBLIGATORIOS.
         */
        public Builder(String cpu, String ram) {
            this.cpu = cpu;
            this.ram = ram;
        }


        // Métodos "set" que retornan el mismo objeto Builder (Fluent Interface)
        public Builder setAlmacenamiento(String almacenamiento) {
            this.almacenamiento = almacenamiento;
            return this;
        }

        public Builder setTarjetaGrafica(String tarjetaGrafica) {
            this.tarjetaGrafica = tarjetaGrafica;
            return this;
        }

        public Builder setEsWifiHabilitado(boolean esWifiHabilitado) {
            this.esWifiHabilitado = esWifiHabilitado;
            return this;
        }

        /**
         * Método final que crea la instancia real de Computadora.
         */
        public Computadora build() {
            return new Computadora(this);
        }
    }
}