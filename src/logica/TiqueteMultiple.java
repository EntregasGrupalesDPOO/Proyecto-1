package logica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public abstract class TiqueteMultiple extends Tiquete{
	protected static int tiquetesMaximosPorTransaccionMultiples;
	protected ArrayList<Tiquete> tiquetes;
	
	public TiqueteMultiple(double precioBase, double cargoPorServicio, LocalDate fecha, LocalTime hora, int cantidad) {
		super(precioBase, cargoPorServicio, fecha, hora);
		this.precioReal = precioBase * (1 + cargoPorServicio) + impresion * cantidad;
		this.tiquetes = new ArrayList<Tiquete>();
		if (TiqueteMultiple.tiquetesMaximosPorTransaccionMultiples==0){
			TiqueteMultiple.tiquetesMaximosPorTransaccionMultiples=5;
		}
	}
	
	@Override
	public void setComprado(boolean comprado) {
		this.comprado=comprado;
		for (Tiquete t : tiquetes) {
			t.setComprado(comprado);
		}
	}
	
	@Override
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		for (Tiquete t : tiquetes) {
			t.setCliente(cliente);
		}
	}
	
	public Tiquete getTiquete (Tiquete tiquete) {
		for (Tiquete t : tiquetes) {
			if (t.equals(tiquete)) {
				return t;
			}
		}
		return null;
	}
	public ArrayList<Tiquete> getTiquetes() {
		return new ArrayList<Tiquete>(this.tiquetes);
	}
	
	public static int getTiquetesMaximosPorTransaccion() {
		return tiquetesMaximosPorTransaccionMultiples;
	}
	
	public static void setTiquetesMaximosPorTransaccion(int tiquetesMaximosPorTransaccion) {
		tiquetesMaximosPorTransaccionMultiples = tiquetesMaximosPorTransaccion;
	}
}
