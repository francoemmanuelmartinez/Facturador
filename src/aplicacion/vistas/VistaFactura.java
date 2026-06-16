package aplicacion.vistas;

import aplicacion.controladores.ControladorClienteABM;
import aplicacion.controladores.ControladorFactura;
import aplicacion.modelos.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VistaFactura {
    private JLabel labelTitulo;
    private JTable tableFacturas;
    private JButton verDetalleButton;
    private JButton volverButton;
    public JPanel panelFactura;

    private DefaultTableModel modeloTablaFacturas;
    private String[] columnasFacturas = {"N°", "Fecha", "Total ($)", "Vendedor"};
    private List<Object[]> facturas;
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

        modeloTablaFacturas = new DefaultTableModel(columnasFacturas, 0);
        tableFacturas.setModel(modeloTablaFacturas);
        tableFacturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cargarFacturas();

        verDetalleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tableFacturas.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una factura");
                    return;
                }
                int idFactura = (int) facturas.get(fila)[0];
                VistaDetallesFactura vdf = new VistaDetallesFactura(usuario, ventanaPrincipal, idFactura, idCliente, nombreCliente, apellidoCliente);
                ventanaPrincipal.setVista(vdf.panelDetallesFactura);
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ControladorClienteABM(usuario, ventanaPrincipal);
            }
        });
    }

    private void cargarFacturas() {
        modeloTablaFacturas.setRowCount(0);
        facturas = controladorFactura.obtenerFacturasPorCliente(idCliente);
        int contador = 1;
        for (Object[] f : facturas) {
            String vendedor = f[4] + " " + f[5];
            modeloTablaFacturas.addRow(new Object[]{
                    contador,
                    f[2],
                    f[3],
                    vendedor.trim()
            });
            contador++;
        }
    }
}
