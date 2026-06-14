package aplicacion.vistas;

import aplicacion.controladores.ControladorCliente;
import aplicacion.controladores.ControladorLogin;
import aplicacion.controladores.ControladorUsuarioABM;
import aplicacion.modelos.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaAdmin {
    public JPanel panelAdmin;
    private JButton cerrarSesion;
    private JButton botonFacturar;
    private JButton botonAdministrarUsuarios;
    private JButton botonAdministrarClientes;
    private JButton botonAdministrarDeposito;

    public VistaAdmin(Usuario usuario, VentanaPrincipal ventanaPrincipal) {

        botonAdministrarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorUsuarioABM usuarioABM = new ControladorUsuarioABM(usuario, ventanaPrincipal);

            }
        });
        botonAdministrarClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorCliente controladorCliente = new ControladorCliente(usuario, ventanaPrincipal);
            }
        });
        botonFacturar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        botonAdministrarDeposito.addActionListener(new ActionListener() {
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
