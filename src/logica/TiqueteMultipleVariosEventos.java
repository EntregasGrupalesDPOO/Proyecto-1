package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import Exepciones.CapacidadLocalidadExcedidaException;

public class TiqueteMultipleVariosEventos extends TiqueteMultiple{
	protected HashMap<Evento, Tiquete> tiquetes;
	public static final String MULTIPLEVARIOSEVENTOS = "MULTIPLEVARIOSEVENTOS";
	public static HashMap<Integer, Double> precios;
	
	public TiqueteMultipleVariosEventos(HashMap<Evento, Integer> eventos, Usuario usuario) throws CapacidadLocalidadExcedidaException {
		super();
		int cantidadTiquetes = eventos.size();
		double nuevoPrecio = precios.get(cantidadTiquetes)/cantidadTiquetes;
		this.tipo = MULTIPLEVARIOSEVENTOS;
		Set<Evento> llaves = eventos.keySet();
		for (Evento llave : llaves) {
			if (!(llave.getLocalidades().get(eventos.get(llave)).compararCapacidad(1))) {
				throw new CapacidadLocalidadExcedidaException(1);
			}
		}for (Evento llave : llaves) {
			Tiquete nuevoTiquete = new TiqueteBasico(eventos.get(llave), llave, usuario);
			nuevoTiquete.setPrecioBase(nuevoPrecio);
			nuevoTiquete.setPrecioReal(nuevoPrecio * (1 + Tiquete.tiposEventos.get(llave.getTipoEvento())) + Tiquete.getImpresion());
			tiquetes.put(llave, nuevoTiquete);
		}
	}

	public HashMap<Evento, Tiquete> getTiquetes() {
		return tiquetes;
	}
	
	public static double calcularPrecioReal(HashMap<Evento, Integer> eventos) {
		double valor = 0;
		int cantidadTiquetes = eventos.size();
		double nuevoPrecio = precios.get(cantidadTiquetes)/cantidadTiquetes;
		Set<Evento> llaves = eventos.keySet();
		for (Evento llave : llaves) {
			valor += nuevoPrecio * (1 + Tiquete.tiposEventos.get(llave.getTipoEvento())) + Tiquete.getImpresion();
		}
		return valor;
	}
	
	public void setTiquetes(HashMap<Evento, Tiquete> tiquetes) {
		this.tiquetes = tiquetes;
	}

	public static HashMap<Integer, Double> getPrecios() {
		return precios;
	}

	public static void setPrecios(HashMap<Integer, Double> precios) {
		TiqueteMultipleVariosEventos.precios = precios;
	}

	public static String getMultiplevarioseventos() {
		return MULTIPLEVARIOSEVENTOS;
	}
	
}
