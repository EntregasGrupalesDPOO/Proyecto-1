package Exepciones;

import logica.Cliente;
import logica.Organizador;
public class PasswordIncorrectoException extends Exception{
	
	public PasswordIncorrectoException(Cliente usuario) {
        super("La contrasena para el usuario" + usuario.getLogin() + " no es correcto");
    }
    
    public PasswordIncorrectoException(String mensaje) {
        super(mensaje);
    }

    	public PasswordIncorrectoException(Organizador usuario) {
        super("La contrasena para el usuario" + usuario.getLogin() + " no es correcto");
    }
    
}
