package logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Cliente {
	protected String login;
	protected String contrasena;
	protected double saldoVirtual;
	protected HashMap<Integer, Tiquete> tiquetes;
	protected String tipoCliente;
	protected static HashMap<String, Cliente> clientes = new HashMap<String, Cliente>();
	protected ArrayList<String> beneficios;
	
	
	public Cliente(String login, String contrasena) {
		this.login = login;
		this.contrasena = contrasena;
		this.tipoCliente = "Normal";
		this.tiquetes = new HashMap<Integer, Tiquete>();
		clientes.put(login, this);
	}
	
	
	
	// funciona si la localidad vende tiquetes multiples
	public void comprarTiquete(int cantidad, Evento evento, String localidad, boolean comprarConSaldo) throws Exception {
		Localidad l = evento.getLocalidadPorNombre(localidad);
		if (l.getTipoTiquete().equals("MULTIPLE")) {
			if(cantidad > TiqueteMultiple.getTiquetesMaximosPorTransaccion()) {
				throw new Exception();
			}
		}
		Tiquete ti = l.obtenerTiqueteDisponible();
		if (comprarConSaldo) {
			if (ti.getPrecioReal()*cantidad > this.saldoVirtual) {
				throw new Exception();
			}
			this.saldoVirtual = this.saldoVirtual - ti.getPrecioReal()*cantidad;
		}
		for (int i = 0; i < cantidad; i++) {
			Tiquete t = l.obtenerTiqueteDisponible();
			tiquetes.put(t.getId(), t);
			t.setComprado(true);
			t.setCliente(this);
		}
	}
	
	public void comprarTiquete(int cantidad, Evento evento, String localidad, int idSilla, boolean comprarConSaldo) throws Exception {
		Localidad l = evento.getLocalidadPorNombre(localidad);
		if(cantidad > Tiquete.getTiquetesMaximosPorTransaccion()) {
			throw new Exception();
		}
		Tiquete ti = l.obtenerTiqueteDisponible(idSilla);
		if (comprarConSaldo) {
			if (ti.getPrecioReal() * cantidad > this.saldoVirtual) {
				throw new Exception();
			}
			this.saldoVirtual = this.saldoVirtual - ti.getPrecioReal() * cantidad;
		}
		for (int i = 0; i < cantidad; i++) {
			Tiquete t = l.obtenerTiqueteDisponible();
			tiquetes.put(t.getId(), t);
			t.setComprado(true);
			t.setCliente(this);
		}
	}
	
	public void comprarTiqueteMultiEvento(HashMap<Evento, String> eventos, boolean comprarConSaldo) throws Exception {
		TiqueteMultiEvento t = new TiqueteMultiEvento(eventos, this);
		if (eventos.size() > TiqueteMultiple.getTiquetesMaximosPorTransaccion()) {
			throw new Exception();
		}
		if (comprarConSaldo) {
			if (t.getPrecioReal() > this.saldoVirtual) {
				throw new Exception();
			}
			this.saldoVirtual = this.saldoVirtual - t.getPrecioReal();
		}
		tiquetes.put(t.getId(), t);
		t.setComprado(true);
		t.setCliente(this);
	}

	public String getTipoCliente() {
		return tipoCliente;
	}
	
	public void comprarPaqueteDeluxe(Evento evento, String localidad, boolean comprarConSaldo) throws Exception {
		PaqueteDeluxe pd = new PaqueteDeluxe(evento, localidad);
		if (comprarConSaldo) {
			if (pd.getTiquetePrincipal().getPrecioReal() > this.saldoVirtual) {
				throw new Exception();
			}
		this.saldoVirtual = this.saldoVirtual - pd.getTiquetePrincipal().getPrecioReal();
		}
		tiquetes.put(pd.getTiquetePrincipal().getId(), pd.getTiquetePrincipal());
		pd.getTiquetePrincipal().setComprado(true);
		pd.getTiquetePrincipal().setCliente(this);
		this.beneficios.addAll(pd.getBeneficios());
		for (Tiquete t: pd.getCortesias()) {
			this.tiquetes.put(t.getId(),t);
		}
		
	}
	
	public void transferirTiquete(Tiquete tiquete, String login, String contrasena ) throws Exception {
		if (!contrasena.equals(this.contrasena)) {
			throw new Exception();
		}
		
		if (!tiquete.isTransferible()) {
			throw new Exception();
		}
		
		if (tiquete.getFecha().isBefore(LocalDate.now())){
			throw new Exception();
		}
		eliminarTiquete(tiquete);
		clientes.get(login).agregarTiquete(tiquete);
		if (clientes.get(login).equals(null)) {
			throw new Exception();
		}
		tiquete.setCliente(clientes.get(login));
	}
	
	public void transferirTiquete(TiqueteMultiple tiqueteMultiple, Tiquete tiquete, String login, String contrasena ) throws Exception {
		Tiquete t = tiqueteMultiple.getTiquete(tiquete);
		if (t.equals(null)) {
			throw new Exception();
		}
		transferirTiquete(tiquete, login, contrasena);
		tiqueteMultiple.setTransferible(false);
	}
	
	public void agregarTiquete(Tiquete tiquete) {
		this.tiquetes.put(tiquete.getId(), tiquete);
	}
	
	public void eliminarTiquete(Tiquete tiquete) {
		this.tiquetes.remove(tiquete.getId());
	}
	
	public void actualizarSaldoVirtual(double valor) {
		this.saldoVirtual += valor;
	}
}

