package logica;

import java.util.HashMap;
import java.util.Map;

import Exepciones.LocalidadNoExisteException;
import Exepciones.TiqueteNoEncontradoException;

public class TiqueteMultiEvento extends TiqueteMultiple {
	private static final Map<Integer, Double> DESCUENTOS = new HashMap<>();
    static {
        DESCUENTOS.put(1, 0.0);  
        DESCUENTOS.put(2, 0.05);  
        DESCUENTOS.put(3, 0.10);  
        DESCUENTOS.put(4, 0.15);  
    }
	
    public TiqueteMultiEvento(HashMap<Evento, String> eventos, Cliente cliente) throws TiqueteNoEncontradoException, LocalidadNoExisteException {
        // No tiene un solo precioBase ni una sola fecha, así que se pasan valores neutros
        super(0, 0, null, null, eventos.size());
        this.tipoTiquete = "MULTI_EVENTO";

        asociarTiquetes(eventos);
        calcularPrecioTotal(); 
    } 
	
	private void asociarTiquetes(HashMap<Evento, String> eventos) throws TiqueteNoEncontradoException, LocalidadNoExisteException {
		for (Map.Entry<Evento, String> entry : eventos.entrySet()) {
		    Evento evento = entry.getKey();
		    String nombreLocalidad = entry.getValue();
		    Localidad l = evento.getLocalidadPorNombre(nombreLocalidad);
		    if(l != null) {
	    		Tiquete tiquete = l.obtenerTiqueteDisponible();
	    		if (tiquete != null) {
		    		this.tiquetes.add(tiquete);
	    		} else {
		    		throw new TiqueteNoEncontradoException(-1);
		    	}
		    } else {
	    		throw new LocalidadNoExisteException(nombreLocalidad);
	    	}
		}
	}
	
	private void calcularPrecioTotal() {
        double sumaPreciosBase = 0;
        double sumaPreciosReales = 0;

        for (Tiquete t : this.tiquetes) {
            sumaPreciosBase += t.getPrecioBase();
            sumaPreciosReales += t.getPrecioReal();
        }

        int cantidad = this.tiquetes.size();
        double descuento = DESCUENTOS.getOrDefault(cantidad, DESCUENTOS.get(4));
        this.precioBase = sumaPreciosBase;
        this.precioReal = (sumaPreciosReales * (1 - descuento));
        

        for (Tiquete t : this.tiquetes) {
            t.setPrecioReal(t.getPrecioReal() * (1 - descuento));
        }
    }
	
}
