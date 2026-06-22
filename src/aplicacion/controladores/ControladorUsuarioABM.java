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
            ResultSet rsUsuarios = stmtUsuarios.executeQuery();
            while (rsUsuarios.next()) {
                usuarios.add(mapearUsuario(rsUsuarios));
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
            PreparedStatement pstInsert = c.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            pstInsert.setString(1, nombre);
            pstInsert.setString(2, apellido);
            pstInsert.setString(3, dni);
            pstInsert.setString(4, telefono);
            pstInsert.setString(5, direccion);
            pstInsert.setString(6, mail);
            pstInsert.setString(7, rol);
            pstInsert.setString(8, password);
            pstInsert.executeUpdate();

            ResultSet rsGeneratedKeys = pstInsert.getGeneratedKeys();
            int id = -1;
            if (rsGeneratedKeys.next()) {
                id = rsGeneratedKeys.getInt(1);
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
            PreparedStatement pstUpdate = c.getConnection().prepareStatement(sqlUpdate);
            pstUpdate.setString(1, nombre);
            pstUpdate.setString(2, apellido);
            pstUpdate.setString(3, dni);
            pstUpdate.setString(4, telefono);
            pstUpdate.setString(5, direccion);
            pstUpdate.setString(6, mail);
            pstUpdate.setString(7, rol);
            pstUpdate.setString(8, password);
            pstUpdate.setInt(9, id);
            pstUpdate.executeUpdate();
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
            PreparedStatement pstUpdate = c.getConnection().prepareStatement(sqlUpdateHabilitado);
            pstUpdate.setInt(1, id);
            pstUpdate.executeUpdate();
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
            PreparedStatement stmtUsuario = c.getConnection().prepareStatement(sqlSelect);
            stmtUsuario.setString(1, dni);
            stmtUsuario.setInt(2, habilitado);
            ResultSet rsUsuario = stmtUsuario.executeQuery();
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
