package logica;

import java.util.ArrayList;
import java.util.HashMap;

public class TiqueteMultipleUnicoEvento extends TiqueteMultiple{
	private ArrayList<Tiquete> tiquetes;
	public static HashMap<Integer,HashMap<Integer,Double>> precios;
	private Evento evento;
	public static final String MULTIPLEUNICOEVENTO = "MULTIPLEUNICOEVENTO";
	
	public TiqueteMultipleUnicoEvento(Evento evento, Integer idLocalidad, int cantidadTiquetes, Usuario usuario) {
		super();
		this.evento = evento;
		this.tipo = MULTIPLEUNICOEVENTO;
		double nuevoPrecio = precios.get(idLocalidad).get(cantidadTiquetes)/cantidadTiquetes;
		for (int i = 0;i < cantidadTiquetes; i++) {
			Tiquete nuevoTiquete = new TiqueteBasico(idLocalidad, evento, usuario);
			nuevoTiquete.setPrecioReal(nuevoPrecio * (1 + Tiquete.tiposEventos.get(evento.getTipoEvento())) + Tiquete.getImpresion());
			this.tiquetes.addLast(nuevoTiquete);
			}
		evento.getLocalidades().get(idLocalidad).getTiquetesMultiplesUsados().put(this.id, this);
	}

	public ArrayList<Tiquete> getTiquetes() {
		return tiquetes;
	}

	public void setTiquetes(ArrayList<Tiquete> tiquetes) {
		this.tiquetes = tiquetes;
	}

	public static HashMap<Integer, HashMap<Integer, Double>> getPrecios() {
		return precios;
	}

	public static void setPrecios(HashMap<Integer, HashMap<Integer, Double>> precios) {
		TiqueteMultipleUnicoEvento.precios = precios;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public static String getMultipleunicoevento() {
		return MULTIPLEUNICOEVENTO;
	}
	
	
}
