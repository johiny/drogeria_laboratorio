import javax.swing.SwingUtilities;

public class Drogueria {
    private String nombre;

    public Drogueria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public static void main(String[] args) {
        // No usaremos el Look and Feel del sistema para que respete los colores oscuros
        // Iniciar la interfaz gráfica directamente
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}
