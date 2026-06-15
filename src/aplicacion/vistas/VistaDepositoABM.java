package aplicacion.vistas;

import aplicacion.controladores.ControladorAdmin;
import aplicacion.controladores.ControladorDepositoABM;
import aplicacion.modelos.Producto;
import aplicacion.modelos.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class VistaDepositoABM {
    private JTable tableProductos;
    private JLabel labelBuscar;
    private JComboBox comboBoxFlitro;
    private JButton buscarButton;
    private JButton agregarButton;
    private JTextField textFieldID;
    private JButton modificarButton;
    private JButton volverButton;
    private JButton deshabilitarButton;
    public JPanel panelDepositoABM;
    DefaultTableModel modeloTabla = new DefaultTableModel();
    String[] columnas = {"ID", "Descripcion", "Precio", "Stock", "Habilitado"};

    public VistaDepositoABM(Usuario usuario, VentanaPrincipal ventanaPrincipal) {

        setModeloTabla();

        tableProductos.removeColumn(tableProductos.getColumnModel().getColumn(4));
        //tableProductos.removeColumn(tableProductos.getColumnModel().getColumn(0));

        comboBoxFlitro.addItem("Habilitados");
        comboBoxFlitro.addItem("Deshabilitados");
        comboBoxFlitro.setSelectedIndex(0);

        ControladorDepositoABM controlador = new ControladorDepositoABM();
        List<Producto> productos = controlador.obtenerProductosPorHabilitado(1);
        poblarTabla(productos);

        comboBoxFlitro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filtro = comboBoxFlitro.getSelectedIndex() == 0 ? 1 : 0;
                ControladorDepositoABM ctrl = new ControladorDepositoABM();
                poblarTabla(ctrl.obtenerProductosPorHabilitado(filtro));

                if (comboBoxFlitro.getSelectedIndex() == 0) {
                    deshabilitarButton.setText("Deshabilitar");
                } else {
                    deshabilitarButton.setText("Habilitar");
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filtro = comboBoxFlitro.getSelectedIndex() == 0 ? 1 : 0;
                ControladorDepositoABM ctrl = new ControladorDepositoABM();
                String texto = textFieldID.getText().trim();
                if (texto.isEmpty()) {
                    poblarTabla(ctrl.obtenerProductosPorHabilitado(filtro));
                } else {
                    List<Producto> resultados = ctrl.buscarProducto(texto, filtro);
                    if (!resultados.isEmpty()) {
                        poblarTabla(resultados);
                    }
                }
            }
        });

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, String> valores = VistaFormulario.mostrarDialogo("Nuevo Producto",
                        new VistaFormulario.Campo("Descripción:"),
                        new VistaFormulario.Campo("Precio:"),
                        new VistaFormulario.Campo("Stock:")
                );
                if (valores != null) {
                    ControladorDepositoABM ctrl = new ControladorDepositoABM();
                    try {
                        int precio = Integer.parseInt(valores.get("Precio:"));
                        int stock = Integer.parseInt(valores.get("Stock:"));
                        if (ctrl.agregarProducto(
                                valores.get("Descripción:"),
                                precio,
                                stock
                        )) {
                            poblarTabla(ctrl.obtenerProductosPorHabilitado(comboBoxFlitro.getSelectedIndex() == 0 ? 1 : 0));
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Precio y Stock deben ser números enteros");
                    }
                }
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tableProductos.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla");
                    return;
                }

                int idProducto = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());

                Map<String, String> valores = VistaFormulario.mostrarDialogo("Modificar Producto",
                        new VistaFormulario.Campo("Descripción:", modeloTabla.getValueAt(fila, 1).toString()),
                        new VistaFormulario.Campo("Precio:", modeloTabla.getValueAt(fila, 2).toString()),
                        new VistaFormulario.Campo("Stock:", modeloTabla.getValueAt(fila, 3).toString())
                );
                if (valores != null) {
                    ControladorDepositoABM ctrl = new ControladorDepositoABM();
                    try {
                        int precio = Integer.parseInt(valores.get("Precio:"));
                        int stock = Integer.parseInt(valores.get("Stock:"));
                        if (ctrl.modificarProducto(
                                idProducto,
                                valores.get("Descripción:"),
                                precio,
                                stock
                        )) {
                            poblarTabla(ctrl.obtenerProductosPorHabilitado(comboBoxFlitro.getSelectedIndex() == 0 ? 1 : 0));
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Precio y Stock deben ser números enteros");
                    }
                }
            }
        });

        deshabilitarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tableProductos.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla");
                    return;
                }

                int idProducto = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                int habilitadoActual = Integer.parseInt(modeloTabla.getValueAt(fila, 4).toString());
                String nuevoEstado = habilitadoActual == 1 ? "deshabilitar" : "habilitar";

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de " + nuevoEstado + " el producto \"" + modeloTabla.getValueAt(fila, 1) + "\"?",
                        nuevoEstado.equals("deshabilitar") ? "Deshabilitar Producto" : "Habilitar Producto",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    ControladorDepositoABM ctrl = new ControladorDepositoABM();
                    if (ctrl.toggleHabilitadoProducto(idProducto)) {
                        poblarTabla(ctrl.obtenerProductosPorHabilitado(comboBoxFlitro.getSelectedIndex() == 0 ? 1 : 0));
                    }
                }
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorAdmin controladorAdmin = new ControladorAdmin(usuario, ventanaPrincipal);
            }
        });
    }

    public void setModeloTabla() {
        modeloTabla.setColumnIdentifiers(columnas);
        tableProductos.setModel(modeloTabla);
    }

    public void poblarTabla(List<Producto> productos) {
        modeloTabla.setRowCount(0);

        for (Producto p : productos) {
            modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getDescripcion(),
                    p.getPrecio(),
                    p.getCantidad(),
                    p.getHabilitado()
            });
        }
    }
}
