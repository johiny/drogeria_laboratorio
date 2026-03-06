public class Medicamento {
    public enum Tipo {
        ANALGESICO("Analgésico"),
        ANALEPTICO("Analéptico"),
        ANESTESICO("Anestésico"),
        ANTIACIDO("Antiácido"),
        ANTIDEPRESIVO("Antidepresivo"),
        ANTIBIOTICO("Antibiótico");

        private final String nombre;
        Tipo(String nombre) { this.nombre = nombre; }
        
        @Override
        public String toString() { return nombre; }
    }

    public enum Distribuidor {
        COFARMA("Cofarma"),
        EMPSEPHAR("Empsephar"),
        CEMEFAR("Cemefar");

        private final String nombre;
        Distribuidor(String nombre) { this.nombre = nombre; }
        
        @Override
        public String toString() { return nombre; }
    }

    public enum Sucursal {
        PRINCIPAL("Principal"),
        SECUNDARIA("Secundaria");

        private final String nombre;
        Sucursal(String nombre) { this.nombre = nombre; }
        
        @Override
        public String toString() { return nombre; }
    }

    private String nombre;
    private Tipo tipo;
    private Distribuidor distribuidor;
    private Sucursal sucursal;
    private double precio;
    private int stock;

    public Medicamento(String nombre, Tipo tipo, Distribuidor distribuidor, Sucursal sucursal, double precio, int stock) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.distribuidor = distribuidor;
        this.sucursal = sucursal;
        this.precio = precio;
        this.stock = stock;
    }

    public String getNombre() { return nombre; }
    public Tipo getTipo() { return tipo; }
    public Distribuidor getDistribuidor() { return distribuidor; }
    public Sucursal getSucursal() { return sucursal; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    
    @Override
    public String toString() {
        return String.format("%-15s | %-13s | %-10s | Branch: %-10s | Price: $%7.2f | Stock: %d", 
            nombre, tipo, distribuidor, sucursal, precio, stock);
    }
}
