# SpeedFast — Interfaz gráfica para gestión de entregas

Actividad formativa **Semana 6 — Desarrollo Orientado a Objetos II**.

## Descripción

Aplicación de escritorio en Java Swing para la empresa SpeedFast, que permite
registrar pedidos, visualizarlos en una tabla y asignar repartidores simulando
el inicio de la entrega. Los datos se almacenan en listas en memoria
compartidas entre todas las ventanas mediante un controlador común.

## Estructura del proyecto

| Paquete | Clases |
|---|---|
| `main` | `Main` — punto de entrada, llama a `new VentanaPrincipal()` |
| `modelo` | `Pedido` (abstracta), `PedidoComida`, `PedidoEncomienda`, `PedidoExpress`, `ControladorDeEnvios`, interfaces `Despachable`, `Cancelable`, `Rastreable` |
| `vista` | `VentanaPrincipal`, `VentanaRegistroPedido`, `VentanaListaPedidos`, `VentanaAsignarRepartidor` |

## Funcionalidades

- **Ventana principal** (`BorderLayout` + `GridLayout`) con navegación hacia las demás ventanas.
- **Registro de pedidos** con campos ID, dirección, distancia y tipo (`JComboBox`).
  Valida campos vacíos, formato numérico, valores positivos e ID duplicado,
  y confirma con `JOptionPane`.
- **Listado de pedidos** en `JTable` gestionada con `DefaultTableModel`,
  con refresco manual y automático al volver a la ventana.
- **Asignación de repartidor**: selecciona un pedido pendiente, asigna el
  repartidor y simula la entrega en un hilo separado sin congelar la interfaz
  (Pendiente → En reparto → Entregado).

## Ejecución

Clase principal: `main.Main`
