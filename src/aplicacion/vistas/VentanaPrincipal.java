package aplicacion.vistas;

import javax.swing.*;

/**
 * Ventana principal de la aplicacion. Extiende {@link JFrame} y
 * administra la navegacion entre paneles a traves de
 * {@link #mostrarVista(JPanel)}.
 *
 * @since 1.0
 */
public class VentanaPrincipal extends JFrame {


    /**
     * Configura la ventana principal con tamano 800x600 y cierre
     * por defecto (EXIT_ON_CLOSE).
     */
    public VentanaPrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
    }

    /**
     * Reemplaza el contenido actual de la ventana por un nuevo panel.
     *
     * @param vista Nuevo JPanel a mostrar
     */
    public void mostrarVista(JPanel vista) {
        getContentPane().removeAll();
        add(vista);
        revalidate();
        repaint();
        setVisible(true);
    }
}
