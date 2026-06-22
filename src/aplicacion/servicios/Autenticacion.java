package aplicacion.servicios;

import aplicacion.modelos.Usuario;

public class Autenticacion {
    private String mail;
    private String password;

    public Autenticacion(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public boolean autenticar(Usuario usuario) {
        if (usuario.getMail().equals(this.mail) && usuario.getPassword().equals(this.password)) {
            return true;
        }
        return false;
    }
}