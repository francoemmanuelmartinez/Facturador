package aplicacion.controladores;

import aplicacion.modelos.Usuario;
import aplicacion.servicios.Conexion;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaUsuarioABM;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControladorUsuarioABM {

    private Conexion c = new Conexion();

    public ControladorUsuarioABM() {
    }

    public ControladorUsuarioABM(Usuario usuario, VentanaPrincipal ventanaPrincipal) {

        VistaUsuarioABM vistaUsuarioABM = new VistaUsuarioABM(usuario, ventanaPrincipal);
        ventanaPrincipal.mostrarVista(vistaUsuarioABM.panelUsuarioABM);

    }

    public List<Usuario> obtenerUsuariosPorHabilitado(int habilitado) {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            c.conectar();
            String sqlSelect = "SELECT id, nombre, apellido, dni, telefono, direccion, mail, rol, password, habilitado FROM usuarios WHERE habilitado = ?";
            PreparedStatement stmtUsuarios = c.getConnection().prepareStatement(sqlSelect);
            stmtUsuarios.setInt(1, habilitado);
            ResultSet rs = stmtUsuarios.executeQuery();
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    public int agregarUsuario(String nombre, String apellido, String dni, String telefono, String direccion, String mail, String password, String rol) {
        try {
            c.conectar();

            String sqlSelect = "SELECT mail FROM usuarios WHERE mail = ?";
            PreparedStatement stmtSelectMail = c.getConnection().prepareStatement(sqlSelect);
            stmtSelectMail.setString(1, mail);
            ResultSet rsMailExistente = stmtSelectMail.executeQuery();
            if (rsMailExistente.next()) {
                JOptionPane.showMessageDialog(null, "Este mail ya ha sido registrado");
                return -1;
            }

            String sqlInsert = "INSERT INTO usuarios(nombre, apellido, dni, telefono, direccion, mail, rol, password) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = c.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, nombre);
            pst.setString(2, apellido);
            pst.setString(3, dni);
            pst.setString(4, telefono);
            pst.setString(5, direccion);
            pst.setString(6, mail);
            pst.setString(7, rol);
            pst.setString(8, password);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            int id = -1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente");
            return id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean modificarUsuario(int id, String nombre, String apellido, String dni, String telefono, String direccion, String mail, String rol, String password) {
        try {
            c.conectar();

            String sqlSelect = "SELECT mail FROM usuarios WHERE mail = ? AND id != ?";
            PreparedStatement stmtSelectMail = c.getConnection().prepareStatement(sqlSelect);
            stmtSelectMail.setString(1, mail);
            stmtSelectMail.setInt(2, id);
            ResultSet rsMailExistente = stmtSelectMail.executeQuery();
            if (rsMailExistente.next()) {
                JOptionPane.showMessageDialog(null, "Ese mail ya pertenece a otro usuario");
                return false;
            }

            String sqlUpdate = "UPDATE usuarios SET nombre = ?, apellido = ?, dni = ?, telefono = ?, direccion = ?, mail = ?, rol = ?, password = ? WHERE id = ?";
            PreparedStatement pst = c.getConnection().prepareStatement(sqlUpdate);
            pst.setString(1, nombre);
            pst.setString(2, apellido);
            pst.setString(3, dni);
            pst.setString(4, telefono);
            pst.setString(5, direccion);
            pst.setString(6, mail);
            pst.setString(7, rol);
            pst.setString(8, password);
            pst.setInt(9, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario modificado exitosamente");
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean alternarHabilitadoUsuario(int id) {
        try {
            c.conectar();
            String sqlUpdateHabilitado = "UPDATE usuarios SET habilitado = CASE WHEN habilitado = 1 THEN 0 ELSE 1 END WHERE id = ?";
            PreparedStatement pst = c.getConnection().prepareStatement(sqlUpdateHabilitado);
            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Estado cambiado exitosamente");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Usuario buscarUsuario(String dni, int habilitado) {
        Usuario u = null;

        try {
            c.conectar();
            String sqlSelect = "SELECT id, nombre, apellido, dni, telefono, direccion, mail, rol, password, habilitado FROM usuarios WHERE dni = ? AND habilitado = ?";
            PreparedStatement stmtSelectUsuario = c.getConnection().prepareStatement(sqlSelect);
            stmtSelectUsuario.setString(1, dni);
            stmtSelectUsuario.setInt(2, habilitado);
            ResultSet rsUsuario = stmtSelectUsuario.executeQuery();
            if (rsUsuario.next()) {
                u = mapearUsuario(rsUsuario);
            } else {
                JOptionPane.showMessageDialog(null, "No existe el usuario con el dni");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return u;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setDni(rs.getString("dni"));
        u.setTelefono(rs.getString("telefono"));
        u.setDireccion(rs.getString("direccion"));
        u.setMail(rs.getString("mail"));
        u.setRol(rs.getString("rol"));
        u.setPassword(rs.getString("password"));
        u.setHabilitado(rs.getInt("habilitado"));
        return u;
    }

}
