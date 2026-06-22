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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String sqlSelect = "SELECT id, nombre, apellido, dni, telefono, direccion, mail, rol, password FROM usuarios WHERE mail = ?";
            PreparedStatement selectFrom = c.getConnection().prepareStatement(sqlSelect);
            selectFrom.setString(1, mail);
            ResultSet resultados = selectFrom.executeQuery();

            if (resultados.next()) {
                usuario.setId(resultados.getInt("id"));
                usuario.setNombre(resultados.getString("nombre"));
                usuario.setApellido(resultados.getString("apellido"));
                usuario.setDni(resultados.getString("dni"));
                usuario.setTelefono(resultados.getString("telefono"));
                usuario.setDireccion(resultados.getString("direccion"));
                usuario.setMail(resultados.getString("mail"));
                usuario.setRol(resultados.getString("rol"));
                usuario.setPassword(resultados.getString("password"));
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
            default -> JOptionPane.showMessageDialog(null, "USUARIO INVALIDO");
        }
    }

}
