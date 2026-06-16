package aplicacion.vistas;

import aplicacion.controladores.ControladorAdmin;
import aplicacion.controladores.ControladorCajero;
import aplicacion.controladores.ControladorClienteABM;
import aplicacion.controladores.ControladorDepositoABM;
import aplicacion.controladores.ControladorLogin;
import aplicacion.modelos.Cliente;
import aplicacion.modelos.Producto;
import aplicacion.modelos.Usuario;

import javax.swing.*;
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
    private JTable tableCarrito;
    private JLabel labelNombreCajero;
    private JTextField textFieldNombreCliente;
    private JTextField textFieldApellidoCliente;
    private JTextField textFieldDNICliente;
    private JTextField textFieldDomicilioCiente;
    private JTextField textFieldTelefonoCliente;
    private JTextField textFieldMailCliente;
    private JTextField textFieldBuscarCliente;
    private JButton buscarClienteButton;
    private JTextField textFieldBuscarProducto;
    private JButton buscarProductoButton;
    private JButton modificarClienteButton;
    private JButton nuevoClienteButton;
    private JButton modificarProductoButton;
    private JLabel labelIDProducto;
    private JTextField textFieldIDProducto;
    private JLabel labelNombreCliente;
    private JLabel labelApellidoCliente;
    private JLabel labelDomicilioCliente;
    private JLabel labelTelefonoCliente;
    private JLabel labelMailCliente;
    private JLabel labelDNICliente;
    private JLabel labelDescripcionProducto;
    private JTextField textFieldDescripcionProducto;
    private JTextField textFieldPrecioProducto;
    private JTextField textFieldDescuentoProducto;
    private JLabel labelPrecioProducto;
    private JLabel labelCantidadProducto;
    private JLabel labelDescuentoProducto;
    private JButton nuevoProductoButton;
    private JButton agregarAlCarroButton;
    private JTextField textFieldNombreCajero;
    private JTextField textFieldIDCajero;
    private JLabel labelIDCajero;
    private JButton volverButton;
    private JButton modificarArticuloButton;
    private JButton eliminarArticuloButton;
    private JButton calcularTotalButton;
    private JButton finalizarCompraButton;
    private JButton cancelarCompraButton;
    private JTextField textFieldSubtotal;
    private JTextField textFieldDescuento;
    private JTextField textFieldTotal;
    private JLabel labelTotal;
    private JLabel labelDescuento;
    private JLabel labelSubtotal;
    private JPanel panelCliente;
    private JPanel panelProducto;
    private JPanel panelOpcionesCarrito;
    private JScrollPane scrollCarrito;
    private JTextField textFieldIDCliente;
    private JLabel labelIDCliente;
    private JLabel labelStock;
    private JTextField textFieldStock;
    private JTextField textFieldCantidadProducto;
    private JTextField textFieldApellidoCajero;
    private JLabel labelApellidoCajero;
    private DefaultTableModel modeloTablaCarrito;
    private String[] columnasCarrito = {"ID", "Descripción", "Precio Unit.", "Cantidad", "Descuento", "Subtotal"};

    public VistaCajero(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        this.usuario = usuario;
        this.ventanaPrincipal = ventanaPrincipal;
        textFieldNombreCajero.setText(usuario.getNombre());
        textFieldApellidoCajero.setText(usuario.getApellido());
        textFieldIDCajero.setText(usuario.getId());
        textFieldSubtotal.setEditable(false);
        textFieldTotal.setEditable(false);
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usuario.getRol().equals("Administrador")) {
                    new ControladorAdmin(usuario, ventanaPrincipal);
                } else {
                    new ControladorLogin(ventanaPrincipal);
                }
            }
        });
        buscarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dni = textFieldBuscarCliente.getText().trim();
                if (dni.isEmpty()) return;
                ControladorClienteABM ctrl = new ControladorClienteABM();
                Cliente c = ctrl.buscarCliente(dni, 1);
                if (c != null) {
                    textFieldIDCliente.setText(String.valueOf(c.getId()));
                    textFieldNombreCliente.setText(c.getNombre());
                    textFieldApellidoCliente.setText(c.getApellido());
                    textFieldDNICliente.setText(c.getDni());
                    textFieldDomicilioCiente.setText(c.getDireccion());
                    textFieldTelefonoCliente.setText(c.getTelefono());
                    textFieldMailCliente.setText(c.getMail());
                } else {
                    limpiarCamposCliente();
                }
            }
        });
        modificarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idStr = textFieldIDCliente.getText().trim();
                if (idStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay cliente seleccionado");
                    return;
                }
                int id = Integer.parseInt(idStr);
                Map<String, String> valores = VistaFormulario.mostrarDialogo("Modificar Cliente",
                        new VistaFormulario.Campo("Nombre:", textFieldNombreCliente.getText()),
                        new VistaFormulario.Campo("Apellido:", textFieldApellidoCliente.getText()),
                        new VistaFormulario.Campo("DNI:", textFieldDNICliente.getText()),
                        new VistaFormulario.Campo("Teléfono:", textFieldTelefonoCliente.getText()),
                        new VistaFormulario.Campo("Dirección:", textFieldDomicilioCiente.getText()),
                        new VistaFormulario.Campo("Mail:", textFieldMailCliente.getText())
                );
                if (valores != null) {
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "¿Confirmar modificación del cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        ControladorClienteABM ctrl = new ControladorClienteABM();
                        if (ctrl.modificarCliente(id,
                                valores.get("Nombre:"),
                                valores.get("Apellido:"),
                                valores.get("DNI:"),
                                valores.get("Teléfono:"),
                                valores.get("Dirección:"),
                                valores.get("Mail:")
                        )) {
                            textFieldNombreCliente.setText(valores.get("Nombre:"));
                            textFieldApellidoCliente.setText(valores.get("Apellido:"));
                            textFieldDNICliente.setText(valores.get("DNI:"));
                            textFieldDomicilioCiente.setText(valores.get("Dirección:"));
                            textFieldTelefonoCliente.setText(valores.get("Teléfono:"));
                            textFieldMailCliente.setText(valores.get("Mail:"));
                        }
                    }
                }
            }
        });
        nuevoClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, String> valores = VistaFormulario.mostrarDialogo("Nuevo Cliente",
                        new VistaFormulario.Campo("Nombre:"),
                        new VistaFormulario.Campo("Apellido:"),
                        new VistaFormulario.Campo("DNI:"),
                        new VistaFormulario.Campo("Teléfono:"),
                        new VistaFormulario.Campo("Dirección:"),
                        new VistaFormulario.Campo("Mail:")
                );
                if (valores != null) {
                    ControladorClienteABM ctrl = new ControladorClienteABM();
                    int id = ctrl.agregarCliente(
                            valores.get("Nombre:"),
                            valores.get("Apellido:"),
                            valores.get("DNI:"),
                            valores.get("Teléfono:"),
                            valores.get("Dirección:"),
                            valores.get("Mail:")
                    );
                    if (id > -1) {
                        textFieldIDCliente.setText(String.valueOf(id));
                        textFieldNombreCliente.setText(valores.get("Nombre:"));
                        textFieldApellidoCliente.setText(valores.get("Apellido:"));
                        textFieldDNICliente.setText(valores.get("DNI:"));
                        textFieldDomicilioCiente.setText(valores.get("Dirección:"));
                        textFieldTelefonoCliente.setText(valores.get("Teléfono:"));
                        textFieldMailCliente.setText(valores.get("Mail:"));
                    }
                }
            }
        });
        buscarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = textFieldBuscarProducto.getText().trim();
                if (texto.isEmpty()) return;

                ControladorDepositoABM ctrl = new ControladorDepositoABM();
                Producto p = ctrl.seleccionarProducto(texto, 1);

                if (p != null) {
                    textFieldIDProducto.setText(String.valueOf(p.getId()));
                    textFieldDescripcionProducto.setText(p.getDescripcion());
                    textFieldPrecioProducto.setText(String.valueOf(p.getPrecio()));
                    textFieldStock.setText(String.valueOf(p.getStock()));
                } else {
                    limpiarCamposProducto();
                }
            }
        });
        modificarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idStr = textFieldIDProducto.getText().trim();
                if (idStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay producto seleccionado");
                    return;
                }
                int id = Integer.parseInt(idStr);

                Map<String, String> valores = VistaFormulario.mostrarDialogo("Modificar Producto",
                        new VistaFormulario.Campo("Descripción:", textFieldDescripcionProducto.getText()),
                        new VistaFormulario.Campo("Precio:", textFieldPrecioProducto.getText()),
                        new VistaFormulario.Campo("Stock:", textFieldStock.getText())
                );

                if (valores != null) {
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "¿Confirmar modificación del producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        ControladorDepositoABM ctrl = new ControladorDepositoABM();
                        boolean ok = ctrl.modificarProducto(id,
                                valores.get("Descripción:"),
                                Integer.parseInt(valores.get("Precio:")),
                                Integer.parseInt(valores.get("Stock:"))
                        );
                        if (ok) {
                            textFieldDescripcionProducto.setText(valores.get("Descripción:"));
                            textFieldPrecioProducto.setText(valores.get("Precio:"));
                            textFieldStock.setText(valores.get("Stock:"));
                        }
                    }
                }
            }
        });
        agregarAlCarroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idStr = textFieldIDProducto.getText().trim();
                if (idStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay producto seleccionado");
                    return;
                }

                String descripcion = textFieldDescripcionProducto.getText();
                int precio = Integer.parseInt(textFieldPrecioProducto.getText());
                int cantidad = Integer.parseInt(textFieldCantidadProducto.getText().trim());
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0");
                    return;
                }

                String descuentoStr = textFieldDescuentoProducto.getText().trim();
                int descuento = descuentoStr.isEmpty() ? 0 : Integer.parseInt(descuentoStr);
                int subtotal = precio * cantidad * (100 - descuento) / 100;

                modeloTablaCarrito.addRow(new Object[]{
                        idStr, descripcion, precio, cantidad, descuento + "%", subtotal
                });

                actualizarTotales();
                limpiarCamposProducto();
            }
        });
        nuevoProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, String> valores = VistaFormulario.mostrarDialogo("Nuevo Producto",
                        new VistaFormulario.Campo("Descripción:"),
                        new VistaFormulario.Campo("Precio:"),
                        new VistaFormulario.Campo("Stock:")
                );

                if (valores != null) {
                    ControladorDepositoABM ctrl = new ControladorDepositoABM();
                    int id = ctrl.agregarProducto(
                            valores.get("Descripción:"),
                            Integer.parseInt(valores.get("Precio:")),
                            Integer.parseInt(valores.get("Stock:"))
                    );
                    if (id > -1) {
                        textFieldIDProducto.setText(String.valueOf(id));
                        textFieldDescripcionProducto.setText(valores.get("Descripción:"));
                        textFieldPrecioProducto.setText(valores.get("Precio:"));
                        textFieldStock.setText(valores.get("Stock:"));
                    }
                }
            }
        });
        configurarTablaCarrito();
        modificarArticuloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tableCarrito.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un artículo del carrito");
                    return;
                }

                String cantidadActual = modeloTablaCarrito.getValueAt(fila, 3).toString();
                String descuentoActual = modeloTablaCarrito.getValueAt(fila, 4).toString().replace("%", "");

                Map<String, String> valores = VistaFormulario.mostrarDialogo("Modificar Artículo",
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

                        int precio = Integer.parseInt(modeloTablaCarrito.getValueAt(fila, 2).toString());
                        int nuevoSubtotal = precio * nuevaCantidad * (100 - nuevoDescuento) / 100;

                        modeloTablaCarrito.setValueAt(nuevaCantidad, fila, 3);
                        modeloTablaCarrito.setValueAt(nuevoDescuento + "%", fila, 4);
                        modeloTablaCarrito.setValueAt(nuevoSubtotal, fila, 5);

                        actualizarTotales();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Ingrese valores numéricos válidos");
                    }
                }
            }
        });
        eliminarArticuloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tableCarrito.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un artículo del carrito");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Eliminar artículo del carrito?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    modeloTablaCarrito.removeRow(fila);
                    actualizarTotales();
                }
            }
        });
        calcularTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subtotalStr = textFieldSubtotal.getText().trim();
                if (subtotalStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay artículos en el carrito");
                    return;
                }

                int subtotal = Integer.parseInt(subtotalStr);
                String descuentoStr = textFieldDescuento.getText().trim();
                int descuento = descuentoStr.isEmpty() ? 0 : Integer.parseInt(descuentoStr);

                if (descuento < 0 || descuento > 100) {
                    JOptionPane.showMessageDialog(null, "El descuento debe ser entre 0 y 100");
                    return;
                }

                int total = subtotal * (100 - descuento) / 100;
                textFieldTotal.setText(String.valueOf(total));
            }
        });
        finalizarCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idClienteStr = textFieldIDCliente.getText().trim();
                if (idClienteStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente");
                    return;
                }

                if (modeloTablaCarrito.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "El carrito está vacío");
                    return;
                }

                if (textFieldTotal.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe calcular el total primero");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Confirmar finalización de la compra?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;

                int idCliente = Integer.parseInt(idClienteStr);
                String nombreCliente = textFieldNombreCliente.getText().trim();
                String apellidoCliente = textFieldApellidoCliente.getText().trim();
                int idVendedor = Integer.parseInt(usuario.getId());
                String nombreVendedor = textFieldNombreCajero.getText().trim();
                String apellidoVendedor = textFieldApellidoCajero.getText().trim();

                List<Object[]> carrito = new ArrayList<>();
                for (int i = 0; i < modeloTablaCarrito.getRowCount(); i++) {
                    carrito.add(new Object[]{
                            modeloTablaCarrito.getValueAt(i, 0),
                            modeloTablaCarrito.getValueAt(i, 1),
                            modeloTablaCarrito.getValueAt(i, 2),
                            modeloTablaCarrito.getValueAt(i, 3),
                            modeloTablaCarrito.getValueAt(i, 4),
                            modeloTablaCarrito.getValueAt(i, 5)
                    });
                }

                ControladorCajero cc = new ControladorCajero();
                if (cc.finalizarCompra(idCliente, nombreCliente, apellidoCliente, idVendedor, nombreVendedor, apellidoVendedor, carrito)) {
                    limpiarCarrito();
                }
            }
        });
        cancelarCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modeloTablaCarrito.getRowCount() == 0) return;

                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Cancelar la compra? Se perderán todos los datos del carrito",
                        "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    limpiarCarrito();
                }
            }
        });
    }

    private void limpiarCamposCliente() {
        textFieldIDCliente.setText("");
        textFieldNombreCliente.setText("");
        textFieldApellidoCliente.setText("");
        textFieldDNICliente.setText("");
        textFieldDomicilioCiente.setText("");
        textFieldTelefonoCliente.setText("");
        textFieldMailCliente.setText("");
    }

    private void configurarTablaCarrito() {
        modeloTablaCarrito = new DefaultTableModel();
        modeloTablaCarrito.setColumnIdentifiers(columnasCarrito);
        tableCarrito.setModel(modeloTablaCarrito);
    }

    private void limpiarCamposProducto() {
        textFieldIDProducto.setText("");
        textFieldDescripcionProducto.setText("");
        textFieldPrecioProducto.setText("");
        textFieldStock.setText("");
        textFieldCantidadProducto.setText("");
        textFieldDescuentoProducto.setText("");
    }

    private void actualizarTotales() {
        int subtotal = 0;
        for (int i = 0; i < modeloTablaCarrito.getRowCount(); i++) {
            subtotal += (int) modeloTablaCarrito.getValueAt(i, 5);
        }
        textFieldSubtotal.setText(String.valueOf(subtotal));
        textFieldDescuento.setText("");
        textFieldTotal.setText("");
    }

    private void limpiarCarrito() {
        modeloTablaCarrito.setRowCount(0);
        limpiarCamposCliente();
        limpiarCamposProducto();
        textFieldSubtotal.setText("");
        textFieldDescuento.setText("");
        textFieldTotal.setText("");
        textFieldBuscarCliente.setText("");
        textFieldBuscarProducto.setText("");
    }
}
