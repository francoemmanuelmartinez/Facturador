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
    private JTable tblProveedores;
    private JComboBox cbxFiltroHabilitado;
    private JButton btnBuscar;
    private JTextField tfIdProveedor;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnDeshabilitar;
    private JButton btnVolver;
    private JLabel labelIdProveedor;
    private VentanaPrincipal ventanaPrincipal;
    private DefaultTableModel mdlProveedores = new DefaultTableModel();
    private String[] colsProveedores = {"ID", "Nombre", "Telefono", "Direccion", "Mail", "Habilitado"};

    public VistaProveedorABM(Usuario usuario, VentanaPrincipal ventanaPrincipal) {

        this.ventanaPrincipal = ventanaPrincipal;
        configurarTabla();
        cbxFiltroHabilitado.addItem("Habilitados");
        cbxFiltroHabilitado.addItem("Deshabilitados");
        cbxFiltroHabilitado.setSelectedIndex(0);

        tblProveedores.removeColumn(tblProveedores.getColumnModel().getColumn(5));
        //tblProveedores.removeColumn(tblProveedores.getColumnModel().getColumn(0));

        ControladorProveedorABM controlador = new ControladorProveedorABM();
        List<Proveedor> proveedores = controlador.obtenerProveedoresPorHabilitado(1);
        poblarTabla(proveedores);

        cbxFiltroHabilitado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filtro = cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0;

                ControladorProveedorABM ctrl = new ControladorProveedorABM();

                poblarTabla(ctrl.obtenerProveedoresPorHabilitado(filtro));

                if(cbxFiltroHabilitado.getSelectedIndex()==0)

                {
                    btnDeshabilitar.setText("Deshabilitar");
                } else

                {
                    btnDeshabilitar.setText("Habilitar");
                }
        }
        });
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filtro = cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0;
                ControladorProveedorABM ctrl = new ControladorProveedorABM();
                String texto = tfIdProveedor.getText().trim();
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
        btnAgregar.addActionListener(new ActionListener() {
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
                        poblarTabla(ctrl.obtenerProveedoresPorHabilitado(cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tblProveedores.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un proveedor de la tabla");
                    return;
                }

                int idProveedor = Integer.parseInt(mdlProveedores.getValueAt(fila, 0).toString());

                Map<String, String> valores = VistaFormulario.mostrarDialogo("Modificar Proveedor",
                        new VistaFormulario.Campo("Nombre:", mdlProveedores.getValueAt(fila, 1).toString()),
                        new VistaFormulario.Campo("Telefono:", mdlProveedores.getValueAt(fila, 2).toString()),
                        new VistaFormulario.Campo("Direccion:", mdlProveedores.getValueAt(fila, 3).toString()),
                        new VistaFormulario.Campo("Mail:", mdlProveedores.getValueAt(fila, 4).toString())
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
                        poblarTabla(ctrl.obtenerProveedoresPorHabilitado(cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });
        btnDeshabilitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tblProveedores.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un proveedor de la tabla");
                    return;
                }

                int idProveedor = Integer.parseInt(mdlProveedores.getValueAt(fila, 0).toString());
                int habilitadoActual = Integer.parseInt(mdlProveedores.getValueAt(fila, 5).toString());
                String nuevoEstado = habilitadoActual == 1 ? "deshabilitar" : "habilitar";

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Esta seguro de " + nuevoEstado + " al proveedor con ID " + idProveedor + "?",
                        nuevoEstado.equals("deshabilitar") ? "Deshabilitar Proveedor" : "Habilitar Proveedor",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    ControladorProveedorABM ctrl = new ControladorProveedorABM();
                    if (ctrl.toggleHabilitadoProveedor(idProveedor)) {
                        poblarTabla(ctrl.obtenerProveedoresPorHabilitado(cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
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

    }
    public void configurarTabla() {

        mdlProveedores.setColumnIdentifiers(colsProveedores);
        tblProveedores.setModel(mdlProveedores);
    }
    public void poblarTabla(List<Proveedor> proveedores) {
        mdlProveedores.setRowCount(0);

        for (Proveedor p : proveedores) {
            mdlProveedores.addRow(new Object[]{
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
