package logica;
import java.util.ArrayList;
import java.util.HashMap;

public class Organizador extends Usuario {
	protected HashMap<Evento,HashMap<Localidad,Integer>> ganancias;
	
	public HashMap<Evento,HashMap<Localidad,Integer>> getGanancias(){
		return ganancias;
	}
	
	@Override
	public void comprarTiquetes(int cantidad, Evento evento, Integer idLocalidad, boolean usarSaldo) {
		for (int i = 0;i < cantidad; i++) {
			if (this.tiquetes.get(evento).equals(null)) {
				this.tiquetes.put(evento, new ArrayList<Tiquete>());
			}
			Tiquete nuevoTiquete = new TiqueteBasico(idLocalidad, evento, this);
			this.tiquetes.get(evento).addLast(nuevoTiquete);
			}
	}

	@Override
	public void comprarTiquetesEnumerados(int cantidad, Evento evento, Integer idLocalidad, int idSilla, boolean usarSaldo) {
		for (int i = 0;i < cantidad; i++) {
			if (this.tiquetes.get(evento).equals(null)) {
				this.tiquetes.put(evento, new ArrayList<Tiquete>());
			}
			Tiquete nuevoTiquete = new TiqueteEnumerado(idLocalidad, evento, this, idSilla);
			this.tiquetes.get(evento).addLast(nuevoTiquete);
			}
	}
	
	@Override
	public void comprarTiquetesMultiplesUE(int cantidad, Evento evento, Integer idLocalidad, boolean usarSaldo {
		TiqueteMultiple nuevoTM = new TiqueteMultipleUnicoEvento(evento, idLocalidad, cantidad, this);
		tiquetesMultiples.put(nuevoTM.getId(), nuevoTM);
	}
	
	@Override
	public void comprarTiquetesMultiplesVE(HashMap<Evento,Integer> eventos, boolean usarSaldo) {
		TiqueteMultiple nuevoTM = new TiqueteMultipleVariosEventos(eventos, this);
		tiquetesMultiples.put(nuevoTM.getId(), nuevoTM);
	}
	
	@Override
	public void comprarTiquetesDeluxe(int cantidad, Evento evento, Integer idLocalidad) {
		for (int i = 0;i < cantidad; i++) {
			if (this.tiquetes.get(evento).equals(null)) {
				this.tiquetes.put(evento, new ArrayList<Tiquete>());
			}
			Tiquete nuevoTiquete = new TiqueteDeluxe(idLocalidad, evento, this);
			this.tiquetes.get(evento).addLast(nuevoTiquete);
			}
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    
	    sb.append("Organizador: ");
	    if (this.getLogin() != null) {
	        sb.append(this.getLogin());
	    } else {
	        sb.append("Desconocido");
	    }
	    sb.append("\nGanancias:\n");
	    
	    if (ganancias != null && !ganancias.isEmpty()) {
	        for (Evento evento : ganancias.keySet()) {
	            sb.append("Evento: ").append(evento.getNombre()).append("\n");
	            HashMap<Localidad, Integer> locGanancias = ganancias.get(evento);
	            for (Localidad loc : locGanancias.keySet()) {
	                sb.append("  Localidad: ").append(loc.getNombre())
	                  .append(", Ganancia: ").append(locGanancias.get(loc)).append("\n");
	            }
	        }
	    } else {
	        sb.append("  No hay ganancias registradas.\n");
	    }
	    
	    return sb.toString();
	}
	
	public void crearEvento() {
		
	}
	
}
