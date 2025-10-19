package Exepciones;

import logica.Localidad;

public class CapacidadLocalidadExcedidaException extends Exception{

	public CapacidadLocalidadExcedidaException(int tiquetes) {
		super("La capacidad de la localidad no permite comprar " + tiquetes + " tiquetes más.");
	}
	
}
