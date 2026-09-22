package vista;

import modelo.ControladorDeEnvios;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Ventana que muestra todos los pedidos registrados en una JTable.
 * Los datos se gestionan con DefaultTableModel y se refrescan automáticamente.
 */
public class VentanaListaPedidos extends JFrame {

    private ControladorDeEnvios controlador;
    private DefaultTableModel modeloTabla;
    private JTable tablaPedidos;
    private JLabel lblTotal;

    public VentanaListaPedidos(ControladorDeEnvios controlador) {
        this.controlador = controlador;

        setTitle("SpeedFast - Listado de pedidos");
        setSize(800, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ----- Título -----
        JLabel lblTitulo = new JLabel("Pedidos registrados", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));

        // ----- Tabla -----
        String[] columnas = {"ID", "Tipo", "Dirección", "Distancia (km)", "Repartidor", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setRowHeight(24);
        tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPedidos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablaPedidos.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tablaPedidos);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        // ----- Pie: total + botones -----
        lblTotal = new JLabel("Total de pedidos: 0");
        lblTotal.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JButton btnRefrescar = new JButton("Refrescar");
        JButton btnCerrar = new JButton("Cerrar");

        btnRefrescar.addActionListener(e -> refrescarTabla());
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnCerrar);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));
        panelInferior.add(lblTotal, BorderLayout.WEST);
        panelInferior.add(panelBotones, BorderLayout.EAST);

        add(lblTitulo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        // Refresca la tabla cada vez que la ventana vuelve a tener el foco.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                refrescarTabla();
            }
        });

        refrescarTabla();
    }

    /**
     * Vacía la tabla y la vuelve a llenar con la lista común del controlador.
     */
    public void refrescarTabla() {
        modeloTabla.setRowCount(0);

        for (Pedido p : controlador.getPedidos()) {
            String tipo = p.getClass().getSimpleName().replace("Pedido", "");
            String repartidor = (p.getRepartidor() == null) ? "Sin asignar" : p.getRepartidor();

            modeloTabla.addRow(new Object[]{
                    p.getIdPedido(),
                    tipo,
                    p.getDireccionEntrega(),
                    p.getDistanciaKm(),
                    repartidor,
                    p.getEstado()
            });
        }

        lblTotal.setText("Total de pedidos: " + controlador.getPedidos().size());
    }
}