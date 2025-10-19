package Exepciones;

import logica.TiqueteMultiple;

public class TiqueteNoTransferibleException extends Exception {
	public TiqueteNoTransferibleException(TiqueteMultiple tiquete) {
		super("El tiquete con id" + tiquete.getId() +  "ya ha transferido un tiquete individual y no se puede transferir.");
	}
}
