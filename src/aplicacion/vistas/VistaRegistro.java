package aplicacion.vistas;
import aplicacion.controladores.ControladorLogin;
import aplicacion.controladores.ControladorRegistro;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        btnCrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorRegistro controlador = new ControladorRegistro();
                if (controlador.registrar(tfNombre.getText(), tfApellido.getText(), tfDni.getText(), tfTelefono.getText(), tfDireccion.getText(), tfMail.getText(), new String(tfPassword.getPassword()))) {
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
