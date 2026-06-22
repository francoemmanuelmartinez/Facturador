package aplicacion.controladores;

import aplicacion.modelos.Cliente;
import aplicacion.modelos.Usuario;
import aplicacion.servicios.Conexion;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaClienteABM;

import aplicacion.filtros.ValidadorCampos;
import aplicacion.filtros.ValidadorMail;

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
            String sqlSelect = "SELECT id, nombre, apellido, dni, telefono, direccion, mail, habilitado FROM clientes WHERE habilitado = ?";
            PreparedStatement stmtClientes = c.getConnection().prepareStatement(sqlSelect);
            stmtClientes.setInt(1, habilitado);
            ResultSet rsClientes = stmtClientes.executeQuery();
            while (rsClientes.next()) {
                clientes.add(mapearCliente(rsClientes));
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
            String sqlSelect = "SELECT id, nombre, apellido, dni, telefono, direccion, mail, habilitado FROM clientes WHERE dni = ? AND habilitado = ?";
            PreparedStatement stmtCliente = c.getConnection().prepareStatement(sqlSelect);
            stmtCliente.setString(1, dni);
            stmtCliente.setInt(2, habilitado);
            ResultSet rsCliente = stmtCliente.executeQuery();
            if (rsCliente.next()) {
                cliente = mapearCliente(rsCliente);
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
            String[][] requeridos = {
                {"Nombre", nombre},
                {"Apellido", apellido},
                {"DNI", dni},
                {"Mail", mail}
            };
            if (!ValidadorCampos.validarRequeridos(requeridos)) return -1;

            if (!ValidadorMail.esValido(mail)) {
                JOptionPane.showMessageDialog(null, "El formato del mail no es valido");
                return -1;
            }
            c.conectar();

            String sqlSelect = "SELECT mail FROM clientes WHERE mail = ?";
            PreparedStatement stmtSelectMail = c.getConnection().prepareStatement(sqlSelect);
            stmtSelectMail.setString(1, mail);
            ResultSet rsMailExistente = stmtSelectMail.executeQuery();
            if (rsMailExistente.next()) {
                JOptionPane.showMessageDialog(null, "Este mail ya ha sido registrado");
                return -1;
            }

            String sqlInsert = "INSERT INTO clientes(nombre, apellido, dni, telefono, direccion, mail) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement pstInsert = c.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            pstInsert.setString(1, nombre);
            pstInsert.setString(2, apellido);
            pstInsert.setString(3, dni);
            pstInsert.setString(4, telefono);
            pstInsert.setString(5, direccion);
            pstInsert.setString(6, mail);
            pstInsert.executeUpdate();

            ResultSet rsGeneratedKeys = pstInsert.getGeneratedKeys();
            int id = -1;
            if (rsGeneratedKeys.next()) {
                id = rsGeneratedKeys.getInt(1);
            }

            JOptionPane.showMessageDialog(null, "Cliente agregado exitosamente");
            return id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean modificarCliente(int id, String nombre, String apellido, String dni, String telefono, String direccion, String mail) {
        try {
            String[][] requeridos = {
                {"Nombre", nombre},
                {"Apellido", apellido},
                {"DNI", dni},
                {"Mail", mail}
            };
            if (!ValidadorCampos.validarRequeridos(requeridos)) return false;

            if (!ValidadorMail.esValido(mail)) {
                JOptionPane.showMessageDialog(null, "El formato del mail no es valido");
                return false;
            }
            c.conectar();

            String sqlSelect = "SELECT mail FROM clientes WHERE mail = ? AND id != ?";
            PreparedStatement stmtSelectMail = c.getConnection().prepareStatement(sqlSelect);
            stmtSelectMail.setString(1, mail);
            stmtSelectMail.setInt(2, id);
            ResultSet rsMailExistente = stmtSelectMail.executeQuery();
            if (rsMailExistente.next()) {
                JOptionPane.showMessageDialog(null, "Ese mail ya pertenece a otro cliente");
                return false;
            }

            String sqlUpdate = "UPDATE clientes SET nombre = ?, apellido = ?, dni = ?, telefono = ?, direccion = ?, mail = ? WHERE id = ?";
            PreparedStatement pstUpdate = c.getConnection().prepareStatement(sqlUpdate);
            pstUpdate.setString(1, nombre);
            pstUpdate.setString(2, apellido);
            pstUpdate.setString(3, dni);
            pstUpdate.setString(4, telefono);
            pstUpdate.setString(5, direccion);
            pstUpdate.setString(6, mail);
            pstUpdate.setInt(7, id);
            pstUpdate.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente modificado exitosamente");
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean alternarHabilitadoCliente(int id) {
        try {
            c.conectar();
            String sqlUpdateHabilitado = "UPDATE clientes SET habilitado = CASE WHEN habilitado = 1 THEN 0 ELSE 1 END WHERE id = ?";
            PreparedStatement pstUpdate = c.getConnection().prepareStatement(sqlUpdateHabilitado);
            pstUpdate.setInt(1, id);
            pstUpdate.executeUpdate();
            JOptionPane.showMessageDialog(null, "Estado cambiado exitosamente");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
