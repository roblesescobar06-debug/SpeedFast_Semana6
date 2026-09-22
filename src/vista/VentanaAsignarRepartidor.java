package vista;

import modelo.ControladorDeEnvios;
import modelo.Pedido;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Ventana para asignar un repartidor a un pedido pendiente
 * y simular el inicio de la entrega en un hilo separado.
 */
public class VentanaAsignarRepartidor extends JFrame {

    private ControladorDeEnvios controlador;
    private JComboBox<String> cboPedidos;
    private JTextField txtRepartidor;
    private JLabel lblEstado;
    private ArrayList<Pedido> pendientes = new ArrayList<>();

    public VentanaAsignarRepartidor(ControladorDeEnvios controlador) {
        this.controlador = controlador;

        setTitle("SpeedFast - Asignar repartidor");
        setSize(480, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // ----- Título -----
        JLabel lblTitulo = new JLabel("Asignar repartidor / Iniciar entrega", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));

        // ----- Formulario -----
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 12));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 25, 10, 25));

        cboPedidos = new JComboBox<>();
        txtRepartidor = new JTextField();
        lblEstado = new JLabel("Esperando asignación...");
        lblEstado.setForeground(new Color(90, 90, 90));

        panelFormulario.add(new JLabel("Pedido pendiente:"));
        panelFormulario.add(cboPedidos);
        panelFormulario.add(new JLabel("Nombre del repartidor:"));
        panelFormulario.add(txtRepartidor);
        panelFormulario.add(new JLabel("Estado:"));
        panelFormulario.add(lblEstado);

        // ----- Botones -----
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnIniciar = new JButton("Asignar e iniciar entrega");
        JButton btnCerrar = new JButton("Cerrar");
        panelBotones.add(btnIniciar);
        panelBotones.add(btnCerrar);

        btnIniciar.addActionListener(e -> iniciarEntrega());
        btnCerrar.addActionListener(e -> dispose());

        add(lblTitulo, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarPendientes();
    }

    /**
     * Llena el combo solo con los pedidos que aún están pendientes.
     */
    private void cargarPendientes() {
        cboPedidos.removeAllItems();
        pendientes.clear();

        for (Pedido p : controlador.getPedidos()) {
            if (p.getEstado().equals("Pendiente")) {
                pendientes.add(p);
                String tipo = p.getClass().getSimpleName().replace("Pedido", "");
                cboPedidos.addItem("#" + p.getIdPedido() + " - " + tipo + " - " + p.getDireccionEntrega());
            }
        }

        if (pendientes.isEmpty()) {
            cboPedidos.addItem("(No hay pedidos pendientes)");
            cboPedidos.setEnabled(false);
        } else {
            cboPedidos.setEnabled(true);
        }
    }

    /**
     * Valida, asigna el repartidor y simula la entrega en un hilo aparte
     * para no congelar la interfaz.
     */
    private void iniciarEntrega() {
        if (pendientes.isEmpty()) {
            mostrarError("No hay pedidos pendientes. Registra uno primero.");
            return;
        }

        String nombre = txtRepartidor.getText().trim();
        if (nombre.isEmpty()) {
            mostrarError("Debes ingresar el nombre del repartidor.");
            return;
        }

        if (nombre.length() < 3) {
            mostrarError("El nombre debe tener al menos 3 caracteres.");
            return;
        }

        Pedido pedido = pendientes.get(cboPedidos.getSelectedIndex());

        pedido.asignarRepartidor(nombre);
        pedido.setEstado("En reparto");
        lblEstado.setText("Pedido #" + pedido.getIdPedido() + " en reparto...");

        JOptionPane.showMessageDialog(this,
                "Repartidor " + nombre + " asignado al pedido #" + pedido.getIdPedido() + ".\nLa entrega ha comenzado.",
                "Entrega iniciada",
                JOptionPane.INFORMATION_MESSAGE);

        txtRepartidor.setText("");
        cargarPendientes();

        Thread hiloEntrega = new Thread(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            pedido.setEstado("Entregado");
            controlador.despachar(pedido);

            SwingUtilities.invokeLater(() ->
                    lblEstado.setText("Pedido #" + pedido.getIdPedido() + " entregado ✔"));
        });
        hiloEntrega.start();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.WARNING_MESSAGE);
    }
}