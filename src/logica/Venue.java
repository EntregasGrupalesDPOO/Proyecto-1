package logica;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Venue {
	public HashMap<LocalDate,Evento> eventos;
	public int capacidad;
	public String nombre;
	public String ubicacion;
	
	
	public Venue(int capacidad, String nombre, String ubicacion) {
		this.capacidad = capacidad;
		this.nombre = nombre;
		this.ubicacion = ubicacion;
		this.eventos = new HashMap<LocalDate, Evento>();
	}


	public HashMap<LocalDate, Evento> getEventos() {
		return eventos;
	}


	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}


	public int getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getUbicacion() {
		return ubicacion;
	}


	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}


	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Venue{");
	    sb.append("nombre='").append(nombre).append('\'');
	    sb.append(", ubicacion='").append(ubicacion).append('\'');
	    sb.append(", capacidad=").append(capacidad);
	    sb.append('}');
	    return sb.toString();
	}

}
