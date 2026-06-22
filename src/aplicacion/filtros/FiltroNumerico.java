package aplicacion.filtros;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Filtro de entrada que permite solo digitos (0-9).
 * Los caracteres no numericos son descartados silenciosamente.
 *
 * <p>Aplicado en campos como DNI, telefono, precio, stock,
 * cantidad y descuento en las vistas del sistema.</p>
 *
 * @see aplicacion.vistas.VistaFormulario
 * @see aplicacion.vistas.VistaRegistro
 * @see aplicacion.vistas.VistaCajero
 * @see aplicacion.vistas.VistaClienteABM
 * @see aplicacion.vistas.VistaProveedorABM
 * @see aplicacion.vistas.VistaDepositoABM
 * @since 1.0
 */
public class FiltroNumerico extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        if (text == null) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isDigit(c)) sb.append(c);
        }
        super.insertString(fb, offset, sb.toString(), attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
        if (text == null) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isDigit(c)) sb.append(c);
        }
        super.replace(fb, offset, length, sb.toString(), attr);
    }
}
