package logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import Exepciones.CantidadTiquetesExcedidaException;
import Exepciones.CapacidadLocalidadExcedidaException;
import Exepciones.SaldoInsuficienteException;
import Exepciones.TiqueteNoEncontradoException;
import Exepciones.TiqueteNoTransferibleException;
import Exepciones.TiqueteVencidoFecha;
import Exepciones.TransferirTiqueteDeluxeException;
import Exepciones.UsuarioNoEncontradoException;
import Exepciones.PasswordIncorrectoException;

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



	public ArrayList<Tiquete> comprarTiquetes(int cantidad, Evento evento, Integer idLocalidad, boolean usarSaldo) throws Exception{
		ArrayList<Tiquete> lista = new ArrayList<Tiquete>(); 
		if (cantidad > Tiquete.tiquetesMax) {
			throw new CantidadTiquetesExcedidaException(Tiquete.tiquetesMax);
		}
		else if (!(evento.getLocalidades().get(idLocalidad).compararCapacidad(cantidad))) {
			throw new CapacidadLocalidadExcedidaException(cantidad);
		}
		else if (usarSaldo == true && (this.saldoVirtual >= cantidad * evento.getLocalidades().get(idLocalidad).getPrecioTiquete())) {
			this.saldoVirtual -= cantidad * evento.getLocalidades().get(idLocalidad).getPrecioTiquete();
			for (int i = 0;i < cantidad; i++) {
				if (this.tiquetes.get(evento).equals(null)) {
					this.tiquetes.put(evento, new ArrayList<Tiquete>());
				}
				Tiquete nuevoTiquete = new TiqueteBasico(idLocalidad, evento, this);
				this.tiquetes.get(evento).addLast(nuevoTiquete);
				lista.addLast(nuevoTiquete);		
			}
			return lista;
		} else if (usarSaldo == true && this.saldoVirtual < cantidad * evento.getLocalidades().get(idLocalidad).getPrecioTiquete()) {
			throw new SaldoInsuficienteException(this);
		} else {
			for (int i = 0;i < cantidad; i++) {
				if (this.tiquetes.get(evento).equals(null)) {
					this.tiquetes.put(evento, new ArrayList<Tiquete>());
				}
				Tiquete nuevoTiquete = new TiqueteBasico(idLocalidad, evento, this);
				this.tiquetes.get(evento).addLast(nuevoTiquete);
				lista.addLast(nuevoTiquete);		
			}
			return lista;
		}
	}

	
	public ArrayList<Tiquete> comprarTiquetesEnumerados(int cantidad, Evento evento, Integer idLocalidad, int idSilla, boolean usarSaldo)throws Exception {
		ArrayList<Tiquete> lista = new ArrayList<Tiquete>(); 
		if (cantidad > Tiquete.tiquetesMax) {
			throw new CantidadTiquetesExcedidaException(Tiquete.tiquetesMax);
		}
		else if (!(evento.getLocalidades().get(idLocalidad).compararCapacidad(cantidad))) {
			throw new CapacidadLocalidadExcedidaException(cantidad);
		}
		else if (usarSaldo == true && (this.saldoVirtual >= cantidad * evento.getLocalidades().get(idLocalidad).getPrecioTiquete())) {
			this.saldoVirtual -= cantidad * evento.getLocalidades().get(idLocalidad).getPrecioTiquete();
			for (int i = 0;i < cantidad; i++) {
				if (this.tiquetes.get(evento).equals(null)) {
					this.tiquetes.put(evento, new ArrayList<Tiquete>());
				}
				Tiquete nuevoTiquete = new TiqueteEnumerado(idLocalidad, evento, this, idSilla);
				this.tiquetes.get(evento).addLast(nuevoTiquete);
				lista.addLast(nuevoTiquete);	
				}
			return lista;
		}else if (usarSaldo == true && this.saldoVirtual < cantidad * evento.getLocalidades().get(idLocalidad).getPrecioTiquete()) {
			throw new SaldoInsuficienteException(this);
		} else {
			for (int i = 0;i < cantidad; i++) {
				if (this.tiquetes.get(evento).equals(null)) {
					this.tiquetes.put(evento, new ArrayList<Tiquete>());
				}
				Tiquete nuevoTiquete = new TiqueteEnumerado(idLocalidad, evento, this, idSilla);
				this.tiquetes.get(evento).addLast(nuevoTiquete);
				lista.addLast(nuevoTiquete);	
			}
			return lista;
		}	
	} 
	
	
	public TiqueteMultiple comprarTiquetesMultiplesUE(int cantidad, Evento evento, Integer idLocalidad, boolean usarSaldo) throws Exception{
		if (cantidad > TiqueteMultiple.tiquetesMax) {
			throw new CantidadTiquetesExcedidaException(Tiquete.tiquetesMax);
		}
		else if (!(evento.getLocalidades().get(idLocalidad).compararCapacidad(cantidad))) {
			throw new CapacidadLocalidadExcedidaException(cantidad);
		}
		else if (usarSaldo == true && this.saldoVirtual >= TiqueteMultipleUnicoEvento.precios.get(idLocalidad).get(cantidad)) {
			this.saldoVirtual -= TiqueteMultipleUnicoEvento.precios.get(idLocalidad).get(cantidad);
			TiqueteMultiple nuevoTM = new TiqueteMultipleUnicoEvento(evento, idLocalidad, cantidad, this);
			tiquetesMultiples.put(nuevoTM.getId(), nuevoTM);
			return nuevoTM;
		} else if(usarSaldo == true && this.saldoVirtual < TiqueteMultipleUnicoEvento.precios.get(idLocalidad).get(cantidad)) {
			throw new SaldoInsuficienteException(this);
		} else {
			TiqueteMultiple nuevoTM = new TiqueteMultipleUnicoEvento(evento, idLocalidad, cantidad, this);
			tiquetesMultiples.put(nuevoTM.getId(), nuevoTM);
			return nuevoTM;
		}
	}
	
	
	public TiqueteMultiple comprarTiquetesMultiplesVE(HashMap<Evento,Integer> eventos, boolean usarSaldo) throws Exception{

		if (eventos.size() > TiqueteMultiple.tiquetesMax) {
			throw new CantidadTiquetesExcedidaException(Tiquete.tiquetesMax);
		}
		
		else if (usarSaldo == true && this.saldoVirtual >= TiqueteMultipleVariosEventos.precios.get(eventos.size())) {
			this.saldoVirtual -= TiqueteMultipleVariosEventos.precios.get(eventos.size());
			TiqueteMultiple nuevoTM = new TiqueteMultipleVariosEventos(eventos, this);
			tiquetesMultiples.put(nuevoTM.getId(), nuevoTM);
			return nuevoTM;
		} else if(usarSaldo == true && this.saldoVirtual < TiqueteMultipleVariosEventos.precios.get(eventos.size())) {
			throw new SaldoInsuficienteException(this);
		} else {
			TiqueteMultiple nuevoTM = new TiqueteMultipleVariosEventos(eventos, this);
			tiquetesMultiples.put(nuevoTM.getId(), nuevoTM);
			return nuevoTM;
		}
	}
	
	

	public ArrayList<Tiquete> comprarTiquetesDeluxe(int cantidad, Evento evento, Integer idLocalidad, boolean usarSaldo)throws Exception {
		ArrayList<Tiquete> lista = new ArrayList<Tiquete>(); 	
		if (cantidad > TiqueteMultiple.tiquetesMax) {
			throw new CantidadTiquetesExcedidaException(Tiquete.tiquetesMax);
		}
		else if (!(evento.getLocalidades().get(idLocalidad).compararCapacidad(cantidad))) {
			throw new CapacidadLocalidadExcedidaException(cantidad);
		}
		else if (usarSaldo == true && this.saldoVirtual >= TiqueteDeluxe.precio * cantidad) {
			this.saldoVirtual -= TiqueteDeluxe.precio * cantidad;
			for (int i = 0;i < cantidad; i++) {
				if (this.tiquetes.get(evento).equals(null)) {
					this.tiquetes.put(evento, new ArrayList<Tiquete>());
				}
				Tiquete nuevoTiquete = new TiqueteDeluxe(idLocalidad, evento, this);
				this.tiquetes.get(evento).addLast(nuevoTiquete);
				lista.addLast(nuevoTiquete);	
			}
			return lista;
		} else if (usarSaldo == true && this.saldoVirtual < TiqueteDeluxe.precio * cantidad){
			throw new SaldoInsuficienteException(this);
		} else {
			for (int i = 0;i < cantidad; i++) {
				if (this.tiquetes.get(evento).equals(null)) {
					this.tiquetes.put(evento, new ArrayList<Tiquete>());
				}
				Tiquete nuevoTiquete = new TiqueteDeluxe(idLocalidad, evento, this);
				this.tiquetes.get(evento).addLast(nuevoTiquete);
				lista.addLast(nuevoTiquete);	
			}
			return lista;
		}
	}
	
	public void transferirTiquete(String login, String contrasena, Tiquete tiquete) throws Exception {
		if (tiquete.getTipo().equals("TIQUETEDELUXE")) {
			throw new TransferirTiqueteDeluxeException(tiquete);
		}
		if (!usuarios.containsKey(login)) {
			throw new UsuarioNoEncontradoException(login);
		}
		if (tiquete.getFecha().isBefore(LocalDate.now())) {
			throw new TiqueteVencidoFecha(tiquete);
		}
		if (contrasena.equals(this.contrasena)) {
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
		else {
			throw new PasswordIncorrectoException(this);
		}
	}
	
	public void transferirTiqueteMultiple(String login, String contrasena, TiqueteMultiple tiquete) throws Exception {
		if (!usuarios.containsKey(login)) {
			throw new UsuarioNoEncontradoException(login);
		}
		if (!tiquete.transferible) {
			throw new TiqueteNoTransferibleException(tiquete);
		}
		if (contrasena.equals(this.contrasena) && tiquete.transferible) {
			if (tiquete instanceof TiqueteMultipleVariosEventos) {
				TiqueteMultipleVariosEventos tiqueteMultipleVE = (TiqueteMultipleVariosEventos) tiquete;
				Set<Evento> llaves = tiqueteMultipleVE.getTiquetes().keySet();
				Iterator<Evento> iterador = llaves.iterator();
				while(iterador.hasNext()) {
					Tiquete tiq = tiqueteMultipleVE.getTiquetes().get(iterador.next());
					transferirTiquete(login, contrasena, tiq);
					}
			} else if(tiquete instanceof TiqueteMultipleUnicoEvento) {
				TiqueteMultipleUnicoEvento tiqueteMultipleUE = (TiqueteMultipleUnicoEvento) tiquete;
				ArrayList<Tiquete> tiquetes = tiqueteMultipleUE.getTiquetes();
				Iterator<Tiquete> iterador = tiquetes.iterator();
				while(iterador.hasNext()) {
					Tiquete tiq = iterador.next();
					transferirTiquete(login, contrasena, tiq);
					
				}
			}
			tiquete.setTransferible(false);
			this.tiquetesMultiples.remove(tiquete.id);
			usuarios.get(login).tiquetesMultiples.put(tiquete.id, tiquete);
		} else {
			throw new PasswordIncorrectoException(this);
		}
	}
	
		
	
	public void transferirTiqueteDeTM(String login, String contrasena, TiqueteMultiple tiqueteMultiple, int idTiquete ) throws Exception {
		Tiquete tiquete = null;
		if (!usuarios.containsKey(login)) {
			throw new UsuarioNoEncontradoException(login);
		}
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
				if (tiquete.equals(null)){
					throw new TiqueteNoEncontradoException(idTiquete);
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
				if (tiquete.equals(null)){
					throw new TiqueteNoEncontradoException(idTiquete);
				}
			}
			transferirTiquete(login, contrasena, tiquete);
			tiqueteMultiple.setTransferible(false);
		}
		else {
			throw new PasswordIncorrectoException(this);
		}
	}
	
	public record Tupla<A, B>(A primero, B segundo) {}
	public Tupla<Tiquete, String> solicitarReembolso(Tiquete tiquete, String razon) {
		return new Tupla<>(tiquete, razon);
	}
	
	public void realizarReembolso(Tiquete tiquete) {
		this.saldoVirtual += tiquete.getPrecioReal();
		Iterator<Tiquete> iterador = this.tiquetes.get(tiquete.getEvento()).iterator();
		while (iterador.hasNext()) {
			Tiquete tiq = iterador.next();
			if (tiq.equals(tiquete)) {
				tiq.setPrecioBase(0);
				tiq.setPrecioReal(0);
				iterador.remove();
				break;
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
	
	public void setSaldoVirtual(double saldoVirtual) {
		this.saldoVirtual = saldoVirtual;
	}


	public HashMap<Evento, ArrayList<Tiquete>> getTiquetes() {
		return tiquetes;
	}

	public HashMap<Integer, TiqueteMultiple> getTiquetesMultiples() {
		return tiquetesMultiples;
	}
	
	
	
}
