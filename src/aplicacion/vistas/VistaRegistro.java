package aplicacion.vistas;
import aplicacion.controladores.ControladorLogin;
import aplicacion.controladores.ControladorRegistro;

import aplicacion.filtros.FiltroAlfanumerico;
import aplicacion.filtros.FiltroNumerico;
import aplicacion.filtros.FiltroTexto;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
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

        ((AbstractDocument) tfDni.getDocument()).setDocumentFilter(new FiltroNumerico());
        ((AbstractDocument) tfTelefono.getDocument()).setDocumentFilter(new FiltroNumerico());
        ((AbstractDocument) tfNombre.getDocument()).setDocumentFilter(new FiltroTexto());
        ((AbstractDocument) tfApellido.getDocument()).setDocumentFilter(new FiltroTexto());
        ((AbstractDocument) tfDireccion.getDocument()).setDocumentFilter(new FiltroAlfanumerico());

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
