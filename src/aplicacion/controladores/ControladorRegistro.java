package aplicacion.controladores;

import aplicacion.servicios.Conexion;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaRegistro;

import aplicacion.filtros.ValidadorCampos;
import aplicacion.filtros.ValidadorMail;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controlador de registro de nuevos usuarios. Valida campos requeridos
 * y formato de mail antes de insertar en BD con rol "Ninguno".
 *
 * @see VistaRegistro
 * @see Conexion
 * @see ValidadorCampos
 * @see ValidadorMail
 * @since 1.0
 */
public class ControladorRegistro {

    private Conexion c = new Conexion();

    /** Constructor vacio para instanciacion desde la vista. */
    public ControladorRegistro() {}

    /**
     * Constructor que muestra el formulario de registro.
     *
     * @param ventanaPrincipal JFrame contenedor
     */
    public ControladorRegistro(VentanaPrincipal ventanaPrincipal) {

        VistaRegistro vistaRegistro = new VistaRegistro(ventanaPrincipal);
        ventanaPrincipal.mostrarVista(vistaRegistro.panelRegistro);
    }

    /**
     * Registra un nuevo usuario. Valida campos requeridos, formato de mail
     * y unicidad del mail. Asigna rol "Ninguno" por defecto.
     *
     * @param nombre     Nombre
     * @param apellido   Apellido
     * @param dni        Documento
     * @param telefono   Telefono
     * @param direccion  Direccion
     * @param mail       Mail (debe ser unico)
     * @param password   Contrasena
     * @return {@code true} si el registro fue exitoso
     * @throws RuntimeException si hay error de SQL
     * @see ValidadorCampos#validarRequeridos(String[][])
     * @see ValidadorMail#esValido(String)
     */
    public boolean registrar(String nombre, String apellido, String dni, String telefono, String direccion, String mail, String password) {
        try {
            String[][] requeridos = {
                {"Nombre", nombre},
                {"Apellido", apellido},
                {"DNI", dni},
                {"Mail", mail},
                {"Contrasena", password}
            };
            if (!ValidadorCampos.validarRequeridos(requeridos)) return false;

            if (!ValidadorMail.esValido(mail)) {
                JOptionPane.showMessageDialog(null, "El formato del mail no es valido");
                return false;
            }
            c.conectar();
            String sqlSelect = "SELECT mail FROM usuarios WHERE mail = ?";
            PreparedStatement stmtSelectMail = c.getConnection().prepareStatement(sqlSelect);
            stmtSelectMail.setString(1, mail);
            ResultSet rsMailExistente = stmtSelectMail.executeQuery();
            if (rsMailExistente.next()) {
                JOptionPane.showMessageDialog(null, "Este mail ya ha sido registrado");
                return false;
            }
            else {

                String sqlInsert = "INSERT INTO usuarios(nombre, apellido, dni, telefono, direccion, mail, rol, password) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = c.getConnection().prepareStatement(sqlInsert);
                pst.setString(1, nombre);
                pst.setString(2, apellido);
                pst.setString(3, dni);
                pst.setString(4, telefono);
                pst.setString(5, direccion);
                pst.setString(6, mail);
                pst.setString(7, "Ninguno");
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
