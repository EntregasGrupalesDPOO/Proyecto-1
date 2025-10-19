package logica;

import java.util.ArrayList;
import java.util.List;

public class TiqueteDeluxe extends Tiquete{
	
	// lista con los beneficios del paquete
	private ArrayList<String> paquete ;
	private ArrayList<Tiquete>  cortesias;
	public static double precio;
	public static final String TIQUETEDELUXE  = "TIQUETEDELUXE";
	private int idSilla;
	
	public TiqueteDeluxe(Integer idLocalidad, Evento evento, Usuario usuario) {
		super(idLocalidad, evento, usuario);
		this.tipo = TIQUETEDELUXE;
		this.precioReal = precio;
		this.idSilla = -1;
	}
	
}
