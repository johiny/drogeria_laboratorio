import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    private Drogueria drogueria;
    private JTextField txtNombre;
    private JTextField txtCantidad;
    private JComboBox<Object> comboTipo;
    private ButtonGroup grupoDistribuidor;
    private ButtonGroup grupoSucursal;

    public MenuPrincipal() {
        drogueria = new Drogueria("Farmacia La Esperanza");
        
        setTitle("Droguería - " + drogueria.getNombre());
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // La ventana está lista para agregar componentes
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Panel superior para entradas de texto, combo, radios y checkboxes
        JPanel panelNorte = new JPanel(new GridLayout(4, 1));
        
        // Fila 1: Nombre, Tipo, Cantidad
        JPanel panelFila1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFila1.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(15);
        panelFila1.add(txtNombre);

        panelFila1.add(new JLabel("Tipo:"));
        
        // Crear el modelo del combo con el placeholder al inicio
        DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();
        model.addElement("Seleccione una opción...");
        for (Medicamento.Tipo t : Medicamento.Tipo.values()) {
            model.addElement(t);
        }
        
        comboTipo = new JComboBox<>(model);
        panelFila1.add(comboTipo);

        panelFila1.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField(5);
        panelFila1.add(txtCantidad);

        // Fila 2: Distribuidor (Radio Buttons dinámicos)
        JPanel panelDistribuidor = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDistribuidor.add(new JLabel("Distribuidor:"));
        
        grupoDistribuidor = new ButtonGroup();
        
        for (Medicamento.Distribuidor d : Medicamento.Distribuidor.values()) {
            JRadioButton rb = new JRadioButton(d.toString());
            rb.setActionCommand(d.name());
            
            grupoDistribuidor.add(rb);
            panelDistribuidor.add(rb);
        }

        // Fila 3: Sucursal (Checkboxes exclusivos)
        JPanel panelSucursal = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSucursal.add(new JLabel("Sucursal:"));
        
        grupoSucursal = new ButtonGroup();
        for (Medicamento.Sucursal s : Medicamento.Sucursal.values()) {
            JCheckBox cb = new JCheckBox(s.toString());
            cb.setActionCommand(s.name());
            
            grupoSucursal.add(cb);
            panelSucursal.add(cb);
        }

        panelNorte.add(panelFila1);
        panelNorte.add(panelDistribuidor);
        panelNorte.add(panelSucursal);

        // Fila 4: Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnConfirmar = new JButton("Confirmar");
        JButton btnBorrar = new JButton("Borrar");

        btnConfirmar.addActionListener(e -> confirmarPedido());
        btnBorrar.addActionListener(e -> borrarCampos());

        panelBotones.add(btnConfirmar);
        panelBotones.add(btnBorrar);
        panelNorte.add(panelBotones);

        add(panelNorte, BorderLayout.NORTH);
        add(new JLabel("Bienvenido al Sistema de Gestión de Droguería", SwingConstants.CENTER), BorderLayout.CENTER);
    }

    private void confirmarPedido() {
        String nombre = txtNombre.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();
        int selectedIndex = comboTipo.getSelectedIndex();
        ButtonModel distribuidorSelected = grupoDistribuidor.getSelection();
        ButtonModel sucursalSelected = grupoSucursal.getSelection();
        
        // 1. Validación de campos obligatorios
        java.util.List<String> camposFaltantes = new java.util.ArrayList<>();
        if (nombre.isEmpty()) camposFaltantes.add("Nombre del medicamento");
        if (selectedIndex == 0) camposFaltantes.add("Tipo de medicamento");
        if (cantidadStr.isEmpty()) camposFaltantes.add("Cantidad");
        if (distribuidorSelected == null) camposFaltantes.add("Distribuidor");
        if (sucursalSelected == null) camposFaltantes.add("Sucursal");

        if (!camposFaltantes.isEmpty()) {
            StringBuilder mensajeError = new StringBuilder("Error: Los siguientes campos son obligatorios:\n");
            for (String campo : camposFaltantes) {
                mensajeError.append("- ").append(campo).append("\n");
            }
            JOptionPane.showMessageDialog(this, mensajeError.toString(), "Campos incompletos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Validaciones específicas (solo si los campos obligatorios están llenos)
        StringBuilder erroresEspecificos = new StringBuilder();

        // Validar nombre alfanumérico
        if (!nombre.matches("^[a-zA-Z0-9 ]+$")) {
            erroresEspecificos.append("- El nombre solo puede contener letras y números.\n");
        }

        // Validar cantidad numérica y positiva
        int cantidad = -1;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                erroresEspecificos.append("- La cantidad debe ser un número positivo mayor a cero.\n");
            }
        } catch (NumberFormatException e) {
            erroresEspecificos.append("- La cantidad debe ser un valor numérico entero.\n");
        }

        // Si hay errores específicos, mostrarlos y salir
        if (erroresEspecificos.length() > 0) {
            JOptionPane.showMessageDialog(this, "Error de validación:\n" + erroresEspecificos.toString(), "Datos inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Medicamento.Tipo tipo = (Medicamento.Tipo) comboTipo.getSelectedItem();
            String distribuidor = distribuidorSelected.getActionCommand();
            String sucursal = sucursalSelected.getActionCommand();

            String mensaje = String.format("Pedido Confirmado:\n- Medicamento: %s\n- Tipo: %s\n- Cantidad: %d\n- Distribuidor: %s\n- Sucursal: %s",
                    nombre, tipo, cantidad, distribuidor, sucursal);
            
            JOptionPane.showMessageDialog(this, mensaje, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error desconocido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void borrarCampos() {
        txtNombre.setText("");
        txtCantidad.setText("");
        comboTipo.setSelectedIndex(0);
        // Limpiar selecciones de ButtonGroups
        grupoDistribuidor.clearSelection();
        grupoSucursal.clearSelection();
    }
}
