package aplicacion.filtros;

/**
 * Validador de formato de correo electronico.
 *
 * <p>Verifica que el mail cumpla el patron basico
 * {@code usuario@dominio.tld} mediante una expresion regular.
 * Utilizado por los controladores antes de operaciones contra la BD.</p>
 *
 * @see aplicacion.controladores.ControladorRegistro
 * @see aplicacion.controladores.ControladorClienteABM
 * @see aplicacion.controladores.ControladorProveedorABM
 * @see aplicacion.controladores.ControladorUsuarioABM
 * @since 1.0
 */
public class ValidadorMail {

    /**
     * Verifica si un string tiene formato de email valido.
     *
     * @param mail Cadena a validar (puede ser {@code null})
     * @return {@code true} si coincide con el patron
     *         {@code ^[\w.-]+@[\w.-]+\.\w{2,}$}
     */
    public static boolean esValido(String mail) {
        return mail != null && mail.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    }
}
