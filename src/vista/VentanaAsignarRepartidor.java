package vista;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import modelo.ControladorDeEnvios;
import modelo.Entrega;
import modelo.Pedido;
import modelo.Repartidor;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Ventana para asignar un repartidor a un pedido pendiente,
 * registrar la entrega en la base de datos y simular
 * el reparto en un hilo separado.
 */
public class VentanaAsignarRepartidor extends JFrame {

    private final ControladorDeEnvios controlador;
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final RepartidorDAO repartidorDAO = new RepartidorDAO();
    private final EntregaDAO entregaDAO = new EntregaDAO();

    private JComboBox<String> cboPedidos;
    private JComboBox<String> cboRepartidores;
    private JLabel lblEstado;

    private final List<Integer> idsPendientes = new ArrayList<>();
    private final List<Repartidor> repartidores = new ArrayList<>();

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
        cboRepartidores = new JComboBox<>();
        lblEstado = new JLabel("Esperando asignación...");
        lblEstado.setForeground(new Color(90, 90, 90));

        panelFormulario.add(new JLabel("Pedido pendiente:"));
        panelFormulario.add(cboPedidos);
        panelFormulario.add(new JLabel("Repartidor:"));
        panelFormulario.add(cboRepartidores);
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

        cargarRepartidores();
        cargarPendientes();
    }

    /**
     * Llena el combo con los repartidores registrados en la base de datos.
     */
    private void cargarRepartidores() {
        cboRepartidores.removeAllItems();
        repartidores.clear();
        repartidores.addAll(repartidorDAO.listarTodos());

        for (Repartidor r : repartidores) {
            cboRepartidores.addItem(r.getId() + " - " + r.getNombre());
        }

        if (repartidores.isEmpty()) {
            cboRepartidores.addItem("(No hay repartidores registrados)");
            cboRepartidores.setEnabled(false);
        } else {
            cboRepartidores.setEnabled(true);
        }
    }

    /**
     * Llena el combo solo con los pedidos que están "Pendiente" en la base de datos.
     */
    private void cargarPendientes() {
        cboPedidos.removeAllItems();
        idsPendientes.clear();

        for (Object[] fila : pedidoDAO.listarTodos()) {
            int id = (int) fila[0];
            String direccion = (String) fila[1];
            String tipo = (String) fila[2];
            String estado = (String) fila[3];

            if ("Pendiente".equals(estado)) {
                idsPendientes.add(id);
                cboPedidos.addItem("#" + id + " - " + tipo + " - " + direccion);
            }
        }

        if (idsPendientes.isEmpty()) {
            cboPedidos.addItem("(No hay pedidos pendientes)");
            cboPedidos.setEnabled(false);
        } else {
            cboPedidos.setEnabled(true);
        }
    }

    /**
     * Registra la entrega en la base de datos, cambia el estado del pedido
     * y simula el reparto en un hilo aparte para no congelar la interfaz.
     */
    private void iniciarEntrega() {
        if (idsPendientes.isEmpty()) {
            mostrarError("No hay pedidos pendientes. Registra uno primero.");
            return;
        }

        if (repartidores.isEmpty()) {
            mostrarError("No hay repartidores registrados en la base de datos.");
            return;
        }

        final int idPedido = idsPendientes.get(cboPedidos.getSelectedIndex());
        final Repartidor repartidor = repartidores.get(cboRepartidores.getSelectedIndex());

        // 1) Registrar la entrega en la tabla entrega
        Entrega entrega = new Entrega(
                idPedido,
                repartidor.getId(),
                Date.valueOf(LocalDate.now()),
                Time.valueOf(LocalTime.now().withNano(0))
        );

        if (!entregaDAO.guardar(entrega)) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar la entrega en la base de datos.\nRevisa la consola para ver el detalle.",
                    "Error de base de datos",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2) Cambiar el estado del pedido en la base de datos
        pedidoDAO.actualizarEstado(idPedido, "En reparto");

        // 3) Sincronizar con el controlador si el pedido está en memoria
        final Pedido pedidoEnMemoria = buscarEnControlador(idPedido);
        if (pedidoEnMemoria != null) {
            pedidoEnMemoria.asignarRepartidor(repartidor.getNombre());
            pedidoEnMemoria.setEstado("En reparto");
        }

        lblEstado.setText("Pedido #" + idPedido + " en reparto...");

        JOptionPane.showMessageDialog(this,
                "Repartidor " + repartidor.getNombre() + " asignado al pedido #" + idPedido
                        + ".\nLa entrega ha comenzado.",
                "Entrega iniciada",
                JOptionPane.INFORMATION_MESSAGE);

        cargarPendientes();

        // 4) Simular el reparto en un hilo separado
        Thread hiloEntrega = new Thread(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            pedidoDAO.actualizarEstado(idPedido, "Entregado");

            if (pedidoEnMemoria != null) {
                pedidoEnMemoria.setEstado("Entregado");
                controlador.despachar(pedidoEnMemoria);
            }

            SwingUtilities.invokeLater(() ->
                    lblEstado.setText("Pedido #" + idPedido + " entregado ✓"));
        });
        hiloEntrega.start();
    }

    /**
     * Busca en la lista del controlador el pedido con el ID indicado.
     */
    private Pedido buscarEnControlador(int idPedido) {
        for (Pedido p : controlador.getPedidos()) {
            if (p.getIdPedido() == idPedido) {
                return p;
            }
        }
        return null;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.WARNING_MESSAGE);
    }
}