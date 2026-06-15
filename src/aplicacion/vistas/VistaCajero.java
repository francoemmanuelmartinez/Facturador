package aplicacion.vistas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaCajero {
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
    private JTextField textFieldCantidadProducto;
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

    public VistaCajero() {
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buscarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        modificarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        nuevoClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        buscarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        modificarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
}
