package Exepciones;

import logica.Tiquete;
import logica.Venue;

public class VenueNoDisponibleException extends Exception{
	
	public VenueNoDisponibleException(Venue venue) {
        super("La venue " + venue.nombre +  " no esta disponible para esta fecha");
    }
	
	public VenueNoDisponibleException(String mensaje) {
		super(mensaje);
	}
    
   

}
