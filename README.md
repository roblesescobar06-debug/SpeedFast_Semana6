# SpeedFast — Sistema de Reparto a Domicilio

Actividad formativa **Semana 3 — Desarrollo Orientado a Objetos II**: *"Diseñando un sistema orientado a objetos con clases abstractas, polimorfismo e interfaces"*.

## Descripción

Sistema en Java para **SpeedFast**, empresa de reparto a domicilio con tres tipos de servicio: **Comida**, **Encomiendas** y **Compras Express**. Cada tipo de pedido tiene reglas de negocio propias para la asignación de repartidor y el cálculo del tiempo de entrega, resueltas mediante los principios de la Programación Orientada a Objetos.

## Estructura del sistema

### Clase abstracta
- `Pedido`: clase base abstracta con atributos comunes (`idPedido`, `direccionEntrega`, `distanciaKm`, `repartidor`), el método implementado `mostrarResumen()` y el método abstracto `calcularTiempoEntrega()`.

### Herencia y subclases
- `PedidoComida`, `PedidoEncomienda`, `PedidoExpress` heredan de `Pedido` e implementan su propia fórmula de tiempo de entrega.

### Polimorfismo
- **Sobrescritura (override):** cada subclase redefine `asignarRepartidor()` con su regla de negocio.
- **Sobrecarga (overload):** `asignarRepartidor(String nombre)` permite la asignación manual.

### Interfaces
- `Despachable` → método `despachar()`
- `Cancelable` → método `cancelar()`
- `Rastreable` → método `verHistorial()`

La clase `ControladorDeEnvios` implementa las tres interfaces para desacoplar las operaciones funcionales del sistema y gestiona el historial de entregas mediante un `ArrayList`.

## Criterios de asignación por tipo de pedido

| Tipo de pedido | Criterio de asignación |
|---|---|
| Comida | Repartidor en moto |
| Encomienda | Repartidor en camioneta |
| Compra Express | Repartidor express prioritario |

## Simulación (clase Main)

La clase `Main` demuestra el flujo completo: asignación automática y manual de repartidores, cálculo del tiempo estimado, despacho de pedidos, cancelación de un envío y visualización del historial de entregas.

## Conceptos de POO aplicados

- **Herencia:** las tres subclases heredan de la clase base `Pedido`.
- **Abstracción:** `Pedido` es abstracta con un método abstracto obligatorio en las subclases.
- **Encapsulamiento:** atributos protegidos con getters y setters.
- **Polimorfismo:** sobrescritura y sobrecarga de `asignarRepartidor()`.
- **Interfaces:** desacoplan responsabilidades comunes a distintas clases.

## Tecnologías

- Java (JDK 23)
- IntelliJ IDEA
