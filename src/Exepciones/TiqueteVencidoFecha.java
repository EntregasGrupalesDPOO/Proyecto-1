package Exepciones;

import logica.Tiquete;

public class TiqueteVencidoFecha extends Exception {

	public TiqueteVencidoFecha(Tiquete tiquete) {
		super("El tiquete con id " + tiquete.getId() + " vencio el " + tiquete.getFecha());
	}

}
