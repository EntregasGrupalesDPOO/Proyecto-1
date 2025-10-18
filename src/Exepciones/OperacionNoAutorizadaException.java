package Exepciones;

import logica.Usuario;

public class OperacionNoAutorizadaException extends Exception{
	
	public OperacionNoAutorizadaException(Usuario usuario) {
        super("Este tipo de usuario no puede comprar tiquete");
    }
    
    public OperacionNoAutorizadaException(String mensaje) {
        super(mensaje);
    }
}
