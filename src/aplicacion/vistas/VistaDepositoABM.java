package aplicacion.vistas;

import aplicacion.controladores.ControladorAdmin;
import aplicacion.controladores.ControladorDepositoABM;
import aplicacion.controladores.ControladorProveedorABM;
import aplicacion.modelos.Producto;
import aplicacion.modelos.Proveedor;
import aplicacion.modelos.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class VistaDepositoABM {
    private JLabel labelBuscarProducto;
    private JTextField tfIdProducto;
    private JComboBox cbxFiltroHabilitado;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnDeshabilitar;
    private JButton btnVolver;
    public JPanel panelDepositoABM;
    private VentanaPrincipal ventanaPrincipal;
    private DefaultTableModel mdlProductos = new DefaultTableModel();
    private String[] colsProductos = {"ID", "Descripcion", "Precio", "Stock", "Nombre Proveedor", "Habilitado"};

    public VistaDepositoABM(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;

        configurarTabla();

        tblProductos.removeColumn(tblProductos.getColumnModel().getColumn(5));

        cbxFiltroHabilitado.addItem("Habilitados");
        cbxFiltroHabilitado.addItem("Deshabilitados");
        cbxFiltroHabilitado.setSelectedIndex(0);

        ControladorDepositoABM controlador = new ControladorDepositoABM();
        List<Producto> productos = controlador.obtenerProductosPorHabilitado(1);
        poblarTabla(productos);

        cbxFiltroHabilitado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filtro = cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0;
                ControladorDepositoABM ctrl = new ControladorDepositoABM();
                poblarTabla(ctrl.obtenerProductosPorHabilitado(filtro));

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
                ControladorDepositoABM ctrl = new ControladorDepositoABM();
                String texto = tfIdProducto.getText().trim();
                if (texto.isEmpty()) {
                    poblarTabla(ctrl.obtenerProductosPorHabilitado(filtro));
                } else {
                    List<Producto> resultados = ctrl.buscarProductos(texto, filtro);
                    if (!resultados.isEmpty()) {
                        poblarTabla(resultados);
                    }
                }
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorProveedorABM ctrlProv = new ControladorProveedorABM();
                List<Proveedor> proveedores = ctrlProv.obtenerProveedoresPorHabilitado(1);
                String[] opcionesProveedores = new String[proveedores.size()];
                for (int i = 0; i < proveedores.size(); i++) {
                    opcionesProveedores[i] = proveedores.get(i).getId() + " - " + proveedores.get(i).getNombre();
                }

                Map<String, String> valores = VistaFormulario.mostrarDialogo("Nuevo Producto",
                        new VistaFormulario.Campo("Descripcion:"),
                        new VistaFormulario.Campo("Precio:"),
                        new VistaFormulario.Campo("Stock:"),
                        new VistaFormulario.Campo("Proveedor:", opcionesProveedores)
                );
                if (valores != null) {
                    ControladorDepositoABM ctrl = new ControladorDepositoABM();
                    try {
                        int precio = Integer.parseInt(valores.get("Precio:"));
                        int stock = Integer.parseInt(valores.get("Stock:"));
                        String selProv = valores.get("Proveedor:");
                        int idProveedor = Integer.parseInt(selProv.split(" - ")[0]);
                        if (ctrl.agregarProducto(
                                valores.get("Descripcion:"),
                                precio,
                                stock,
                                idProveedor
                        ) > -1) {
                            poblarTabla(ctrl.obtenerProductosPorHabilitado(cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Precio y Stock deben ser numeros enteros");
                    }
                }
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tblProductos.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla");
                    return;
                }

                int idProducto = Integer.parseInt(mdlProductos.getValueAt(fila, 0).toString());

                ControladorProveedorABM ctrlProv = new ControladorProveedorABM();
                List<Proveedor> proveedores = ctrlProv.obtenerProveedoresPorHabilitado(1);
                String[] opcionesProveedores = new String[proveedores.size()];
                String valorInicialProveedor = null;
                String nombreActualProveedor = mdlProductos.getValueAt(fila, 4).toString();
                for (int i = 0; i < proveedores.size(); i++) {
                    String item = proveedores.get(i).getId() + " - " + proveedores.get(i).getNombre();
                    opcionesProveedores[i] = item;
                    if (proveedores.get(i).getNombre().equals(nombreActualProveedor)) {
                        valorInicialProveedor = item;
                    }
                }

                Map<String, String> valores = VistaFormulario.mostrarDialogo("Modificar Producto",
                        new VistaFormulario.Campo("Descripcion:", mdlProductos.getValueAt(fila, 1).toString()),
                        new VistaFormulario.Campo("Precio:", mdlProductos.getValueAt(fila, 2).toString()),
                        new VistaFormulario.Campo("Stock:", mdlProductos.getValueAt(fila, 3).toString()),
                        new VistaFormulario.Campo("Proveedor:", opcionesProveedores, valorInicialProveedor)
                );
                if (valores != null) {
                    ControladorDepositoABM ctrl = new ControladorDepositoABM();
                    try {
                        int precio = Integer.parseInt(valores.get("Precio:"));
                        int stock = Integer.parseInt(valores.get("Stock:"));
                        String selProv = valores.get("Proveedor:");
                        int idProveedor = Integer.parseInt(selProv.split(" - ")[0]);
                        if (ctrl.modificarProducto(
                                idProducto,
                                valores.get("Descripcion:"),
                                precio,
                                stock,
                                idProveedor
                        )) {
                            poblarTabla(ctrl.obtenerProductosPorHabilitado(cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Precio y Stock deben ser numeros enteros");
                    }
                }
            }
        });

        btnDeshabilitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tblProductos.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla");
                    return;
                }

                int idProducto = Integer.parseInt(mdlProductos.getValueAt(fila, 0).toString());
                int habilitadoActual = Integer.parseInt(mdlProductos.getValueAt(fila, 5).toString());
                String nuevoEstado = habilitadoActual == 1 ? "deshabilitar" : "habilitar";

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Esta seguro de " + nuevoEstado + " el producto \"" + mdlProductos.getValueAt(fila, 1) + "\"?",
                        nuevoEstado.equals("deshabilitar") ? "Deshabilitar Producto" : "Habilitar Producto",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    ControladorDepositoABM ctrl = new ControladorDepositoABM();
                    if (ctrl.alternarHabilitadoProducto(idProducto)) {
                        poblarTabla(ctrl.obtenerProductosPorHabilitado(cbxFiltroHabilitado.getSelectedIndex() == 0 ? 1 : 0));
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
        mdlProductos.setColumnIdentifiers(colsProductos);
        tblProductos.setModel(mdlProductos);
    }

    public void poblarTabla(List<Producto> productos) {
        mdlProductos.setRowCount(0);

        for (Producto p : productos) {
            mdlProductos.addRow(new Object[]{
                    p.getId(),
                    p.getDescripcion(),
                    p.getPrecio(),
                    p.getStock(),
                    p.getNombreProveedor(),
                    p.getHabilitado()
            });
        }
    }
}
