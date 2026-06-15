package aplicacion.modelos;

public class Producto {

    private int id;
    private int habilitado;
    private String descripcion;
    private int precio;
    private int stock;

    public Producto(int id, int habilitado, String descripcion, int precio, int stock) {
        this.id = id;
        this.habilitado = habilitado;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getHabilitado() {
        return habilitado;
    }
    public void setHabilitado(int habilitado) {
        this.habilitado = habilitado;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public int getPrecio() {
        return precio;
    }
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

}