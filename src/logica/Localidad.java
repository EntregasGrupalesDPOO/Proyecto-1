package logica;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.io.Serializable;


public class Localidad implements Serializable{
	private String nombre;
	private double precioTiquete ;
	private int capacidad ;
	private boolean numerada;
	private Venue venue;
	private HashMap<Integer, Tiquete> tiquetesUsados;
	private HashMap<Integer, TiqueteMultiple>tiquetesMultiplesUsados;
	
	
	
	public Localidad(String nombre, double precioTiquete, int capacidad, boolean numerada, Venue venue) {
		this.nombre = nombre;
		this.precioTiquete = precioTiquete;
		this.capacidad = capacidad;
		this.numerada = numerada;
		this.venue = venue;
		this.tiquetesUsados = new HashMap<Integer, Tiquete>();
		this.tiquetesMultiplesUsados = new HashMap<Integer, TiqueteMultiple>();
	}
	
	public boolean compararCapacidad(int cantidadAComprar) {
	    int ocupados = 0;

	    ocupados += tiquetesUsados.size();

	    for (TiqueteMultiple tiquete : tiquetesMultiplesUsados.values()) {
	        if (tiquete instanceof TiqueteMultipleUnicoEvento) {
	            ocupados += ((TiqueteMultipleUnicoEvento) tiquete).getTiquetes().size();
	        } else if (tiquete instanceof TiqueteMultipleVariosEventos) {
	            ocupados += ((TiqueteMultipleVariosEventos) tiquete).getTiquetes().size();
	        }
	    }

	    return this.capacidad >= ocupados + cantidadAComprar;
	}

	
	@Override
	public String toString() {
		return this.nombre;
		
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


	public HashMap<Integer, Tiquete> getTiquetesUsados() {
		return tiquetesUsados;
	}

	public void setTiquetesUsados(HashMap<Integer, Tiquete> tiquetesUsados) {
		this.tiquetesUsados = tiquetesUsados;
	}

	public HashMap<Integer, TiqueteMultiple> getTiquetesMultiplesUsados() {
		return tiquetesMultiplesUsados;
	}

	public void setTiquetesMultiplesUsados(HashMap<Integer, TiqueteMultiple> tiquetesMultiplesUsados) {
		this.tiquetesMultiplesUsados = tiquetesMultiplesUsados;
	}
	
	
}
