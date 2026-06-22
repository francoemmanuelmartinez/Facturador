package aplicacion.controladores;

import aplicacion.modelos.Usuario;
import aplicacion.servicios.Conexion;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaRegistro;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorRegistro {

    Conexion c = new Conexion();
    String sqlInsert = "INSERT INTO usuarios(nombre, apellido, dni, telefono, direccion, mail, rol, password) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

    public ControladorRegistro(){}

    public ControladorRegistro(VentanaPrincipal ventanaPrincipal) {

        VistaRegistro vistaRegistro = new VistaRegistro(ventanaPrincipal);
        ventanaPrincipal.setVista(vistaRegistro.panelRegistro);
    }

    public boolean registrar(String nombre, String apellido, String dni, String telefono, String direccion, String mail, String password){
        try {
            c.conectar();
            String sqlSelect = "SELECT mail FROM usuarios WHERE mail = ?";
            PreparedStatement selectFrom = c.con.prepareStatement(sqlSelect);
            selectFrom.setString(1,mail);
            ResultSet mailDatabase = selectFrom.executeQuery();
            if (mailDatabase.next()) {
                JOptionPane.showMessageDialog(null, "Este mail ya ha sido registrado");
                return false;
            }
            else {

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
                JOptionPane.showMessageDialog(null, "Registro exitoso");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
