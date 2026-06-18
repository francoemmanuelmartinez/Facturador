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

    String mail;
    String password;
    String sql = "SELECT id, nombre, apellido,dni,telefono,direccion,mail,rol,password FROM usuarios WHERE mail = ?";
    Conexion c = new Conexion();
    Usuario usuario = new Usuario();

    public ControladorLogin(VentanaPrincipal ventanaPrincipal){

        VistaLogin vistaLogin = new VistaLogin(ventanaPrincipal);
        ventanaPrincipal.setVista(vistaLogin.panelLogin);
    }

    public ControladorLogin() {
    }

    public boolean validar(String mail, String password) {
        this.mail=mail;
        this.password=password;

        try {
            c.conectar();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            PreparedStatement selectFrom = c.con.prepareStatement(sql);
            selectFrom.setString(1,mail);
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

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Autenticacion autenticacion = new Autenticacion(mail,password);
        if(autenticacion.autenticar(usuario) == false){
            return false;
        }
        else {
            return true;
        }
    }

    public void logear (VentanaPrincipal ventanaPrincipal){
        switch (usuario.getRol()) {
            case "Administrador" -> {
                ControladorAdmin controladorAdmin = new ControladorAdmin(usuario,ventanaPrincipal);
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


    //if si no esta logeado tirame la vista de registrar
   // si el usuario ingreso algo mal que me limpi la ventana y salte un mensaje de error
}
