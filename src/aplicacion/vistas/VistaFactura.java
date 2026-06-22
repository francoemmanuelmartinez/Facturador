package aplicacion.vistas;

import aplicacion.controladores.ControladorClienteABM;
import aplicacion.controladores.ControladorFactura;
import aplicacion.modelos.Factura;
import aplicacion.modelos.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaFactura {
    private JLabel labelTitulo;
    private JTable tblFacturas;
    private JButton btnVerDetalle;
    private JButton btnVolver;
    public JPanel panelFactura;

    private DefaultTableModel mdlFacturas;
    private String[] colsFacturas = {"N°", "Fecha", "Total ($)", "Vendedor"};
    private List<Factura> facturas;
    private int idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private Usuario usuario;
    private VentanaPrincipal ventanaPrincipal;
    private ControladorFactura controladorFactura;

    public VistaFactura(Usuario usuario, VentanaPrincipal ventanaPrincipal, int idCliente, String nombreCliente, String apellidoCliente) {
        this.usuario = usuario;
        this.ventanaPrincipal = ventanaPrincipal;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.controladorFactura = new ControladorFactura();

        labelTitulo.setText("Facturas de: " + nombreCliente + " " + apellidoCliente);

        mdlFacturas = new DefaultTableModel(colsFacturas, 0);
        tblFacturas.setModel(mdlFacturas);
        tblFacturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cargarFacturas();

        btnVerDetalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tblFacturas.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una factura");
                    return;
                }
                int idFactura = facturas.get(fila).getId();
                VistaDetallesFactura vdf = new VistaDetallesFactura(usuario, ventanaPrincipal, idFactura, idCliente, nombreCliente, apellidoCliente);
                ventanaPrincipal.setVista(vdf.panelDetallesFactura);
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ControladorClienteABM(usuario, ventanaPrincipal);
            }
        });
    }

    private void cargarFacturas() {
        mdlFacturas.setRowCount(0);
        facturas = controladorFactura.obtenerFacturasPorCliente(idCliente);
        int contador = 1;
        for (Factura f : facturas) {
            String vendedor = f.getNombreVendedor() + " " + f.getApellidoVendedor();
            mdlFacturas.addRow(new Object[]{
                    contador,
                    f.getFechaEmision(),
                    f.getTotalCompra(),
                    vendedor.trim()
            });
            contador++;
        }
    }
}
