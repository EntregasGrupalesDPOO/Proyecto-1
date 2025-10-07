package logica;

import java.util.List;

public class TiqueteDeluxe extends TiqueteMultiple{
	
	// lista con los beneficios del paquete
	private List<String> paquete ;

	public static final String TIQUETEDELUXE  = "TIQUETEDELUXE";
	public TiqueteDeluxe(Localidad localidad, int id, Usuario dueno, double precioBase, double comision, List<String> paquete) {
		super(localidad, id, dueno, precioBase, comision);
		this.tipo = TiqueteDeluxe.TIQUETEDELUXE;
		this.transferible = false;
		this.paquete = paquete;
		
		
	}
	public List<String> getPaquete() {
		return paquete;
	}
	
	
	
	



}
