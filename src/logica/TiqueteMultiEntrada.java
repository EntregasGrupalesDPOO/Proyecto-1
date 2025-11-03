package logica;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class TiqueteMultiEntrada extends TiqueteMultiple implements Serializable{

	public TiqueteMultiEntrada(double precioBase, double cargoPorServicio, LocalDate fecha, LocalTime hora,
			int cantidad) {
		super(precioBase, cargoPorServicio, fecha, hora, cantidad);
		crearTiquetes(cantidad, cliente);
	}
	private void crearTiquetes(int cantidad, Cliente cliente) {
		for (int i = 0; i < cantidad; i++) {
			this.tiquetes.add(new TiqueteBasico(this.precioBase/cantidad, this.cargoPorServicio, this.fecha, this.hora));
		}
	}
}
