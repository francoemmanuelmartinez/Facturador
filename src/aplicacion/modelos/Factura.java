package aplicacion.modelos;

import java.time.LocalDate;
import java.util.List;

/**
 * Modelo que representa una factura emitida.
 * Mapea la tabla {@code facturas} de la BD e incluye una lista de
 * {@link DetalleFactura} con las lineas de la factura.
 *
 * @see DetalleFactura
 * @see aplicacion.controladores.ControladorCajero
 * @see aplicacion.controladores.ControladorFactura
 * @see aplicacion.vistas.VistaFactura
 * @see aplicacion.vistas.VistaDetallesFactura
 * @since 1.0
 */
public class Factura {

    private int id;
    private String numeroFactura;
    private int idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String dniCliente;
    private String direccionCliente;
    private int idVendedor;
    private String nombreVendedor;
    private String apellidoVendedor;
    private LocalDate fechaEmision;
    private int subtotal;
    private int descuentoPorcentaje;
    private int valorDescontado;
    private int totalCompra;
    private List<DetalleFactura> detalles;

    public Factura() {}

    public Factura(int id, String numeroFactura, int idCliente, String nombreCliente, String apellidoCliente, String dniCliente, String direccionCliente, int idVendedor, String nombreVendedor, String apellidoVendedor, LocalDate fechaEmision, int subtotal, int descuentoPorcentaje, int valorDescontado, int totalCompra) {
        this.id = id;
        this.numeroFactura = numeroFactura;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.dniCliente = dniCliente;
        this.direccionCliente = direccionCliente;
        this.idVendedor = idVendedor;
        this.nombreVendedor = nombreVendedor;
        this.apellidoVendedor = apellidoVendedor;
        this.fechaEmision = fechaEmision;
        this.subtotal = subtotal;
        this.descuentoPorcentaje = descuentoPorcentaje;
        this.valorDescontado = valorDescontado;
        this.totalCompra = totalCompra;
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

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
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

    public String getApellidoVendedor() {
        return apellidoVendedor;
    }

    public void setApellidoVendedor(String apellidoVendedor) {
        this.apellidoVendedor = apellidoVendedor;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getDescuentoPorcentaje() {
        return descuentoPorcentaje;
    }

    public void setDescuentoPorcentaje(int descuentoPorcentaje) {
        this.descuentoPorcentaje = descuentoPorcentaje;
    }

    public int getValorDescontado() {
        return valorDescontado;
    }

    public void setValorDescontado(int valorDescontado) {
        this.valorDescontado = valorDescontado;
    }

    public int getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(int totalCompra) {
        this.totalCompra = totalCompra;
    }

    public List<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
    }
}
