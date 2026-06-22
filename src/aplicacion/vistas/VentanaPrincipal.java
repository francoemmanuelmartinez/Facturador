package aplicacion.vistas;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {


    public VentanaPrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
    }

    public void mostrarVista(JPanel vista) {
        getContentPane().removeAll();
        add(vista);
        revalidate();
        repaint();
        setVisible(true);
    }
}
