package aplicacion.vistas;

import aplicacion.controladores.ControladorModificarUsuario;
import aplicacion.modelos.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaModificarUsuarios {
    private JLabel labelUsuario;
    private JButton botonBuscar;
    private JTable tablaUsuarios;
    public JPanel panelModificarUsuarios;
    private JTextField dniTF;
    DefaultTableModel modeloTabla = new DefaultTableModel();
    String[] columnas = {"Nombre", "Apellido", "Dni", "Telefono", "Direccion", "Mail", "Rol", "Password", "Habilitado"};

    public VistaModificarUsuarios(VentanaPrincipal ventanaPrincipal) {

        setModeloTabla();
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorModificarUsuario controladorModificarUsuario = new ControladorModificarUsuario();
                Usuario usuario = controladorModificarUsuario.buscarUsuario(dniTF.getText().trim());
                poblarTabla(usuario);
            }
        });
    }

    public void setModeloTabla() {
        modeloTabla.setColumnIdentifiers(columnas);
        tablaUsuarios.setModel(modeloTabla);
    }

    public void poblarTabla(Usuario usuario) {

        modeloTabla.setRowCount(0);

        if (usuario != null) {
            modeloTabla.addRow(new Object[]{
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getDni(),
                    usuario.getTelefono(),
                    usuario.getDireccion(),
                    usuario.getMail(),
                    usuario.getRol(),
                    usuario.getPassword(),
                    usuario.getHabilitado()
            });
        }
    }
}



