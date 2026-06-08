package aplicacion.vistas;

import aplicacion.controladores.ControladorLogin;
import aplicacion.controladores.ControladorModificarUsuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaAdmin {
    public JPanel panelAdmin;
    private JButton cerrarSesion;
    private JButton botonFacturar;
    private JButton botonModificarUsuarios;
    private JButton botonClientes;
    private JButton botonDeposito;

    public VistaAdmin(VentanaPrincipal ventanaPrincipal) {

        botonModificarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorModificarUsuario modificarUsuario = new ControladorModificarUsuario(ventanaPrincipal);

            }
        });
        botonClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        botonFacturar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        botonDeposito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorLogin controladorLogin = new ControladorLogin(ventanaPrincipal);
            }
        });
    }
}
