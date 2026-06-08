package aplicacion.modelos;

public class Producto {

    private String descripcion;
    private String precio;
    private String cantidad; //La cantidad que lleva el cliente
    private String stock; //Este es el stock en deposito

    public Producto(String descripcion, String precio, String cantidad, String stock) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getPrecio() {
        return precio;
    }
    public void setPrecio(String precio) {
        this.precio = precio;
    }
    public String getCantidad() {
        return cantidad;
    }
    public void setCantidad(String cantidad) {

    }
    public String getStock() {
        return stock;
    }
    public void setStock(String stock) {
        this.stock = stock;
    }

}