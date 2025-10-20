	package logica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Evento {
	private String nombre;
	private LocalDate fecha;
	private LocalTime hora;
	private Venue venue;
	private Organizador organizador;
	private HashMap<Integer, Localidad> localidades;
	public static final String MUSICAL = "MUSICAL";
	public static final String DEPORTIVO = "DEPORTIVO";
	public static final String RELIGIOSO  = "RELIGIOSO";
	public static final String CULTURAL  = "CULTURAL";



	private String estado;
	private String tipoEvento;
	public static HashMap<String, Double> comisionEventos;
	
	
	
	public Evento(String nombre, LocalDate fecha, LocalTime hora, Venue venue, Organizador organizador,
			HashMap<Integer, Localidad> localidades, String tipoEvento) {
		this.nombre = nombre;
		this.fecha = fecha;
		this.hora = hora;
		this.venue = venue;
		this.organizador = organizador;
		this.localidades = localidades;
		this.estado = "CREADO";
		this.tipoEvento = tipoEvento;
	}

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

	public HashMap<Integer, Localidad> getLocalidades() {
		return localidades;
	}

	public String getEstado() {
		return estado;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	
	

}
