package aplicacion.vistas;
import aplicacion.controladores.ControladorLogin;
import aplicacion.controladores.ControladorRegistro;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaRegistro {

    public JPanel panelRegistro;
    private JTextField nombreTF;
    private JTextField apellidoTF;
    private JTextField dniTF;
    private JTextField telefonoTF;
    private JTextField direccionTF;
    private JTextField mailTF;
    private JTextField password;
    private JButton registrarse;
    private JButton botonVolver;


    public VistaRegistro(VentanaPrincipal ventanaPrincipal) {

        registrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorRegistro controlador = new ControladorRegistro();
                if(controlador.registrar(nombreTF.getText(), apellidoTF.getText(),dniTF.getText(),telefonoTF.getText(),direccionTF.getText(),mailTF.getText(),password.getText())) {
                    ControladorLogin controladorLogin = new ControladorLogin(ventanaPrincipal);
                    JOptionPane.showMessageDialog(null, "Logee");
                }
            }
        });
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorLogin controladorLogin = new ControladorLogin(ventanaPrincipal);
            }
        });
    }
}
