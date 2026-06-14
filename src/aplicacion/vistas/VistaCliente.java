package aplicacion.vistas;

import aplicacion.controladores.ControladorCliente;
import aplicacion.controladores.ControladorAdmin;
import aplicacion.modelos.Cliente;
import aplicacion.modelos.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class VistaCliente {
    private JLabel labelCliente;
    private JButton botonBuscar;
    private JTable tablaClientes;
    public JPanel panelClientes;
    private JTextField dniTF;
    private JButton eliminarButton;
    private JButton modificarButton;
    private JButton agregarButton;
    private JButton botonVolver;
    private JComboBox comboBoxFiltroHabilitado;
    DefaultTableModel modeloTabla = new DefaultTableModel();
    String[] columnas = {"ID", "Nombre", "Apellido", "Dni", "Telefono", "Direccion", "Mail", "Habilitado"};

    public VistaCliente(Usuario usuario, VentanaPrincipal ventanaPrincipal) {

        setModeloTabla();

        comboBoxFiltroHabilitado.addItem("Habilitados");
        comboBoxFiltroHabilitado.addItem("Deshabilitados");
        comboBoxFiltroHabilitado.setSelectedIndex(0);

        ControladorCliente controlador = new ControladorCliente();
        List<Cliente> clientes = controlador.obtenerClientesPorHabilitado(1);
        poblarTabla(clientes);

        comboBoxFiltroHabilitado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filtro = comboBoxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0;
                ControladorCliente ctrl = new ControladorCliente();
                poblarTabla(ctrl.obtenerClientesPorHabilitado(filtro));

                if (comboBoxFiltroHabilitado.getSelectedIndex() == 0) {
                    eliminarButton.setText("Deshabilitar");
                } else {
                    eliminarButton.setText("Habilitar");
                }
            }
        });

        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorCliente ctrl = new ControladorCliente();
                int filtro = comboBoxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0;
                Cliente cliente = ctrl.buscarCliente(dniTF.getText().trim(), filtro);
                if (cliente != null) {
                    poblarTabla(Collections.singletonList(cliente));
                }
            }
        });

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nombre = new JTextField();
                JTextField apellido = new JTextField();
                JTextField dni = new JTextField();
                JTextField telefono = new JTextField();
                JTextField direccion = new JTextField();
                JTextField mail = new JTextField();

                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(2, 2, 2, 2);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                Object[][] campos = {
                        {"Nombre:", nombre},
                        {"Apellido:", apellido},
                        {"DNI:", dni},
                        {"Teléfono:", telefono},
                        {"Dirección:", direccion},
                        {"Mail:", mail}
                };

                for (int i = 0; i < campos.length; i++) {
                    gbc.gridx = 0; gbc.gridy = i;
                    gbc.gridwidth = 1;
                    gbc.weightx = 0;
                    panel.add(new JLabel((String) campos[i][0]), gbc);
                    gbc.gridx = 1;
                    gbc.weightx = 1;
                    panel.add((Component) campos[i][1], gbc);
                }

                int option = JOptionPane.showConfirmDialog(null, panel, "Nuevo Cliente", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    ControladorCliente ctrl = new ControladorCliente();
                    if (ctrl.agregarCliente(
                            nombre.getText().trim(),
                            apellido.getText().trim(),
                            dni.getText().trim(),
                            telefono.getText().trim(),
                            direccion.getText().trim(),
                            mail.getText().trim()
                    )) {
                        poblarTabla(ctrl.obtenerClientesPorHabilitado(comboBoxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaClientes.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un cliente de la tabla");
                    return;
                }

                int idCliente = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());

                JTextField nombre = new JTextField(modeloTabla.getValueAt(fila, 1).toString());
                JTextField apellido = new JTextField(modeloTabla.getValueAt(fila, 2).toString());
                JTextField dni = new JTextField(modeloTabla.getValueAt(fila, 3).toString());
                JTextField telefono = new JTextField(modeloTabla.getValueAt(fila, 4).toString());
                JTextField direccion = new JTextField(modeloTabla.getValueAt(fila, 5).toString());
                JTextField mail = new JTextField(modeloTabla.getValueAt(fila, 6).toString());

                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(2, 2, 2, 2);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                Object[][] campos = {
                        {"Nombre:", nombre},
                        {"Apellido:", apellido},
                        {"DNI:", dni},
                        {"Teléfono:", telefono},
                        {"Dirección:", direccion},
                        {"Mail:", mail}
                };

                for (int i = 0; i < campos.length; i++) {
                    gbc.gridx = 0; gbc.gridy = i;
                    gbc.gridwidth = 1;
                    gbc.weightx = 0;
                    panel.add(new JLabel((String) campos[i][0]), gbc);
                    gbc.gridx = 1;
                    gbc.weightx = 1;
                    panel.add((Component) campos[i][1], gbc);
                }

                int option = JOptionPane.showConfirmDialog(null, panel, "Modificar Cliente", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    ControladorCliente ctrl = new ControladorCliente();
                    if (ctrl.modificarCliente(
                            idCliente,
                            nombre.getText().trim(),
                            apellido.getText().trim(),
                            dni.getText().trim(),
                            telefono.getText().trim(),
                            direccion.getText().trim(),
                            mail.getText().trim()
                    )) {
                        poblarTabla(ctrl.obtenerClientesPorHabilitado(comboBoxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tablaClientes.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un cliente de la tabla");
                    return;
                }

                int idCliente = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                int habilitadoActual = Integer.parseInt(modeloTabla.getValueAt(fila, 7).toString());
                String nuevoEstado = habilitadoActual == 1 ? "deshabilitado" : "habilitado";

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de " + nuevoEstado + " al cliente con ID " + idCliente + "?",
                        nuevoEstado.equals("deshabilitado") ? "Deshabilitar Cliente" : "Habilitar Cliente",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    ControladorCliente ctrl = new ControladorCliente();
                    if (ctrl.toggleHabilitadoCliente(idCliente)) {
                        poblarTabla(ctrl.obtenerClientesPorHabilitado(comboBoxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });

        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorAdmin controladorAdmin = new ControladorAdmin(usuario, ventanaPrincipal);
            }
        });
    }

    public void setModeloTabla() {
        modeloTabla.setColumnIdentifiers(columnas);
        tablaClientes.setModel(modeloTabla);
    }

    public void poblarTabla(List<Cliente> clientes) {
        modeloTabla.setRowCount(0);

        for (Cliente c : clientes) {
            modeloTabla.addRow(new Object[]{
                    c.getId(),
                    c.getNombre(),
                    c.getApellido(),
                    c.getDni(),
                    c.getTelefono(),
                    c.getDireccion(),
                    c.getMail(),
                    c.getHabilitado()
            });
        }
    }
}
