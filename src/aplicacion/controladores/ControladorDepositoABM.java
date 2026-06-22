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

    Conexion c = new Conexion();

    public ControladorDepositoABM() {}

    public ControladorDepositoABM(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        VistaDepositoABM vistaDepositoABM = new VistaDepositoABM(usuario, ventanaPrincipal);
        ventanaPrincipal.setVista(vistaDepositoABM.panelDepositoABM);
    }

    public List<Producto> obtenerProductosPorHabilitado(int habilitado) {
        List<Producto> productos = new ArrayList<>();
        try {
            c.conectar();
            String sql = "SELECT p.id, p.descripcion, p.precio, p.stock, p.id_proveedor, pr.nombre AS nombreProveedor, p.habilitado FROM productos p LEFT JOIN proveedores pr ON p.id_proveedor = pr.id WHERE p.habilitado = ?";
            PreparedStatement stmt = c.con.prepareStatement(sql);
            stmt.setInt(1, habilitado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    public List<Producto> buscarProducto(String texto, int habilitado) {
        List<Producto> productos = new ArrayList<>();
        try {
            c.conectar();
            PreparedStatement stmt;
            if (texto.matches("\\d+")) {
                String sql = "SELECT p.id, p.descripcion, p.precio, p.stock, p.id_proveedor, pr.nombre AS nombreProveedor, p.habilitado FROM productos p LEFT JOIN proveedores pr ON p.id_proveedor = pr.id WHERE p.id = ? AND p.habilitado = ?";
                stmt = c.con.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(texto));
                stmt.setInt(2, habilitado);
            } else {
                String sql = "SELECT p.id, p.descripcion, p.precio, p.stock, p.id_proveedor, pr.nombre AS nombreProveedor, p.habilitado FROM productos p LEFT JOIN proveedores pr ON p.id_proveedor = pr.id WHERE p.descripcion LIKE ? AND p.habilitado = ?";
                stmt = c.con.prepareStatement(sql);
                stmt.setString(1, "%" + texto + "%");
                stmt.setInt(2, habilitado);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                productos.add(mapearProducto(rs));
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
        List<Producto> productos = buscarProducto(texto, habilitado);
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
            PreparedStatement pst;
            if (idProveedor > 0) {
                sqlInsert = "INSERT INTO productos(descripcion, precio, stock, id_proveedor) VALUES(?,?,?,?)";
                pst = c.con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, descripcion);
                pst.setInt(2, precio);
                pst.setInt(3, stock);
                pst.setInt(4, idProveedor);
            } else {
                sqlInsert = "INSERT INTO productos(descripcion, precio, stock) VALUES(?,?,?)";
                pst = c.con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, descripcion);
                pst.setInt(2, precio);
                pst.setInt(3, stock);
            }
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            int id = -1;
            if (rs.next()) {
                id = rs.getInt(1);
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
            PreparedStatement pst;
            if (idProveedor > 0) {
                sqlUpdate = "UPDATE productos SET descripcion=?, precio=?, stock=?, id_proveedor=? WHERE id=?";
                pst = c.con.prepareStatement(sqlUpdate);
                pst.setString(1, descripcion);
                pst.setInt(2, precio);
                pst.setInt(3, stock);
                pst.setInt(4, idProveedor);
                pst.setInt(5, id);
            } else {
                sqlUpdate = "UPDATE productos SET descripcion=?, precio=?, stock=?, id_proveedor=NULL WHERE id=?";
                pst = c.con.prepareStatement(sqlUpdate);
                pst.setString(1, descripcion);
                pst.setInt(2, precio);
                pst.setInt(3, stock);
                pst.setInt(4, id);
            }
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto modificado exitosamente");
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean alternarHabilitadoProducto(int id) {
        try {
            c.conectar();
            String sqlToggle = "UPDATE productos SET habilitado = CASE WHEN habilitado = 1 THEN 0 ELSE 1 END WHERE id = ?";
            PreparedStatement pst = c.con.prepareStatement(sqlToggle);
            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Estado cambiado exitosamente");
            return true;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto(
                rs.getInt("id"),
                rs.getInt("habilitado"),
                rs.getString("descripcion"),
                rs.getInt("precio"),
                rs.getInt("stock")
        );
        p.setIdProveedor(rs.getInt("id_proveedor"));
        p.setNombreProveedor(rs.getString("nombreProveedor"));
        return p;
    }
}
