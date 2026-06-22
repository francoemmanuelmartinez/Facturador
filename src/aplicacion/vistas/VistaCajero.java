package aplicacion.vistas;

import aplicacion.controladores.ControladorAdmin;
import aplicacion.controladores.ControladorCajero;
import aplicacion.controladores.ControladorClienteABM;
import aplicacion.controladores.ControladorDepositoABM;
import aplicacion.controladores.ControladorLogin;
import aplicacion.modelos.Cliente;
import aplicacion.modelos.Producto;
import aplicacion.modelos.Usuario;

import aplicacion.filtros.FiltroNumerico;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VistaCajero {
    private Usuario usuario;
    private VentanaPrincipal ventanaPrincipal;
    public JPanel panelCajero;
    private JTable tblCarrito;
    private JLabel labelNombreCajero;
    private JTextField tfNombreCliente;
    private JTextField tfApellidoCliente;
    private JTextField tfDniCliente;
    private JTextField tfDireccionCliente;
    private JTextField tfTelefonoCliente;
    private JTextField tfMailCliente;
    private JTextField tfBuscarCliente;
    private JButton btnBuscarCliente;
    private JTextField tfBuscarProducto;
    private JButton btnBuscarProducto;
    private JButton btnModificarCliente;
    private JButton btnNuevoCliente;
    private JButton btnModificarProducto;
    private JLabel labelIDProducto;
    private JTextField tfIdProducto;
    private JLabel labelNombreCliente;
    private JLabel labelApellidoCliente;
    private JLabel labelDomicilioCliente;
    private JLabel labelTelefonoCliente;
    private JLabel labelMailCliente;
    private JLabel labelDNICliente;
    private JLabel labelDescripcionProducto;
    private JTextField tfDescripcionProducto;
    private JTextField tfPrecioProducto;
    private JTextField tfDescuentoProducto;
    private JLabel labelPrecioProducto;
    private JLabel labelCantidadProducto;
    private JLabel labelDescuentoProducto;
    private JButton btnNuevoProducto;
    private JButton btnAgregarAlCarro;
    private JTextField tfNombreCajero;
    private JTextField tfIdCajero;
    private JLabel labelIDCajero;
    private JButton btnVolver;
    private JButton btnModificarArticulo;
    private JButton btnEliminarArticulo;
    private JButton btnCalcularTotal;
    private JButton btnFinalizarCompra;
    private JButton btnCancelarCompra;
    private JTextField tfSubtotal;
    private JTextField tfDescuento;
    private JTextField tfTotal;
    private JLabel labelTotal;
    private JLabel labelDescuento;
    private JLabel labelSubtotal;
    private JPanel panelCliente;
    private JPanel panelProducto;
    private JPanel panelOpcionesCarrito;
    private JScrollPane scrollCarrito;
    private JTextField tfIdCliente;
    private JLabel labelIDCliente;
    private JLabel labelStock;
    private JTextField tfStockProducto;
    private JTextField tfCantidadProducto;
    private JTextField tfApellidoCajero;
    private JLabel labelApellidoCajero;
    private JTextField tfValorDescontado;
    private JLabel labelDescontado;
    private DefaultTableModel mdlCarrito;
    private String[] colsCarrito = {"ID", "Descripcion", "Precio Unit.", "Cantidad", "Descuento", "Subtotal"};

    public VistaCajero(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        this.usuario = usuario;
        this.ventanaPrincipal = ventanaPrincipal;
        tfNombreCajero.setText(usuario.getNombre());
        tfApellidoCajero.setText(usuario.getApellido());
        tfIdCajero.setText(String.valueOf(usuario.getId()));
        ((AbstractDocument) tfBuscarCliente.getDocument()).setDocumentFilter(new FiltroNumerico());
        ((AbstractDocument) tfPrecioProducto.getDocument()).setDocumentFilter(new FiltroNumerico());
        ((AbstractDocument) tfStockProducto.getDocument()).setDocumentFilter(new FiltroNumerico());
        ((AbstractDocument) tfCantidadProducto.getDocument()).setDocumentFilter(new FiltroNumerico());
        ((AbstractDocument) tfDescuentoProducto.getDocument()).setDocumentFilter(new FiltroNumerico());
        ((AbstractDocument) tfDescuento.getDocument()).setDocumentFilter(new FiltroNumerico());
        tfSubtotal.setEditable(false);
        tfTotal.setEditable(false);
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usuario.getRol().equals("Administrador")) {
                    new ControladorAdmin(usuario, ventanaPrincipal);
                } else {
                    new ControladorLogin(ventanaPrincipal);
                }
            }
        });
        btnBuscarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dni = tfBuscarCliente.getText().trim();
                if (dni.isEmpty()) return;
                ControladorClienteABM ctrl = new ControladorClienteABM();
                Cliente c = ctrl.buscarCliente(dni, 1);
                if (c != null) {
                    tfIdCliente.setText(String.valueOf(c.getId()));
                    tfNombreCliente.setText(c.getNombre());
                    tfApellidoCliente.setText(c.getApellido());
                    tfDniCliente.setText(c.getDni());
                    tfDireccionCliente.setText(c.getDireccion());
                    tfTelefonoCliente.setText(c.getTelefono());
                    tfMailCliente.setText(c.getMail());
                } else {
                    limpiarCamposCliente();
                }
            }
        });
        btnModificarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idStr = tfIdCliente.getText().trim();
                if (idStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay cliente seleccionado");
                    return;
                }
                int id = Integer.parseInt(idStr);
                Map<String, String> valores = VistaFormulario.mostrarDialogo("Modificar Cliente",
                        new VistaFormulario.Campo("Nombre:", tfNombreCliente.getText()),
                        new VistaFormulario.Campo("Apellido:", tfApellidoCliente.getText()),
                        new VistaFormulario.Campo("DNI:", tfDniCliente.getText()),
                        new VistaFormulario.Campo("Telefono:", tfTelefonoCliente.getText()),
                        new VistaFormulario.Campo("Direccion:", tfDireccionCliente.getText()),
                        new VistaFormulario.Campo("Mail:", tfMailCliente.getText())
                );
                if (valores != null) {
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "¿Confirmar modificacion del cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        ControladorClienteABM ctrl = new ControladorClienteABM();
                        if (ctrl.modificarCliente(id,
                                valores.get("Nombre:"),
                                valores.get("Apellido:"),
                                valores.get("DNI:"),
                                valores.get("Telefono:"),
                                valores.get("Direccion:"),
                                valores.get("Mail:")
                        )) {
                            tfNombreCliente.setText(valores.get("Nombre:"));
                            tfApellidoCliente.setText(valores.get("Apellido:"));
                            tfDniCliente.setText(valores.get("DNI:"));
                            tfDireccionCliente.setText(valores.get("Direccion:"));
                            tfTelefonoCliente.setText(valores.get("Telefono:"));
                            tfMailCliente.setText(valores.get("Mail:"));
                        }
                    }
                }
            }
        });
        btnNuevoCliente.addActionListener(new ActionListener() {
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
                        tfIdCliente.setText(String.valueOf(id));
                        tfNombreCliente.setText(valores.get("Nombre:"));
                        tfApellidoCliente.setText(valores.get("Apellido:"));
                        tfDniCliente.setText(valores.get("DNI:"));
                        tfDireccionCliente.setText(valores.get("Direccion:"));
                        tfTelefonoCliente.setText(valores.get("Telefono:"));
                        tfMailCliente.setText(valores.get("Mail:"));
                    }
                }
            }
        });
        btnBuscarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = tfBuscarProducto.getText().trim();
                if (texto.isEmpty()) return;

                ControladorDepositoABM ctrl = new ControladorDepositoABM();
                Producto p = ctrl.seleccionarProducto(texto, 1);

                if (p != null) {
                    tfIdProducto.setText(String.valueOf(p.getId()));
                    tfDescripcionProducto.setText(p.getDescripcion());
                    tfPrecioProducto.setText(String.valueOf(p.getPrecio()));
                    tfStockProducto.setText(String.valueOf(p.getStock()));
                } else {
                    limpiarCamposProducto();
                }
            }
        });
        btnModificarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idStr = tfIdProducto.getText().trim();
                if (idStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay producto seleccionado");
                    return;
                }
                int id = Integer.parseInt(idStr);

                Map<String, String> valores = VistaFormulario.mostrarDialogo("Modificar Producto",
                        new VistaFormulario.Campo("Descripcion:", tfDescripcionProducto.getText()),
                        new VistaFormulario.Campo("Precio:", tfPrecioProducto.getText()),
                        new VistaFormulario.Campo("Stock:", tfStockProducto.getText())
                );

                if (valores != null) {
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "¿Confirmar modificacion del producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        ControladorDepositoABM ctrl = new ControladorDepositoABM();
                        boolean ok = ctrl.modificarProducto(id,
                                valores.get("Descripcion:"),
                                Integer.parseInt(valores.get("Precio:")),
                                Integer.parseInt(valores.get("Stock:"))
                        );
                        if (ok) {
                            tfDescripcionProducto.setText(valores.get("Descripcion:"));
                            tfPrecioProducto.setText(valores.get("Precio:"));
                            tfStockProducto.setText(valores.get("Stock:"));
                        }
                    }
                }
            }
        });
        btnAgregarAlCarro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idStr = tfIdProducto.getText().trim();
                if (idStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay producto seleccionado");
                    return;
                }

                String descripcion = tfDescripcionProducto.getText();
                int precio = Integer.parseInt(tfPrecioProducto.getText());
                int cantidad = Integer.parseInt(tfCantidadProducto.getText().trim());
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0");
                    return;
                }

                String descuentoStr = tfDescuentoProducto.getText().trim();
                int descuento = descuentoStr.isEmpty() ? 0 : Integer.parseInt(descuentoStr);
                int subtotal = precio * cantidad * (100 - descuento) / 100;

                mdlCarrito.addRow(new Object[]{
                        idStr, descripcion, precio, cantidad, descuento + "%", subtotal
                });

                actualizarTotales();
                limpiarCamposProducto();
            }
        });
        btnNuevoProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, String> valores = VistaFormulario.mostrarDialogo("Nuevo Producto",
                        new VistaFormulario.Campo("Descripcion:"),
                        new VistaFormulario.Campo("Precio:"),
                        new VistaFormulario.Campo("Stock:")
                );

                if (valores != null) {
                    ControladorDepositoABM ctrl = new ControladorDepositoABM();
                    int id = ctrl.agregarProducto(
                            valores.get("Descripcion:"),
                            Integer.parseInt(valores.get("Precio:")),
                            Integer.parseInt(valores.get("Stock:"))
                    );
                    if (id > -1) {
                        tfIdProducto.setText(String.valueOf(id));
                        tfDescripcionProducto.setText(valores.get("Descripcion:"));
                        tfPrecioProducto.setText(valores.get("Precio:"));
                        tfStockProducto.setText(valores.get("Stock:"));
                    }
                }
            }
        });
        configurarTablaCarrito();
        btnModificarArticulo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tblCarrito.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un articulo del carrito");
                    return;
                }

                String cantidadActual = mdlCarrito.getValueAt(fila, 3).toString();
                String descuentoActual = mdlCarrito.getValueAt(fila, 4).toString().replace("%", "");

                Map<String, String> valores = VistaFormulario.mostrarDialogo("Modificar Articulo",
                        new VistaFormulario.Campo("Cantidad:", cantidadActual),
                        new VistaFormulario.Campo("Descuento %:", descuentoActual)
                );

                if (valores != null) {
                    try {
                        int nuevaCantidad = Integer.parseInt(valores.get("Cantidad:"));
                        int nuevoDescuento = Integer.parseInt(valores.get("Descuento %:"));
                        if (nuevaCantidad <= 0) {
                            JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0");
                            return;
                        }
                        if (nuevoDescuento < 0 || nuevoDescuento > 100) {
                            JOptionPane.showMessageDialog(null, "El descuento debe ser entre 0 y 100");
                            return;
                        }

                        int precio = Integer.parseInt(mdlCarrito.getValueAt(fila, 2).toString());
                        int nuevoSubtotal = precio * nuevaCantidad * (100 - nuevoDescuento) / 100;

                        mdlCarrito.setValueAt(nuevaCantidad, fila, 3);
                        mdlCarrito.setValueAt(nuevoDescuento + "%", fila, 4);
                        mdlCarrito.setValueAt(nuevoSubtotal, fila, 5);

                        actualizarTotales();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Ingrese valores numericos validos");
                    }
                }
            }
        });
        btnEliminarArticulo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tblCarrito.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un articulo del carrito");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Eliminar articulo del carrito?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    mdlCarrito.removeRow(fila);
                    actualizarTotales();
                }
            }
        });
        btnCalcularTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subtotalStr = tfSubtotal.getText().trim();
                if (subtotalStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay articulos en el carrito");
                    return;
                }

                int subtotal = Integer.parseInt(subtotalStr);
                String descuentoStr = tfDescuento.getText().trim();
                int descuento = descuentoStr.isEmpty() ? 0 : Integer.parseInt(descuentoStr);

                if (descuento < 0 || descuento > 100) {
                    JOptionPane.showMessageDialog(null, "El descuento debe ser entre 0 y 100");
                    return;
                }

                int total = subtotal * (100 - descuento) / 100;
                int valorDescontado = subtotal - total;
                tfTotal.setText(String.valueOf(total));
                tfValorDescontado.setText(String.valueOf(valorDescontado));
            }
        });
        btnFinalizarCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idClienteStr = tfIdCliente.getText().trim();
                if (idClienteStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente");
                    return;
                }

                if (mdlCarrito.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "El carrito esta vacio");
                    return;
                }

                if (tfTotal.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe calcular el total primero");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Confirmar finalizacion de la compra?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;

                int idCliente = Integer.parseInt(idClienteStr);
                String nombreCliente = tfNombreCliente.getText().trim();
                String apellidoCliente = tfApellidoCliente.getText().trim();
                int idVendedor = usuario.getId();
                String nombreVendedor = tfNombreCajero.getText().trim();
                String apellidoVendedor = tfApellidoCajero.getText().trim();

                List<Object[]> carrito = new ArrayList<>();
                for (int i = 0; i < mdlCarrito.getRowCount(); i++) {
                    carrito.add(new Object[]{
                            mdlCarrito.getValueAt(i, 0),
                            mdlCarrito.getValueAt(i, 1),
                            mdlCarrito.getValueAt(i, 2),
                            mdlCarrito.getValueAt(i, 3),
                            mdlCarrito.getValueAt(i, 4),
                            mdlCarrito.getValueAt(i, 5)
                    });
                }

                int subtotalVal = Integer.parseInt(tfSubtotal.getText().trim());
                String descuentoStrVal = tfDescuento.getText().trim();
                int descuentoPorcentaje = descuentoStrVal.isEmpty() ? 0 : Integer.parseInt(descuentoStrVal);
                int totalVal = Integer.parseInt(tfTotal.getText().trim());
                int valorDescontadoVal = subtotalVal - totalVal;

                ControladorCajero cc = new ControladorCajero();
                if (cc.finalizarCompra(idCliente, nombreCliente, apellidoCliente, idVendedor, nombreVendedor, apellidoVendedor, carrito, subtotalVal, descuentoPorcentaje, valorDescontadoVal, totalVal)) {
                    limpiarCarrito();
                }
            }
        });
        btnCancelarCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mdlCarrito.getRowCount() == 0) return;

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Cancelar la compra? Se perderan todos los datos del carrito",
                        "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    limpiarCarrito();
                }
            }
        });
    }

    private void limpiarCamposCliente() {
        tfIdCliente.setText("");
        tfNombreCliente.setText("");
        tfApellidoCliente.setText("");
        tfDniCliente.setText("");
        tfDireccionCliente.setText("");
        tfTelefonoCliente.setText("");
        tfMailCliente.setText("");
    }

    private void configurarTablaCarrito() {
        mdlCarrito = new DefaultTableModel();
        mdlCarrito.setColumnIdentifiers(colsCarrito);
        tblCarrito.setModel(mdlCarrito);
    }

    private void limpiarCamposProducto() {
        tfIdProducto.setText("");
        tfDescripcionProducto.setText("");
        tfPrecioProducto.setText("");
        tfStockProducto.setText("");
        tfCantidadProducto.setText("");
        tfDescuentoProducto.setText("");
    }

    private void actualizarTotales() {
        int subtotal = 0;
        for (int i = 0; i < mdlCarrito.getRowCount(); i++) {
            subtotal += (int) mdlCarrito.getValueAt(i, 5);
        }
        tfSubtotal.setText(String.valueOf(subtotal));
        tfDescuento.setText("");
        tfTotal.setText("");
        tfValorDescontado.setText("");
    }

    private void limpiarCarrito() {
        mdlCarrito.setRowCount(0);
        limpiarCamposCliente();
        limpiarCamposProducto();
        tfSubtotal.setText("");
        tfDescuento.setText("");
        tfTotal.setText("");
        tfValorDescontado.setText("");
        tfBuscarCliente.setText("");
        tfBuscarProducto.setText("");
    }
}
