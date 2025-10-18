package logica;

import java.util.ArrayList;

public class TiqueteMultipleUnicoEvento extends TiqueteMultiple{
	private ArrayList<Tiquete> tiquetes;
	private Evento evento;
	public static final String MULTIPLEUNICOEVENTO = "MULTIPLEUNICOEVENTO";
	
	public TiqueteMultipleUnicoEvento(Evento evento, Integer idLocalidad, int cantidadTiquetes, Usuario usuario) {
		super();
		this.evento = evento;
		this.tipo = MULTIPLEUNICOEVENTO;
		double nuevoPrecio = this.precioBase/cantidadTiquetes;
		for (int i = 0;i < cantidadTiquetes; i++) {
			Tiquete nuevoTiquete = new TiqueteBasico(idLocalidad, evento, usuario);
			nuevoTiquete.setPrecioReal(nuevoPrecio * (1 + Tiquete.tiposEventos.get(evento.getTipoEvento())) + Tiquete.getImpresion());
			this.tiquetes.addLast(nuevoTiquete);
			}
	}
	
	
}
