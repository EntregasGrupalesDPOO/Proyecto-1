package logica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class  Organizador extends Cliente{
	private ArrayList<Evento> eventos;
	private Administrador administrador;
	private final static String ORGANIZADOR = "ORGANIZADOR";
	
	public Organizador(String login, String contrasena, Administrador administrador) {
		super(login, contrasena);
		this.tipoCliente = ORGANIZADOR;
		this.eventos = new ArrayList<Evento>();
		this.administrador = administrador;
	}
	
	public Evento crearEvento(Venue venue, String tipoDeEvento, LocalDate fecha, LocalTime hora) throws Exception {
		if (!venue.getEventos().get(fecha).equals(null)) {
			throw new Exception();
		}
		Evento evento = new Evento(venue, this, tipoDeEvento, fecha, hora);
		this.eventos.add(evento);
		venue.asociarFecha(fecha, evento);
		administrador.añadirEvento(evento);
		return evento;
	}
	
	public Localidad anadirLocalidadAEvento(String nombre, int capacidad, double precioTiquete, String tipoTiquete, Evento evento) throws Exception {
		if(evento.capacidadActual() + capacidad > evento.getVenue().getCapacidad()) {
			throw new Exception();
		}
		Localidad localidad = new Localidad(nombre, capacidad, precioTiquete, tipoTiquete, evento);
		return localidad;
	}

	public Localidad anadirLocalidadAEvento(String nombre, int capacidad, double precioTiquete, String tipoTiquete, Evento evento, double descuento) throws Exception {
		if(evento.capacidadActual() + capacidad > evento.getVenue().getCapacidad()) {
			throw new Exception();
		}
		Localidad localidad = new Localidad(nombre, capacidad, precioTiquete, tipoTiquete, evento, descuento);
		return localidad;
	}
	
	public Localidad anadirLocalidadAEvento(String nombre, int capacidad, double precioTiquete, String tipoTiquete, Evento evento, int capacidadTiquetesMultiples) throws Exception {
		if(evento.capacidadActual() + capacidad > evento.getVenue().getCapacidad()) {
			throw new Exception();
		}
		Localidad localidad = new Localidad(nombre, capacidad, precioTiquete, tipoTiquete, evento, capacidadTiquetesMultiples);
		return localidad;
	}
	
	public Localidad anadirLocalidadAEvento(String nombre, int capacidad, double precioTiquete, String tipoTiquete, Evento evento, double descuento, int capacidadTiquetesMultiples) throws Exception {
		if(evento.capacidadActual() + capacidad > evento.getVenue().getCapacidad()) {
			throw new Exception();
		}
		Localidad localidad = new Localidad(nombre, capacidad, precioTiquete, tipoTiquete, evento, descuento, capacidadTiquetesMultiples);
		return localidad;
	}
	
	public double consultarGananciasGlobales() {
		double ganancias = 0;
		for(Evento e: this.eventos) {
			ganancias += consultarGananciasEvento(e);
		}
		return ganancias;
	}
	
	public double consultarGananciasEvento(Evento evento) {
		double ganancias = 0;
		for (Localidad l: evento.getLocalidades()) {
			ganancias += consultarGananciasLocalidad(l);
		}
		return ganancias;
	}
	
	public double consultarGananciasLocalidad(Localidad localidad) {
		double ganancias = 0;
		for (Tiquete t: localidad.getTiquetes()) {
			if (t.isComprado() && !t.getCliente().equals(this)) {
				ganancias += t.getPrecioBase();
			}
		}
		return ganancias;
	}
	
	public double consultarPorcentajeGlobales() {
		double ganancias = cantidadTiqueteGlobalVendido();
		double total = cantidadTiqueteGlobal();
		return ganancias/total;
	}
	
	public double consultarPorcentajeEvento(Evento evento) {
		double ganancias = cantidadTiqueteEvento(evento);
		double total = cantidadTiqueteEvento(evento);
		return ganancias/total;
	}
	
	public double consultarPorcentajeLocalidad(Localidad localidad) {
		double ganancias = cantidadTiqueteLocalidadVendido(localidad);
		double total = cantidadTiqueteLocalidad(localidad);
		return ganancias/total;
	}
	
	public int cantidadTiqueteGlobal() {
		int total = 0;
		for (Evento e: this.eventos) {
			total += cantidadTiqueteEvento(e);
		}
		return total;
	}
	
	public int cantidadTiqueteEvento(Evento evento) {
		int total = 0;
		for (Localidad l:evento.getLocalidades()) {
			total += cantidadTiqueteLocalidad(l);
		}
		return total;
	}
	
	public int cantidadTiqueteLocalidad(Localidad localidad) {
		return localidad.getTiquetes().size();
	}
	
	public int cantidadTiqueteGlobalVendido() {
		int total = 0;
		for (Evento e: this.eventos) {
			total += cantidadTiqueteEventoVendido(e);
		}
		return total;
	}
	
	public int cantidadTiqueteEventoVendido(Evento evento) {
		int total = 0;
		for (Localidad l:evento.getLocalidades()) {
			total += cantidadTiqueteLocalidadVendido(l);
		}
		return total;
	}
	
	public int cantidadTiqueteLocalidadVendido(Localidad localidad) {
		int cantidad = 0;
		for(Tiquete t: localidad.getTiquetes()) {
			if(t.isComprado()) {
				cantidad++;
			}
		}
		return cantidad;
	}
}
