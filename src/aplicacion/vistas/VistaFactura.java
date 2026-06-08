package aplicacion.vistas;

import aplicacion.modelos.Factura;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class VistaFactura {

    public JPanel panelFactura;
    private JTable tablaFacturas;
    private DefaultTableModel modeloTabla;

    public VistaFactura() {
        if (panelFactura == null) {
            crearVista();
        }
        configurarTabla();
    }

    private void crearVista() {
        panelFactura = new JPanel(new BorderLayout(10, 10));
        panelFactura.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Facturas");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        tablaFacturas = new JTable();

        JScrollPane scrollTabla = new JScrollPane(tablaFacturas);

        panelFactura.add(titulo, BorderLayout.NORTH);
        panelFactura.add(scrollTabla, BorderLayout.CENTER);
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(new String[]{"Numero", "Cliente", "Fecha", "Total", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaFacturas.setModel(modeloTabla);
        tablaFacturas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaFacturas.getTableHeader().setReorderingAllowed(false);
        tablaFacturas.setRowHeight(24);
    }

    public void mostrarFacturas(List<Factura> facturas) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-AR"));
        modeloTabla.setRowCount(0);

        for (Factura factura : facturas) {
            modeloTabla.addRow(new Object[]{
                    factura.getNumero(),
                    factura.getCliente(),
                    factura.getFecha(),
                    formatoMoneda.format(factura.getTotal()),
                    factura.getEstado()
            });
        }
    }

    public JTable getTablaFacturas() {
        return tablaFacturas;
    }
}
