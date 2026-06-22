package aplicacion.controladores;

import aplicacion.modelos.Usuario;
import aplicacion.servicios.Conexion;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaCajero;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ControladorCajero {

    private Conexion c = new Conexion();
    private Usuario usuario;

    public ControladorCajero() {}

    public ControladorCajero(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        this.usuario = usuario;
        VistaCajero vistaCajero = new VistaCajero(usuario, ventanaPrincipal);
        ventanaPrincipal.mostrarVista(vistaCajero.panelCajero);
    }

    public String generarNumeroFactura() {
        return "FACT-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
    }

    public boolean finalizarCompra(int idCliente, String nombreCliente, String apellidoCliente, int idVendedor, String nombreVendedor, String apellidoVendedor, List<Object[]> carrito, int subtotal, int descuentoPorcentaje, int valorDescontado, int totalCompra) {
        try {
            c.conectar();
            c.getConnection().setAutoCommit(false);

            String numeroFactura = generarNumeroFactura();
            LocalDate fechaEmision = LocalDate.now();

            String sqlInsertFactura = "INSERT INTO facturas(numero_factura, id_cliente, nombre_cliente, apellido_cliente, id_vendedor, nombre_vendedor, apellido_vendedor, fecha_emision, subtotal, descuento_porcentaje, valor_descontado, total_compra) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstFactura = c.getConnection().prepareStatement(sqlInsertFactura, Statement.RETURN_GENERATED_KEYS);
            pstFactura.setString(1, numeroFactura);
            pstFactura.setInt(2, idCliente);
            pstFactura.setString(3, nombreCliente);
            pstFactura.setString(4, apellidoCliente);
            pstFactura.setInt(5, idVendedor);
            pstFactura.setString(6, nombreVendedor);
            pstFactura.setString(7, apellidoVendedor);
            pstFactura.setString(8, fechaEmision.toString());
            pstFactura.setInt(9, subtotal);
            pstFactura.setInt(10, descuentoPorcentaje);
            pstFactura.setInt(11, valorDescontado);
            pstFactura.setInt(12, totalCompra);
            pstFactura.executeUpdate();

            ResultSet rs = pstFactura.getGeneratedKeys();
            int idFactura;
            if (rs.next()) {
                idFactura = rs.getInt(1);
            } else {
                c.getConnection().rollback();
                return false;
            }

            String sqlInsertDetalle = "INSERT INTO detalles_de_facturas(id_factura, id_producto, cantidad, precio_unitario, descuento, subtotal) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement pstDetalle = c.getConnection().prepareStatement(sqlInsertDetalle);

            String sqlUpdateStock = "UPDATE productos SET stock = stock - ? WHERE id = ? AND stock >= ?";

            for (Object[] fila : carrito) {
                int idProducto = Integer.parseInt((String) fila[0]);
                int cantidad = (int) fila[3];
                int precioUnitario = (int) fila[2];
                int descuento = fila[4] instanceof String
                        ? Integer.parseInt(((String) fila[4]).replace("%", ""))
                        : 0;
                int subtotalDetalle = (int) fila[5];

                pstDetalle.setInt(1, idFactura);
                pstDetalle.setInt(2, idProducto);
                pstDetalle.setInt(3, cantidad);
                pstDetalle.setInt(4, precioUnitario);
                pstDetalle.setInt(5, descuento);
                pstDetalle.setInt(6, subtotalDetalle);
                pstDetalle.addBatch();

                PreparedStatement pstStock = c.getConnection().prepareStatement(sqlUpdateStock);
                pstStock.setInt(1, cantidad);
                pstStock.setInt(2, idProducto);
                pstStock.setInt(3, cantidad);
                int affected = pstStock.executeUpdate();
                if (affected == 0) {
                    c.getConnection().rollback();
                    JOptionPane.showMessageDialog(null, "Stock insuficiente para el producto ID: " + idProducto);
                    return false;
                }
            }

            pstDetalle.executeBatch();
            c.getConnection().commit();
            JOptionPane.showMessageDialog(null, "Compra finalizada exitosamente. Factura N°: " + numeroFactura);
            return true;

        } catch (SQLException e) {
            try {
                c.getConnection().rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
