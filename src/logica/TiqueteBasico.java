package logica;

import java.time.LocalDate;
import java.time.LocalTime;

public class TiqueteBasico extends Tiquete{
	private static final String BASICO = "BASICO";
	
	public TiqueteBasico(double precioBase, double cargoPorServicio, LocalDate fecha, LocalTime hora) {
		super(precioBase, cargoPorServicio, fecha, hora);
		this.tipoTiquete = BASICO;
	}

}
