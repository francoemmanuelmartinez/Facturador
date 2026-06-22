package aplicacion.vistas;
import aplicacion.controladores.ControladorLogin;
import aplicacion.controladores.ControladorRegistro;

import aplicacion.filtros.FiltroAlfanumerico;
import aplicacion.filtros.FiltroNumerico;
import aplicacion.filtros.FiltroTexto;
import aplicacion.filtros.ValidadorCampos;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Formulario de registro de nuevo usuario. Aplica filtros de entrada
 * (FiltroNumerico, FiltroTexto, FiltroAlfanumerico) y valida campos
 * requeridos antes de llamar al controlador.
 *
 * @see ControladorRegistro
 * @see ControladorLogin
 * @see FiltroNumerico
 * @see FiltroTexto
 * @see FiltroAlfanumerico
 * @see ValidadorCampos
 * @since 1.0
 */
public class VistaRegistro {

    public JPanel panelRegistro;
    private JTextField tfNombre;
    private JTextField tfApellido;
    private JTextField tfDni;
    private JTextField tfTelefono;
    private JTextField tfDireccion;
    private JTextField tfMail;
    private JPasswordField tfPassword;
    private JButton btnCrearUsuario;
    private JButton btnVolver;


    public VistaRegistro(VentanaPrincipal ventanaPrincipal) {

        ((AbstractDocument) tfDni.getDocument()).setDocumentFilter(new FiltroNumerico());
        ((AbstractDocument) tfTelefono.getDocument()).setDocumentFilter(new FiltroNumerico());
        ((AbstractDocument) tfNombre.getDocument()).setDocumentFilter(new FiltroTexto());
        ((AbstractDocument) tfApellido.getDocument()).setDocumentFilter(new FiltroTexto());
        ((AbstractDocument) tfDireccion.getDocument()).setDocumentFilter(new FiltroAlfanumerico());

        btnCrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = tfNombre.getText();
                String apellido = tfApellido.getText();
                String dni = tfDni.getText();
                String telefono = tfTelefono.getText();
                String direccion = tfDireccion.getText();
                String mail = tfMail.getText();
                String password = new String(tfPassword.getPassword());

                String[][] requeridos = {
                    {"Nombre", nombre},
                    {"Apellido", apellido},
                    {"DNI", dni},
                    {"Mail", mail},
                    {"Contrasena", password}
                };
                if (!ValidadorCampos.validarRequeridos(requeridos)) return;

                ControladorRegistro controlador = new ControladorRegistro();
                if (controlador.registrar(nombre, apellido, dni, telefono, direccion, mail, password)) {
                    ControladorLogin controladorLogin = new ControladorLogin(ventanaPrincipal);
                    JOptionPane.showMessageDialog(null, "Inicie sesion");
                }
            }
        });
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorLogin controladorLogin = new ControladorLogin(ventanaPrincipal);
            }
        });
    }
}
