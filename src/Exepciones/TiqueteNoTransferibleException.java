package Exepciones;

import logica.Tiquete;

public class TiqueteNoTransferibleException extends Exception {
	public TiqueteNoTransferibleException(Tiquete tiquete) {
		super("El tiquete con id" + tiquete.getId() +  "ya ha transferido un tiquete individual y no se puede transferir.");
	}
}
