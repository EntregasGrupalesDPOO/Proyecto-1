package Exepciones;

import logica.Cliente;

public class ReembolsoNoPermitidoException extends Exception{
	
	public ReembolsoNoPermitidoException(Cliente usuario) {
        super("El reembolso para el usuario" + usuario.getLogin() + " no es permitido");
    }
    
    public ReembolsoNoPermitidoException(String mensaje) {
        super(mensaje);
    }
}

