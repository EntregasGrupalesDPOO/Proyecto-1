package logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Administrador {
	private HashMap<LocalDate, ArrayList<Evento>> eventosPorFecha;
	private HashMap<Organizador, ArrayList<Evento>> eventosPorOrganizador;
	private ArrayList<Solicitud> solicitudes;
	private String login;
	private String contrasena;

	public Administrador(String login, String contrasena) {
		this.login = login;
		this.contrasena = contrasena;
		this.eventosPorFecha = new HashMap<LocalDate, ArrayList<Evento>>();
		this.eventosPorOrganizador = new HashMap<Organizador, ArrayList<Evento>>();
	}
	// Método para autenticar al administrador
	public boolean login(String login, String contrasena) {
		return this.login.equals(login) && this.contrasena.equals(contrasena);
	}
	
	public void fijarTarifaImpresion(int valor) {
		Tiquete.setImpresion(valor);
	}
	
	public void anadirTarifaTipoEvento(String tipoEvento, double valor) {
		Evento.addTipoEvento(tipoEvento, valor);
	}
	
	public double gananciasPorEvento(Evento evento) {
		double ganancias = 0;
		for (Localidad l: evento.getLocalidades()) {
			ganancias += consultarGananciasLocalidad(evento, l);
		}
		return ganancias;
	}
	
	private double consultarGananciasLocalidad(Evento evento, Localidad localidad) {
		double ganancias = 0;
		for (Tiquete t: localidad.getTiquetes()) {
			if (t.isComprado() && !t.getCliente().equals(evento.getOrganizador())) {
				ganancias += t.getPrecioReal() - t.getPrecioBase();
			}
		}
		return ganancias;
	}
	
	public double gananciasPorFecha(LocalDate fecha) {
		double ganancias = 0;
		for(Evento e: this.eventosPorFecha.get(fecha)) {
			ganancias += gananciasPorEvento(e);
		}
		return ganancias;
	}
	
	public double gananciasPorOrganizador(Organizador organizador) {
		double ganancias = 0;
		for(Evento e: this.eventosPorOrganizador.get(organizador)) {
			ganancias += gananciasPorEvento(e);
		}
		return ganancias;
	}
	
	public double gananciasGlobales() {
		double ganancias = 0;
		for(ArrayList<Evento> eventos: this.eventosPorFecha.values()) {
			for (Evento e:eventos) {
				ganancias += gananciasPorEvento(e);
			}
		}
		return ganancias;
	}
	
	public void reembolsarTiqueteCancelacionEvento(Cliente cliente,Tiquete tiquete) {
		cliente.eliminarTiquete(tiquete);
		cliente.actualizarSaldoVirtual(tiquete.getPrecioReal()-Tiquete.impresion);
		tiquete.setPrecioBase(0);
		tiquete.setPrecioReal(Tiquete.impresion);
		tiquete.setTransferible(false);
	}
	
	public void reembolsarTiqueteCancelacionEventoInsolvencia(Cliente cliente,Tiquete tiquete) {
		cliente.eliminarTiquete(tiquete);
		cliente.actualizarSaldoVirtual(tiquete.getPrecioBase());
		tiquete.setPrecioReal(tiquete.getPrecioReal()-tiquete.getPrecioBase());
		tiquete.setPrecioBase(0);
		tiquete.setTransferible(false);
	}
	
	public void reembolsarTiqueteCalamidad(Cliente cliente,Tiquete tiquete) {
		cliente.eliminarTiquete(tiquete);
		cliente.actualizarSaldoVirtual(tiquete.getPrecioReal());
		tiquete.setPrecioReal(0);
		tiquete.setPrecioBase(0);
		tiquete.setTransferible(false);
	}
	
	public void cancelarEvento(Evento evento) {
		evento.setEstado("CANCELADO");
		for (Localidad l: evento.getLocalidades()) {
			for (Tiquete t: l.getTiquetes()) {
				reembolsarTiqueteCancelacionEvento(t.getCliente(), t);
			}
		}
	}
	
	public void cancelarEventoInsolvencia(Evento evento) {
		evento.setEstado("CANCELADO");
		for (Localidad l: evento.getLocalidades()) {
			for (Tiquete t: l.getTiquetes()) {
				reembolsarTiqueteCancelacionEventoInsolvencia(t.getCliente(), t);
			}
		}
	}
	
	public void añadirEvento(Evento evento) {
		if(this.eventosPorFecha.containsKey(evento.getFecha())) {
			ArrayList<Evento> lista = this.eventosPorFecha.get(evento.getFecha());
			if (lista.equals(null)) {
				lista = new ArrayList<Evento>();
			}
			lista.add(evento);
		}else {
			ArrayList<Evento> lista = new ArrayList<Evento>();
			lista.add(evento);
			this.eventosPorFecha.put(evento.getFecha(), lista);
		}
		
		if(this.eventosPorOrganizador.containsKey(evento.getOrganizador())) {
			ArrayList<Evento> lista = this.eventosPorOrganizador.get(evento.getOrganizador());
			if (lista.equals(null)) {
				lista = new ArrayList<Evento>();
			}
			lista.add(evento);
		}else {
			ArrayList<Evento> lista = new ArrayList<Evento>();
			lista.add(evento);
			this.eventosPorOrganizador.put(evento.getOrganizador(), lista);
		}
		
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public HashMap<LocalDate, ArrayList<Evento>> getEventosPorFecha() {
		return eventosPorFecha;
	}

	public HashMap<Organizador, ArrayList<Evento>> getEventosPorOrganizador() {
		return eventosPorOrganizador;
	}
	


	
}
