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
    private JTextField textFieldNumeroFactura;
    private JTextField textFieldFecha;
    private JTextField textFieldNombreCliente;
    private JTextField textFieldApellidoCliente;
    private JTextField textFieldDNICliente;
    private JTextField textFieldDireccionCliente;
    private JTextField textFieldNombreVendedor;
    private JTextField textFieldApellidoVendedor;
    private JTextField textFieldTotal;
    private JTable tableDetalles;
    private JButton volverButton;
    public JPanel panelDetallesFactura;

    private DefaultTableModel modeloTablaDetalles;
    private String[] columnasDetalles = {"Producto", "Cantidad", "P/U", "Descuento %", "Subtotal"};
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

        modeloTablaDetalles = new DefaultTableModel(columnasDetalles, 0);
        tableDetalles.setModel(modeloTablaDetalles);

        textFieldNumeroFactura.setEditable(false);
        textFieldFecha.setEditable(false);
        textFieldNombreCliente.setEditable(false);
        textFieldApellidoCliente.setEditable(false);
        textFieldDNICliente.setEditable(false);
        textFieldDireccionCliente.setEditable(false);
        textFieldNombreVendedor.setEditable(false);
        textFieldApellidoVendedor.setEditable(false);
        textFieldTotal.setEditable(false);

        cargarFactura();
        cargarDetalles();

        volverButton.addActionListener(new ActionListener() {
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

        textFieldNumeroFactura.setText((String) factura[0]);
        textFieldFecha.setText(factura[1].toString());
        textFieldNombreCliente.setText((String) factura[2]);
        textFieldApellidoCliente.setText((String) factura[3]);
        textFieldNombreVendedor.setText((String) factura[4]);
        textFieldApellidoVendedor.setText((String) factura[5]);
        textFieldTotal.setText("$ " + factura[6].toString());
        textFieldDNICliente.setText((String) factura[7]);
        textFieldDireccionCliente.setText((String) factura[8]);
    }

    private void cargarDetalles() {
        modeloTablaDetalles.setRowCount(0);
        List<Object[]> detalles = controladorFactura.obtenerDetallesPorFactura(idFactura);
        for (Object[] d : detalles) {
            modeloTablaDetalles.addRow(new Object[]{
                    d[0],
                    d[1],
                    d[2],
                    d[3] + "%",
                    d[4]
            });
        }
    }
}
