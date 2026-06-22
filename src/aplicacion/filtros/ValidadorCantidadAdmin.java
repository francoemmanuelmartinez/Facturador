package aplicacion.filtros;

import aplicacion.servicios.Conexion;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Evita deshabilitar al unico administrador habilitado en el sistema.
 *
 * <p>Ejecuta un conteo en BD de administradores habilitados excluyendo
 * al usuario en cuestion. Si es el unico, muestra un mensaje y rechaza
 * la operacion.</p>
 *
 * @see aplicacion.servicios.Conexion
 * @see aplicacion.controladores.ControladorUsuarioABM#alternarHabilitadoUsuario(int)
 * @since 1.0
 */
public class ValidadorCantidadAdmin {

    /**
     * Verifica si existe al menos otro administrador habilitado
     * ademas del especificado.
     *
     * @param idUsuario ID del usuario que se intenta deshabilitar
     * @return {@code true} si hay otro administrador disponible,
     *         {@code false} si es el unico
     * @throws RuntimeException si ocurre un error de SQL
     */
    public static boolean permitirDeshabilitar(int idUsuario) {
        try {
            Conexion c = new Conexion();
            c.conectar();
            String sql = "SELECT COUNT(*) FROM usuarios WHERE rol = 'Administrador' AND habilitado = 1 AND id != ?";
            PreparedStatement stmt = c.getConnection().prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                JOptionPane.showMessageDialog(null,
                    "No se puede deshabilitar al unico administrador habilitado");
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
