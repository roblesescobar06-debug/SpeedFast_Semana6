# SpeedFast – Semana 7: JDBC + MySQL

Sistema de gestión de pedidos y entregas en Java (Swing) conectado a una base de datos MySQL mediante JDBC.

## Estructura
- `modelo`: Pedido (abstracta), PedidoComida, PedidoEncomienda, PedidoExpress, Repartidor, Entrega, ControladorDeEnvios
- `dao`: ConexionDB, PedidoDAO, RepartidorDAO, EntregaDAO, TestDAO
- `vista`: VentanaPrincipal, VentanaRegistroPedido, VentanaListaPedidos, VentanaAsignarRepartidor
- `main`: Main

## Funcionalidades
- Registrar pedidos en la tabla `pedido` (ID generado por MySQL).
- Listar pedidos desde la base de datos.
- Asignar repartidor desde la tabla `repartidor`, registrar la entrega en la tabla `entrega` y actualizar el estado del pedido (Pendiente → En reparto → Entregado) usando un hilo.

## Requisitos
- JDK 21 o superior
- MySQL 8 con la base de datos `speedfast_db` (tablas `repartidor`, `pedido`, `entrega`)
- Conector `mysql-connector-j` agregado como librería del proyecto

## Ejecución
Ejecutar `main.Main`.
