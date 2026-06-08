package aplicacion.controladores;

import aplicacion.modelos.Usuario;
import aplicacion.servicios.Conexion;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaModificarUsuarios;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorModificarUsuario {

    Conexion c = new Conexion();

    public ControladorModificarUsuario() {
    }

    public ControladorModificarUsuario(VentanaPrincipal ventanaPrincipal) {

        VistaModificarUsuarios vistaModificarUsuarios = new VistaModificarUsuarios(ventanaPrincipal);
        ventanaPrincipal.setVista(vistaModificarUsuarios.panelModificarUsuarios);

    }

    public Usuario buscarUsuario(String dni) {
        Usuario u = null;

        try {
            c.conectar();
            String sqlSelect = "SELECT nombre, apellido, dni, telefono, direccion, mail, rol, password, habilitado FROM usuarios WHERE dni = ?";
            PreparedStatement selectFrom = c.con.prepareStatement(sqlSelect);
            selectFrom.setString(1, dni);
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
