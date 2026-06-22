package aplicacion.filtros;

import javax.swing.JOptionPane;

public class ValidadorCampos {

    public static boolean validarRequeridos(String[][] campos) {
        StringBuilder faltan = new StringBuilder();

        for (String[] par : campos) {
            String nombre = par[0];
            String valor = par[1];

            if (valor == null || valor.trim().isEmpty()) {
                if (faltan.length() > 0) faltan.append(", ");
                faltan.append(nombre);
            }
        }

        if (faltan.length() > 0) {
            JOptionPane.showMessageDialog(null,
                "Los siguientes campos son obligatorios: " + faltan.toString());
            return false;
        }

        return true;
    }
}
