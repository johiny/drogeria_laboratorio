import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    private Drogueria drogueria;
    private JTextField txtNombre;
    private JTextField txtCantidad;
    private JComboBox<Object> comboTipo;
    private ButtonGroup grupoDistribuidor;
    private ButtonGroup grupoSucursal;

    // Colores personalizados (Pastel Medicinal)
    public static final Color COLOR_FONDO = new Color(30, 35, 35);
    public static final Color COLOR_PANEL = new Color(40, 48, 48);
    public static final Color COLOR_TEXTO = new Color(240, 245, 245);
    public static final Color COLOR_ACCENT = new Color(77, 182, 172);
    public static final Color COLOR_ACCENT_HOVER = new Color(128, 203, 196);

    public MenuPrincipal() {
        // Personalizar colores globales para los popups (JOptionPane)
        UIManager.put("OptionPane.background", COLOR_PANEL);
        UIManager.put("Panel.background", COLOR_PANEL);
        UIManager.put("OptionPane.messageForeground", COLOR_TEXTO);
        UIManager.put("Button.background", COLOR_ACCENT);
        UIManager.put("Button.foreground", COLOR_FONDO);
        UIManager.put("Button.font", new Font("Segoe UI Bold", Font.PLAIN, 14));
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 15));

        drogueria = new Drogueria("Farmacia La Esperanza");
        
        setTitle("Drogueria - " + drogueria.getNombre());
        setSize(850, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new BorderLayout(25, 25));
        contentPane.setBackground(COLOR_FONDO);
        contentPane.setBorder(new EmptyBorder(40, 40, 40, 40));
        setContentPane(contentPane);

        JLabel lblTitulo = new JLabel("Solicitud de Suministros Medicos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 28));
        lblTitulo.setForeground(COLOR_ACCENT);
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(COLOR_PANEL);
        panelCentral.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 1: Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panelCentral.add(crearLabel("Nombre del Medicamento:"), gbc);
        gbc.gridx = 1;
        txtNombre = crearTextField(20);
        panelCentral.add(txtNombre, gbc);

        // Fila 2: Tipo
        gbc.gridx = 0; gbc.gridy = 1;
        panelCentral.add(crearLabel("Tipo de Medicamento:"), gbc);
        gbc.gridx = 1;
        DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();
        model.addElement("Seleccione una opcion...");
        for (Medicamento.Tipo t : Medicamento.Tipo.values()) {
            model.addElement(t);
        }
        
        comboTipo = new JComboBox<>(model);
        comboTipo.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton();
                button.setBackground(COLOR_PANEL);
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setContentAreaFilled(false);
                button.setFocusPainted(false);
                return button;
            }
            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                g.setColor(COLOR_PANEL);
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        });
        
        estiloComponente(comboTipo);
        comboTipo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_ACCENT),
            new EmptyBorder(8, 10, 8, 10)
        ));
        
        comboTipo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setBackground(isSelected ? COLOR_ACCENT : COLOR_PANEL);
                c.setForeground(isSelected ? Color.BLACK : Color.WHITE);
                list.setBackground(COLOR_PANEL);
                list.setSelectionBackground(COLOR_ACCENT);
                ((JComponent)c).setBorder(new EmptyBorder(8, 10, 8, 10));
                return c;
            }
        });
        panelCentral.add(comboTipo, gbc);

        // Fila 3: Cantidad
        gbc.gridx = 0; gbc.gridy = 2;
        panelCentral.add(crearLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        txtCantidad = crearTextField(10);
        panelCentral.add(txtCantidad, gbc);

        // Fila 4: Distribuidor
        gbc.gridx = 0; gbc.gridy = 3;
        panelCentral.add(crearLabel("Distribuidor Farmaceutico:"), gbc);
        gbc.gridx = 1;
        JPanel pnlDist = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        pnlDist.setOpaque(false);
        grupoDistribuidor = new ButtonGroup();
        for (Medicamento.Distribuidor d : Medicamento.Distribuidor.values()) {
            JRadioButton rb = crearRadioButton(d.toString(), d.name());
            grupoDistribuidor.add(rb);
            pnlDist.add(rb);
        }
        panelCentral.add(pnlDist, gbc);

        // Fila 5: Sucursal
        gbc.gridx = 0; gbc.gridy = 4;
        panelCentral.add(crearLabel("Sucursal de Destino:"), gbc);
        gbc.gridx = 1;
        JPanel pnlSuc = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        pnlSuc.setOpaque(false);
        grupoSucursal = new ButtonGroup();
        for (Medicamento.Sucursal s : Medicamento.Sucursal.values()) {
            JCheckBox cb = crearCheckBox(s.toString(), s.name());
            grupoSucursal.add(cb);
            pnlSuc.add(cb);
        }
        panelCentral.add(pnlSuc, gbc);

        contentPane.add(panelCentral, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(new EmptyBorder(30, 0, 0, 0));

        JButton btnConfirmar = crearBoton("CONFIRMAR PEDIDO");
        JButton btnBorrar = crearBoton("LIMPIAR FORMULARIO");

        btnConfirmar.addActionListener(e -> confirmarPedido());
        btnBorrar.addActionListener(e -> borrarCampos());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnBorrar);
        contentPane.add(panelBotones, BorderLayout.SOUTH);
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(COLOR_TEXTO);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        return label;
    }

    private JTextField crearTextField(int columnas) {
        JTextField field = new JTextField(columnas);
        estiloComponente(field);
        return field;
    }

    private void estiloComponente(JComponent c) {
        c.setBackground(COLOR_PANEL);
        c.setForeground(Color.WHITE);
        c.setOpaque(true);
        if (c instanceof javax.swing.text.JTextComponent) {
            ((javax.swing.text.JTextComponent)c).setCaretColor(COLOR_ACCENT);
        }
        c.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_ACCENT),
            new EmptyBorder(8, 10, 8, 10)
        ));
    }

    private JRadioButton crearRadioButton(String texto, String command) {
        JRadioButton rb = new JRadioButton(texto);
        rb.setIcon(new CustomRadioIcon());
        rb.setIconTextGap(8);
        rb.setOpaque(false);
        rb.setBackground(COLOR_PANEL);
        rb.setForeground(COLOR_TEXTO);
        rb.setFocusPainted(false);
        rb.setBorderPainted(false);
        rb.setContentAreaFilled(false);
        rb.setBorder(BorderFactory.createEmptyBorder());
        rb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rb.setActionCommand(command);
        return rb;
    }

    private JCheckBox crearCheckBox(String texto, String command) {
        JCheckBox cb = new JCheckBox(texto);
        cb.setIcon(new CustomCheckIcon());
        cb.setIconTextGap(8);
        cb.setOpaque(false);
        cb.setBackground(COLOR_PANEL);
        cb.setForeground(COLOR_TEXTO);
        cb.setFocusPainted(false);
        cb.setBorderPainted(false);
        cb.setContentAreaFilled(false);
        cb.setBorder(BorderFactory.createEmptyBorder());
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cb.setActionCommand(command);
        return cb;
    }

    // Iconos personalizados para eliminar completamente el renderizado feo del sistema

    private class CustomRadioIcon implements Icon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(1.5f));
            AbstractButton b = (AbstractButton) c;
            
            g2.setColor(COLOR_ACCENT);
            // Dibujar con padding interno para evitar recortes por el antialiasing
            g2.drawOval(x + 2, y + 2, 14, 14);
            
            if (b.isSelected()) {
                g2.fillOval(x + 5, y + 5, 8, 8);
            }
            g2.dispose();
        }
        @Override
        public int getIconWidth() { return 20; }
        @Override
        public int getIconHeight() { return 20; }
    }

    private class CustomCheckIcon implements Icon {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(1.5f));
            AbstractButton b = (AbstractButton) c;
            
            g2.setColor(COLOR_ACCENT);
            // Dibujar con padding interno para evitar recortes por el antialiasing
            g2.drawRect(x + 2, y + 2, 14, 14);
            
            if (b.isSelected()) {
                g2.fillRect(x + 5, y + 5, 9, 9);
            }
            g2.dispose();
        }
        @Override
        public int getIconWidth() { return 20; }
        @Override
        public int getIconHeight() { return 20; }
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

    private void confirmarPedido() {
        String nombre = txtNombre.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();
        int selectedIndex = comboTipo.getSelectedIndex();
        ButtonModel distribuidorSelected = grupoDistribuidor.getSelection();
        ButtonModel sucursalSelected = grupoSucursal.getSelection();
        
        java.util.List<String> camposFaltantes = new java.util.ArrayList<>();
        if (nombre.isEmpty()) camposFaltantes.add("Nombre del medicamento");
        if (selectedIndex == 0) camposFaltantes.add("Tipo de medicamento");
        if (cantidadStr.isEmpty()) camposFaltantes.add("Cantidad");
        if (distribuidorSelected == null) camposFaltantes.add("Distribuidor");
        if (sucursalSelected == null) camposFaltantes.add("Sucursal");

        if (!camposFaltantes.isEmpty()) {
            StringBuilder mensajeError = new StringBuilder("ERROR: CAMPOS REQUERIDOS\n");
            for (String campo : camposFaltantes) {
                mensajeError.append("* ").append(campo).append("\n");
            }
            JOptionPane.showMessageDialog(this, mensajeError.toString(), "Validacion", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder erroresEspecificos = new StringBuilder();
        if (!nombre.matches("^[a-zA-Z0-9 ]+$")) {
            erroresEspecificos.append("* El nombre solo permite caracteres alfanumericos.\n");
        }

        int cantidad = -1;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                erroresEspecificos.append("* La cantidad debe ser mayor a cero.\n");
            }
        } catch (NumberFormatException e) {
            erroresEspecificos.append("* La cantidad debe ser un numero entero.\n");
        }

        if (erroresEspecificos.length() > 0) {
            JOptionPane.showMessageDialog(this, "DATOS INVALIDOS:\n" + erroresEspecificos.toString(), "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Medicamento.Tipo tipo = (Medicamento.Tipo) Medicamento.Tipo.values()[selectedIndex - 1];
            String distribuidor = distribuidorSelected.getActionCommand();
            String sucursal = sucursalSelected.getActionCommand();

            String mensaje = String.format("PEDIDO REGISTRADO CON EXITO\n\nMedicamento: %s\nTipo: %s\nCantidad: %d\nDistribuidor: %s\nSucursal: %s",
                    nombre, tipo, cantidad, distribuidor, sucursal);
            
            JOptionPane.showMessageDialog(this, mensaje, "Sistema de Drogueria", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error critico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void borrarCampos() {
        txtNombre.setText("");
        txtCantidad.setText("");
        comboTipo.setSelectedIndex(0);
        grupoDistribuidor.clearSelection();
        grupoSucursal.clearSelection();
    }
}
