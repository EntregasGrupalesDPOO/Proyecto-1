package logica;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class TiqueteEnumerado extends Tiquete implements Serializable{
	private int idSilla;
	public static final String ENUMERADO = "ENUMERADO";

	public TiqueteEnumerado(double precioBase, double cargoPorServicio, LocalDate fecha, LocalTime hora, int idSilla) {
		super(precioBase, cargoPorServicio, fecha, hora);
		this.tipoTiquete = ENUMERADO;
		this.idSilla = idSilla;
	}

	public int getIdSilla() {
		return idSilla;
	}
	
	
}
