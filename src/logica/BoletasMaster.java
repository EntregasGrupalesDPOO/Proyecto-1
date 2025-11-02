package logica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Persistencia.ArchivoSerializable;
import java.io.Serializable;
import Marketplace.*;
import Exepciones.PasswordIncorrectoException;
import Exepciones.TiqueteNoTransferibleException;
import Exepciones.UsuarioNoEncontradoException;

public class BoletasMaster implements Serializable{
	private boolean esAdministrador;
	private boolean esOrganizador;
	private boolean esCliente;
	private ArrayList<Evento> eventos;
	private HashMap<LocalDate, ArrayList<Evento>> eventosPorFecha; 
	private ArrayList<Venue> venues;
	private HashMap<Integer, Tiquete> tiquetes;
	private HashMap<String, Organizador> organizadores;
	private HashMap<String, Cliente> clientes;
	private Administrador administrador; 
	private transient ArchivoSerializable archivoSerializable = new ArchivoSerializable();
	private MarketPlace marketPlace;
	// Usuarios que accede a la plataforma(diferente de administrador)
	private Cliente usuarioActual;
	//paquetes deluxe

	private HashMap<Integer, PaqueteDeluxe> paquetesDeluxe = new HashMap<Integer, PaqueteDeluxe>();

	
	

	
	
	
	public BoletasMaster() {
		esAdministrador = false;
		esOrganizador = false;
		esCliente = false;
	    this.venues = new ArrayList<Venue>();
	    this.tiquetes = new HashMap<Integer, Tiquete>();
	    this.organizadores = new HashMap<String, Organizador>();
	    this.clientes = new HashMap<String, Cliente>();
		this.eventos = new ArrayList<Evento>();
		this.eventosPorFecha = new HashMap<LocalDate, ArrayList<Evento>>();
		this.marketPlace = new Marketplace.MarketPlace();
		 
	

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
	}
	public void agregarOrganizador(String login, String contrasena) {
		Organizador nuevoOrganizador = new Organizador(login, contrasena, this.administrador);
		organizadores.put(login, nuevoOrganizador);
	}
	
	public void loginCliente(String login, String contrasena) throws UsuarioNoEncontradoException, PasswordIncorrectoException {
		if (!clientes.containsKey(login)) throw new UsuarioNoEncontradoException(login);
		Cliente cliente = clientes.get(login);
		if (cliente != null && cliente.login(login, contrasena)) {
			esCliente = true;
			esOrganizador = false;
			esAdministrador = false;
			this.usuarioActual = cliente;

		}
		else {
			throw new PasswordIncorrectoException(cliente);
		}
	}
	public void loginOrganizador(String login, String contrasena) throws UsuarioNoEncontradoException, PasswordIncorrectoException {
		if (!organizadores.containsKey(login)) throw new UsuarioNoEncontradoException(login);
		Organizador organizador = organizadores.get(login);
		if (organizador != null && organizador.login(login, contrasena)) {
			esOrganizador = true;
			esCliente = false;
			esAdministrador = false;
			this.usuarioActual = organizador;
		}
				else {
			throw new PasswordIncorrectoException(organizador);
		}

	}
	public void loginAdministrador(String login, String contrasena) throws PasswordIncorrectoException {
		if (administrador != null && this.administrador.login(login, contrasena)) {
			esAdministrador = true;
			esCliente = false;
			esOrganizador = false;
			Solicitud.adm =  this.administrador;
		}


		else {	
			throw new PasswordIncorrectoException("La contrasena para el administrador no es correcto");
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






public void comprarTiquetes(int cantidad, Evento evento, String idLocalidad) throws Exception {
    if (usuarioActual != null && esCliente) {
        boolean conSaldo = usuarioActual.getSaldoVirtual() > 0;
        ArrayList<Tiquete> tiquetesCompra  = (usuarioActual.comprarTiquete(cantidad, evento, idLocalidad, conSaldo));
		for (Tiquete tiquete : tiquetesCompra) {
			tiquetes.put(tiquete.getId(), tiquete);
		}
	}
}

public void comprarTiquetesEnumerados(int cantidad, Evento evento, String idLocalidad, int idSilla)
		throws UsuarioNoEncontradoException, Exception {
	if (usuarioActual != null && (esCliente || esOrganizador)) {
		boolean conSaldo = usuarioActual.getSaldoVirtual() > 0;
		ArrayList<Tiquete> tiquetesCompra  = (usuarioActual.comprarTiquete(cantidad, evento, idLocalidad, idSilla, conSaldo));
		for (Tiquete tiquete : tiquetesCompra) {
			tiquetes.put(tiquete.getId(), tiquete);
		}
	}
}


public void comprarTiquetesMultiplesMultiEvento(HashMap<Evento,String> eventos) throws UsuarioNoEncontradoException, Exception {
    if (usuarioActual != null && (esCliente || esOrganizador)) {
        boolean conSaldo = usuarioActual.getSaldoVirtual() > 0;
		TiqueteMultiEvento tm  = usuarioActual.comprarTiqueteMultiEvento(eventos, conSaldo);
		ArrayList<Tiquete> tiquetesVenta = tm.getTiquetes();
		for (Tiquete tiquete : tiquetesVenta) {
			tiquetes.put(tiquete.getId(), tiquete);
		}
	}
	}

public void comprarPaqueteDeluxe(Evento evento, String idLocalidad)
        throws UsuarioNoEncontradoException, Exception {
    if (usuarioActual != null && (esCliente || esOrganizador)) {
        boolean conSaldo = usuarioActual.getSaldoVirtual() > 0; 

		PaqueteDeluxe tm  = usuarioActual.comprarPaqueteDeluxe(evento, idLocalidad, conSaldo);
		Tiquete tiquetePrincipal = tm.getTiquetePrincipal();
		tiquetes.put(tiquetePrincipal.getId(), tiquetePrincipal);
		for (Tiquete cortesia :  tm.getCortesias()){
			tiquetes.put(cortesia.getId(), cortesia);
		}
        paquetesDeluxe.put(tm.getId(), tm);
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
		administrador.agregarSolicitud(new SolicitudCancelacionEvento((Organizador)this.usuarioActual, razon, evento));

	}
	public void agendarEvento(Venue venue, Organizador organizador, String tipoDeEvento, LocalDate fecha, LocalTime hora) {

		Evento nuevoEvento  =  (new Evento( venue,  organizador,  tipoDeEvento,  fecha,  hora));
		agregarEvento(nuevoEvento);
		System.out.println("Evento agendado: " + nuevoEvento.getTipoDeEvento() +  " Sin localidades asignadas.");


	}



// Helper de validacion para cliente organizador
private Organizador getOrganizadorActual() throws Exception {
    if (this.usuarioActual == null || !(this.usuarioActual instanceof Organizador)) {
        throw new Exception("El usuario actual no es un Organizador.");
    }
    return (Organizador) this.usuarioActual;
}

//  Sin parámetros opcionales
public Localidad crearLocalidadEvento(String nombre,
                                      int capacidad,
                                      double precioTiquete,
                                      String tipoTiquete,
                                      Evento evento) throws Exception {
    Organizador org = getOrganizadorActual();
    Localidad localidad = org.anadirLocalidadAEvento(nombre, capacidad, precioTiquete, tipoTiquete, evento);
    return localidad;
}

//  Con descuento
public Localidad crearLocalidadEvento(String nombre,
                                      int capacidad,
                                      double precioTiquete,
                                      String tipoTiquete,
                                      Evento evento,
                                      double descuento) throws Exception {
    Organizador org = getOrganizadorActual();
    Localidad localidad = org.anadirLocalidadAEvento(nombre, capacidad, precioTiquete, tipoTiquete, evento, descuento);
    return localidad;
}

//  Con capacidad de tiquetes múltiples
public Localidad crearLocalidadEvento(String nombre,
                                      int capacidad,
                                      double precioTiquete,
                                      String tipoTiquete,
                                      Evento evento,
                                      int capacidadTiquetesMultiples) throws Exception {
    Organizador org = getOrganizadorActual();
    Localidad localidad = org.anadirLocalidadAEvento(nombre, capacidad, precioTiquete, tipoTiquete, evento, capacidadTiquetesMultiples);
    return localidad;
}

// Con descuento y capacidad de tiquetes múltiples
public Localidad crearLocalidadEvento(String nombre,
                                      int capacidad,
                                      double precioTiquete,
                                      String tipoTiquete,
                                      Evento evento,
                                      double descuento,
                                      int capacidadTiquetesMultiples) throws Exception {
    Organizador org = getOrganizadorActual();
    Localidad localidad = org.anadirLocalidadAEvento(nombre, capacidad, precioTiquete, tipoTiquete, evento, descuento, capacidadTiquetesMultiples);
  
    return localidad;
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
			Solicitud.adm.atenderSolicitud(solicitud, aceptar);
		}
		else {
			solicitud.rechazarSolicitud();
		} 
		
	} 

	//metodos para obtener ganancias especificas o generales

	public void obtenerGananciasGenerales(){
		asegurarAdmin();
		System.out.println("Las ganancias generales son: " + administrador.gananciasGlobales());
	}


private void asegurarAdmin() throws IllegalStateException {
    if (!this.esAdministrador || this.administrador == null) {
        throw new IllegalStateException("Se requieren privilegios de Administrador.");
    }
}

// ------------------------
// GANANCIAS POR ORGANIZADOR
// ------------------------ 

public void imprimirGananciasPorOrganizador(Organizador organizador) {
    asegurarAdmin();
    if (organizador == null) {
        System.out.println("Organizador nulo. No se puede calcular ganancias.");
        return;
    }
    double g = this.administrador.gananciasPorOrganizador(organizador);
    System.out.printf("Ganancias del organizador %s: %.2f%n",
            organizador.getLogin(), g);
}

public void imprimirGananciasPorTodosLosOrganizadores() {
    asegurarAdmin();
    if (this.organizadores == null || this.organizadores.isEmpty()) {
        System.out.println("No hay organizadores registrados.");
        return;
    }

    double total = 0.0;
    for (Organizador org : this.organizadores.values()) {
        if (org == null) continue;
        double g = this.administrador.gananciasPorOrganizador(org);
        total += g;
        System.out.printf("Ganancias del organizador %s: %.2f%n",
                org.getLogin(), g);
    }
    System.out.printf("Ganancias acumuladas (todos los organizadores): %.2f%n", total);
}

// --------------
// GANANCIAS POR FECHA
// --------------

public void imprimirGananciasPorFecha(LocalDate fecha) {
    asegurarAdmin();
    if (fecha == null) {
        System.out.println("Fecha nula. No se puede calcular ganancias.");
        return;
    }
    if (this.eventosPorFecha == null || !this.eventosPorFecha.containsKey(fecha)) {
        System.out.printf("No hay eventos en la fecha %s. Ganancias: 0.00%n", fecha);
        return;
    }
    double g = this.administrador.gananciasPorFecha(fecha);
    System.out.printf("Ganancias para la fecha %s: %.2f%n", fecha, g);
}

public void imprimirGananciasPorTodasLasFechas() {
    asegurarAdmin();
    if (this.eventosPorFecha == null || this.eventosPorFecha.isEmpty()) {
        System.out.println("No hay fechas registradas en eventosPorFecha.");
        return;
    }

    double total = 0.0;
    for (LocalDate fecha : this.eventosPorFecha.keySet()) {
        double g = 0.0;
        if (this.eventosPorFecha.get(fecha) != null && !this.eventosPorFecha.get(fecha).isEmpty()) {
            g = this.administrador.gananciasPorFecha(fecha);
        }
        total += g;
        System.out.printf("Ganancias para la fecha %s: %.2f%n", fecha, g);
    }
    System.out.printf("Ganancias acumuladas (todas las fechas): %.2f%n", total);
}


	
	public void fijarComisionPorTipoEvento (double cultural, double deportivo, double musical, double religioso) {
		HashMap<String, Double> comisiones = new HashMap<String, Double>();
		comisiones.put(Evento.CULTURAL, cultural);
		comisiones.put(Evento.DEPORTIVO, deportivo);
		comisiones.put(Evento.MUSICAL, musical);
		comisiones.put(Evento.RELIGIOSO, religioso);
		Evento.tiposDeEventos = comisiones;

	}

	public void mostrarComisionesPorTipoEvento() {
		System.out.println("Comisiones por tipo de evento:");
		for (String tipoEvento : Evento.tiposDeEventos.keySet()) {
			System.out.println(tipoEvento + ": " + Evento.tiposDeEventos.get(tipoEvento) + "%");
		}
	}

	



	// MÉTODOS MARKETPLACE
		//quisas crear otras exceptiones para marketPlace

		public void publicarOferta(Tiquete tiquete, TiqueteMultiple tm, String descripcion, double precio) {
		    if (!(usuarioActual instanceof Cliente)) {
		        System.out.println("Solo los clientes pueden publicar ofertas.");
		        return;
		    }

		        Cliente vendedor = (Cliente) usuarioActual;
		        Oferta nueva;
				try {
					nueva = new Oferta(tiquete, tm, vendedor, descripcion, precio);
					marketPlace.publicarOferta(nueva);
				} catch (TiqueteNoTransferibleException e) {
					e.printStackTrace();
				}
		        
		}

		public void eliminarOferta(Oferta oferta) {
		    if (!(usuarioActual instanceof Cliente)) {
		    	return;
		    }
		    Cliente cliente = (Cliente) usuarioActual;
		    if (oferta.getVendedor().equals(cliente)) {
		        marketPlace.eliminarOferta(oferta, cliente);
		    } else {
		        System.out.println("No puedes eliminar una oferta que no te pertenece.");
		    }
		}

		public void hacerContraOferta(Oferta oferta, double nuevoPrecio,boolean usarSaldo) {
		    if (!(usuarioActual instanceof Cliente)) {
		        System.out.println("Solo los clientes pueden hacer contraofertas.");
		        return;
		    }

		    Cliente comprador = (Cliente) usuarioActual;
		    ContraOferta contra = new ContraOferta(comprador,oferta, nuevoPrecio,usarSaldo);
		    marketPlace.publicarContraOferta(contra);
		}

		public void aceptarContraOferta(ContraOferta contra) {
		    Cliente vendedor = contra.getOfertaOriginal().getVendedor();
		    if (!usuarioActual.equals(vendedor)) {
		        System.out.println("Solo el vendedor puede aceptar una contraoferta.");
		        return;
		    }
		    marketPlace.aceptarContraOferta(contra);
		}

		public void rechazarContraOferta(ContraOferta contra) {
		    Cliente vendedor = contra.getOfertaOriginal().getVendedor();
		    if (!usuarioActual.equals(vendedor)) {
		        System.out.println("Solo el vendedor puede rechazar una contraoferta.");
		        return;
		    }
		    marketPlace.rechazarContraOferta(contra);
		}

		public ArrayList<Oferta> verOfertas() {
		    return new ArrayList<>(marketPlace.getOfertas());
		}

		public void verLogMarketplace() {
		    if (!esAdministrador) {
		        System.out.println("Solo el administrador puede ver el log del marketplace.");
		        return;
		    }
		    System.out.println("===== LOG DEL MARKETPLACE =====");
		    for (String evento : marketPlace.getLog().getEventos()) {
		        System.out.println(evento);
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


	

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
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


	public void escribirMarketplace() {
	    archivoSerializable.escribir(this.marketPlace, "./datos/marketplace.ser");
	}

	public void leerMarketplace() {
	    Object obj = archivoSerializable.leer("./datos/marketplace.ser");
	    if (obj != null) {
	        this.marketPlace = (Marketplace.MarketPlace) obj;
	    } else {
	        this.marketPlace = new Marketplace.MarketPlace();
	    }
	}


}


