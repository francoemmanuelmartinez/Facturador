package aplicacion.controladores;

import aplicacion.modelos.Usuario;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaAdmin;

/**
 * Controlador del panel de administracion. Muestra la vista
 * {@link VistaAdmin} con acceso a todos los modulos del sistema.
 *
 * @see VistaAdmin
 * @see Usuario
 * @since 1.0
 */
public class ControladorAdmin {

    private Usuario usuario;

    /**
     * Construye y muestra el panel de administracion.
     *
     * @param usuario           Usuario autenticado
     * @param ventanaPrincipal  JFrame contenedor
     */
    public ControladorAdmin(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        this.usuario = usuario;
        VistaAdmin vistaAdmin = new VistaAdmin(usuario, ventanaPrincipal);
        ventanaPrincipal.mostrarVista(vistaAdmin.panelAdmin);
    }


}
