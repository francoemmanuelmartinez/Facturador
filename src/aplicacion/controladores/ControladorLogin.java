package aplicacion.controladores;
import aplicacion.modelos.Usuario;
import aplicacion.servicios.Autenticacion;
import aplicacion.servicios.Conexion;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaLogin;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorLogin {

    private Conexion c = new Conexion();
    private Usuario usuario = new Usuario();

    public ControladorLogin(VentanaPrincipal ventanaPrincipal) {

        VistaLogin vistaLogin = new VistaLogin(ventanaPrincipal);
        ventanaPrincipal.mostrarVista(vistaLogin.panelLogin);
    }

    public ControladorLogin() {
    }

    public boolean validar(String mail, String password) {
        try {
            c.conectar();
            String sqlSelect = "SELECT id, nombre, apellido, dni, telefono, direccion, mail, rol, password FROM usuarios WHERE mail = ?";
            PreparedStatement stmtSelectUsuario = c.getConnection().prepareStatement(sqlSelect);
            stmtSelectUsuario.setString(1, mail);
            ResultSet rsUsuario = stmtSelectUsuario.executeQuery();

            if (rsUsuario.next()) {
                usuario.setId(rsUsuario.getInt("id"));
                usuario.setNombre(rsUsuario.getString("nombre"));
                usuario.setApellido(rsUsuario.getString("apellido"));
                usuario.setDni(rsUsuario.getString("dni"));
                usuario.setTelefono(rsUsuario.getString("telefono"));
                usuario.setDireccion(rsUsuario.getString("direccion"));
                usuario.setMail(rsUsuario.getString("mail"));
                usuario.setRol(rsUsuario.getString("rol"));
                usuario.setPassword(rsUsuario.getString("password"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Autenticacion autenticacion = new Autenticacion(mail, password);
        if (autenticacion.autenticar(usuario) == false) {
            return false;
        }
        else {
            return true;
        }
    }

    public void iniciarSesion(VentanaPrincipal ventanaPrincipal) {
        switch (usuario.getRol()) {
            case "Administrador" -> {
                ControladorAdmin controladorAdmin = new ControladorAdmin(usuario, ventanaPrincipal);
            }
            case "Cajero" -> {
                ControladorCajero cajero = new ControladorCajero(usuario, ventanaPrincipal);
            }
            case "Deposito" -> {
                ControladorDepositoABM deposito = new ControladorDepositoABM(usuario, ventanaPrincipal);
            }
            case "Ninguno" -> JOptionPane.showMessageDialog(null, "Sin rol asignado. Contacte a un administrador");
            default -> JOptionPane.showMessageDialog(null, "USUARIO INVALIDO");
        }
    }

}
