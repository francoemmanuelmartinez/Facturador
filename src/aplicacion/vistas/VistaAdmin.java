package aplicacion.vistas;

import aplicacion.controladores.*;
import aplicacion.modelos.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaAdmin {
    public JPanel panelAdmin;
    private JButton btnCerrarSesion;
    private JButton btnFacturar;
    private JButton btnAdministrarUsuarios;
    private JButton btnAdministrarClientes;
    private JButton btnAdministrarDeposito;
    private JButton btnAdministrarProveedores;

    public VistaAdmin(Usuario usuario, VentanaPrincipal ventanaPrincipal) {

        btnAdministrarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorUsuarioABM usuarioABM = new ControladorUsuarioABM(usuario, ventanaPrincipal);

            }
        });
        btnAdministrarClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorClienteABM controladorClienteABM = new ControladorClienteABM(usuario, ventanaPrincipal);
            }
        });
        btnFacturar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorCajero controladorCajero = new ControladorCajero(usuario, ventanaPrincipal);
            }
        });
        btnAdministrarDeposito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorDepositoABM depositoABM = new ControladorDepositoABM(usuario, ventanaPrincipal);
            }
        });
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorLogin controladorLogin = new ControladorLogin(ventanaPrincipal);
            }
        });
        btnAdministrarProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorProveedorABM controladorProveedorABM = new ControladorProveedorABM(usuario, ventanaPrincipal);
            }
        });
    }
}
