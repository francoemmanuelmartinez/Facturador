package aplicacion.modelos;

/**
 * Modelo que representa un cliente.
 * Mapea la tabla {@code clientes} de la BD.
 *
 * @see aplicacion.controladores.ControladorClienteABM
 * @see aplicacion.controladores.ControladorCajero
 * @see aplicacion.controladores.ControladorFactura
 * @see aplicacion.vistas.VistaCajero
 * @see aplicacion.vistas.VistaClienteABM
 * @see aplicacion.vistas.VistaFactura
 * @since 1.0
 */
public class Cliente {

    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String direccion;
    private String mail;
    private int habilitado;

    public Cliente() {}

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
