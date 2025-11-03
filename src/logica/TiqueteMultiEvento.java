package logica;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TiqueteMultiEvento extends TiqueteMultiple implements Serializable {
	private static final Map<Integer, Double> DESCUENTOS = new HashMap<>();
    static {
        DESCUENTOS.put(1, 0.0);  
        DESCUENTOS.put(2, 0.05);  
        DESCUENTOS.put(3, 0.10);  
        DESCUENTOS.put(4, 0.15);  
    }
	
    public TiqueteMultiEvento(HashMap<Evento, String> eventos, Cliente cliente) {
        // No tiene un solo precioBase ni una sola fecha, así que se pasan valores neutros
        super(0, 0, null, null, eventos.size());
        this.tipoTiquete = "MULTI_EVENTO";

        asociarTiquetes(eventos);
        calcularPrecioTotal();
    }
	
	private void asociarTiquetes(HashMap<Evento, String> eventos) {
		for (Map.Entry<Evento, String> entry : eventos.entrySet()) {
		    Evento evento = entry.getKey();
		    String nombreLocalidad = entry.getValue();
		    Localidad l = evento.getLocalidadPorNombre(nombreLocalidad);
		    if(l != null) {
	    		Tiquete tiquete = l.obtenerTiqueteDisponible();
	    		if (tiquete != null) {
		    		this.tiquetes.add(tiquete);
		    	}
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
        this.precioReal = (sumaPreciosReales * (1 - descuento)) + impresion;

        for (Tiquete t : this.tiquetes) {
            t.actualizarPrecios(t.getPrecioReal() * (1 - descuento));
        }
    }

}
