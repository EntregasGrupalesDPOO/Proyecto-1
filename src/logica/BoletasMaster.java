package logica;

import java.util.ArrayList;
import java.util.HashMap;

public class BoletasMaster {
	
	
	private boolean esAdministrador;
	private boolean esOrganizador;
	private boolean esCliente;
	private ArrayList<Venue> venues;
	private HashMap<String, Tiquete> tiquetes;
	private HashMap<String, Organizador> organizadores;
	private HashMap<String, Cliente> clientes;
	private HashMap<String, Usuario> usuarios;
	private Administrador administrador; 
	
	
	

	
	
	
	public BoletasMaster() {
		esAdministrador = false;
		esOrganizador = false;
		esCliente = false;
	    this.venues = new ArrayList<Venue>();
	    this.tiquetes = new HashMap<String, Tiquete>();
	    this.organizadores = new HashMap<String, Organizador>();
	    this.clientes = new HashMap<String, Cliente>();
	    this.usuarios = new HashMap<String, Usuario>();
		
		 
	

	}
	
	public void solicitarReembolsoCalamidad() {
		
		
	}

}
