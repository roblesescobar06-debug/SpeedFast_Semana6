package dao;

import modelo.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Operaciones de base de datos sobre la tabla entrega.
 * Registra la relación entre un pedido y un repartidor.
 */
public class EntregaDAO {

    public boolean guardar(Entrega entrega) {
        String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setDate(3, entrega.getFecha());
            ps.setTime(4, entrega.getHora());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar la entrega: " + e.getMessage());
            return false;
        }
    }
}