/**
 * Punto de entrada del sistema Facturador. Inicializa la ventana
 * principal y el controlador de login.
 *
 * @see aplicacion.vistas.VentanaPrincipal
 * @see aplicacion.controladores.ControladorLogin
 * @since 1.0
 */

import aplicacion.controladores.ControladorLogin;
import aplicacion.vistas.VentanaPrincipal;
import javax.swing.*;

/**
 * Inicia la interfaz grafica en el hilo de eventos de Swing.
 */
void main() {

    SwingUtilities.invokeLater(() ->{
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
        ControladorLogin controladorLogin = new ControladorLogin(ventanaPrincipal);
    });
}
