package aplicacion.controladores;

import aplicacion.modelos.Cliente;
import aplicacion.modelos.Usuario;
import aplicacion.servicios.Conexion;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaClienteABM;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ControladorClienteABM {

    private Conexion c = new Conexion();

    public ControladorClienteABM() {}

    public ControladorClienteABM(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        VistaClienteABM vistaClienteABM = new VistaClienteABM(usuario, ventanaPrincipal);
        ventanaPrincipal.mostrarVista(vistaClienteABM.panelClienteABM);
    }

    public List<Cliente> obtenerClientesPorHabilitado(int habilitado) {
        List<Cliente> clientes = new ArrayList<>();
        try {
            c.conectar();
            String sql = "SELECT id, nombre, apellido, dni, telefono, direccion, mail, habilitado FROM clientes WHERE habilitado = ?";
            PreparedStatement stmt = c.getConnection().prepareStatement(sql);
            stmt.setInt(1, habilitado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }

    public Cliente buscarCliente(String dni, int habilitado) {
        Cliente cliente = null;
        try {
            c.conectar();
            String sql = "SELECT id, nombre, apellido, dni, telefono, direccion, mail, habilitado FROM clientes WHERE dni = ? AND habilitado = ?";
            PreparedStatement stmt = c.getConnection().prepareStatement(sql);
            stmt.setString(1, dni);
            stmt.setInt(2, habilitado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cliente = mapearCliente(rs);
            } else {
                JOptionPane.showMessageDialog(null, "No existe el cliente con ese dni");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }

    public int agregarCliente(String nombre, String apellido, String dni, String telefono, String direccion, String mail) {
        try {
            c.conectar();

            String sqlSelect = "SELECT mail FROM clientes WHERE mail = ?";
            PreparedStatement selectFrom = c.getConnection().prepareStatement(sqlSelect);
            selectFrom.setString(1, mail);
            ResultSet mailDatabase = selectFrom.executeQuery();
            if (mailDatabase.next()) {
                JOptionPane.showMessageDialog(null, "Este mail ya ha sido registrado");
                return -1;
            }

            String sqlInsert = "INSERT INTO clientes(nombre, apellido, dni, telefono, direccion, mail) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = c.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, nombre);
            pst.setString(2, apellido);
            pst.setString(3, dni);
            pst.setString(4, telefono);
            pst.setString(5, direccion);
            pst.setString(6, mail);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            int id = -1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            JOptionPane.showMessageDialog(null, "Cliente agregado exitosamente");
            return id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean modificarCliente(int id, String nombre, String apellido, String dni, String telefono, String direccion, String mail) {
        try {
            c.conectar();

            String sqlSelect = "SELECT mail FROM clientes WHERE mail = ? AND id != ?";
            PreparedStatement selectFrom = c.getConnection().prepareStatement(sqlSelect);
            selectFrom.setString(1, mail);
            selectFrom.setInt(2, id);
            ResultSet mailDatabase = selectFrom.executeQuery();
            if (mailDatabase.next()) {
                JOptionPane.showMessageDialog(null, "Ese mail ya pertenece a otro cliente");
                return false;
            }

            String sqlUpdate = "UPDATE clientes SET nombre = ?, apellido = ?, dni = ?, telefono = ?, direccion = ?, mail = ? WHERE id = ?";
            PreparedStatement pst = c.getConnection().prepareStatement(sqlUpdate);
            pst.setString(1, nombre);
            pst.setString(2, apellido);
            pst.setString(3, dni);
            pst.setString(4, telefono);
            pst.setString(5, direccion);
            pst.setString(6, mail);
            pst.setInt(7, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente modificado exitosamente");
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean alternarHabilitadoCliente(int id) {
        try {
            c.conectar();
            String sqlToggle = "UPDATE clientes SET habilitado = CASE WHEN habilitado = 1 THEN 0 ELSE 1 END WHERE id = ?";
            PreparedStatement pst = c.getConnection().prepareStatement(sqlToggle);
            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Estado cambiado exitosamente");
            return true;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNombre(rs.getString("nombre"));
        c.setApellido(rs.getString("apellido"));
        c.setDni(rs.getString("dni"));
        c.setTelefono(rs.getString("telefono"));
        c.setDireccion(rs.getString("direccion"));
        c.setMail(rs.getString("mail"));
        c.setHabilitado(rs.getInt("habilitado"));
        return c;
    }
}
