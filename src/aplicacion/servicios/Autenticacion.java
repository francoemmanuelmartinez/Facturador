package aplicacion.servicios;

import aplicacion.modelos.Usuario;

/**
 * Valida credenciales de un usuario comparando mail y password
 * contra los datos almacenados en la BD (obtenidos a traves de
 * {@link Usuario}).
 *
 * @see aplicacion.controladores.ControladorLogin
 * @see Usuario
 * @since 1.0
 */
public class Autenticacion {
    private String mail;
    private String password;

    /**
     * Construye el validador con las credenciales ingresadas.
     *
     * @param mail     Mail ingresado por el usuario
     * @param password Contrasena ingresada por el usuario
     */
    public Autenticacion(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    /**
     * Compara las credenciales almacenadas con las ingresadas.
     *
     * @param usuario Objeto {@link Usuario} obtenido de la BD
     * @return {@code true} si mail y password coinciden
     */
    public boolean autenticar(Usuario usuario) {
        if (usuario.getMail().equals(this.mail) && usuario.getPassword().equals(this.password)) {
            return true;
        }
        return false;
    }
}