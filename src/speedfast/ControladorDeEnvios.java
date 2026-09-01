package speedfast;

import java.util.ArrayList;

/**
 * Clase que centraliza las operaciones funcionales del sistema.
 * IMPLEMENTA las tres interfaces para DESACOPLAR responsabilidades:
 * despachar, cancelar y rastrear historial.
 */
public class ControladorDeEnvios implements Despachable, Cancelable, Rastreable {

    // Historial de entregas realizadas (guardado en un ArrayList).
    private ArrayList<String> historial = new ArrayList<>();

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