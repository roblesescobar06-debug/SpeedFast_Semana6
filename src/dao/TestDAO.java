package dao;
import modelo.Repartidor;

import java.util.List;

/**
 * Prueba de las operaciones JDBC antes de integrarlas a la interfaz.
 */
public class TestDAO {

    public static void main(String[] args) {
        RepartidorDAO repartidorDAO = new RepartidorDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();

        System.out.println("=== Repartidores en la base de datos ===");
        List<Repartidor> repartidores = repartidorDAO.listarTodos();
        for (Repartidor r : repartidores) {
            System.out.println(r.getId() + " - " + r.getNombre());
        }

        System.out.println("\n=== Pedidos en la base de datos ===");
        List<Object[]> pedidos = pedidoDAO.listarTodos();
        if (pedidos.isEmpty()) {
            System.out.println("(sin pedidos registrados)");
        }
        for (Object[] fila : pedidos) {
            System.out.println(fila[0] + " | " + fila[1] + " | " + fila[2] + " | " + fila[3]);
        }
    }
}