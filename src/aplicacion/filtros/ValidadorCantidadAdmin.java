package aplicacion.filtros;

import aplicacion.servicios.Conexion;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidadorCantidadAdmin {

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
