package aplicacion.vistas;

import aplicacion.controladores.ControladorCajero;
import aplicacion.controladores.ControladorClienteABM;
import aplicacion.controladores.ControladorDepositoABM;
import aplicacion.controladores.ControladorLogin;
import aplicacion.controladores.ControladorProveedorABM;
import aplicacion.controladores.ControladorUsuarioABM;
import aplicacion.modelos.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel principal del administrador con acceso a todos los modulos
 * del sistema: usuarios, clientes, caja, deposito y proveedores.
 *
 * @see ControladorUsuarioABM
 * @see ControladorClienteABM
 * @see ControladorCajero
 * @see ControladorDepositoABM
 * @see ControladorProveedorABM
 * @see ControladorLogin
 * @see Usuario
 * @since 1.0
 */
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
