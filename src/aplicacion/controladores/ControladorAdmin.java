package aplicacion.controladores;

import aplicacion.modelos.Usuario;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaAdmin;

public class ControladorAdmin {

    Usuario usuario;

    public ControladorAdmin(){}

    public ControladorAdmin(Usuario usuario, VentanaPrincipal ventanaPrincipal){
        this.usuario=usuario;
        VistaAdmin vistaAdmin = new VistaAdmin(usuario, ventanaPrincipal);
        ventanaPrincipal.setVista(vistaAdmin.panelAdmin);
    }


}
