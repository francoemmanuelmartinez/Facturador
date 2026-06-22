package aplicacion.filtros;

public class ValidadorMail {
    public static boolean esValido(String mail) {
        return mail != null && mail.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    }
}
