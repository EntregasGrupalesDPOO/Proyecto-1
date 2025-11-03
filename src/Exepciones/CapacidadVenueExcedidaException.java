package Exepciones;

public class CapacidadVenueExcedidaException extends Exception {
	public CapacidadVenueExcedidaException(int capacidad) {
		super("No se puede agrega la localidad con " + capacidad + " cupos porque excede la capacidad del Venue");
	}
}