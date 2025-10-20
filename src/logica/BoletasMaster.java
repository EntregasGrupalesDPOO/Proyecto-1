package logica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Persistencia.ArchivoSerializable;
import java.io.Serializable;


import Exepciones.UsuarioNoEncontradoException;

public class BoletasMaster implements Serializable{
	private boolean esAdministrador;
	private boolean esOrganizador;
	private boolean esCliente;
	private ArrayList<Evento> eventos;
	private HashMap<LocalDate, ArrayList<Evento>> eventosPorFecha; 
	private ArrayList<Venue> venues;
	private HashMap<Integer, Tiquete> tiquetes;
	private HashMap<Integer, TiqueteMultiple> tiquetesMultiples;
	private HashMap<String, Organizador> organizadores;
	private HashMap<String, Cliente> clientes;
	private HashMap<String, Usuario> usuarios;
	private Administrador administrador; 
	private Usuario usuarioActual;
	private transient ArchivoSerializable archivoSerializable = new ArchivoSerializable();
	
	
	

	
	
	
	public BoletasMaster() {
		esAdministrador = false;
		esOrganizador = false;
		esCliente = false;
	    this.venues = new ArrayList<Venue>();
	    this.tiquetes = new HashMap<Integer, Tiquete>();
	    this.organizadores = new HashMap<String, Organizador>();
	    this.clientes = new HashMap<String, Cliente>();
	    this.usuarios = new HashMap<String, Usuario>();
		this.eventos = new ArrayList<Evento>();
		this.eventosPorFecha = new HashMap<LocalDate, ArrayList<Evento>>();
		this.tiquetesMultiples =  new HashMap<Integer, TiqueteMultiple>();
		
		 
	

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
		this.tiquetes.put(tiquete.getId(), tiquete);
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
	
	public void loginCliente(String login, String contrasena) throws UsuarioNoEncontradoException {
		if (!clientes.containsKey(login)) throw new UsuarioNoEncontradoException(login);
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





	//Metodos de compra de boletas del usuario actual(discrimina el tipo de usuario)






public void comprarTiquetes(int cantidad, Evento evento, Integer idLocalidad) throws Exception {
    if (usuarioActual != null && esCliente) {
        boolean conSaldo = usuarioActual.getSaldoVirtual() > 0;
        ArrayList<Tiquete> tiquetesCompra  = (usuarioActual.comprarTiquetes(cantidad, evento, idLocalidad, conSaldo));
		for (Tiquete tiquete : tiquetesCompra) {
			tiquetes.put(tiquete.getId(), tiquete);
		}
	}
}

public void comprarTiquetesEnumerados(int cantidad, Evento evento, Integer idLocalidad, int idSilla)
		throws UsuarioNoEncontradoException, Exception {
	if (usuarioActual != null && (esCliente || esOrganizador)) {
		boolean conSaldo = usuarioActual.getSaldoVirtual() > 0;
		ArrayList<Tiquete> tiquetesCompra  = (usuarioActual.comprarTiquetesEnumerados(cantidad, evento, idLocalidad, idSilla, conSaldo));
		for (Tiquete tiquete : tiquetesCompra) {
			tiquetes.put(tiquete.getId(), tiquete);
		}
	}
}


public void comprarTiquetesMultiplesUE(int cantidad, Evento evento, Integer idLocalidad) throws UsuarioNoEncontradoException, Exception {
    if (usuarioActual != null && (esCliente || esOrganizador)) {
        boolean conSaldo = usuarioActual.getSaldoVirtual() > 0;
		TiqueteMultiple tm  = usuarioActual.comprarTiquetesMultiplesUE(cantidad, evento, idLocalidad, conSaldo);
		tiquetesMultiples.put(tm.getId(), tm);
		}
	}

public void comprarTiquetesMultiplesVE(HashMap<Evento, Integer> eventos)
        throws UsuarioNoEncontradoException, Exception {
    if (usuarioActual != null && (esCliente || esOrganizador)) {
        boolean conSaldo = usuarioActual.getSaldoVirtual() > 0;

		TiqueteMultiple tm  = usuarioActual.comprarTiquetesMultiplesVE(eventos, conSaldo);
        tiquetesMultiples.put(tm.getId(), tm);
    }
}





	//metodos para el cliente





	public void solicitarReembolso(Integer idTiquete, String razon) throws Exception {
		if (usuarioActual != null && esCliente) {;
			if (administrador != null) {
				administrador.agregarSolicitud(
					new SolicitudCalamidad(this.usuarioActual, razon, this.tiquetes.get(idTiquete))
				);
			}
		} 
	}




	
	// metodos para el organizador

	public void proponerVenue (int capacidad, String nombre, String ubicacion) {
		Venue nuevoVenue = new Venue(capacidad, nombre, ubicacion);
		administrador.agregarSolicitud(new SolicitudVenue(this.usuarioActual, "Propuesta de Venue: " + nombre, nuevoVenue, this ));	
	}

	public void solicitarCancelacionEvento(Evento evento, String razon) {
		administrador.agregarSolicitud(new SolicitudCancelacionEvento(this.usuarioActual, razon, evento, this.administrador));

	}
	public void agendarEvento(String nombre, String descripcion, LocalDate fecha, LocalTime hora, Venue venue, String tipoEvento, List<Localidad> localidades) {

		Evento nuevoEvento  =  (new Evento(nombre, fecha, hora, venue, (Organizador) this.usuarioActual, new HashMap<Integer, Localidad>(), tipoEvento));
		agregarEvento(nuevoEvento);
		for (int i = 0; i < localidades.size(); i++) {
			nuevoEvento.getLocalidades().put(i + 1, localidades.get(i));
		}

	}
	public void agendarEvento(String nombre, String descripcion, LocalDate fecha, LocalTime hora, Venue venue, String tipoEvento) {

		Evento nuevoEvento  =  (new Evento(nombre, fecha, hora, venue, (Organizador) this.usuarioActual, new HashMap<Integer, Localidad>(), tipoEvento));
		agregarEvento(nuevoEvento);
		System.out.println("Evento agendado: " + nuevoEvento.getNombre()  +  " Sin localidades asignadas.");
			

	}
	public Localidad crearLocalidadEvento(Evento evento, String nombre, double precioTiquete, int capacidad, boolean numerada) {
		Localidad nuevaLocalidad = new Localidad(nombre, precioTiquete, capacidad, numerada, evento.getVenue(), evento);
		int idLocalidad = evento.getLocalidades().size() + 1;
		evento.getLocalidades().put(idLocalidad, nuevaLocalidad);
		return nuevaLocalidad;
	}










	// metodos para el administrador
	public void verSolicitudesPendientes() {
		System.out.println("Solicitudes pendientes:");
		administrador.mostrarSolicitudesPendientes();
	}



	//metodo para atender solicitudes del administrador
	public void atenderSolicitud(int nSolicitud, boolean aceptar) throws Exception {
		Solicitud solicitud = administrador.getSolicitudes().get(nSolicitud);
		if (aceptar){
			solicitud.aceptarSolicitud();
		}
		else {
			solicitud.rechazarSolicitud();
		}
		
		administrador.atenderSolicitud(solicitud, aceptar);
	} 

	public void obtenerGananciasGenerales(){
		System.out.println("Las ganancias generales son: " + administrador.obtenerGananciasGenerales(this.eventos));
	}
	public void fijarComisionPorTipoEvento (double cultural, double deportivo, double musical, double religioso) {
		HashMap<String, Double> comisiones = new HashMap<String, Double>();
		comisiones.put(Evento.CULTURAL, cultural);
		comisiones.put(Evento.DEPORTIVO, deportivo);
		comisiones.put(Evento.MUSICAL, musical);
		comisiones.put(Evento.RELIGIOSO, religioso);
		Evento.comisionEventos = comisiones;
		Tiquete.tiposEventos=comisiones;

	}

	public void mostrarComisionesPorTipoEvento() {
		System.out.println("Comisiones por tipo de evento:");
		for (String tipoEvento : Evento.comisionEventos.keySet()) {
			System.out.println(tipoEvento + ": " + Evento.comisionEventos.get(tipoEvento) + "%");
		}
	}

	




















	// getters y setters

	public boolean isEsAdministrador() {
		return esAdministrador;
	}

	public void setEsAdministrador(boolean esAdministrador) {
		this.esAdministrador = esAdministrador;
	}

	public boolean isEsOrganizador() {
		return esOrganizador;
	}

	public void setEsOrganizador(boolean esOrganizador) {
		this.esOrganizador = esOrganizador;
	}

	public boolean isEsCliente() {
		return esCliente;
	}

	public void setEsCliente(boolean esCliente) {
		this.esCliente = esCliente;
	}

	public ArrayList<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(ArrayList<Evento> eventos) {
		this.eventos = eventos;
	}

	public HashMap<LocalDate, ArrayList<Evento>> getEventosPorFecha() {
		return eventosPorFecha;
	}

	public void setEventosPorFecha(HashMap<LocalDate, ArrayList<Evento>> eventosPorFecha) {
		this.eventosPorFecha = eventosPorFecha;
	}

	public ArrayList<Venue> getVenues() {
		return venues;
	}

	public void setVenues(ArrayList<Venue> venues) {
		this.venues = venues;
	}

	public HashMap<Integer, Tiquete> getTiquetes() {
		return tiquetes;
	}

	public void setTiquetes(HashMap<Integer, Tiquete> tiquetes) {
		this.tiquetes = tiquetes;
	}

	public HashMap<String, Organizador> getOrganizadores() {
		return organizadores;
	}

	public void setOrganizadores(HashMap<String, Organizador> organizadores) {
		this.organizadores = organizadores;
	}

	public HashMap<String, Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(HashMap<String, Cliente> clientes) {
		this.clientes = clientes;
	}

	public HashMap<String, Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(HashMap<String, Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}
	
	// Persistencia
	
	public void escribirUsuarios() {
	    archivoSerializable.escribir(this.usuarios, "./datos/usuarios.ser");
	}

	public void leerUsuarios() {
	    Object obj = archivoSerializable.leer("./datos/usuarios.ser");
	    if (obj != null) {
	        this.usuarios = (HashMap<String, Usuario>) obj;
	    } else {
	        this.usuarios = new HashMap<>();
	    }
	}

	public void escribirEventos() {
	    archivoSerializable.escribir(this.eventos, "./datos/eventos.ser");
	}

	public void leerEventos() {
	    Object obj = archivoSerializable.leer("./datos/eventos.ser");
	    if (obj != null) {
	        this.eventos = (ArrayList<Evento>) obj;
	    } else {
	        this.eventos = new ArrayList<>();
	    }
	}

	public void escribirTiquetes() {
	    archivoSerializable.escribir(this.tiquetes, "./datos/tiquetes.ser");
	}


	public void leerTiquetes() {
	    Object obj = archivoSerializable.leer("./datos/tiquetes.ser");
	    if (obj != null) {
	        this.tiquetes = (HashMap<Integer, Tiquete>) obj;
	    } else {
	        this.tiquetes = new HashMap<>();
	    }
	}

	public void escribirAdministrador() {
	    archivoSerializable.escribir(this.administrador, "./datos/administrador.ser");
	}

	public void leerAdministrador() {
	    Object obj = archivoSerializable.leer("./datos/administrador.ser");
	    if (obj != null) {
	        this.administrador = (Administrador) obj;
	    }
	}
	
	public void escribirVenues() {
	    ArchivoSerializable archivo = new ArchivoSerializable();
	    archivo.escribir(this.venues, "datos/venues.ser");
	}

	public void leerVenues() {
	    ArchivoSerializable archivo = new ArchivoSerializable();
	    Object obj = archivo.leer("datos/venues.ser");
	    if (obj instanceof ArrayList<?>) {
	        this.venues = (ArrayList<Venue>) obj;
	    } else {
	        this.venues = new ArrayList<>();
	    }
	}





}


