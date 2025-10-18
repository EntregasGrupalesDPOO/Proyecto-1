package logica;
import java.util.ArrayList;
import java.util.HashMap;

public class Organizador extends Usuario {
	protected HashMap<Evento,HashMap<Localidad,Integer>> ganancias;
	
	public HashMap<Evento,HashMap<Localidad,Integer>> getGanancias(){
		return ganancias;
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
