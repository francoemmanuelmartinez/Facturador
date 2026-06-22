package aplicacion.vistas;

import aplicacion.controladores.ControladorLogin;
import aplicacion.controladores.ControladorRegistro;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaLogin {

    //private VentanaPrincipal ventana;
    public JPanel panelLogin;
    private JTextField tfMail;
    private JPasswordField tfPassword;
    private JButton btnIngresar;
    private JButton btnCrearCuenta;

    public VistaLogin(VentanaPrincipal ventanaPrincipal) {

        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String mail = tfMail.getText();
                String password = new String(tfPassword.getPassword());

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
        btnCrearCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorRegistro controladorRegistro = new ControladorRegistro(ventanaPrincipal);


            }
        });
    }
}
