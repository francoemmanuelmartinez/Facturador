package aplicacion.controladores;

import aplicacion.modelos.Usuario;
import aplicacion.servicios.Conexion;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaUsuarioABM;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorUsuarioABM {

    Conexion c = new Conexion();

    public ControladorUsuarioABM() {
    }

    public ControladorUsuarioABM(Usuario usuario,VentanaPrincipal ventanaPrincipal) {

        VistaUsuarioABM vistaUsuarioABM = new VistaUsuarioABM(usuario, ventanaPrincipal);
        ventanaPrincipal.setVista(vistaUsuarioABM.panelUsuarioABM);

    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            c.conectar();
            String sql = "SELECT nombre, apellido, dni, telefono, direccion, mail, rol, password, habilitado FROM usuarios";
            PreparedStatement stmt = c.con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setDni(rs.getString("dni"));
                u.setTelefono(rs.getString("telefono"));
                u.setDireccion(rs.getString("direccion"));
                u.setMail(rs.getString("mail"));
                u.setRol(rs.getString("rol"));
                u.setPassword(rs.getString("password"));
                u.setHabilitado(rs.getInt("habilitado"));
                usuarios.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    public List<Usuario> obtenerUsuariosPorHabilitado(int habilitado) {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            c.conectar();
            String sql = "SELECT nombre, apellido, dni, telefono, direccion, mail, rol, password, habilitado FROM usuarios WHERE habilitado = ?";
            PreparedStatement stmt = c.con.prepareStatement(sql);
            stmt.setInt(1, habilitado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setDni(rs.getString("dni"));
                u.setTelefono(rs.getString("telefono"));
                u.setDireccion(rs.getString("direccion"));
                u.setMail(rs.getString("mail"));
                u.setRol(rs.getString("rol"));
                u.setPassword(rs.getString("password"));
                u.setHabilitado(rs.getInt("habilitado"));
                usuarios.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    public boolean agregarUsuario(String nombre, String apellido, String dni, String telefono, String direccion, String mail, String password) {
        try {
            c.conectar();

            String sqlSelect = "SELECT mail FROM usuarios WHERE mail = ?";
            PreparedStatement selectFrom = c.con.prepareStatement(sqlSelect);
            selectFrom.setString(1, mail);
            ResultSet mailDatabase = selectFrom.executeQuery();
            if (mailDatabase.next()) {
                JOptionPane.showMessageDialog(null, "Este mail ya ha sido registrado");
                return false;
            }

            String sqlInsert = "INSERT INTO usuarios(nombre, apellido, dni, telefono, direccion, mail, rol, password) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = c.con.prepareStatement(sqlInsert);
            pst.setString(1, nombre);
            pst.setString(2, apellido);
            pst.setString(3, dni);
            pst.setString(4, telefono);
            pst.setString(5, direccion);
            pst.setString(6, mail);
            pst.setInt(7, 3);
            pst.setString(8, password);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente");
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean modificarUsuario(String dniOriginal, String nombre, String apellido, String dni, String telefono, String direccion, String mail, String rol, String password) {
        try {
            c.conectar();

            String sqlSelect = "SELECT mail FROM usuarios WHERE mail = ? AND dni != ?";
            PreparedStatement selectFrom = c.con.prepareStatement(sqlSelect);
            selectFrom.setString(1, mail);
            selectFrom.setString(2, dniOriginal);
            ResultSet mailDatabase = selectFrom.executeQuery();
            if (mailDatabase.next()) {
                JOptionPane.showMessageDialog(null, "Ese mail ya pertenece a otro usuario");
                return false;
            }

            String sqlUpdate = "UPDATE usuarios SET nombre=?, apellido=?, dni=?, telefono=?, direccion=?, mail=?, rol=?, password=? WHERE dni=?";
            PreparedStatement pst = c.con.prepareStatement(sqlUpdate);
            pst.setString(1, nombre);
            pst.setString(2, apellido);
            pst.setString(3, dni);
            pst.setString(4, telefono);
            pst.setString(5, direccion);
            pst.setString(6, mail);
            pst.setString(7, rol);
            pst.setString(8, password);
            pst.setString(9, dniOriginal);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario modificado exitosamente");
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean toggleHabilitado(String dni) {
        try {
            c.conectar();
            String sqlToggle = "UPDATE usuarios SET habilitado = CASE WHEN habilitado = 1 THEN 0 ELSE 1 END WHERE dni = ?";
            PreparedStatement pst = c.con.prepareStatement(sqlToggle);
            pst.setString(1, dni);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Estado cambiado exitosamente");
            return true;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Usuario buscarUsuario(String dni, int habilitado) {
        Usuario u = null;

        try {
            c.conectar();
            String sqlSelect = "SELECT nombre, apellido, dni, telefono, direccion, mail, rol, password, habilitado FROM usuarios WHERE dni = ? AND habilitado = ?";
            PreparedStatement selectFrom = c.con.prepareStatement(sqlSelect);
            selectFrom.setString(1, dni);
            selectFrom.setInt(2, habilitado);
            ResultSet resultados = selectFrom.executeQuery();
            if (resultados.next()) {
                u = new Usuario();
                u.setNombre(resultados.getString("nombre"));
                u.setApellido(resultados.getString("apellido"));
                u.setDni(resultados.getString("dni"));
                u.setTelefono(resultados.getString("telefono"));
                u.setDireccion(resultados.getString("direccion"));
                u.setMail(resultados.getString("mail"));
                u.setRol(resultados.getString("rol"));
                u.setPassword(resultados.getString("password"));
                u.setHabilitado(resultados.getInt("habilitado"));
            } else {
                JOptionPane.showMessageDialog(null, "No existe el usuario con el dni");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return u;
    }

}
