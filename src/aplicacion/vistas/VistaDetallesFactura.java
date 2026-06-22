package aplicacion.vistas;

import aplicacion.controladores.ControladorFactura;
import aplicacion.modelos.DetalleFactura;
import aplicacion.modelos.Factura;
import aplicacion.modelos.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaDetallesFactura {
    private JLabel labelTitulo;
    private JTextField tfNumeroFactura;
    private JTextField tfFechaFactura;
    private JTextField tfNombreCliente;
    private JTextField tfApellidoCliente;
    private JTextField tfDniCliente;
    private JTextField tfDireccionCliente;
    private JTextField tfNombreVendedor;
    private JTextField tfApellidoVendedor;
    private JTextField tfTotal;
    private JTable tblDetalles;
    private JButton btnVolver;
    private JPanel panelCliente;
    private JPanel panelVendedor;
    public JPanel panelDetallesFactura;
    private JTextField tfIdVendedor;
    private JLabel labelIDVendedor;
    private JTextField tfIdCliente;
    private JLabel labelIDCliente;
    private JTextField tfValorDescontado;
    private JLabel labelDescontado;
    private JTextField tfPorcentajeDescuento;
    private JTextField tfSubtotal;

    private DefaultTableModel mdlDetalles;
    private String[] colsDetalles = {"ID", "Producto", "Cantidad", "P/U", "Descuento %", "Subtotal"};
    private Usuario usuario;
    private VentanaPrincipal ventanaPrincipal;
    private int idFactura;
    private int idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private ControladorFactura controladorFactura;

    public VistaDetallesFactura(Usuario usuario, VentanaPrincipal ventanaPrincipal, int idFactura, int idCliente, String nombreCliente, String apellidoCliente) {
        this.usuario = usuario;
        this.ventanaPrincipal = ventanaPrincipal;
        this.idFactura = idFactura;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.controladorFactura = new ControladorFactura();

        mdlDetalles = new DefaultTableModel(colsDetalles, 0);
        tblDetalles.setModel(mdlDetalles);

        tfNumeroFactura.setEditable(false);
        tfFechaFactura.setEditable(false);
        tfNombreCliente.setEditable(false);
        tfApellidoCliente.setEditable(false);
        tfDniCliente.setEditable(false);
        tfDireccionCliente.setEditable(false);
        tfNombreVendedor.setEditable(false);
        tfApellidoVendedor.setEditable(false);
        tfTotal.setEditable(false);
        tfIdCliente.setEditable(false);
        tfIdVendedor.setEditable(false);
        tfValorDescontado.setEditable(false);

        cargarFactura();
        cargarDetalles();

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VistaFactura vf = new VistaFactura(usuario, ventanaPrincipal, idCliente, nombreCliente, apellidoCliente);
                ventanaPrincipal.setVista(vf.panelFactura);
            }
        });
    }

    private void cargarFactura() {
        Factura factura = controladorFactura.obtenerFacturaCompleta(idFactura);
        if (factura == null) return;

        tfNumeroFactura.setText(factura.getNumeroFactura());
        tfFechaFactura.setText(factura.getFechaEmision().toString());
        tfNombreCliente.setText(factura.getNombreCliente());
        tfApellidoCliente.setText(factura.getApellidoCliente());
        tfNombreVendedor.setText(factura.getNombreVendedor());
        tfApellidoVendedor.setText(factura.getApellidoVendedor());
        tfSubtotal.setText("$ " + factura.getSubtotal());
        tfPorcentajeDescuento.setText(factura.getDescuentoPorcentaje() + "%");
        tfValorDescontado.setText("$ " + factura.getValorDescontado());
        tfTotal.setText("$ " + factura.getTotalCompra());
        tfDniCliente.setText(factura.getDniCliente());
        tfDireccionCliente.setText(factura.getDireccionCliente());
        tfIdCliente.setText(String.valueOf(factura.getIdCliente()));
        tfIdVendedor.setText(String.valueOf(factura.getIdVendedor()));
    }

    private void cargarDetalles() {
        mdlDetalles.setRowCount(0);
        List<DetalleFactura> detalles = controladorFactura.obtenerDetallesPorFactura(idFactura);
        for (DetalleFactura d : detalles) {
            mdlDetalles.addRow(new Object[]{
                    d.getId(),
                    d.getDescripcion(),
                    d.getCantidad(),
                    d.getPrecioUnitario(),
                    d.getDescuento() + "%",
                    d.getSubtotal()
            });
        }
    }
}
