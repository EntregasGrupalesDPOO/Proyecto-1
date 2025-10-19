package Exepciones;

import logica.Tiquete;

public class TransferirTiqueteDeluxeException extends Exception {
	public TransferirTiqueteDeluxeException(Tiquete tiquete) {
		super("El tiquete con id "+tiquete.getId()+ " es Deluxe y no se puede transferir");
	}
}
