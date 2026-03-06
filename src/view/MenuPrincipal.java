package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.TipoMedicamento;
import models.Distribuidor;
import models.Farmacia;
import controller.PedidoController;
import handlers.PedidoException;

public class MenuPrincipal extends JFrame {
    private Drogueria drogueria;
    private JTextField txtNombre;
    private JTextField txtCantidad;
    private JComboBox<Object> comboTipo;
    private ButtonGroup grupoDistribuidor;
    private Map<JCheckBox, Farmacia> farmaciasCheckboxMap;
    private PedidoController controller;
    private JPanel pnlFarmacias; // Panel que contiene los checkboxes de farmacias

    // Colores personalizados (Pastel Medicinal)
    public static final Color COLOR_FONDO = new Color(30, 35, 35);
    public static final Color COLOR_PANEL = new Color(40, 48, 48);
    public static final Color COLOR_TEXTO = new Color(240, 245, 245);
    public static final Color COLOR_ACCENT = new Color(77, 182, 172);
    public static final Color COLOR_ACCENT_HOVER = new Color(128, 203, 196);

    public MenuPrincipal() {
        drogueria = new Drogueria("Drogueria Paco");
        controller = new PedidoController();
        farmaciasCheckboxMap = new HashMap<>();
        
        setTitle(drogueria.getNombre());
        setSize(850, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new BorderLayout(0, 15));
        contentPane.setBackground(COLOR_FONDO);
        // Se reduce un poco el borde superior e inferior
        contentPane.setBorder(new EmptyBorder(25, 40, 25, 40));
        setContentPane(contentPane);

        JPanel pnlHeader = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlHeader.setOpaque(false);
        
        JLabel lblTitulo = new JLabel("Pedido de Medicamentos - " + drogueria.getNombre(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 28));
        lblTitulo.setForeground(COLOR_ACCENT);
        pnlHeader.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("¡La droguería que sí entrega de verdad!", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        lblSubtitulo.setForeground(COLOR_TEXTO);
        pnlHeader.add(lblSubtitulo);

        contentPane.add(pnlHeader, BorderLayout.NORTH);

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
        model.addElement("Seleccione una opción...");
        for (TipoMedicamento t : TipoMedicamento.values()) {
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
        panelCentral.add(crearLabel("Distribuidor Farmacéutico:"), gbc);
        gbc.gridx = 1;
        JPanel pnlDist = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlDist.setOpaque(false);
        grupoDistribuidor = new ButtonGroup();
        for (Distribuidor d : Distribuidor.values()) {
            JRadioButton rb = crearRadioButton(d.toString(), d.name());
            // Agregar listener para actualizar farmacias cuando cambie el distribuidor
            rb.addActionListener(e -> actualizarFarmacias(d));
            grupoDistribuidor.add(rb);
            pnlDist.add(rb);
        }
        panelCentral.add(pnlDist, gbc);

        // Fila 5: Farmacias (dinámico desde el controller según distribuidor seleccionado)
        gbc.gridx = 0; gbc.gridy = 4;
        panelCentral.add(crearLabel("Sucursal de Destino:"), gbc);
        gbc.gridx = 1;
        pnlFarmacias = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlFarmacias.setOpaque(false);
        // Inicialmente vacío hasta que se seleccione un distribuidor
        panelCentral.add(pnlFarmacias, gbc);

        contentPane.add(panelCentral, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(new EmptyBorder(30, 0, 0, 0));

        JButton btnConfirmar = crearBoton("Confirmar");
        JButton btnBorrar = crearBoton("Borrar");

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

    /**
     * Actualiza el panel de farmacias según el distribuidor seleccionado.
     * Cada distribuidor tiene sus propias farmacias (principal y secundaria).
     */
    private void actualizarFarmacias(Distribuidor distribuidor) {
        // Limpiar el panel y el mapa
        pnlFarmacias.removeAll();
        farmaciasCheckboxMap.clear();
        
        // Obtener farmacias del distribuidor seleccionado
        List<Farmacia> farmaciasDisponibles = controller.getFarmaciasDisponibles(distribuidor);
        
        // Crear checkboxes para cada farmacia
        for (Farmacia farmacia : farmaciasDisponibles) {
            String displayName = farmacia.isPrincipal() ? "Principal" : "Secundaria";
            JCheckBox cb = crearCheckBox(displayName, String.valueOf(farmacia.getId()));
            farmaciasCheckboxMap.put(cb, farmacia);
            pnlFarmacias.add(cb);
        }
        
        // Refrescar el panel
        pnlFarmacias.revalidate();
        pnlFarmacias.repaint();
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
        // Recolectar datos del formulario
        String nombre = txtNombre.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();
        
        // Obtener tipo seleccionado (puede ser String o TipoMedicamento)
        Object selectedItem = comboTipo.getSelectedItem();
        TipoMedicamento tipo = null;
        if (selectedItem instanceof TipoMedicamento) {
            tipo = (TipoMedicamento) selectedItem;
        }
        
        // Obtener distribuidor seleccionado
        ButtonModel distribuidorSelected = grupoDistribuidor.getSelection();
        Distribuidor distribuidor = null;
        if (distribuidorSelected != null) {
            distribuidor = Distribuidor.valueOf(distribuidorSelected.getActionCommand());
        }
        
        // Obtener farmacias seleccionadas
        List<Farmacia> farmaciasSeleccionadas = new ArrayList<>();
        for (Map.Entry<JCheckBox, Farmacia> entry : farmaciasCheckboxMap.entrySet()) {
            if (entry.getKey().isSelected()) {
                farmaciasSeleccionadas.add(entry.getValue());
            }
        }
        
        // Delegar toda la validación al controller
        try {
            // Primero validar campos
            List<String> errores = controller.validarCampos(nombre, tipo, cantidadStr, distribuidor, farmaciasSeleccionadas);
            
            if (!errores.isEmpty()) {
                // Determinar si son campos requeridos o errores de formato
                boolean sonCamposRequeridos = errores.stream()
                    .anyMatch(e -> e.contains("medicamento") || e.contains("Tipo") || 
                                  e.contains("Cantidad") || e.contains("Distribuidor") || 
                                  e.contains("Sucursal"));
                
                StringBuilder mensajeError = new StringBuilder();
                if (sonCamposRequeridos) {
                    mensajeError.append("ERROR: CAMPOS REQUERIDOS\n");
                } else {
                    mensajeError.append("DATOS INVÁLIDOS:\n");
                }
                
                for (String error : errores) {
                    mensajeError.append("* ").append(error).append("\n");
                }
                
                JOptionPane.showMessageDialog(this, 
                    mensajeError.toString(), 
                    sonCamposRequeridos ? "Validacion" : "Error", 
                    sonCamposRequeridos ? JOptionPane.ERROR_MESSAGE : JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Si no hay errores, crear el pedido
            models.Pedido pedido = controller.crearPedido(nombre, tipo, cantidadStr, distribuidor, farmaciasSeleccionadas);
            
            // Abrir ventana de resumen (ahora es JFrame)
            ResumenPedidoView resumenView = new ResumenPedidoView(pedido);
            resumenView.setVisible(true);
            
        } catch (PedidoException e) {
            JOptionPane.showMessageDialog(this, "ERROR:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error crítico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void borrarCampos() {
        txtNombre.setText("");
        txtCantidad.setText("");
        comboTipo.setSelectedIndex(0);
        grupoDistribuidor.clearSelection();
        
        // Limpiar panel de farmacias
        pnlFarmacias.removeAll();
        farmaciasCheckboxMap.clear();
        pnlFarmacias.revalidate();
        pnlFarmacias.repaint();
    }
}
