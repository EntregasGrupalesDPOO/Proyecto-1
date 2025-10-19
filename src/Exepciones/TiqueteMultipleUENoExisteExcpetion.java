package Exepciones;

import logica.Evento;

public class TiqueteMultipleUENoExisteExcpetion extends Exception {
	public TiqueteMultipleUENoExisteExcpetion(int cantidad, Integer idLocalidad, Evento evento) {
		super("No existen tiquetes multiples de cantidad " + cantidad + " en la localidad " + idLocalidad +" en el evento " + evento.getNombre());
	}
}
