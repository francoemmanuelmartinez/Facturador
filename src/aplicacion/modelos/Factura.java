package aplicacion.modelos;

import java.time.LocalDate;
import java.util.List;

public class Factura {

    private int id;
    private String numeroFactura;
    private int idCliente;
    private String nombreCliente;
    private int idVendedor;
    private String nombreVendedor;
    private LocalDate fechaEmision;
    private double totalCompra;
    private List<DetalleFactura> detalles;

    public Factura() {}

    public Factura(String numeroFactura, int idCliente, String nombreCliente,
                   int idVendedor, String nombreVendedor, LocalDate fechaEmision,
                   double totalCompra, List<DetalleFactura> detalles) {
        this.numeroFactura = numeroFactura;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.idVendedor = idVendedor;
        this.nombreVendedor = nombreVendedor;
        this.fechaEmision = fechaEmision;
        this.totalCompra = totalCompra;
        this.detalles = detalles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public List<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
    }
}
