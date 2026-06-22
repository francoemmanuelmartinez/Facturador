package aplicacion.controladores;

import aplicacion.modelos.Producto;
import aplicacion.modelos.Usuario;
import aplicacion.servicios.Conexion;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaDepositoABM;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControladorDepositoABM {

    private Conexion c = new Conexion();

    public ControladorDepositoABM() {}

    public ControladorDepositoABM(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        VistaDepositoABM vistaDepositoABM = new VistaDepositoABM(usuario, ventanaPrincipal);
        ventanaPrincipal.mostrarVista(vistaDepositoABM.panelDepositoABM);
    }

    public List<Producto> obtenerProductosPorHabilitado(int habilitado) {
        List<Producto> productos = new ArrayList<>();
        try {
            c.conectar();
            String sqlSelect = "SELECT p.id, p.descripcion, p.precio, p.stock, p.id_proveedor, pr.nombre AS nombreProveedor, p.habilitado FROM productos p LEFT JOIN proveedores pr ON p.id_proveedor = pr.id WHERE p.habilitado = ?";
            PreparedStatement stmtProductos = c.getConnection().prepareStatement(sqlSelect);
            stmtProductos.setInt(1, habilitado);
            ResultSet rsProductos = stmtProductos.executeQuery();
            while (rsProductos.next()) {
                productos.add(mapearProducto(rsProductos));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    public List<Producto> buscarProductos(String texto, int habilitado) {
        List<Producto> productos = new ArrayList<>();
        try {
            c.conectar();
            PreparedStatement stmtProducto;
            if (texto.matches("\\d+")) {
                String sqlSelect = "SELECT p.id, p.descripcion, p.precio, p.stock, p.id_proveedor, pr.nombre AS nombreProveedor, p.habilitado FROM productos p LEFT JOIN proveedores pr ON p.id_proveedor = pr.id WHERE p.id = ? AND p.habilitado = ?";
                stmtProducto = c.getConnection().prepareStatement(sqlSelect);
                stmtProducto.setInt(1, Integer.parseInt(texto));
                stmtProducto.setInt(2, habilitado);
            } else {
                String sqlSelect = "SELECT p.id, p.descripcion, p.precio, p.stock, p.id_proveedor, pr.nombre AS nombreProveedor, p.habilitado FROM productos p LEFT JOIN proveedores pr ON p.id_proveedor = pr.id WHERE p.descripcion LIKE ? AND p.habilitado = ?";
                stmtProducto = c.getConnection().prepareStatement(sqlSelect);
                stmtProducto.setString(1, "%" + texto + "%");
                stmtProducto.setInt(2, habilitado);
            }
            ResultSet rsProductos = stmtProducto.executeQuery();
            while (rsProductos.next()) {
                productos.add(mapearProducto(rsProductos));
            }
            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron productos con ese criterio");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    public Producto seleccionarProducto(String texto, int habilitado) {
        List<Producto> productos = buscarProductos(texto, habilitado);
        if (productos.isEmpty()) return null;
        if (productos.size() == 1) return productos.get(0);

        String[] opciones = new String[productos.size()];
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            opciones[i] = p.getId() + " - " + p.getDescripcion() + " - $" + p.getPrecio() + " - Stock: " + p.getStock();
        }
        String seleccion = (String) JOptionPane.showInputDialog(null,
                "Seleccione un producto:", "Multiples productos encontrados",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (seleccion == null) return null;
        int id = Integer.parseInt(seleccion.split(" - ")[0]);
        for (Producto p : productos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public int agregarProducto(String descripcion, int precio, int stock) {
        return agregarProducto(descripcion, precio, stock, 0);
    }

    public int agregarProducto(String descripcion, int precio, int stock, int idProveedor) {
        try {
            c.conectar();

            String sqlInsert;
            PreparedStatement pstInsert;
            if (idProveedor > 0) {
                sqlInsert = "INSERT INTO productos(descripcion, precio, stock, id_proveedor) VALUES(?, ?, ?, ?)";
                pstInsert = c.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                pstInsert.setString(1, descripcion);
                pstInsert.setInt(2, precio);
                pstInsert.setInt(3, stock);
                pstInsert.setInt(4, idProveedor);
            } else {
                sqlInsert = "INSERT INTO productos(descripcion, precio, stock) VALUES(?, ?, ?)";
                pstInsert = c.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                pstInsert.setString(1, descripcion);
                pstInsert.setInt(2, precio);
                pstInsert.setInt(3, stock);
            }
            pstInsert.executeUpdate();

            ResultSet rsGeneratedKeys = pstInsert.getGeneratedKeys();
            int id = -1;
            if (rsGeneratedKeys.next()) {
                id = rsGeneratedKeys.getInt(1);
            }

            JOptionPane.showMessageDialog(null, "Producto agregado exitosamente");
            return id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean modificarProducto(int id, String descripcion, int precio, int stock) {
        return modificarProducto(id, descripcion, precio, stock, 0);
    }

    public boolean modificarProducto(int id, String descripcion, int precio, int stock, int idProveedor) {
        try {
            c.conectar();

            String sqlUpdate;
            PreparedStatement pstUpdate;
            if (idProveedor > 0) {
                sqlUpdate = "UPDATE productos SET descripcion = ?, precio = ?, stock = ?, id_proveedor = ? WHERE id = ?";
                pstUpdate = c.getConnection().prepareStatement(sqlUpdate);
                pstUpdate.setString(1, descripcion);
                pstUpdate.setInt(2, precio);
                pstUpdate.setInt(3, stock);
                pstUpdate.setInt(4, idProveedor);
                pstUpdate.setInt(5, id);
            } else {
                sqlUpdate = "UPDATE productos SET descripcion = ?, precio = ?, stock = ?, id_proveedor = NULL WHERE id = ?";
                pstUpdate = c.getConnection().prepareStatement(sqlUpdate);
                pstUpdate.setString(1, descripcion);
                pstUpdate.setInt(2, precio);
                pstUpdate.setInt(3, stock);
                pstUpdate.setInt(4, id);
            }
            pstUpdate.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto modificado exitosamente");
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean alternarHabilitadoProducto(int id) {
        try {
            c.conectar();
            String sqlUpdateHabilitado = "UPDATE productos SET habilitado = CASE WHEN habilitado = 1 THEN 0 ELSE 1 END WHERE id = ?";
            PreparedStatement pstUpdate = c.getConnection().prepareStatement(sqlUpdateHabilitado);
            pstUpdate.setInt(1, id);
            pstUpdate.executeUpdate();
            JOptionPane.showMessageDialog(null, "Estado cambiado exitosamente");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto(
                rs.getInt("id"),
                rs.getString("descripcion"),
                rs.getInt("precio"),
                rs.getInt("stock"),
                rs.getInt("habilitado")
        );
        p.setIdProveedor(rs.getInt("id_proveedor"));
        p.setNombreProveedor(rs.getString("nombreProveedor"));
        return p;
    }
}
