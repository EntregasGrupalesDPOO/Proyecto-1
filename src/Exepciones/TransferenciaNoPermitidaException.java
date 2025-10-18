package Exepciones;

import logica.Tiquete;

public class TransferenciaNoPermitidaException extends Exception {
	
	public TransferenciaNoPermitidaException(Tiquete tiquete) {
        super("El tiquete con id" + tiquete.getId() +  "del tipo" + tiquete.getTipo() +  "no puede transferirse");
    }
    
    public TransferenciaNoPermitidaException(String mensaje) {
        super(mensaje);
    }
}
