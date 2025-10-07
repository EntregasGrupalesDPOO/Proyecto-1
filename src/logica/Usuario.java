package logica;

import java.util.List;

public abstract class Usuario {
	protected String login;
	protected String contrasena;
	protected List <Tiquete> tiquetes;
	protected double saldoVirtual;
	
	
	public String getLogin() {
		return login;
	}
	public String getContrasena() {
		return contrasena;
	}
	public List<Tiquete> getTiquetes() {
		return tiquetes;
	}
	public double getSaldoVirtual() {
		return saldoVirtual;
	}
	
	

}
