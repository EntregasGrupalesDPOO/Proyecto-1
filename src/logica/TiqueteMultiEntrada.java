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
		for (int i = 0; i < cantidad; i++) {
			this.tiquetes.add(new TiqueteBasico(this.precioBase/cantidad, this.cargoPorServicio, this.fecha, this.hora));
		}
	}
}
