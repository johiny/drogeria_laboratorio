package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import models.Pedido;
import models.Farmacia;

public class ResumenPedidoView extends JFrame {
    private Pedido pedido;
    
    // Usar los mismos colores de MenuPrincipal para consistencia
    private static final Color COLOR_FONDO = MenuPrincipal.COLOR_FONDO;
    private static final Color COLOR_PANEL = MenuPrincipal.COLOR_PANEL;
    private static final Color COLOR_TEXTO = MenuPrincipal.COLOR_TEXTO;
    private static final Color COLOR_ACCENT = MenuPrincipal.COLOR_ACCENT;
    private static final Color COLOR_ACCENT_HOVER = MenuPrincipal.COLOR_ACCENT_HOVER;

    public ResumenPedidoView(Pedido pedido) {
        super();
        this.pedido = pedido;
        
        // Título dinámico con el nombre del distribuidor
        setTitle("Pedido al distribuidor " + pedido.getDistribuidor().toString());
        
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new BorderLayout(0, 20));
        contentPane.setBackground(COLOR_FONDO);
        contentPane.setBorder(new EmptyBorder(40, 50, 40, 50));
        setContentPane(contentPane);



        // Panel central con información del pedido
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(COLOR_PANEL);
        panelCentral.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_ACCENT, 2),
            new EmptyBorder(30, 40, 30, 40)
        ));
        
        // Subtítulo: "X unidades del T M"
        String cantidad = String.valueOf(pedido.getCantidad());
        String tipo = pedido.getMedicamento().getTipo().toString();
        String nombre = pedido.getMedicamento().getNombre();
        
        JLabel lblSubtitulo = new JLabel(cantidad + " unidades del " + tipo + " " + nombre);
        lblSubtitulo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 22));
        lblSubtitulo.setForeground(COLOR_TEXTO);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(lblSubtitulo);
        
        panelCentral.add(Box.createRigidArea(new Dimension(0, 25)));
        
        // Información de farmacias
        for (Farmacia farmacia : pedido.getFarmacias()) {
            JLabel lblFarmacia = new JLabel("Para la farmacia situada en " + farmacia.getDireccion());
            lblFarmacia.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            lblFarmacia.setForeground(COLOR_TEXTO);
            lblFarmacia.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelCentral.add(lblFarmacia);
            
            panelCentral.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        
        contentPane.add(panelCentral, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setOpaque(false);
        
        JButton btnCancelar = crearBoton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        
        JButton btnEnviar = crearBoton("Enviar Pedido");
        btnEnviar.addActionListener(e -> enviarPedido());
        
        panelBotones.add(btnCancelar);
        panelBotones.add(btnEnviar);
        contentPane.add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void enviarPedido() {
        // Crear el JDialog personalizado
        JDialog dialogConfirmacion = new JDialog(this, "Confirmación", true);
        dialogConfirmacion.setSize(650, 250);
        dialogConfirmacion.setLocationRelativeTo(this);
        dialogConfirmacion.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        // Panel central con el mensaje
        JPanel panelMensaje = new JPanel();
        panelMensaje.setLayout(new BoxLayout(panelMensaje, BoxLayout.Y_AXIS));
        panelMensaje.setBackground(COLOR_PANEL);
        panelMensaje.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_ACCENT, 2),
            new EmptyBorder(30, 30, 30, 30)
        ));
        
        JLabel lblMensaje1 = new JLabel("Pedido enviado correctamente!");
        lblMensaje1.setFont(new Font("Segoe UI Bold", Font.PLAIN, 24));
        lblMensaje1.setForeground(COLOR_ACCENT);
        lblMensaje1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMensaje.add(lblMensaje1);
        
        panelMensaje.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JLabel lblMensaje2 = new JLabel("Gracias por usar Droguería Paco");
        lblMensaje2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblMensaje2.setForeground(COLOR_TEXTO);
        lblMensaje2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMensaje.add(lblMensaje2);
        
        panel.add(panelMensaje, BorderLayout.CENTER);
        
        // Botón Aceptar
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setOpaque(false);
        
        JButton btnAceptar = crearBoton("Aceptar");
        btnAceptar.addActionListener(e -> {
            dialogConfirmacion.dispose();
            dispose(); // Cerrar también la ventana de resumen
        });
        
        panelBoton.add(btnAceptar);
        panel.add(panelBoton, BorderLayout.SOUTH);
        
        dialogConfirmacion.setContentPane(panel);
        dialogConfirmacion.setVisible(true);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setBackground(COLOR_ACCENT);
        btn.setForeground(COLOR_FONDO);
        btn.setFont(new Font("Segoe UI Bold", Font.PLAIN, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(15, 35, 15, 35));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(COLOR_ACCENT_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(COLOR_ACCENT);
            }
        });
        
        return btn;
    }
}
