package speedfast;

/**
 * Pedido de tipo Express.
 *
 * Hereda de la clase abstracta Pedido e implementa el método abstracto
 * calcularTiempoEntrega() con su propia fórmula:
 *   tiempo = 10 min base; si la distancia es mayor a 5 km, se suman 5 min extra.
 */
public class PedidoExpress extends Pedido {

    /**
     * Constructor. Llama al constructor de la clase abstracta con super().
     *
     * @param idPedido         identificador único del pedido
     * @param direccionEntrega dirección de entrega
     * @param distanciaKm      distancia de reparto en kilómetros
     */
    public PedidoExpress(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    /**
     * Implementación del método abstracto para pedidos Express.
     * Fórmula: 10 minutos base. Si la distancia supera los 5 km,
     * se agregan 5 minutos extra.
     *
     * @return tiempo estimado de entrega en minutos
     */
    @Override
    public int calcularTiempoEntrega() {
        int tiempo = 10;
        if (distanciaKm > 5) {
            tiempo = tiempo + 5;
        }
        return tiempo;
    }
}