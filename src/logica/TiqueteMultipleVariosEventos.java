package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class TiqueteMultipleVariosEventos extends TiqueteMultiple{
	protected HashMap<Evento, Tiquete> tiquetes;
	public static final String MULTIPLEVARIOSEVENTOS = "MULTIPLEVARIOSEVENTOS";
	
	public TiqueteMultipleVariosEventos(HashMap<Evento, Integer> eventos, Usuario usuario) {
		super();
		int cantidadTiquetes = eventos.size();
		double nuevoPrecio = this.precioBase/cantidadTiquetes;
		this.tipo = MULTIPLEVARIOSEVENTOS;
		Set<Evento> llaves = eventos.keySet();
		for (Evento llave : llaves) {
			Tiquete nuevoTiquete = new TiqueteBasico(eventos.get(llave), llave, usuario);
			nuevoTiquete.setPrecioReal(nuevoPrecio * (1 + Tiquete.tiposEventos.get(llave.getTipoEvento())) + Tiquete.getImpresion());
			tiquetes.put(llave, nuevoTiquete);
		}
	}
}
