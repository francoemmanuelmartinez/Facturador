package aplicacion.modelos;

/**
 * Modelo que representa un usuario del sistema.
 * Mapea la tabla {@code usuarios} de la BD.
 *
 * @see aplicacion.controladores.ControladorLogin
 * @see aplicacion.controladores.ControladorRegistro
 * @see aplicacion.controladores.ControladorUsuarioABM
 * @see aplicacion.controladores.ControladorAdmin
 * @see aplicacion.controladores.ControladorCajero
 * @see aplicacion.vistas.VistaAdmin
 * @see aplicacion.vistas.VistaCajero
 * @see aplicacion.vistas.VistaUsuarioABM
 * @see aplicacion.servicios.Autenticacion
 * @since 1.0
 */
public class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String direccion;
    private String mail;
    private String rol;
    private String password;
    private int habilitado;

    public Usuario() {}

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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(int habilitado) {
        this.habilitado = habilitado;
    }
}
