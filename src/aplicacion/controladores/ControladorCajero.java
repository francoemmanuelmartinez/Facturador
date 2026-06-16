package aplicacion.controladores;

import aplicacion.modelos.Usuario;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaCajero;

public class ControladorCajero {

    Usuario usuario;

    public ControladorCajero(){}

    public ControladorCajero(Usuario usuario, VentanaPrincipal ventanaPrincipal){
        this.usuario = usuario;
        VistaCajero vistaCajero = new VistaCajero(usuario, ventanaPrincipal);
        ventanaPrincipal.setVista(vistaCajero.panelCajero);
    }
}
