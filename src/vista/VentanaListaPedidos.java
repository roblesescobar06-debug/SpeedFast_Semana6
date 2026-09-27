package vista;

import dao.PedidoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Muestra los pedidos almacenados en la base de datos speedfast_db.
 */
public class VentanaListaPedidos extends JFrame {

    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final DefaultTableModel modeloTabla;
    private final JTable tablaPedidos;

    public VentanaListaPedidos() {
        setTitle("SpeedFast - Lista de Pedidos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        String[] columnas = {"ID", "Dirección", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar");
        JButton btnCerrar = new JButton("Cerrar");

        btnActualizar.addActionListener(e -> cargarPedidos());
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        cargarPedidos();
    }

    private void cargarPedidos() {
        modeloTabla.setRowCount(0);
        List<Object[]> filas = pedidoDAO.listarTodos();
        for (Object[] fila : filas) {
            modeloTabla.addRow(fila);
        }
        if (filas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay pedidos registrados en la base de datos.",
                    "Lista de Pedidos",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaListaPedidos().setVisible(true));
    }
}