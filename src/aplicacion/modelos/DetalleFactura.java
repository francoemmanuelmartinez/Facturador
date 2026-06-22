package aplicacion.modelos;

/**
 * Modelo que representa una linea de detalle de una factura.
 * Mapea la tabla {@code detalles_de_facturas} de la BD.
 *
 * @see Factura
 * @see aplicacion.controladores.ControladorCajero
 * @see aplicacion.controladores.ControladorFactura
 * @see aplicacion.vistas.VistaDetallesFactura
 * @since 1.0
 */
public class DetalleFactura {
    private int id;
    private String descripcion;
    private int cantidad;
    private int precioUnitario;
    private int descuento;
    private int subtotal;

    public DetalleFactura() {}

    public DetalleFactura(int id, String descripcion, int cantidad, int precioUnitario, int descuento, int subtotal) {
        this.id = id;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.subtotal = subtotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(int precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
}
