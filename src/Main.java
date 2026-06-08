
import aplicacion.controladores.ControladorLogin;
import aplicacion.vistas.VentanaPrincipal;
import javax.swing.*;

void main() {

    SwingUtilities.invokeLater(() ->{
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
        ControladorLogin controladorLogin = new ControladorLogin(ventanaPrincipal);
    });
}
