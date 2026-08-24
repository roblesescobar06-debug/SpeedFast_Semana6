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
        System.out.println("     SISTEMA DE PEDIDOS - SpeedFast (Semana 2)");
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