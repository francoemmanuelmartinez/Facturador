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

/**
 * Controlador de inicio de sesion. Busca el usuario en BD por mail,
 * valida credenciales via {@link Autenticacion} y redirige segun el
 * rol al panel correspondiente.
 *
 * @see VistaLogin
 * @see Conexion
 * @see Autenticacion
 * @see Usuario
 * @see ControladorAdmin
 * @see ControladorCajero
 * @see ControladorDepositoABM
 * @since 1.0
 */
public class ControladorLogin {

    private Conexion c = new Conexion();
    private Usuario usuario = new Usuario();

    /**
     * Constructor que muestra la vista de login.
     *
     * @param ventanaPrincipal JFrame contenedor donde se mostrara el panel
     */
    public ControladorLogin(VentanaPrincipal ventanaPrincipal) {

        VistaLogin vistaLogin = new VistaLogin(ventanaPrincipal);
        ventanaPrincipal.mostrarVista(vistaLogin.panelLogin);
    }

    /** Constructor vacio para instanciacion desde la vista. */
    public ControladorLogin() {
    }

    /**
     * Busca un usuario por mail en BD y valida la contrasena.
     *
     * @param mail     Mail ingresado
     * @param password Contrasena ingresada
     * @return {@code true} si las credenciales son correctas
     * @throws RuntimeException si hay error de SQL
     * @see Autenticacion#autenticar(Usuario)
     */
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

    /**
     * Redirige al usuario al panel correspondiente segun su rol.
     * Roles: Administrador, Cajero, Deposito, Ninguno.
     *
     * @param ventanaPrincipal JFrame contenedor
     * @see ControladorAdmin
     * @see ControladorCajero
     * @see ControladorDepositoABM
     */
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
