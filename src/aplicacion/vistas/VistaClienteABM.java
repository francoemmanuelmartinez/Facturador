package aplicacion.vistas;

import aplicacion.controladores.ControladorAdmin;
import aplicacion.controladores.ControladorClienteABM;
import aplicacion.modelos.Cliente;
import aplicacion.modelos.Usuario;

import aplicacion.filtros.FiltroNumerico;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VistaClienteABM {
    private JLabel labelBuscarCliente;
    private JTextField tfDni;
    private JComboBox cbxFiltroHabilitado;
    private JButton btnBuscar;
    private JTable tblClientes;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnDeshabilitar;
    private JButton btnVolver;
    private JButton btnMostrarFacturas;
    public JPanel panelClienteABM;
    private VentanaPrincipal ventanaPrincipal;
    private DefaultTableModel mdlClientes = new DefaultTableModel();
    private String[] colsClientes = {"ID", "Nombre", "Apellido", "Dni", "Telefono", "Direccion", "Mail", "Habilitado"};

    public VistaClienteABM(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;

        ((AbstractDocument) tfDni.getDocument()).setDocumentFilter(new FiltroNumerico());

        configurarTabla();

        tblClientes.removeColumn(tblClientes.getColumnModel().getColumn(7));
        //tblClientes.removeColumn(tblClientes.getColumnModel().getColumn(0));

        cbxFiltroHabilitado.addItem("Habilitados");
        cbxFiltroHabilitado.addItem("Deshabilitados");
        cbxFiltroHabilitado.setSelectedIndex(0);

        ControladorClienteABM controlador = new ControladorClienteABM();
        List<Cliente> clientes = controlador.obtenerClientesPorHabilitado(1);
        poblarTabla(clientes);

        cbxFiltroHabilitado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filtro = cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0;
                ControladorClienteABM ctrl = new ControladorClienteABM();
                poblarTabla(ctrl.obtenerClientesPorHabilitado(filtro));

                if (cbxFiltroHabilitado.getSelectedIndex() == 0) {
                    btnDeshabilitar.setText("Deshabilitar");
                } else {
                    btnDeshabilitar.setText("Habilitar");
                }
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filtro = cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0;
                ControladorClienteABM ctrl = new ControladorClienteABM();
                String texto = tfDni.getText().trim();
                if (texto.isEmpty()) {
                    poblarTabla(ctrl.obtenerClientesPorHabilitado(filtro));
                } else {
                    Cliente cliente = ctrl.buscarCliente(texto, filtro);
                    if (cliente != null) {
                        poblarTabla(Collections.singletonList(cliente));
                    }
                }
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, String> valores = VistaFormulario.mostrarDialogo("Nuevo Cliente",
                        new VistaFormulario.Campo("Nombre:"),
                        new VistaFormulario.Campo("Apellido:"),
                        new VistaFormulario.Campo("DNI:"),
                        new VistaFormulario.Campo("Telefono:"),
                        new VistaFormulario.Campo("Direccion:"),
                        new VistaFormulario.Campo("Mail:")
                );
                if (valores != null) {
                    ControladorClienteABM ctrl = new ControladorClienteABM();
                    int id = ctrl.agregarCliente(
                            valores.get("Nombre:"),
                            valores.get("Apellido:"),
                            valores.get("DNI:"),
                            valores.get("Telefono:"),
                            valores.get("Direccion:"),
                            valores.get("Mail:")
                    );
                    if (id > -1) {
                        poblarTabla(ctrl.obtenerClientesPorHabilitado(cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tblClientes.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un cliente de la tabla");
                    return;
                }

                int idCliente = Integer.parseInt(mdlClientes.getValueAt(fila, 0).toString());

                Map<String, String> valores = VistaFormulario.mostrarDialogo("Modificar Cliente",
                        new VistaFormulario.Campo("Nombre:", mdlClientes.getValueAt(fila, 1).toString()),
                        new VistaFormulario.Campo("Apellido:", mdlClientes.getValueAt(fila, 2).toString()),
                        new VistaFormulario.Campo("DNI:", mdlClientes.getValueAt(fila, 3).toString()),
                        new VistaFormulario.Campo("Telefono:", mdlClientes.getValueAt(fila, 4).toString()),
                        new VistaFormulario.Campo("Direccion:", mdlClientes.getValueAt(fila, 5).toString()),
                        new VistaFormulario.Campo("Mail:", mdlClientes.getValueAt(fila, 6).toString())
                );
                if (valores != null) {
                    ControladorClienteABM ctrl = new ControladorClienteABM();
                    if (ctrl.modificarCliente(
                            idCliente,
                            valores.get("Nombre:"),
                            valores.get("Apellido:"),
                            valores.get("DNI:"),
                            valores.get("Telefono:"),
                            valores.get("Direccion:"),
                            valores.get("Mail:")
                    )) {
                        poblarTabla(ctrl.obtenerClientesPorHabilitado(cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });

        btnDeshabilitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tblClientes.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un cliente de la tabla");
                    return;
                }

                int idCliente = Integer.parseInt(mdlClientes.getValueAt(fila, 0).toString());
                int habilitadoActual = Integer.parseInt(mdlClientes.getValueAt(fila, 7).toString());
                String nuevoEstado = habilitadoActual == 1 ? "deshabilitar" : "habilitar";

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Esta seguro de " + nuevoEstado + " al cliente con ID " + idCliente + "?",
                        nuevoEstado.equals("deshabilitar") ? "Deshabilitar Cliente" : "Habilitar Cliente",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    ControladorClienteABM ctrl = new ControladorClienteABM();
                    if (ctrl.alternarHabilitadoCliente(idCliente)) {
                        poblarTabla(ctrl.obtenerClientesPorHabilitado(cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorAdmin controladorAdmin = new ControladorAdmin(usuario, ventanaPrincipal);
            }
        });
        btnMostrarFacturas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tblClientes.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un cliente de la tabla");
                    return;
                }

                int idCliente = Integer.parseInt(mdlClientes.getValueAt(fila, 0).toString());
                String nombre = mdlClientes.getValueAt(fila, 1).toString();
                String apellido = mdlClientes.getValueAt(fila, 2).toString();

                VistaFactura vistaFactura = new VistaFactura(usuario, ventanaPrincipal, idCliente, nombre, apellido);
                ventanaPrincipal.mostrarVista(vistaFactura.panelFactura);
            }
        });
    }

    public void configurarTabla() {
        mdlClientes.setColumnIdentifiers(colsClientes);
        tblClientes.setModel(mdlClientes);
    }

    public void poblarTabla(List<Cliente> clientes) {
        mdlClientes.setRowCount(0);

        for (Cliente c : clientes) {
            mdlClientes.addRow(new Object[]{
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
