package vista;

import dao.PedidoDAO;
import modelo.ControladorDeEnvios;
import modelo.Pedido;
import modelo.PedidoComida;
import modelo.PedidoEncomienda;
import modelo.PedidoExpress;

import javax.swing.*;
import java.awt.*;

/**
 * Formulario para registrar nuevos pedidos en la base de datos speedfast_db
 * y en la lista común del controlador.
 */
public class VentanaRegistroPedido extends JFrame {

    private final ControladorDeEnvios controlador;
    private final PedidoDAO pedidoDAO = new PedidoDAO();

    private JTextField txtDireccion;
    private JTextField txtDistancia;
    private JComboBox<String> cboTipo;

    public VentanaRegistroPedido(ControladorDeEnvios controlador) {
        this.controlador = controlador;

        setTitle("SpeedFast - Registrar pedido");
        setSize(450, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // ----- Título -----
        JLabel lblTitulo = new JLabel("Registro de nuevo pedido", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));

        // ----- Formulario -----
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 12));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 25, 10, 25));

        txtDireccion = new JTextField();
        txtDistancia = new JTextField();
        cboTipo = new JComboBox<>(new String[]{"Comida", "Encomienda", "Express"});

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
     * Valida los campos, guarda el pedido en la base de datos
     * y lo agrega al controlador con el ID generado por MySQL.
     */
    private void guardarPedido() {
        String direccion = txtDireccion.getText().trim();
        String distanciaTexto = txtDistancia.getText().trim().replace(",", ".");
        String tipo = (String) cboTipo.getSelectedItem();

        if (direccion.isEmpty() || distanciaTexto.isEmpty()) {
            mostrarError("Todos los campos son obligatorios.");
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

        // 1) Guardar en la base de datos (MySQL genera el ID)
        Pedido pedidoTemporal = crearPedido(tipo, 0, direccion, distancia);
        int idGenerado = pedidoDAO.guardar(pedidoTemporal, tipo);

        if (idGenerado <= 0) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo guardar el pedido en la base de datos.\nRevisa la consola para ver el detalle.",
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2) Agregar al controlador con el ID real de la base de datos
        Pedido pedido = crearPedido(tipo, idGenerado, direccion, distancia);
        controlador.agregarPedido(pedido);

        JOptionPane.showMessageDialog(this,
                "Pedido #" + idGenerado + " (" + tipo + ") guardado correctamente en la base de datos.",
                "Confirmación",
                JOptionPane.INFORMATION_MESSAGE);

        limpiarCampos();
    }

    private Pedido crearPedido(String tipo, int id, String direccion, double distancia) {
        switch (tipo) {
            case "Comida":
                return new PedidoComida(id, direccion, distancia);
            case "Encomienda":
                return new PedidoEncomienda(id, direccion, distancia);
            default:
                return new PedidoExpress(id, direccion, distancia);
        }
    }

    private void limpiarCampos() {
        txtDireccion.setText("");
        txtDistancia.setText("");
        cboTipo.setSelectedIndex(0);
        txtDireccion.requestFocus();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.WARNING_MESSAGE);
    }
}