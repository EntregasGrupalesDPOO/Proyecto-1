package Exepciones;

import logica.Usuario;

public class PasswordIncorrectoException extends Exception{
	
	public PasswordIncorrectoException(Usuario usuario) {
        super("La contrasena para el usuario" + usuario.getLogin() + " no es correcto");
    }
    
    public PasswordIncorrectoException(String mensaje) {
        super(mensaje);
    }
}
