package logica;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Tiquete {
	protected int id;
	protected double precioBase;
	protected double cargoPorServicio;
	protected static double impresion;
	protected double precioReal;
	protected LocalDate fecha;
	protected LocalTime hora;
	protected static int tiquetesTotales;
	protected String tipoTiquete;
	protected boolean comprado;
	protected boolean transferible;
	protected Cliente cliente;
	protected static int tiquetesMaximosPorTransaccion;
	
	public Tiquete(double precioBase, double cargoPorServicio, LocalDate fecha,
			LocalTime hora) {
		this.id = tiquetesTotales++;
		this.precioBase = precioBase;
		this.cargoPorServicio = cargoPorServicio;
		this.fecha = fecha;
		this.hora = hora;
		this.comprado = false;
		this.precioReal = impresion + precioBase * (1 + cargoPorServicio);
		this.transferible = true;
	}

	public boolean isComprado() {
		return comprado;
	}
	
	public void actualizarPrecios(double precio) {
		this.precioBase = precio;
		this.precioReal = impresion + precioBase * (1 + cargoPorServicio);
	}

	public void setComprado(boolean comprado) {
		this.comprado = comprado;
	}

	public void setTransferible(boolean transferible) {
		this.transferible = transferible;
	}

	public double getPrecioReal() {
		return precioReal;
	}

	public double getPrecioBase() {
		return precioBase;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean isTransferible() {
		return transferible;
	}

	public int getId() {
		return id;
	}

	public LocalDate getFecha() {
		return fecha;
	}
	
	public static void setImpresion(double valor) {
		impresion = valor;
	}
	
	public void setPrecioReal(double precio) {
		this.precioReal = precio;
	}
	
	public void setPrecioBase(double precio) {
		this.precioBase = precio;
	}

	public static int getTiquetesMaximosPorTransaccion() {
		return tiquetesMaximosPorTransaccion;
	}

	public static void setTiquetesMaximosPorTransaccion(int tiquetesMaximosPorTransaccion) {
		Tiquete.tiquetesMaximosPorTransaccion = tiquetesMaximosPorTransaccion;
	}
}
