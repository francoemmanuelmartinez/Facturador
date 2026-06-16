package aplicacion.vistas;

import aplicacion.controladores.ControladorAdmin;
import aplicacion.controladores.ControladorClienteABM;
import aplicacion.controladores.ControladorDepositoABM;
import aplicacion.controladores.ControladorLogin;
import aplicacion.modelos.Cliente;
import aplicacion.modelos.Producto;
import aplicacion.modelos.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public VistaCajero(Usuario usuario, VentanaPrincipal ventanaPrincipal) {
        this.usuario = usuario;
        this.ventanaPrincipal = ventanaPrincipal;
        textFieldNombreCajero.setText(usuario.getNombre());
        textFieldIDCajero.setText(usuario.getId());
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
        modificarArticuloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        eliminarArticuloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        calcularTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        finalizarCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cancelarCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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

    private void limpiarCamposProducto() {
        textFieldIDProducto.setText("");
        textFieldDescripcionProducto.setText("");
        textFieldPrecioProducto.setText("");
        textFieldStock.setText("");
    }
}
