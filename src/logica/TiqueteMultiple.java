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
		this.tipo = TiqueteMultiple.TIQUETEMULTIPLE;
	}

	public void marcarTodos() {
		for (Tiquete tiquete: tiquetes) {
			tiquete.marcarTiquete();
		}
		}
		public void agregarTiquete(Tiquete tiquete) {
			tiquetes.add(tiquete);
		
	
	}

		public boolean isTransferible() {
			return transferible;
		}

		public List<Tiquete> getTiquetes() {
			return tiquetes;
		}

		public int getPrecioReal() {
			return precioReal;
		}
		
	
}
