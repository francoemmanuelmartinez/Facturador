package aplicacion.controladores;

import aplicacion.modelos.Proveedor;
import aplicacion.modelos.Usuario;
import aplicacion.servicios.Conexion;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaProveedorABM;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControladorProveedorABM {
    Conexion c = new Conexion();

    public ControladorProveedorABM() {}

    public ControladorProveedorABM(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        VistaProveedorABM vistaProveedorABM = new VistaProveedorABM(usuario, ventanaPrincipal);
        ventanaPrincipal.setVista(vistaProveedorABM.panelProveedor);
    }

    public List<Proveedor> obtenerProveedoresPorHabilitado(int habilitado) {
        List<Proveedor> proveedores = new ArrayList<>();
        try {
            c.conectar();
            String sql = "SELECT id, nombre, telefono, direccion, mail, habilitado FROM proveedores WHERE habilitado = ?";
            PreparedStatement stmt = c.con.prepareStatement(sql);
            stmt.setInt(1, habilitado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                proveedores.add(mapearProveedores(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proveedores;
    }

    public Proveedor buscarProveedores(String id, int habilitado) {
        Proveedor proveedor = null;
        try {
            c.conectar();
            String sql = "SELECT id, nombre, telefono, direccion, mail, habilitado FROM proveedores WHERE id = ? AND habilitado = ?";
            PreparedStatement stmt = c.con.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setInt(2, habilitado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                proveedor = mapearProveedores(rs);
            } else {
                JOptionPane.showMessageDialog(null, "No existe el proveedor con ese ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proveedor;
    }

    public int agregarProveedor(String nombre, String telefono, String direccion, String mail) {
        try {
            c.conectar();

            String sqlSelect = "SELECT mail FROM proveedores WHERE mail = ?";
            PreparedStatement selectFrom = c.con.prepareStatement(sqlSelect);
            selectFrom.setString(1, mail);
            ResultSet mailDatabase = selectFrom.executeQuery();
            if (mailDatabase.next()) {
                JOptionPane.showMessageDialog(null, "Este mail ya ha sido registrado");
                return -1;
            }

            String sqlInsert = "INSERT INTO proveedores(nombre, telefono, direccion, mail) VALUES(?, ?, ?, ?)";
            PreparedStatement pst = c.con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, nombre);
            pst.setString(2, telefono);
            pst.setString(3, direccion);
            pst.setString(4, mail);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            int id = -1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            JOptionPane.showMessageDialog(null, "Proveedor agregado exitosamente");
            return id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean modificarProveedor(int id, String nombre, String telefono, String direccion, String mail) {
        try {
            c.conectar();

            String sqlSelect = "SELECT mail FROM proveedores WHERE mail = ? AND id != ?";
            PreparedStatement selectFrom = c.con.prepareStatement(sqlSelect);
            selectFrom.setString(1, mail);
            selectFrom.setInt(2, id);
            ResultSet mailDatabase = selectFrom.executeQuery();
            if (mailDatabase.next()) {
                JOptionPane.showMessageDialog(null, "Ese mail ya pertenece a otro proveedor");
                return false;
            }

            String sqlUpdate = "UPDATE proveedores SET nombre = ?, telefono = ?, direccion = ?, mail = ? WHERE id = ?";
            PreparedStatement pst = c.con.prepareStatement(sqlUpdate);
            pst.setString(1, nombre);
            pst.setString(2, telefono);
            pst.setString(3, direccion);
            pst.setString(4, mail);
            pst.setInt(5, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Proveedor modificado exitosamente");
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean alternarHabilitadoProveedor(int id) {
        try {
            c.conectar();
            String sqlToggle = "UPDATE proveedores SET habilitado = CASE WHEN habilitado = 1 THEN 0 ELSE 1 END WHERE id = ?";
            PreparedStatement pst = c.con.prepareStatement(sqlToggle);
            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Estado cambiado exitosamente");
            return true;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Proveedor mapearProveedores(ResultSet rs) throws SQLException {
        Proveedor p = new Proveedor();
        p.setId(rs.getInt("id"));
        p.setNombre(rs.getString("nombre"));
        p.setTelefono(rs.getString("telefono"));
        p.setDireccion(rs.getString("direccion"));
        p.setMail(rs.getString("mail"));
        p.setHabilitado(rs.getInt("habilitado"));
        return p;
    }
}
