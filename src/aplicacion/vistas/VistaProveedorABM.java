package aplicacion.vistas;

import aplicacion.controladores.ControladorAdmin;
import aplicacion.controladores.ControladorProveedorABM;
import aplicacion.modelos.Proveedor;
import aplicacion.modelos.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VistaProveedorABM {
    public JPanel panelProveedor;
    private JTable tableProveedores;
    private JComboBox comboBoxHabilitado;
    private JButton buscarButton;
    private JTextField IDProveedorTextField;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton deshabilitarButton;
    private JButton volverButton;
    private JLabel labelIdProveedor;
    private VentanaPrincipal ventana;
    DefaultTableModel modeloTabla = new DefaultTableModel();
    String[] columnas = {"ID", "Nombre", "Telefono", "Direccion", "Mail", "Habilitado"};

    public VistaProveedorABM(Usuario usuario, VentanaPrincipal ventanaPrincipal) {

        this.ventana = ventanaPrincipal;
        setModeloTabla();
        comboBoxHabilitado.addItem("Habilitados");
        comboBoxHabilitado.addItem("Deshabilitados");
        comboBoxHabilitado.setSelectedIndex(0);

        tableProveedores.removeColumn(tableProveedores.getColumnModel().getColumn(5));
        //tableProveedores.removeColumn(tableProveedores.getColumnModel().getColumn(0));

        ControladorProveedorABM controlador = new ControladorProveedorABM();
        List<Proveedor> proveedores = controlador.obtenerProveedoresPorHabilitado(1);
        poblarTabla(proveedores);

        comboBoxHabilitado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filtro = comboBoxHabilitado.getSelectedIndex() == 0 ? 1 : 0;

                ControladorProveedorABM ctrl = new ControladorProveedorABM();

                poblarTabla(ctrl.obtenerProveedoresPorHabilitado(filtro));

                if(comboBoxHabilitado.getSelectedIndex()==0)

                {
                    deshabilitarButton.setText("Deshabilitar");
                } else

                {
                    deshabilitarButton.setText("Habilitar");
                }
        }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filtro = comboBoxHabilitado.getSelectedIndex() == 0 ? 1 : 0;
                ControladorProveedorABM ctrl = new ControladorProveedorABM();
                String texto = IDProveedorTextField.getText().trim();
                if (texto.isEmpty()) {
                    poblarTabla(ctrl.obtenerProveedoresPorHabilitado(filtro));
                } else {
                    Proveedor proveedor = ctrl.buscarProveedores(texto, filtro);
                    if (proveedor != null) {
                        poblarTabla(Collections.singletonList(proveedor));
                    }
                }
            }
        });
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, String> valores = VistaFormulario.mostrarDialogo("Nuevo Proveedor",
                        new VistaFormulario.Campo("Nombre:"),
                        new VistaFormulario.Campo("Telefono:"),
                        new VistaFormulario.Campo("Direccion:"),
                        new VistaFormulario.Campo("Mail:")
                );
                if (valores != null) {
                    ControladorProveedorABM ctrl = new ControladorProveedorABM();
                    int id = ctrl.agregarProveedor(
                            valores.get("Nombre:"),
                            valores.get("Telefono:"),
                            valores.get("Direccion:"),
                            valores.get("Mail:")
                    );
                    if (id > -1) {
                        poblarTabla(ctrl.obtenerProveedoresPorHabilitado(comboBoxHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tableProveedores.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un proveedor de la tabla");
                    return;
                }

                int idProveedor = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());

                Map<String, String> valores = VistaFormulario.mostrarDialogo("Modificar Proveedor",
                        new VistaFormulario.Campo("Nombre:", modeloTabla.getValueAt(fila, 1).toString()),
                        new VistaFormulario.Campo("Telefono:", modeloTabla.getValueAt(fila, 2).toString()),
                        new VistaFormulario.Campo("Direccion:", modeloTabla.getValueAt(fila, 3).toString()),
                        new VistaFormulario.Campo("Mail:", modeloTabla.getValueAt(fila, 4).toString())
                );
                if (valores != null) {
                    ControladorProveedorABM ctrl = new ControladorProveedorABM();
                    if (ctrl.modificarProveedor(
                            idProveedor,
                            valores.get("Nombre:"),
                            valores.get("Telefono:"),
                            valores.get("Direccion:"),
                            valores.get("Mail:")
                    )) {
                        poblarTabla(ctrl.obtenerProveedoresPorHabilitado(comboBoxHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });
        deshabilitarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tableProveedores.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un proveedor de la tabla");
                    return;
                }

                int idProveedor = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                int habilitadoActual = Integer.parseInt(modeloTabla.getValueAt(fila, 5).toString());
                String nuevoEstado = habilitadoActual == 1 ? "deshabilitar" : "habilitar";

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Esta seguro de " + nuevoEstado + " al proveedor con ID " + idProveedor + "?",
                        nuevoEstado.equals("deshabilitar") ? "Deshabilitar Proveedor" : "Habilitar Proveedor",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    ControladorProveedorABM ctrl = new ControladorProveedorABM();
                    if (ctrl.toggleHabilitadoProveedor(idProveedor)) {
                        poblarTabla(ctrl.obtenerProveedoresPorHabilitado(comboBoxHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorAdmin controladorAdmin = new ControladorAdmin(usuario, ventana);
            }
        });

    }
    public void setModeloTabla() {

        modeloTabla.setColumnIdentifiers(columnas);
        tableProveedores.setModel(modeloTabla);
    }
    public void poblarTabla(List<Proveedor> proveedores) {
        modeloTabla.setRowCount(0);

        for (Proveedor p : proveedores) {
            modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getTelefono(),
                    p.getDireccion(),
                    p.getMail(),
                    p.getHabilitado()
            });
        }
    }
}
