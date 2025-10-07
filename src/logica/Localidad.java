package logica;

import java.util.List;

public class Localidad {
	private String nombre;
	private double precioTiquete ;
	private int  capacidad ;
	private boolean numerada;
	private Venue venue;
	private List<Tiquete> tiquetesDisponibles;
	private List<Tiquete>tiquetesUsados;
	private Evento evento;
	
	@Override
	public String toString() {
		return this.nombre;
		
	}

	public Evento getEvento() {
		return evento;
	}
	
	public String getNombre() {
		return nombre;
	}
	

}
