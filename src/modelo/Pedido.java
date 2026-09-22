package modelo;

/**
 * Clase abstracta que representa un pedido genérico del sistema SpeedFast.
 *
 * Define los atributos comunes a todos los pedidos y dos métodos:
 *  - mostrarResumen(): método implementado (con cuerpo) que muestra los datos básicos.
 *  - calcularTiempoEntrega(): método ABSTRACTO, que cada subclase implementa con su propia fórmula.
 *
 * Al ser abstracta, esta clase no se puede instanciar directamente:
 * solo sirve como plantilla base para las subclases.
 */
public abstract class Pedido {

    // Atributos comunes a todos los pedidos
    protected int idPedido;
    protected String direccionEntrega;
    protected double distanciaKm;
    protected String repartidor;
    protected String estado = "Pendiente";

    /**
     * Constructor de la clase abstracta.
     *
     * @param idPedido         identificador único del pedido
     * @param direccionEntrega dirección de entrega
     * @param distanciaKm      distancia de reparto en kilómetros
     */
    public Pedido(int idPedido, String direccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
    }

    /**
     * Método implementado (con cuerpo): imprime los datos básicos del pedido.
     * Todas las subclases lo heredan tal cual.
     */
    public void mostrarResumen() {
        System.out.println("----------------------------------------------------");
        System.out.println("Pedido #" + idPedido);
        System.out.println("Dirección de entrega: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " km");
    }

    /**
     * Método ABSTRACTO: no tiene cuerpo aquí.
     * Cada subclase está OBLIGADA a implementarlo con su propia fórmula
     * de cálculo del tiempo de entrega.
     *
     * @return el tiempo estimado de entrega en minutos
     */
    public abstract int calcularTiempoEntrega();

    /**
     * Método que se SOBRESCRIBE en cada subclase con su lógica de asignación.
     * Asignación AUTOMÁTICA del repartidor.
     */
    public void asignarRepartidor() {
        this.repartidor = "Repartidor genérico";
    }

    /**
     * Método SOBRECARGADO: mismo nombre, distintos parámetros.
     * Asignación MANUAL del repartidor por nombre.
     */
    public void asignarRepartidor(String nombre) {
        this.repartidor = nombre;
        System.out.println("Repartidor asignado manualmente: " + nombre);
    }
    // ------------------- Getters y Setters -------------------

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }
    // ===== Getters y setters para la interfaz gráfica =====


    public String getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(String repartidor) {
        this.repartidor = repartidor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}