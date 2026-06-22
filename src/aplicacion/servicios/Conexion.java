package aplicacion.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexion JDBC a la base de datos MySQL.
 * Utiliza el driver {@code com.mysql.cj.jdbc.Driver} y se conecta
 * a la base {@code comercial} en {@code localhost:3306}.
 *
 * <p>Es la unica fuente de conexion del sistema; todos los
 * controladores y validadores que acceden a BD la utilizan.</p>
 *
 * @see aplicacion.controladores.ControladorLogin
 * @see aplicacion.controladores.ControladorRegistro
 * @see aplicacion.controladores.ControladorCajero
 * @see aplicacion.controladores.ControladorClienteABM
 * @see aplicacion.controladores.ControladorProveedorABM
 * @see aplicacion.controladores.ControladorUsuarioABM
 * @see aplicacion.controladores.ControladorDepositoABM
 * @see aplicacion.controladores.ControladorFactura
 * @see aplicacion.filtros.ValidadorCantidadAdmin
 * @since 1.0
 */
public class Conexion {

    private Connection con;

    /**
     * Retorna la conexion activa.
     *
     * @return Objeto {@link Connection} de JDBC, o {@code null} si
     *         aun no se llamo a {@link #conectar()}
     */
    public Connection getConnection() {
        return con;
    }

    /**
     * Establece la conexion con la base de datos MySQL.
     * Configura driver, URL, usuario y password.
     *
     * @throws SQLException si no se puede conectar
     * @throws RuntimeException si no se encuentra el driver JDBC
     */
    public void conectar() throws SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/comercial?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "1284";

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
