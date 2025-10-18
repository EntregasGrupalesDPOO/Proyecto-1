package logica;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Usuario {
	protected String login;
	protected String contrasena;
	protected HashMap<Evento, ArrayList<Tiquete>> tiquetes;
	protected HashMap<Integer, TiqueteMultiple> tiquetesMultiples;
	protected double saldoVirtual;

	
	public void comprarTiquetes(int cantidad, Evento evento, Integer idLocalidad, boolean usarSaldo) {
		if (cantidad > Tiquete.tiquetesMax) {
			//excepcion
		}
		else if (usarSaldo == true && (this.saldoVirtual >= cantidad * evento.getLocalidades().get(idLocalidad).getPrecioTiquete())) {
			this.saldoVirtual -= cantidad * evento.getLocalidades().get(idLocalidad).getPrecioTiquete();
			for (int i = 0;i < cantidad; i++) {
				if (this.tiquetes.get(evento).equals(null)) {
					this.tiquetes.put(evento, new ArrayList<Tiquete>());
				}
				Tiquete nuevoTiquete = new TiqueteBasico(idLocalidad, evento, this);
				this.tiquetes.get(evento).addLast(nuevoTiquete);
			}
		} else if (usarSaldo == true && this.saldoVirtual < cantidad * evento.getLocalidades().get(idLocalidad).getPrecioTiquete()) {
			//excepcion
		} else {
			for (int i = 0;i < cantidad; i++) {
				if (this.tiquetes.get(evento).equals(null)) {
					this.tiquetes.put(evento, new ArrayList<Tiquete>());
				}
				Tiquete nuevoTiquete = new TiqueteBasico(idLocalidad, evento, this);
				this.tiquetes.get(evento).addLast(nuevoTiquete);
			}
		}
		
	}

	
	public void comprarTiquetesEnumerados(int cantidad, Evento evento, Integer idLocalidad, int idSilla, boolean usarSaldo) {
		if (cantidad > Tiquete.tiquetesMax) {
			//excepcion
		}
		else if (usarSaldo == true && (this.saldoVirtual >= cantidad * evento.getLocalidades().get(idLocalidad).getPrecioTiquete())) {
			this.saldoVirtual -= cantidad * evento.getLocalidades().get(idLocalidad).getPrecioTiquete();
			for (int i = 0;i < cantidad; i++) {
				if (this.tiquetes.get(evento).equals(null)) {
					this.tiquetes.put(evento, new ArrayList<Tiquete>());
				}
				Tiquete nuevoTiquete = new TiqueteEnumerado(idLocalidad, evento, this, idSilla);
				this.tiquetes.get(evento).addLast(nuevoTiquete);
				}
		}else if (usarSaldo == true && this.saldoVirtual < cantidad * evento.getLocalidades().get(idLocalidad).getPrecioTiquete()) {
			//excepcion
		} else {
			for (int i = 0;i < cantidad; i++) {
				if (this.tiquetes.get(evento).equals(null)) {
					this.tiquetes.put(evento, new ArrayList<Tiquete>());
				}
				Tiquete nuevoTiquete = new TiqueteEnumerado(idLocalidad, evento, this, idSilla);
				this.tiquetes.get(evento).addLast(nuevoTiquete);
			}
		}	
	} 
	
	
	public void comprarTiquetesMultiplesUE(int cantidad, Evento evento, Integer idLocalidad, boolean usarSaldo) {
		if (cantidad > TiqueteMultiple.tiquetesMax) {
			//excepcion
		}
		else if (usarSaldo == true && this.saldoVirtual >= TiqueteMultipleUnicoEvento.precios.get(idLocalidad).get(cantidad)) {
			this.saldoVirtual -= TiqueteMultipleUnicoEvento.precios.get(idLocalidad).get(cantidad);
			TiqueteMultiple nuevoTM = new TiqueteMultipleUnicoEvento(evento, idLocalidad, cantidad, this);
			tiquetesMultiples.put(nuevoTM.getId(), nuevoTM);
		} else if(usarSaldo == true && this.saldoVirtual >= TiqueteMultipleUnicoEvento.precios.get(idLocalidad).get(cantidad)) {
			//excepcion
		} else {
			TiqueteMultiple nuevoTM = new TiqueteMultipleUnicoEvento(evento, idLocalidad, cantidad, this);
			tiquetesMultiples.put(nuevoTM.getId(), nuevoTM);
		}
	}
	
	
	public void comprarTiquetesMultiplesVE(HashMap<Evento,Integer> eventos, boolean usarSaldo) {
		if (eventos.size() > TiqueteMultiple.tiquetesMax) {
			//excepcion
		}
		else if (usarSaldo == true && this.saldoVirtual >= TiqueteMultipleVariosEventos.precios.get(eventos.size())) {
			this.saldoVirtual -= TiqueteMultipleVariosEventos.precios.get(eventos.size());
			TiqueteMultiple nuevoTM = new TiqueteMultipleVariosEventos(eventos, this);
			tiquetesMultiples.put(nuevoTM.getId(), nuevoTM);
		} else if(usarSaldo == true && this.saldoVirtual < TiqueteMultipleVariosEventos.precios.get(eventos.size())) {
			//excepcion
		} else {
			TiqueteMultiple nuevoTM = new TiqueteMultipleVariosEventos(eventos, this);
			tiquetesMultiples.put(nuevoTM.getId(), nuevoTM);
		}
	}
	
	
	public void comprarTiquetesDeluxe(int cantidad, Evento evento, Integer idLocalidad, boolean usarSaldo) {
		if (cantidad > TiqueteMultiple.tiquetesMax) {
			//excepcion
		}
		else if (usarSaldo == true && this.saldoVirtual >= TiqueteDeluxe.precio * cantidad) {
			this.saldoVirtual -= TiqueteDeluxe.precio * cantidad;
			for (int i = 0;i < cantidad; i++) {
				if (this.tiquetes.get(evento).equals(null)) {
					this.tiquetes.put(evento, new ArrayList<Tiquete>());
				}
				Tiquete nuevoTiquete = new TiqueteDeluxe(idLocalidad, evento, this);
				this.tiquetes.get(evento).addLast(nuevoTiquete);
				}
		} else if (usarSaldo == true && this.saldoVirtual < TiqueteDeluxe.precio * cantidad){
			//excepcion
		} else {
			for (int i = 0;i < cantidad; i++) {
				if (this.tiquetes.get(evento).equals(null)) {
					this.tiquetes.put(evento, new ArrayList<Tiquete>());
				}
				Tiquete nuevoTiquete = new TiqueteDeluxe(idLocalidad, evento, this);
				this.tiquetes.get(evento).addLast(nuevoTiquete);
				}
		}
	}
	
	public String getLogin() {
		return login;
	}
	public String getContrasena() {
		return contrasena;
	}
	public double getSaldoVirtual() {
		return saldoVirtual;
	}
	
	
}
