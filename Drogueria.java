import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import java.awt.Font;

public class Drogueria {
    private String nombre;

    public Drogueria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public static void main(String[] args) {
        // Mover la personalización de UIManager al main ANTES de cargar la GUI
        UIManager.put("OptionPane.background", MenuPrincipal.COLOR_PANEL);
        UIManager.put("Panel.background", MenuPrincipal.COLOR_PANEL);
        UIManager.put("OptionPane.messageForeground", MenuPrincipal.COLOR_TEXTO);
        UIManager.put("Button.background", MenuPrincipal.COLOR_ACCENT);
        UIManager.put("Button.foreground", MenuPrincipal.COLOR_FONDO);
        UIManager.put("Button.font", new Font("Segoe UI Bold", Font.PLAIN, 14));
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 15));

        // Iniciar la interfaz gráfica directamente
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}
