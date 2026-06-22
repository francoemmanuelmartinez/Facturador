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
    private Conexion c = new Conexion();

    public ControladorProveedorABM() {}

    public ControladorProveedorABM(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        VistaProveedorABM vistaProveedorABM = new VistaProveedorABM(usuario, ventanaPrincipal);
        ventanaPrincipal.mostrarVista(vistaProveedorABM.panelProveedorABM);
    }

    public List<Proveedor> obtenerProveedoresPorHabilitado(int habilitado) {
        List<Proveedor> proveedores = new ArrayList<>();
        try {
            c.conectar();
            String sqlSelect = "SELECT id, nombre, telefono, direccion, mail, habilitado FROM proveedores WHERE habilitado = ?";
            PreparedStatement stmtProveedores = c.getConnection().prepareStatement(sqlSelect);
            stmtProveedores.setInt(1, habilitado);
            ResultSet rs = stmtProveedores.executeQuery();
            while (rs.next()) {
                proveedores.add(mapearProveedores(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proveedores;
    }

    public Proveedor buscarProveedor(int id, int habilitado) {
        Proveedor proveedor = null;
        try {
            c.conectar();
            String sqlSelect = "SELECT id, nombre, telefono, direccion, mail, habilitado FROM proveedores WHERE id = ? AND habilitado = ?";
            PreparedStatement stmtProveedor = c.getConnection().prepareStatement(sqlSelect);
            stmtProveedor.setInt(1, id);
            stmtProveedor.setInt(2, habilitado);
            ResultSet rs = stmtProveedor.executeQuery();
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
            PreparedStatement stmtSelectMail = c.getConnection().prepareStatement(sqlSelect);
            stmtSelectMail.setString(1, mail);
            ResultSet rsMailExistente = stmtSelectMail.executeQuery();
            if (rsMailExistente.next()) {
                JOptionPane.showMessageDialog(null, "Este mail ya ha sido registrado");
                return -1;
            }

            String sqlInsert = "INSERT INTO proveedores(nombre, telefono, direccion, mail) VALUES(?, ?, ?, ?)";
            PreparedStatement pst = c.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
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
            PreparedStatement stmtSelectMail = c.getConnection().prepareStatement(sqlSelect);
            stmtSelectMail.setString(1, mail);
            stmtSelectMail.setInt(2, id);
            ResultSet rsMailExistente = stmtSelectMail.executeQuery();
            if (rsMailExistente.next()) {
                JOptionPane.showMessageDialog(null, "Ese mail ya pertenece a otro proveedor");
                return false;
            }

            String sqlUpdate = "UPDATE proveedores SET nombre = ?, telefono = ?, direccion = ?, mail = ? WHERE id = ?";
            PreparedStatement pst = c.getConnection().prepareStatement(sqlUpdate);
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
            String sqlUpdateHabilitado = "UPDATE proveedores SET habilitado = CASE WHEN habilitado = 1 THEN 0 ELSE 1 END WHERE id = ?";
            PreparedStatement pst = c.getConnection().prepareStatement(sqlUpdateHabilitado);
            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Estado cambiado exitosamente");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
