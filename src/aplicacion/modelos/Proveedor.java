package aplicacion.modelos;

/**
 * Modelo que representa un proveedor.
 * Mapea la tabla {@code proveedores} de la BD.
 *
 * @see aplicacion.controladores.ControladorProveedorABM
 * @see aplicacion.controladores.ControladorDepositoABM
 * @see aplicacion.vistas.VistaCajero
 * @see aplicacion.vistas.VistaProveedorABM
 * @see aplicacion.vistas.VistaDepositoABM
 * @since 1.0
 */
public class Proveedor {

    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String mail;
    private int habilitado;

    public Proveedor() {}

    public Proveedor(int id, String nombre, String direccion, String telefono, String mail, int habilitado) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.mail = mail;
        this.habilitado = habilitado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(int habilitado) {
        this.habilitado = habilitado;
    }
}
