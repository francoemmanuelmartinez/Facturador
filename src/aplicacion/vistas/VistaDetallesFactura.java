package aplicacion.vistas;

import aplicacion.controladores.ControladorFactura;
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
        Object[] factura = controladorFactura.obtenerFacturaCompleta(idFactura);
        if (factura == null) return;

        tfNumeroFactura.setText((String) factura[0]);
        tfFechaFactura.setText(factura[1].toString());
        tfNombreCliente.setText((String) factura[2]);
        tfApellidoCliente.setText((String) factura[3]);
        tfNombreVendedor.setText((String) factura[4]);
        tfApellidoVendedor.setText((String) factura[5]);
        tfSubtotal.setText("$ " + factura[6].toString());
        tfPorcentajeDescuento.setText(factura[7].toString() + "%");
        tfValorDescontado.setText("$ " + factura[8].toString());
        tfTotal.setText("$ " + factura[9].toString());
        tfDniCliente.setText((String) factura[10]);
        tfDireccionCliente.setText((String) factura[11]);
        tfIdCliente.setText(factura[12].toString());
        tfIdVendedor.setText(factura[13].toString());
    }

    private void cargarDetalles() {
        mdlDetalles.setRowCount(0);
        List<Object[]> detalles = controladorFactura.obtenerDetallesPorFactura(idFactura);
        for (Object[] d : detalles) {
            mdlDetalles.addRow(new Object[]{
                    d[0],
                    d[1],
                    d[2],
                    d[3],
                    d[4] + "%",
                    d[5]
            });
        }
    }
}
