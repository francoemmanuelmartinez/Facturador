package aplicacion.controladores;

import aplicacion.modelos.Usuario;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaAdmin;

public class ControladorAdmin {

    private Usuario usuario;

    public ControladorAdmin(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        this.usuario = usuario;
        VistaAdmin vistaAdmin = new VistaAdmin(usuario, ventanaPrincipal);
        ventanaPrincipal.mostrarVista(vistaAdmin.panelAdmin);
    }


}
