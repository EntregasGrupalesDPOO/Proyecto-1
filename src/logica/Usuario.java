package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import logica.Organizador.Tupla;

public abstract class Usuario {
	protected String login;
	protected String contrasena;
	protected HashMap<Evento, ArrayList<Tiquete>> tiquetes;
	protected HashMap<Integer, TiqueteMultiple> tiquetesMultiples;
	protected static HashMap<String, Usuario> usuarios;
	protected double saldoVirtual;
	
	
	
	public Usuario(String login, String contrasena) {
		super();
		this.login = login;
		this.contrasena = contrasena;
		this.saldoVirtual = 0;
		this.tiquetes = new HashMap<Evento, ArrayList<Tiquete>>();
		this.tiquetesMultiples = new HashMap<Integer, TiqueteMultiple>();
	}


	public boolean login(String usuario, String contrasena) {
		return (usuario.equals(this.login)&& contrasena.equals(this.contrasena));
		
		
	}

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
	
	public void transferirTiquete(String login, String contrasena, Tiquete tiquete) {
		if (contrasena.equals(this.contrasena) && !(tiquete instanceof TiqueteDeluxe)) {
			ArrayList<Tiquete> lista = this.tiquetes.get(tiquete.getEvento());
			Iterator<Tiquete> iterador = lista.iterator();
			
			while(iterador.hasNext()) {
				Tiquete tiq = iterador.next();
				if (tiq.equals(tiquete)){
					iterador.remove();
					break;
				}
			}
			tiquete.setIdUsuario(login);
			tiquete.setUsuario(usuarios.get(login));
			usuarios.get(login).getTiquetes().get(tiquete.getEvento()).addLast(tiquete);
		}
	}
	
	public void transferirTiqueteMultiple(String login, String contrasena, TiqueteMultiple tiquete) {
		if (contrasena.equals(this.contrasena) && tiquete.transferible) {
			if (tiquete instanceof TiqueteMultipleVariosEventos) {
				TiqueteMultipleVariosEventos tiqueteMultipleVE = (TiqueteMultipleVariosEventos) tiquete;
				Set<Evento> llaves = tiqueteMultipleVE.getTiquetes().keySet();
				Iterator<Evento> iterador = llaves.iterator();
				while(iterador.hasNext()) {
					Tiquete tiq = tiqueteMultipleVE.getTiquetes().get(iterador.next());
					tiq.setIdUsuario(login);
					tiq.setUsuario(usuarios.get(login));
					usuarios.get(login).getTiquetes().get(tiq.getEvento()).addLast(tiq);
					tiquete.setTransferible(false);
					}
			} else if(tiquete instanceof TiqueteMultipleUnicoEvento) {
				TiqueteMultipleUnicoEvento tiqueteMultipleUE = (TiqueteMultipleUnicoEvento) tiquete;
				ArrayList<Tiquete> tiquetes = tiqueteMultipleUE.getTiquetes();
				Iterator<Tiquete> iterador = tiquetes.iterator();
				while(iterador.hasNext()) {
					Tiquete tiq = iterador.next();
					tiq.setIdUsuario(login);
					tiq.setUsuario(usuarios.get(login));
					usuarios.get(login).getTiquetes().get(tiq.getEvento()).addLast(tiq);
					tiquete.setTransferible(false);
				}
			}
			this.tiquetesMultiples.remove(tiquete.id);
			usuarios.get(login).tiquetesMultiples.put(tiquete.id, tiquete);
		} else {
			//excepcion
		}
	}
	
		
	
	public void transferirTiqueteDeTM(String login, String contrasena, TiqueteMultiple tiqueteMultiple, int idTiquete ) {
		Tiquete tiquete = null;
		if (contrasena.equals(this.contrasena)) {
			if (tiqueteMultiple instanceof TiqueteMultipleVariosEventos) {
				TiqueteMultipleVariosEventos tiqueteMultipleVE = (TiqueteMultipleVariosEventos) tiqueteMultiple;
				Set<Evento> llaves = tiqueteMultipleVE.getTiquetes().keySet();
				Iterator<Evento> iterador = llaves.iterator();
				while(iterador.hasNext()) {
					Tiquete tiq = tiqueteMultipleVE.getTiquetes().get(iterador.next());
					if (tiq.getId() == idTiquete){
						tiquete = tiq;
						iterador.remove();
						break;
					}
				}
			} else if (tiqueteMultiple instanceof TiqueteMultipleUnicoEvento) {
				TiqueteMultipleUnicoEvento tiqueteMultipleUE = (TiqueteMultipleUnicoEvento) tiqueteMultiple;
				ArrayList<Tiquete> tiquetes = tiqueteMultipleUE.getTiquetes();
				Iterator<Tiquete> iterador = tiquetes.iterator();
				while(iterador.hasNext()) {
					Tiquete tiq = iterador.next();
					if (tiq.getId() == idTiquete){
						tiquete = tiq;
						iterador.remove();
						break;
					}
				}
				
			}
			tiquete.setIdUsuario(login);
			tiquete.setUsuario(usuarios.get(login));
			usuarios.get(login).getTiquetes().get(tiquete.getEvento()).addLast(tiquete);
			tiqueteMultiple.setTransferible(false);
		}
	}
	
	public record Tupla<A, B>(A primero, B segundo) {}
	public Tupla<Tiquete, String> solicitarReembolso(Tiquete tiquete, String razon) {
		return new Tupla<>(tiquete, razon);
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

	public HashMap<Evento, ArrayList<Tiquete>> getTiquetes() {
		return tiquetes;
	}

	public HashMap<Integer, TiqueteMultiple> getTiquetesMultiples() {
		return tiquetesMultiples;
	}
	
	
}
