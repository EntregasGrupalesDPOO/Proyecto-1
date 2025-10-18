package logica;

import java.util.ArrayList;

public class Administrador {
	private String usuario;
	private String contrasena;
	private ArrayList<Solicitud> solicitudes;

	
	public boolean login(String usuario, String contrasena) {
		return (usuario.equals(this.usuario) && contrasena.equals(this.contrasena));
		
	}

}
