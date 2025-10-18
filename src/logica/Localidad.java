package logica;

import java.util.HashMap;
import java.util.List;

public class Localidad {
	private String nombre;
	private double precioTiquete ;
	private int  capacidad ;
	private boolean numerada;
	private Venue venue;
	private HashMap<Integer, Tiquete>tiquetesUsados;
	private HashMap<Integer, TiqueteMultiple>tiquetesMultiplesUsados;
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

	public double getPrecioTiquete() {
		return precioTiquete;
	}

	public void setPrecioTiquete(double precioTiquete) {
		this.precioTiquete = precioTiquete;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public boolean isNumerada() {
		return numerada;
	}

	public void setNumerada(boolean numerada) {
		this.numerada = numerada;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public HashMap<Integer, Tiquete> getTiquetesUsados() {
		return tiquetesUsados;
	}

	public void setTiquetesUsados(HashMap<Integer, Tiquete> tiquetesUsados) {
		this.tiquetesUsados = tiquetesUsados;
	}
	
	
}
