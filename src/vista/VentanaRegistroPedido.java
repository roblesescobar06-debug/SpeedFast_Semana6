package vista;

import modelo.ControladorDeEnvios;
import modelo.Pedido;
import modelo.PedidoComida;
import modelo.PedidoEncomienda;
import modelo.PedidoExpress;

import javax.swing.*;
import java.awt.*;

/**
 * Formulario para registrar nuevos pedidos en la lista común del controlador.
 */
public class VentanaRegistroPedido extends JFrame {

    private ControladorDeEnvios controlador;

    private JTextField txtId;
    private JTextField txtDireccion;
    private JTextField txtDistancia;
    private JComboBox<String> cboTipo;

    public VentanaRegistroPedido(ControladorDeEnvios controlador) {
        this.controlador = controlador;

        setTitle("SpeedFast - Registrar pedido");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // ----- Título -----
        JLabel lblTitulo = new JLabel("Registro de nuevo pedido", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));

        // ----- Formulario -----
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 12));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 25, 10, 25));

        txtId = new JTextField();
        txtDireccion = new JTextField();
        txtDistancia = new JTextField();
        cboTipo = new JComboBox<>(new String[]{"Comida", "Encomienda", "Express"});

        panelFormulario.add(new JLabel("ID del pedido:"));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Dirección de entrega:"));
        panelFormulario.add(txtDireccion);
        panelFormulario.add(new JLabel("Distancia (km):"));
        panelFormulario.add(txtDistancia);
        panelFormulario.add(new JLabel("Tipo de pedido:"));
        panelFormulario.add(cboTipo);

        // ----- Botones -----
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCerrar);

        btnGuardar.addActionListener(e -> guardarPedido());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnCerrar.addActionListener(e -> dispose());

        add(lblTitulo, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Valida los campos, crea el pedido según el tipo y lo agrega al controlador.
     */
    private void guardarPedido() {
        String idTexto = txtId.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String distanciaTexto = txtDistancia.getText().trim().replace(",", ".");
        String tipo = (String) cboTipo.getSelectedItem();

        if (idTexto.isEmpty() || direccion.isEmpty() || distanciaTexto.isEmpty()) {
            mostrarError("Todos los campos son obligatorios.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException ex) {
            mostrarError("El ID debe ser un número entero.");
            return;
        }

        if (id <= 0) {
            mostrarError("El ID debe ser mayor que 0.");
            return;
        }

        if (controlador.existeId(id)) {
            mostrarError("Ya existe un pedido con el ID " + id + ".");
            return;
        }

        if (direccion.length() < 5) {
            mostrarError("La dirección debe tener al menos 5 caracteres.");
            return;
        }

        double distancia;
        try {
            distancia = Double.parseDouble(distanciaTexto);
        } catch (NumberFormatException ex) {
            mostrarError("La distancia debe ser un número (ej: 4.5).");
            return;
        }

        if (distancia <= 0) {
            mostrarError("La distancia debe ser mayor que 0.");
            return;
        }

        Pedido pedido;
        switch (tipo) {
            case "Comida":
                pedido = new PedidoComida(id, direccion, distancia);
                break;
            case "Encomienda":
                pedido = new PedidoEncomienda(id, direccion, distancia);
                break;
            default:
                pedido = new PedidoExpress(id, direccion, distancia);
                break;
        }

        controlador.agregarPedido(pedido);

        JOptionPane.showMessageDialog(this,
                "Pedido #" + id + " (" + tipo + ") registrado correctamente.",
                "Confirmación",
                JOptionPane.INFORMATION_MESSAGE);

        limpiarCampos();
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtDireccion.setText("");
        txtDistancia.setText("");
        cboTipo.setSelectedIndex(0);
        txtId.requestFocus();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.WARNING_MESSAGE);
    }
}