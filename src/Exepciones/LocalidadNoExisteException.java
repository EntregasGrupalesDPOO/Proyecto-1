package Exepciones;

public class LocalidadNoExisteException extends Exception {
	public LocalidadNoExisteException(String nombreLocalidad) {
		super("La localidad " + nombreLocalidad + " no existe para este evento.");
	}
}
