package aplicacion.filtros;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Filtro de entrada que permite solo letras y espacios.
 * Los caracteres no alfabeticos (digitos, simbolos) son descartados
 * silenciosamente.
 *
 * <p>Aplicado en campos como nombre y apellido.</p>
 *
 * @see aplicacion.vistas.VistaFormulario
 * @see aplicacion.vistas.VistaRegistro
 * @since 1.0
 */
public class FiltroTexto extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        if (text == null) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c) || c == ' ') sb.append(c);
        }
        super.insertString(fb, offset, sb.toString(), attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
        if (text == null) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c) || c == ' ') sb.append(c);
        }
        super.replace(fb, offset, length, sb.toString(), attr);
    }
}
