package aplicacion.vistas;

import aplicacion.controladores.ControladorLogin;
import aplicacion.controladores.ControladorRegistro;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaLogin {

    //private VentanaPrincipal ventana;
    public JPanel panelLogin;
    private JTextField mailTextField;
    private JTextField passwordTextField;
    private JButton botonLogin;
    private JButton botonRegistrar;

    public VistaLogin(VentanaPrincipal ventanaPrincipal) {

        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String mail = mailTextField.getText();
                String password = passwordTextField.getText();

                if(mail.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese ambos datos");
                }
                else {
                    ControladorLogin controladorLogin = new ControladorLogin();
                    if(controladorLogin.validar(mail,password)) {
                        controladorLogin.logear(ventanaPrincipal);
                    }
                    else {
                         JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
                    }

                }
            }
        });
        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorRegistro controladorRegistro = new ControladorRegistro(ventanaPrincipal);


            }
        });
    }
}
