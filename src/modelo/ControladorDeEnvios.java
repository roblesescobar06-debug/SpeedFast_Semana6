package modelo;

import java.util.ArrayList;

/**
 * Clase que centraliza las operaciones funcionales del sistema.
 * IMPLEMENTA las tres interfaces para DESACOPLAR responsabilidades:
 * despachar, cancelar y rastrear historial.
 */
public class ControladorDeEnvios implements Despachable, Cancelable, Rastreable {

    // Historial de entregas realizadas (guardado en un ArrayList).
    private ArrayList<String> historial = new ArrayList<>();
    // Lista común de pedidos compartida por todas las ventanas.
    private ArrayList<Pedido> pedidos = new ArrayList<>();

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
        historial.add("Pedido #" + pedido.getIdPedido() + " registrado");
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public Pedido buscarPorId(int id) {
        for (Pedido p : pedidos) {
            if (p.getIdPedido() == id) {
                return p;
            }
        }
        return null;
    }

    public boolean existeId(int id) {
        return buscarPorId(id) != null;
    }
    /**
     * Implementación de Despachable: despacha un pedido y lo registra en el historial.
     */
    @Override
    public void despachar() {
        System.out.println(">> Pedido despachado correctamente.");
        historial.add("Entrega despachada");
    }

    /**
     * Sobrecarga de despachar para registrar el pedido concreto en el historial.
     */
    public void despachar(Pedido pedido) {
        System.out.println(">> Pedido #" + pedido.getIdPedido() + " despachado correctamente.");
        historial.add("Pedido #" + pedido.getIdPedido() + " despachado");
    }

    /**
     * Implementación de Cancelable: cancela un envío.
     */
    @Override
    public void cancelar() {
        System.out.println(">> Envío cancelado.");
        historial.add("Un envío fue cancelado");
    }

    /**
     * Implementación de Rastreable: muestra el historial de entregas.
     */
    @Override
    public void verHistorial() {
        System.out.println("\n===== HISTORIAL DE ENTREGAS =====");
        if (historial.isEmpty()) {
            System.out.println("(Sin movimientos registrados)");
        } else {
            for (String registro : historial) {
                System.out.println("- " + registro);
            }
        }
    }
}