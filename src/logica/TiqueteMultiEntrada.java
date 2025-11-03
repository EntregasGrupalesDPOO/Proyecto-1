package logica;

import java.time.LocalDate;
import java.time.LocalTime;

public class TiqueteMultiEntrada extends TiqueteMultiple{
	private final static String MULTIENTRADA = "MULTIENTRADA";
	
	public TiqueteMultiEntrada(double precioBase, double cargoPorServicio, LocalDate fecha, LocalTime hora,
			int cantidad) {
		super(precioBase, cargoPorServicio, fecha, hora, cantidad);
		this.tipoTiquete = MULTIENTRADA;
		crearTiquetes(cantidad, cliente);
	}
	private void crearTiquetes(int cantidad, Cliente cliente) {
		double nuevoPrecioBase = calcularPrecioBase(cantidad);
		for (int i = 0; i < cantidad; i++) {
			this.tiquetes.add(new TiqueteBasico(nuevoPrecioBase, this.cargoPorServicio, this.fecha, this.hora));
		}
	}
	
	private double calcularPrecioBase(int cantidad) {
		double precio = this.getPrecioReal();
		precio = precio/cantidad;
		precio = precio - Tiquete.impresion;
		precio = precio/(1 + this.cargoPorServicio);
		return precio;
	}
}
