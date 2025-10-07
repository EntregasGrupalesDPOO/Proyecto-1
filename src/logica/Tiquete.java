package logica;

import java.time.LocalDate;
import java.time.LocalTime;

import Exepciones.TiqueteUsadoException;
 

public abstract class Tiquete {
	protected Localidad localidad;
	protected String idDueno;
	public static final double IMPRESION = 20;
	protected LocalDate fecha;
	protected LocalTime hora;
	protected int id;
	protected Usuario dueno;
	protected double precioBase;
	protected double comision;
	protected boolean usado;
	protected Evento evento;
	protected String tipo;
	
	
	public Tiquete(Localidad localidad, int id, Usuario dueno,
			double precioBase, double comision) {
		this.localidad = localidad;
		this.idDueno = dueno.getLogin();
		this.fecha = evento.getFecha();
		this.hora = evento.getHora();
		this.id = id;
		this.dueno = dueno;
		this.precioBase = precioBase;
		this.comision = comision;
		this.usado = false;
	}
	@Override
	public String toString() {
		return "Tiquete [localidad=" + localidad + ", idDueno=" + idDueno + ", fecha=" + fecha + ", hora=" + hora
				+ ", id=" + id + ", dueno=" + dueno + ", precioBase=" + precioBase + ", comision=" + comision
				+ ", usado=" + usado + "]";
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
	public String getIdDueno() {
		return idDueno;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public LocalTime getHora() {
		return hora;
	}
	public int getId() {
		return id;
	}
	public Usuario getDueno() {
		return dueno;
	}
	public double getPrecioBase() {
		return precioBase;
	}
	public double getComision() {
		return comision;
	}
	public boolean isUsado() {
		return usado;
	}
	public void setDueno(Usuario dueno) {
		this.dueno = dueno;
	}
	public String getTipo() {
		return this.tipo;
	}
	
	
	
}
