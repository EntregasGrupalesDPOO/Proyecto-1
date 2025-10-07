package logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Exepciones.TiqueteUsadoException;

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

	public void marcarTodos() throws TiqueteUsadoException {
		for (Tiquete tiquete: tiquetes) {
			tiquete.marcarTiquete();


			}
		}
	
	public int totalTiquetes() {
        return tiquetes.size();
    }

    public int totalUsados() {
        return (int) tiquetes.stream().filter(t -> t.usado).count();
    }

    public int totalSinUsar() {
        return totalTiquetes() - totalUsados();
    }
	
    public List<Tiquete> getUsados() {
        return tiquetes.stream()
                .filter(t -> t.usado)
                .toList();
    }

    public List<Tiquete> getSinUsar() {
        return tiquetes.stream()
                .filter(t -> !t.usado)
                .toList();
    }
	

			
		
	
	public void agregarTiquete(Tiquete tiquete) {

		tiquetes.add(tiquete);
	
	}

		public boolean isTransferible() {
			return transferible;
		}

	    public List<Tiquete> getTiquetes() {
	        return Collections.unmodifiableList(tiquetes);
	    }
		public int getPrecioReal() {
			return precioReal;
		}
		
	
}
