package logica;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Organizador extends Usuario {
	
	protected HashMap<String,Evento> eventosCreados;
	
	public Organizador(String login, String contrasena) {
		super(login, contrasena);
	}
	
	public Evento crearEvento(String nombreEvento, LocalDate fecha, LocalTime hora, Venue venue, HashMap<Integer, Localidad> localidades, String tipoEvento) {
		Evento evento = new Evento(nombreEvento, fecha, hora, venue, this, localidades, tipoEvento);
		eventosCreados.put(evento.getNombre(), evento);
		return evento;
	}
	
	public Venue crearVenue(int capacidad, String nombre, String ubicacion) {
		return new Venue(capacidad, nombre, ubicacion);
	}
	
	public Localidad crearLocalidad (String nombre, double precioTiquete, int capacidad, boolean numerada, Venue venue, Evento evento) {
		return new Localidad(nombre, precioTiquete, capacidad, numerada, venue, evento);
	}
	
	public double obtenerGananciasGlobales() {
		Set<Evento> eventos = (Set<Evento>) eventosCreados.values();
		double ganancias = 0;
		for (Evento evento:eventos) {
			Set<Localidad> localidades = (Set<Localidad>) evento.getLocalidades().values();
			for (Localidad localidad:localidades) {
				for(Tiquete tiq: localidad.getTiquetesUsados().values()) {
					ganancias += tiq.precioBase;
				}
				for (TiqueteMultiple tiqM: localidad.getTiquetesMultiplesUsados().values()) {
					if (tiqM instanceof TiqueteMultipleUnicoEvento) {
						TiqueteMultipleUnicoEvento tiqueteMultipleUE = (TiqueteMultipleUnicoEvento) tiqM;
						for (Tiquete tiq: tiqueteMultipleUE.getTiquetes()) {
							ganancias += tiq.precioBase;
						}
					} else if (tiqM instanceof TiqueteMultipleVariosEventos) {
						TiqueteMultipleVariosEventos tiqueteMultipleVE = (TiqueteMultipleVariosEventos) tiqM;
						for (Tiquete tiq: tiqueteMultipleVE.getTiquetes().values()) {
							ganancias += tiq.precioBase;
						}
					}
				}
			}
		}
		return ganancias;
	}
	
	public double obtenerGananciasEvento(Evento evento) {
		double ganancias = 0;
		Set<Localidad> localidades = (Set<Localidad>) evento.getLocalidades().values();
		for (Localidad localidad:localidades) {
			for(Tiquete tiq: localidad.getTiquetesUsados().values()) {
				ganancias += tiq.precioBase;
			}
			for (TiqueteMultiple tiqM: localidad.getTiquetesMultiplesUsados().values()) {
				if (tiqM instanceof TiqueteMultipleUnicoEvento) {
					TiqueteMultipleUnicoEvento tiqueteMultipleUE = (TiqueteMultipleUnicoEvento) tiqM;
					for (Tiquete tiq: tiqueteMultipleUE.getTiquetes()) {
						ganancias += tiq.precioBase;
					}
				} else if (tiqM instanceof TiqueteMultipleVariosEventos) {
					TiqueteMultipleVariosEventos tiqueteMultipleVE = (TiqueteMultipleVariosEventos) tiqM;
					for (Tiquete tiq: tiqueteMultipleVE.getTiquetes().values()) {
						ganancias += tiq.precioBase;
					}
				}
			}
		}
		return ganancias;
	}
	
	public double obtenerGananciasLocalidad(Evento evento, Localidad localidad) {
		double ganancias = 0;
			for(Tiquete tiq: localidad.getTiquetesUsados().values()) {
				ganancias += tiq.precioBase;
			}
			for (TiqueteMultiple tiqM: localidad.getTiquetesMultiplesUsados().values()) {
				if (tiqM instanceof TiqueteMultipleUnicoEvento) {
					TiqueteMultipleUnicoEvento tiqueteMultipleUE = (TiqueteMultipleUnicoEvento) tiqM;
					for (Tiquete tiq: tiqueteMultipleUE.getTiquetes()) {
						ganancias += tiq.precioBase;
					}
				} else if (tiqM instanceof TiqueteMultipleVariosEventos) {
					TiqueteMultipleVariosEventos tiqueteMultipleVE = (TiqueteMultipleVariosEventos) tiqM;
					for (Tiquete tiq: tiqueteMultipleVE.getTiquetes().values()) {
						ganancias += tiq.precioBase;
					}
				}
			}
		return ganancias;
	}
	
	public record Tupla<A, B>(A primero, B segundo) {}
	public Tupla<Evento, String> solicitarCancelarEvento(Evento evento, String razon) {
		return new Tupla<>(evento, razon);
	}
	
	
}
