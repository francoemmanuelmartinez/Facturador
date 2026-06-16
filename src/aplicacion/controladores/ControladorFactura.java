package aplicacion.controladores;

import aplicacion.servicios.Conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorFactura {

    Conexion c = new Conexion();

    public List<Object[]> obtenerFacturasPorCliente(int idCliente) {
        List<Object[]> facturas = new ArrayList<>();
        try {
            c.conectar();
            String sql = "SELECT id, numero_factura, fecha_emision, total_compra, nombre_vendedor, apellido_vendedor FROM facturas WHERE id_cliente = ? ORDER BY fecha_emision DESC, id DESC";
            PreparedStatement stmt = c.con.prepareStatement(sql);
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                facturas.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("numero_factura"),
                        rs.getDate("fecha_emision").toLocalDate(),
                        rs.getBigDecimal("total_compra"),
                        rs.getString("nombre_vendedor"),
                        rs.getString("apellido_vendedor")
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return facturas;
    }

    public Object[] obtenerFacturaCompleta(int idFactura) {
        try {
            c.conectar();
            String sql = "SELECT f.numero_factura, f.fecha_emision, f.nombre_cliente, f.apellido_cliente, f.nombre_vendedor, f.apellido_vendedor, f.total_compra, c.dni, c.direccion FROM facturas f JOIN clientes c ON f.id_cliente = c.id WHERE f.id = ?";
            PreparedStatement stmt = c.con.prepareStatement(sql);
            stmt.setInt(1, idFactura);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Object[]{
                        rs.getString("numero_factura"),
                        rs.getDate("fecha_emision").toLocalDate(),
                        rs.getString("nombre_cliente"),
                        rs.getString("apellido_cliente"),
                        rs.getString("nombre_vendedor"),
                        rs.getString("apellido_vendedor"),
                        rs.getBigDecimal("total_compra"),
                        rs.getString("dni"),
                        rs.getString("direccion")
                };
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Object[]> obtenerDetallesPorFactura(int idFactura) {
        List<Object[]> detalles = new ArrayList<>();
        try {
            c.conectar();
            String sql = "SELECT df.cantidad, df.precio_unitario, df.descuento, df.subtotal, p.descripcion FROM detalles_de_facturas df JOIN productos p ON df.id_producto = p.id WHERE df.id_factura = ?";
            PreparedStatement stmt = c.con.prepareStatement(sql);
            stmt.setInt(1, idFactura);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                detalles.add(new Object[]{
                        rs.getString("descripcion"),
                        rs.getInt("cantidad"),
                        rs.getInt("precio_unitario"),
                        rs.getInt("descuento"),
                        rs.getInt("subtotal")
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return detalles;
    }
}
