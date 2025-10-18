package Exepciones;

import logica.Usuario;

public class ReembolsoNoPermitidoException extends Exception{
	
	public ReembolsoNoPermitidoException(Usuario usuario) {
        super("El reembolso para el usuario" + usuario.getLogin() + " no es permitido");
    }
    
    public ReembolsoNoPermitidoException(String mensaje) {
        super(mensaje);
    }
}

