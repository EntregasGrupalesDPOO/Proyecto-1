package logica;

import java.util.ArrayList;
import java.util.HashMap;

public class Cliente extends Usuario {
	
	@Override
	public void comprarTiquetes(int cantidad, Evento evento, Integer idLocalidad) {
		for (int i = 0;i < cantidad; i++) {
			if (this.tiquetes.get(evento).equals(null)) {
				this.tiquetes.put(evento, new ArrayList<Tiquete>());
			}
			Tiquete nuevoTiquete = new TiqueteBasico(idLocalidad, evento, this);
			this.tiquetes.get(evento).addLast(nuevoTiquete);
			}
	}

	@Override
	public void comprarTiquetesEnumerados(int cantidad, Evento evento, Integer idLocalidad, int idSilla) {
		for (int i = 0;i < cantidad; i++) {
			if (this.tiquetes.get(evento).equals(null)) {
				this.tiquetes.put(evento, new ArrayList<Tiquete>());
			}
			Tiquete nuevoTiquete = new TiqueteEnumerado(idLocalidad, evento, this, idSilla);
			this.tiquetes.get(evento).addLast(nuevoTiquete);
			}
	}
	
	@Override
	public void comprarTiquetesMultiplesUE(int cantidad, Evento evento, Integer idLocalidad) {
		TiqueteMultiple nuevoTM = new TiqueteMultipleUnicoEvento(evento, idLocalidad, cantidad, this);
		tiquetesMultiples.put(nuevoTM.getId(), nuevoTM);
	}
	
	@Override
	public void comprarTiquetesMultiplesVE(HashMap<Evento,Integer> eventos) {
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

}
