package logica;

import java.util.ArrayList;
import java.util.List;

public class TiqueteMultiple extends Tiquete{
	protected boolean transferible = true;
	protected List<Tiquete> tiquetes;
	protected int precioReal;
	public static final String TIQUETEMULTIPLE = "TIQUETEMULTIPLE";
	
	public TiqueteMultiple(Localidad localidad, int id, Usuario dueno, double precioBase, double comision) {
		super(localidad, id, dueno, precioBase, comision);
		this.tiquetes = new ArrayList<Tiquete>();
		this.precioReal = 0;
	}

	public void marcarTodos() {
		for (Tiquete tiquete: tiquetes) {
			tiquete.marcarTiquete();
		}
		
	
	}
}
