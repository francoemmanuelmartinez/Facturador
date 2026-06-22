package aplicacion.controladores;

import aplicacion.modelos.Proveedor;
import aplicacion.modelos.Usuario;
import aplicacion.servicios.Conexion;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaProveedorABM;

import aplicacion.filtros.ValidadorCampos;
import aplicacion.filtros.ValidadorMail;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD de proveedores. Valida campos requeridos y formato de mail
 * antes de cada operacion contra la base de datos.
 *
 * @see VistaProveedorABM
 * @see Proveedor
 * @see ValidadorCampos
 * @see ValidadorMail
 * @see Conexion
 * @since 1.0
 */
public class ControladorProveedorABM {
    private Conexion c = new Conexion();

    /** Constructor vacio requerido por instanciacion directa. */
    public ControladorProveedorABM() {}

    /**
     * Construye la vista de ABM de proveedores y la muestra.
     *
     * @param usuario           Usuario autenticado
     * @param ventanaPrincipal  JFrame contenedor
     */
    public ControladorProveedorABM(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        VistaProveedorABM vistaProveedorABM = new VistaProveedorABM(usuario, ventanaPrincipal);
        ventanaPrincipal.mostrarVista(vistaProveedorABM.panelProveedorABM);
    }

    /**
     * Obtiene proveedores filtrados por estado de habilitacion.
     *
     * @param habilitado 1 para habilitados, 0 para deshabilitados
     * @return Lista de proveedores
     * @throws RuntimeException si error de SQL
     */
    public List<Proveedor> obtenerProveedoresPorHabilitado(int habilitado) {
        List<Proveedor> proveedores = new ArrayList<>();
        try {
            c.conectar();
            String sqlSelect = "SELECT id, nombre, telefono, direccion, mail, habilitado FROM proveedores WHERE habilitado = ?";
            PreparedStatement stmtProveedores = c.getConnection().prepareStatement(sqlSelect);
            stmtProveedores.setInt(1, habilitado);
            ResultSet rsProveedores = stmtProveedores.executeQuery();
            while (rsProveedores.next()) {
                proveedores.add(mapearProveedor(rsProveedores));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proveedores;
    }

    /**
     * Busca un proveedor por ID y estado de habilitacion.
     *
     * @param id         ID del proveedor
     * @param habilitado 1 para habilitados, 0 para deshabilitados
     * @return El proveedor encontrado, o null si no existe
     * @throws RuntimeException si error de SQL
     */
    public Proveedor buscarProveedor(int id, int habilitado) {
        Proveedor proveedor = null;
        try {
            c.conectar();
            String sqlSelect = "SELECT id, nombre, telefono, direccion, mail, habilitado FROM proveedores WHERE id = ? AND habilitado = ?";
            PreparedStatement stmtProveedor = c.getConnection().prepareStatement(sqlSelect);
            stmtProveedor.setInt(1, id);
            stmtProveedor.setInt(2, habilitado);
            ResultSet rsProveedor = stmtProveedor.executeQuery();
            if (rsProveedor.next()) {
                proveedor = mapearProveedor(rsProveedor);
            } else {
                JOptionPane.showMessageDialog(null, "No existe el proveedor con ese ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proveedor;
    }

    /**
     * Inserta un nuevo proveedor. Valida campos requeridos, mail y unicidad.
     *
     * @param nombre     Nombre
     * @param telefono   Telefono
     * @param direccion  Direccion
     * @param mail       Mail (debe ser unico)
     * @return ID generado, o -1 si falla validacion
     * @throws RuntimeException si error de SQL
     * @see ValidadorCampos#validarRequeridos(String[][])
     * @see ValidadorMail#esValido(String)
     * @see #modificarProveedor(int, String, String, String, String)
     */
    public int agregarProveedor(String nombre, String telefono, String direccion, String mail) {
        try {
            String[][] requeridos = {
                {"Nombre", nombre},
                {"Telefono", telefono},
                {"Direccion", direccion},
                {"Mail", mail}
            };
            if (!ValidadorCampos.validarRequeridos(requeridos)) return -1;

            if (!ValidadorMail.esValido(mail)) {
                JOptionPane.showMessageDialog(null, "El formato del mail no es valido");
                return -1;
            }
            c.conectar();

            String sqlSelect = "SELECT mail FROM proveedores WHERE mail = ?";
            PreparedStatement stmtSelectMail = c.getConnection().prepareStatement(sqlSelect);
            stmtSelectMail.setString(1, mail);
            ResultSet rsMailExistente = stmtSelectMail.executeQuery();
            if (rsMailExistente.next()) {
                JOptionPane.showMessageDialog(null, "Este mail ya ha sido registrado");
                return -1;
            }

            String sqlInsert = "INSERT INTO proveedores(nombre, telefono, direccion, mail) VALUES(?, ?, ?, ?)";
            PreparedStatement pstInsert = c.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            pstInsert.setString(1, nombre);
            pstInsert.setString(2, telefono);
            pstInsert.setString(3, direccion);
            pstInsert.setString(4, mail);
            pstInsert.executeUpdate();

            ResultSet rsGeneratedKeys = pstInsert.getGeneratedKeys();
            int id = -1;
            if (rsGeneratedKeys.next()) {
                id = rsGeneratedKeys.getInt(1);
            }

            JOptionPane.showMessageDialog(null, "Proveedor agregado exitosamente");
            return id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actualiza un proveedor existente. Valida campos requeridos, mail y
     * unicidad excluyendo el propio ID.
     *
     * @param id         ID del proveedor
     * @param nombre     Nuevo nombre
     * @param telefono   Nuevo telefono
     * @param direccion  Nueva direccion
     * @param mail       Nuevo mail
     * @return true si exito, false si falla validacion
     * @throws RuntimeException si error de SQL
     * @see #agregarProveedor(String, String, String, String)
     * @see ValidadorCampos#validarRequeridos(String[][])
     * @see ValidadorMail#esValido(String)
     */
    public boolean modificarProveedor(int id, String nombre, String telefono, String direccion, String mail) {
        try {
            String[][] requeridos = {
                {"Nombre", nombre},
                {"Telefono", telefono},
                {"Direccion", direccion},
                {"Mail", mail}
            };
            if (!ValidadorCampos.validarRequeridos(requeridos)) return false;

            if (!ValidadorMail.esValido(mail)) {
                JOptionPane.showMessageDialog(null, "El formato del mail no es valido");
                return false;
            }
            c.conectar();

            String sqlSelect = "SELECT mail FROM proveedores WHERE mail = ? AND id != ?";
            PreparedStatement stmtSelectMail = c.getConnection().prepareStatement(sqlSelect);
            stmtSelectMail.setString(1, mail);
            stmtSelectMail.setInt(2, id);
            ResultSet rsMailExistente = stmtSelectMail.executeQuery();
            if (rsMailExistente.next()) {
                JOptionPane.showMessageDialog(null, "Ese mail ya pertenece a otro proveedor");
                return false;
            }

            String sqlUpdate = "UPDATE proveedores SET nombre = ?, telefono = ?, direccion = ?, mail = ? WHERE id = ?";
            PreparedStatement pstUpdate = c.getConnection().prepareStatement(sqlUpdate);
            pstUpdate.setString(1, nombre);
            pstUpdate.setString(2, telefono);
            pstUpdate.setString(3, direccion);
            pstUpdate.setString(4, mail);
            pstUpdate.setInt(5, id);
            pstUpdate.executeUpdate();
            JOptionPane.showMessageDialog(null, "Proveedor modificado exitosamente");
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invierte el estado de habilitacion de un proveedor.
     *
     * @param id ID del proveedor
     * @return true siempre
     * @throws RuntimeException si error de SQL
     */
    public boolean alternarHabilitadoProveedor(int id) {
        try {
            c.conectar();
            String sqlUpdateHabilitado = "UPDATE proveedores SET habilitado = CASE WHEN habilitado = 1 THEN 0 ELSE 1 END WHERE id = ?";
            PreparedStatement pstUpdate = c.getConnection().prepareStatement(sqlUpdateHabilitado);
            pstUpdate.setInt(1, id);
            pstUpdate.executeUpdate();
            JOptionPane.showMessageDialog(null, "Estado cambiado exitosamente");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Construye un objeto {@link Proveedor} desde la fila actual del ResultSet.
     *
     * @param rs ResultSet posicionado
     * @return Proveedor poblado
     * @throws SQLException si error de columnas
     */
    private Proveedor mapearProveedor(ResultSet rs) throws SQLException {
        Proveedor p = new Proveedor();
        p.setId(rs.getInt("id"));
        p.setNombre(rs.getString("nombre"));
        p.setTelefono(rs.getString("telefono"));
        p.setDireccion(rs.getString("direccion"));
        p.setMail(rs.getString("mail"));
        p.setHabilitado(rs.getInt("habilitado"));
        return p;
    }
}
