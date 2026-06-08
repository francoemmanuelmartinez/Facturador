package aplicacion.controladores;

import aplicacion.modelos.Factura;
import aplicacion.vistas.VentanaPrincipal;
import aplicacion.vistas.VistaFactura;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControladorFactura {

    private VentanaPrincipal ventanaPrincipal;
    private VistaFactura vistaFactura;
    private List<Factura> facturas;

    public ControladorFactura(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.vistaFactura = new VistaFactura();
        this.facturas = obtenerFacturas();

        this.vistaFactura.mostrarFacturas(facturas);
        this.ventanaPrincipal.setVista(vistaFactura.panelFactura);
        this.ventanaPrincipal.setVisible(true);
    }

    private List<Factura> obtenerFacturas() {
        List<Factura> listado = new ArrayList<>();
        listado.add(new Factura("F-0001", "Juan Perez", LocalDate.of(2026, 6, 1), 12500.00, "Pagada"));
        listado.add(new Factura("F-0002", "Maria Gomez", LocalDate.of(2026, 6, 3), 8300.50, "Pendiente"));
        listado.add(new Factura("F-0003", "Carlos Lopez", LocalDate.of(2026, 6, 5), 19999.99, "Anulada"));
        return listado;
    }
}
