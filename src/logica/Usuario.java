package logica;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Usuario {
	protected String login;
	protected String contrasena;
	protected HashMap<Evento, ArrayList<Tiquete>> tiquetes;
	protected HashMap<Integer, TiqueteMultiple> tiquetesMultiples;
	protected double saldoVirtual;

	public abstract void comprarTiquetes(int cantidad, Evento evento, Integer idLocalidad, boolean usarSaldo);
	public abstract void comprarTiquetesEnumerados(int cantidad, Evento evento, Integer idLocalidad, int idSilla, boolean usarSaldo);
	public abstract void comprarTiquetesMultiplesUE(int cantidad, Evento evento, Integer idLocalidad, boolean usarSaldo);
	public abstract void comprarTiquetesMultiplesVE(HashMap<Evento, Integer> eventos, boolean usarSaldo);
	public abstract void comprarTiquetesDeluxe(int cantidad, Evento evento, Integer idLocalidad, boolean usarSaldo);
	
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
