package logica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.io.Serializable;
import Exepciones.TiqueteUsadoException;
 

public abstract class Tiquete implements Serializable {
	protected Localidad localidad;
	protected String idUsuario;
	public static double impresion;
	protected LocalDate fecha;
	protected LocalTime hora;
	protected final Integer id;
	protected Usuario usuario;
	protected double precioBase;
	protected boolean usado;
	protected Evento evento; 
	protected String tipo; 
	protected static HashMap<String, Double> tiposEventos;
	protected double precioReal;
	public static int tiquetesMax = 10;
	
	
	public Tiquete(Integer idLocalidad, Evento evento, Usuario usuario) {
		this.id = 1 + evento.getLocalidades().get(idLocalidad).getTiquetesUsados().size();
		this.localidad = evento.getLocalidades().get(idLocalidad);
		this.idUsuario = usuario.getLogin();
		this.fecha = evento.getFecha();
		this.hora = evento.getHora();
		this.usuario = usuario;
		this.precioBase = evento.getLocalidades().get(idLocalidad).getPrecioTiquete();
		this.evento = evento;
		this.usado = false;
		if (usuario.equals(evento.getOrganizador())) {
			this.precioBase = 0;
			this.precioReal = 0;
		} else {
			this.precioReal = this.precioBase * (1 + tiposEventos.get(evento.getTipoEvento())) + impresion;
		}
		evento.getLocalidades().get(idLocalidad).getTiquetesUsados().put(this.id, this);
	}
	
	public static double calcularPrecioReal(Integer idLocalidad, Evento evento) {
		return evento.getLocalidades().get(idLocalidad).getPrecioTiquete() * (1 + tiposEventos.get(evento.getTipoEvento())) + impresion;
	} 
	
	public void  marcarTiquete() throws TiqueteUsadoException{
		if (this.usado) {
			throw(new TiqueteUsadoException(this));
		}
		this.usado = false;
	}
	public Localidad getLocalidad() {
		return localidad;
	}

	public LocalDate getFecha() {
		return fecha;
	}
	public LocalTime getHora() {
		return hora;
	}
	public Integer getId() {
		return id;
	} 

	public double getPrecioBase() {
		return precioBase;
	}

	public boolean isUsado() {
		return usado;
	}

	public String getTipo() {
		return this.tipo;
	}
	public static double getImpresion() {
		return impresion;
	}
	public static void setImpresion(double impresion) {
		Tiquete.impresion = impresion;
	}

	public void setPrecioReal(double precioReal) {
		this.precioReal = precioReal;
	}
	
	public Evento getEvento() {
		return this.evento;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public double getPrecioReal() {
		return precioReal;
	}

	public void setPrecioBase(double precioBase) {
		this.precioBase = precioBase;
	}
	
}
