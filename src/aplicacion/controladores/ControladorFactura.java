package aplicacion.controladores;

import aplicacion.modelos.DetalleFactura;
import aplicacion.modelos.Factura;
import aplicacion.servicios.Conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorFactura {

    private Conexion c = new Conexion();

    public List<Factura> obtenerFacturasPorCliente(int idCliente) {
        List<Factura> facturas = new ArrayList<>();
        try {
            c.conectar();
            String sqlSelect = "SELECT id, numero_factura, fecha_emision, total_compra, nombre_vendedor, apellido_vendedor FROM facturas WHERE id_cliente = ? ORDER BY fecha_emision DESC, id DESC";
            PreparedStatement stmtFacturas = c.getConnection().prepareStatement(sqlSelect);
            stmtFacturas.setInt(1, idCliente);
            ResultSet rs = stmtFacturas.executeQuery();
            while (rs.next()) {
                Factura f = new Factura();
                f.setId(rs.getInt("id"));
                f.setNumeroFactura(rs.getString("numero_factura"));
                f.setFechaEmision(rs.getDate("fecha_emision").toLocalDate());
                f.setTotalCompra(rs.getInt("total_compra"));
                f.setNombreVendedor(rs.getString("nombre_vendedor"));
                f.setApellidoVendedor(rs.getString("apellido_vendedor"));
                facturas.add(f);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return facturas;
    }

    public Factura obtenerFacturaCompleta(int idFactura) {
        try {
            c.conectar();
            String sqlSelect = "SELECT f.id, f.id_cliente, f.id_vendedor, f.numero_factura, f.fecha_emision, f.nombre_cliente, f.apellido_cliente, f.nombre_vendedor, f.apellido_vendedor, f.subtotal, f.descuento_porcentaje, f.valor_descontado, f.total_compra, c.dni, c.direccion FROM facturas f JOIN clientes c ON f.id_cliente = c.id WHERE f.id = ?";
            PreparedStatement stmtFactura = c.getConnection().prepareStatement(sqlSelect);
            stmtFactura.setInt(1, idFactura);
            ResultSet rs = stmtFactura.executeQuery();
            if (rs.next()) {
                return new Factura(
                        rs.getInt("id"),
                        rs.getString("numero_factura"),
                        rs.getInt("id_cliente"),
                        rs.getString("nombre_cliente"),
                        rs.getString("apellido_cliente"),
                        rs.getString("dni"),
                        rs.getString("direccion"),
                        rs.getInt("id_vendedor"),
                        rs.getString("nombre_vendedor"),
                        rs.getString("apellido_vendedor"),
                        rs.getDate("fecha_emision").toLocalDate(),
                        rs.getInt("subtotal"),
                        rs.getInt("descuento_porcentaje"),
                        rs.getInt("valor_descontado"),
                        rs.getInt("total_compra")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<DetalleFactura> obtenerDetallesPorFactura(int idFactura) {
        List<DetalleFactura> detalles = new ArrayList<>();
        try {
            c.conectar();
            String sqlSelect = "SELECT p.id, df.cantidad, df.precio_unitario, df.descuento, df.subtotal, p.descripcion FROM detalles_de_facturas df JOIN productos p ON df.id_producto = p.id WHERE df.id_factura = ?";
            PreparedStatement stmtDetalles = c.getConnection().prepareStatement(sqlSelect);
            stmtDetalles.setInt(1, idFactura);
            ResultSet rs = stmtDetalles.executeQuery();
            while (rs.next()) {
                detalles.add(new DetalleFactura(
                        rs.getInt("id"),
                        rs.getString("descripcion"),
                        rs.getInt("cantidad"),
                        rs.getInt("precio_unitario"),
                        rs.getInt("descuento"),
                        rs.getInt("subtotal")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return detalles;
    }
}
