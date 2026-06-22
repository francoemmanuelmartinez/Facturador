package aplicacion.filtros;

import javax.swing.JOptionPane;

/**
 * Validador de campos requeridos en formularios.
 *
 * <p>Recibe un arreglo de pares {@code {nombre, valor}} y verifica que
 * ningun valor sea {@code null} o vacio. Si faltan campos, muestra un
 * {@link javax.swing.JOptionPane} con la lista y retorna {@code false}.</p>
 *
 * @see aplicacion.controladores.ControladorRegistro
 * @see aplicacion.controladores.ControladorClienteABM
 * @see aplicacion.controladores.ControladorProveedorABM
 * @see aplicacion.controladores.ControladorUsuarioABM
 * @see aplicacion.controladores.ControladorDepositoABM
 * @see aplicacion.vistas.VistaRegistro
 * @since 1.0
 */
public class ValidadorCampos {

    /**
     * Verifica que todos los campos tengan valor no vacio.
     *
     * @param campos Arreglo de pares {@code {nombre, valor}} donde
     *               {@code nombre} es la etiqueta visible del campo
     *               y {@code valor} es su contenido
     * @return {@code true} si todos los campos tienen valor,
     *         {@code false} si al menos uno esta vacio
     */
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
