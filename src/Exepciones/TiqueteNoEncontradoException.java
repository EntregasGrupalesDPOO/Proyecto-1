package Exepciones;

public class TiqueteNoEncontradoException extends Exception {

	public TiqueteNoEncontradoException(int idTiquete) {
		super("El tiquete con id "+ idTiquete + "no está en el tiquete múltiple especificado.");
	}

}
