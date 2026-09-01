package speedfast;

/**
 * Clase principal que prueba el sistema de pedidos de SpeedFast.
 *
 * Demuestra el uso de una clase abstracta (Pedido) y sus subclases:
 *  - Crea un objeto de cada tipo de pedido.
 *  - Llama a mostrarResumen() (método heredado) y calcularTiempoEntrega()
 *    (método abstracto implementado en cada subclase).
 *  - Imprime los tiempos estimados de forma clara y comparativa.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("====================================================");
        System.out.println("     SISTEMA DE PEDIDOS - SpeedFast (Semana 3)");
        System.out.println("====================================================");

        // ---------- Instanciación de un objeto de cada subclase ----------
        // Se usa polimorfismo: la referencia es del tipo abstracto Pedido.
        Pedido pedido1 = new PedidoComida(101, "Av. Providencia 1234, Santiago", 6.0);
        Pedido pedido2 = new PedidoEncomienda(102, "Calle Los Olmos 567, Ñuñoa", 8.5);
        Pedido pedido3 = new PedidoExpress(103, "Pasaje El Sol 890, Maipú", 7.0);

        // Se guardan en un arreglo del tipo abstracto para recorrerlos.
        Pedido[] pedidos = { pedido1, pedido2, pedido3 };

        // ---------- Recorrido: resumen + tiempo de entrega ----------
        System.out.println("\n>>> DETALLE Y TIEMPO ESTIMADO DE CADA PEDIDO:");
        for (Pedido pedido : pedidos) {
            // mostrarResumen() es heredado de la clase abstracta.
            pedido.mostrarResumen();
            // calcularTiempoEntrega() ejecuta la version de la subclase real.
            int tiempo = pedido.calcularTiempoEntrega();
            System.out.println("Tiempo estimado de entrega: " + tiempo + " minutos");
        }

        // ---------- SEMANA 3: Asignación de repartidores (polimorfismo) ----------
        System.out.println("\n>>> ASIGNACIÓN DE REPARTIDORES:");

        // Asignación AUTOMÁTICA: cada subclase ejecuta su propia versión sobrescrita.
        for (Pedido pedido : pedidos) {
            pedido.asignarRepartidor();
        }

        // Asignación MANUAL: usa el método SOBRECARGADO asignarRepartidor(String).
        System.out.println("\n>>> ASIGNACIÓN MANUAL (método sobrecargado):");
        pedido1.asignarRepartidor("Juan Pérez");

        // ---------- SEMANA 3: Operaciones con interfaces ----------
        // ControladorDeEnvios implementa Despachable, Cancelable y Rastreable.
        ControladorDeEnvios controlador = new ControladorDeEnvios();

        System.out.println("\n>>> DESPACHO DE PEDIDOS:");
        controlador.despachar(pedido1);
        controlador.despachar(pedido2);
        controlador.despachar(pedido3);

        System.out.println("\n>>> CANCELACIÓN DE UN PEDIDO:");
        controlador.cancelar();

        // Ver historial de entregas realizadas (Rastreable).
        controlador.verHistorial();

        // ---------- Comparativa final de tiempos ----------
        System.out.println("\n====================================================");
        System.out.println("     COMPARATIVA DE TIEMPOS DE ENTREGA");
        System.out.println("====================================================");
        System.out.println("Pedido #" + pedido1.getIdPedido() + " (Comida):      " + pedido1.calcularTiempoEntrega() + " min");
        System.out.println("Pedido #" + pedido2.getIdPedido() + " (Encomienda):  " + pedido2.calcularTiempoEntrega() + " min");
        System.out.println("Pedido #" + pedido3.getIdPedido() + " (Express):     " + pedido3.calcularTiempoEntrega() + " min");
        System.out.println("----------------------------------------------------");
        System.out.println("Fin de la ejecucion del sistema SpeedFast.");
    }
}