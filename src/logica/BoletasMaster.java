package logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class BoletasMaster throws Exception {
	
	
	private boolean esAdministrador;
	private boolean esOrganizador;
	private boolean esCliente;
	private ArrayList<Evento> eventos;
	private HashMap<LocalDate, ArrayList<Evento>> eventosPorFecha; 
	private ArrayList<Venue> venues;
	private HashMap<String, Tiquete> tiquetes;
	private HashMap<String, Organizador> organizadores;
	private HashMap<String, Cliente> clientes;
	private HashMap<String, Usuario> usuarios;
	private Administrador administrador; 
	private Usuario usuarioActual;
	
	
	

	
	
	
	public BoletasBoletasMasterMaster() {
		esAdministrador = false;
		esOrganizador = false;
		esCliente = false;
	    this.venues = new ArrayList<Venue>();
	    this.tiquetes = new HashMap<String, Tiquete>();
	    this.organizadores = new HashMap<String, Organizador>();
	    this.clientes = new HashMap<String, Cliente>();
	    this.usuarios = new HashMap<String, Usuario>();
		
		 
	

	}

	// Métodos para gestionar eventos, usuarios, tiquetes, etc.
	public void agregarEvento(Evento evento) {
		this.eventos.add(evento);
		this.eventosPorFecha.computeIfAbsent(evento.getFecha(), k -> new ArrayList<>()).add(evento);
	}
	public void agregarVenue(Venue venue) {
		this.venues.add(venue);
	}
	public void agregarTiquete(Tiquete tiquete) {
		this.tiquetes.put(tiquete.getCodigo(), tiquete);
	}


	


	//metodos para login y registro de usuarios
	public void agregarAdministrador(String login, String contrasena) {
		this.administrador = new Administrador(login, contrasena);
		this.administrador = administrador;

	}
	public void  agregarCliente(String login, String contrasena) {
		Cliente nuevoCliente = new Cliente(login, contrasena);
		clientes.put(login, nuevoCliente);
		usuarios.put(login, nuevoCliente);
	}
	public void agregarOrganizador(String login, String contrasena) {
		Organizador nuevoOrganizador = new Organizador(login, contrasena);
		organizadores.put(login, nuevoOrganizador);
		usuarios.put(login, nuevoOrganizador);
	}
	
	public void loginCliente(String login, String contrasena) {
		Cliente cliente = clientes.get(login);
		if (cliente != null && cliente.login(login, contrasena)) {
			esCliente = true;
			esOrganizador = false;
			esAdministrador = false;
			this.usuarioActual = cliente;

		}
		else {
			// lanzar excepcion de login fallido
		}
	}
	public void loginOrganizador(String login, String contrasena) {
		Organizador organizador = organizadores.get(login);
		if (organizador != null && organizador.login(login, contrasena)) {
			esOrganizador = true;
			esCliente = false;
			esAdministrador = false;
			this.usuarioActual = organizador;
		}
				else {
			// lanzar excepcion de login fallido
		}

	}
	public void loginAdministrador(String login, String contrasena) {
		if (administrador != null && this.administrador.login(login, contrasena)) {
			esAdministrador = true;
			esCliente = false;
			esOrganizador = false;
			this.administrador = administrador;
		}
				else {
			// lanzar excepcion de login fallido
		}
	}
	// Métodos para verificar el rol del usuario actual
	public boolean esAdministrador() {
		return this.esAdministrador;
	}
	public boolean esOrganizador() {
		return this.esOrganizador;
	}
	public boolean esCliente() {
		return this.esCliente;
	}
	//Metodos del usuario actual



	public void  comprarTiquetes(int cantidad, Evento evento, Integer idLocalidad) {
		if (usuarioActual != null && esCliente) {
			usuarioActual.comprarTiquetes(cantidad, evento, idLocalidad);
		} else {
			// lanzar excepcion de usuario no logeado. 
		}
	}

	public void comprarTiquetesEnumerados(int cantidad, Evento evento, Integer idLocalidad, int idSilla) {
		if (usuarioActual != null && esCliente) {
			usuarioActual.comprarTiquetesEnumerados(cantidad, evento, idLocalidad, idSilla);
		} else {
			// lanzar excepcion de usuario no logeado. 
		}
	}
	//TODO REVISAR
	public void comprarTiquetesMultiplesUE(int cantidad, Evento evento, Integer idLocalidad) {
		if (usuarioActual != null && esCliente) {
			usuarioActual.comprarTiquetesMultiplesUE(cantidad, evento, idLocalidad);
		} else {
			// lanzar excepcion de usuario no logeado. 
		}
	}

	//TODO REVISAR
	public void  comprarTiquetesMultiplesVE(HashMap<Evento,Integer> eventos) {
		if (usuarioActual != null && esCliente) {
			usuarioActual.comprarTiquetesMultiplesVE(eventos);
		} else {
			// lanzar excepcion de usuario no logeado. 
		}
	}


	
	







}

