package logica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Evento {
	private String nombre;
	private LocalDate fecha;
	private LocalTime hora;
	private Venue venue;
	private Organizador organizador;
	private List<Localidad> localidades;
	private String estado;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
	    sb.append("Evento{");
	    sb.append("nombre='").append(nombre).append('\'');
	    sb.append(", fecha=").append(fecha);
	    sb.append(", hora=").append(hora);
	    sb.append(", venue=").append(venue); 
	    sb.append(", organizador=").append(organizador); 
	    sb.append(", localidades=").append(localidades); 
	    sb.append(", estado='").append(estado).append('\'');
	    sb.append('}');
	    return sb.toString();
	}

	public String getNombre() {
		return nombre;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public Venue getVenue() {
		return venue;
	}

	public Organizador getOrganizador() {
		return organizador;
	}

	public List<Localidad> getLocalidades() {
		return localidades;
	}

	public String getEstado() {
		return estado;
	}
	
	

}
