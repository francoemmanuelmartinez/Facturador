package aplicacion.controladores;

import aplicacion.filtros.ValidadorCampos;
import aplicacion.modelos.Producto;
import aplicacion.modelos.Usuario;
import aplicacion.servicios.Conexion;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaDepositoABM;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD de productos del deposito. Valida campos requeridos
 * (descripcion, proveedor) antes de cada operacion.
 *
 * @see VistaDepositoABM
 * @see Producto
 * @see ValidadorCampos
 * @see Conexion
 * @since 1.0
 */
public class ControladorDepositoABM {

    private Conexion c = new Conexion();

    /** Constructor vacio requerido por instanciacion directa. */
    public ControladorDepositoABM() {}

    /**
     * Construye la vista de ABM de deposito y la muestra.
     *
     * @param usuario           Usuario autenticado
     * @param ventanaPrincipal  JFrame contenedor
     */
    public ControladorDepositoABM(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        VistaDepositoABM vistaDepositoABM = new VistaDepositoABM(usuario, ventanaPrincipal);
        ventanaPrincipal.mostrarVista(vistaDepositoABM.panelDepositoABM);
    }

    /**
     * Obtiene productos filtrados por estado de habilitacion,
     * incluyendo datos del proveedor via JOIN.
     *
     * @param habilitado 1 para habilitados, 0 para deshabilitados
     * @return Lista de productos
     * @throws RuntimeException si error de SQL
     */
    public List<Producto> obtenerProductosPorHabilitado(int habilitado) {
        List<Producto> productos = new ArrayList<>();
        try {
            c.conectar();
            String sqlSelect = "SELECT p.id, p.descripcion, p.precio, p.stock, p.id_proveedor, pr.nombre AS nombreProveedor, p.habilitado FROM productos p LEFT JOIN proveedores pr ON p.id_proveedor = pr.id WHERE p.habilitado = ?";
            PreparedStatement stmtProductos = c.getConnection().prepareStatement(sqlSelect);
            stmtProductos.setInt(1, habilitado);
            ResultSet rsProductos = stmtProductos.executeQuery();
            while (rsProductos.next()) {
                productos.add(mapearProducto(rsProductos));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    /**
     * Busca productos por ID (busqueda exacta) o descripcion (LIKE).
     *
     * @param texto      ID numerico o texto para busqueda por descripcion
     * @param habilitado Filtro de estado
     * @return Lista de productos coincidentes
     * @throws RuntimeException si error de SQL
     * @see #seleccionarProducto(String, int)
     */
    public List<Producto> buscarProductos(String texto, int habilitado) {
        List<Producto> productos = new ArrayList<>();
        try {
            c.conectar();
            PreparedStatement stmtProducto;
            if (texto.matches("\\d+")) {
                String sqlSelect = "SELECT p.id, p.descripcion, p.precio, p.stock, p.id_proveedor, pr.nombre AS nombreProveedor, p.habilitado FROM productos p LEFT JOIN proveedores pr ON p.id_proveedor = pr.id WHERE p.id = ? AND p.habilitado = ?";
                stmtProducto = c.getConnection().prepareStatement(sqlSelect);
                stmtProducto.setInt(1, Integer.parseInt(texto));
                stmtProducto.setInt(2, habilitado);
            } else {
                String sqlSelect = "SELECT p.id, p.descripcion, p.precio, p.stock, p.id_proveedor, pr.nombre AS nombreProveedor, p.habilitado FROM productos p LEFT JOIN proveedores pr ON p.id_proveedor = pr.id WHERE p.descripcion LIKE ? AND p.habilitado = ?";
                stmtProducto = c.getConnection().prepareStatement(sqlSelect);
                stmtProducto.setString(1, "%" + texto + "%");
                stmtProducto.setInt(2, habilitado);
            }
            ResultSet rsProductos = stmtProducto.executeQuery();
            while (rsProductos.next()) {
                productos.add(mapearProducto(rsProductos));
            }
            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron productos con ese criterio");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    /**
     * Busca productos y si hay multiples resultados, muestra un dialogo
     * de seleccion. Retorna el producto elegido.
     *
     * @param texto      Criterio de busqueda
     * @param habilitado Filtro de estado
     * @return Producto seleccionado, o null si no hay resultados
     * @see #buscarProductos(String, int)
     */
    public Producto seleccionarProducto(String texto, int habilitado) {
        List<Producto> productos = buscarProductos(texto, habilitado);
        if (productos.isEmpty()) return null;
        if (productos.size() == 1) return productos.get(0);

        String[] opciones = new String[productos.size()];
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            opciones[i] = p.getId() + " - " + p.getDescripcion() + " - $" + p.getPrecio() + " - Stock: " + p.getStock();
        }
        String seleccion = (String) JOptionPane.showInputDialog(null,
                "Seleccione un producto:", "Multiples productos encontrados",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (seleccion == null) return null;
        int id = Integer.parseInt(seleccion.split(" - ")[0]);
        for (Producto p : productos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    /**
     * Inserta un nuevo producto. Requiere descripcion y proveedor.
     *
     * @param descripcion  Descripcion del producto
     * @param precio       Precio unitario
     * @param stock        Stock inicial
     * @param idProveedor  ID del proveedor (debe ser > 0)
     * @return ID generado, o -1 si falla validacion
     * @throws RuntimeException si error de SQL
     * @see ValidadorCampos#validarRequeridos(String[][])
     * @see #modificarProducto(int, String, int, int, int)
     */
    public int agregarProducto(String descripcion, int precio, int stock, int idProveedor) {
        try {
            String[][] requeridos = {
                {"Descripcion", descripcion},
                {"Proveedor", idProveedor <= 0 ? "" : String.valueOf(idProveedor)}
            };
            if (!ValidadorCampos.validarRequeridos(requeridos)) return -1;

            c.conectar();

            String sqlInsert;
            PreparedStatement pstInsert;
            if (idProveedor > 0) {
                sqlInsert = "INSERT INTO productos(descripcion, precio, stock, id_proveedor) VALUES(?, ?, ?, ?)";
                pstInsert = c.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                pstInsert.setString(1, descripcion);
                pstInsert.setInt(2, precio);
                pstInsert.setInt(3, stock);
                pstInsert.setInt(4, idProveedor);
            } else {
                sqlInsert = "INSERT INTO productos(descripcion, precio, stock) VALUES(?, ?, ?)";
                pstInsert = c.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                pstInsert.setString(1, descripcion);
                pstInsert.setInt(2, precio);
                pstInsert.setInt(3, stock);
            }
            pstInsert.executeUpdate();

            ResultSet rsGeneratedKeys = pstInsert.getGeneratedKeys();
            int id = -1;
            if (rsGeneratedKeys.next()) {
                id = rsGeneratedKeys.getInt(1);
            }

            JOptionPane.showMessageDialog(null, "Producto agregado exitosamente");
            return id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actualiza un producto existente. Requiere descripcion y proveedor.
     *
     * @param id           ID del producto
     * @param descripcion  Nueva descripcion
     * @param precio       Nuevo precio
     * @param stock        Nuevo stock
     * @param idProveedor  Nuevo ID de proveedor (debe ser > 0)
     * @return true si exito, false si falla validacion
     * @throws RuntimeException si error de SQL
     * @see #agregarProducto(String, int, int, int)
     * @see ValidadorCampos#validarRequeridos(String[][])
     */
    public boolean modificarProducto(int id, String descripcion, int precio, int stock, int idProveedor) {
        try {
            String[][] requeridos = {
                {"Descripcion", descripcion},
                {"Proveedor", idProveedor <= 0 ? "" : String.valueOf(idProveedor)}
            };
            if (!ValidadorCampos.validarRequeridos(requeridos)) return false;

            c.conectar();

            String sqlUpdate;
            PreparedStatement pstUpdate;
            if (idProveedor > 0) {
                sqlUpdate = "UPDATE productos SET descripcion = ?, precio = ?, stock = ?, id_proveedor = ? WHERE id = ?";
                pstUpdate = c.getConnection().prepareStatement(sqlUpdate);
                pstUpdate.setString(1, descripcion);
                pstUpdate.setInt(2, precio);
                pstUpdate.setInt(3, stock);
                pstUpdate.setInt(4, idProveedor);
                pstUpdate.setInt(5, id);
            } else {
                sqlUpdate = "UPDATE productos SET descripcion = ?, precio = ?, stock = ?, id_proveedor = NULL WHERE id = ?";
                pstUpdate = c.getConnection().prepareStatement(sqlUpdate);
                pstUpdate.setString(1, descripcion);
                pstUpdate.setInt(2, precio);
                pstUpdate.setInt(3, stock);
                pstUpdate.setInt(4, id);
            }
            pstUpdate.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto modificado exitosamente");
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invierte el estado de habilitacion de un producto.
     *
     * @param id ID del producto
     * @return true siempre
     * @throws RuntimeException si error de SQL
     */
    public boolean alternarHabilitadoProducto(int id) {
        try {
            c.conectar();
            String sqlUpdateHabilitado = "UPDATE productos SET habilitado = CASE WHEN habilitado = 1 THEN 0 ELSE 1 END WHERE id = ?";
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
     * Construye un objeto {@link Producto} desde la fila actual del ResultSet,
     * incluyendo id_proveedor y nombreProveedor del JOIN.
     *
     * @param rs ResultSet posicionado
     * @return Producto poblado
     * @throws SQLException si error de columnas
     */
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto(
                rs.getInt("id"),
                rs.getString("descripcion"),
                rs.getInt("precio"),
                rs.getInt("stock"),
                rs.getInt("habilitado")
        );
        p.setIdProveedor(rs.getInt("id_proveedor"));
        p.setNombreProveedor(rs.getString("nombreProveedor"));
        return p;
    }
}
