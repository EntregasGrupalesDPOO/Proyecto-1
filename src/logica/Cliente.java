package logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import Exepciones.CantidadTiquetesExcedidaException;
import Exepciones.CapacidadLocalidadExcedidaException;
import Exepciones.PasswordIncorrectoException;
import Exepciones.SaldoInsuficienteException;
import Exepciones.TiqueteNoEncontradoException;
import Exepciones.TiqueteNoTransferibleException;
import Exepciones.TiqueteVencidoFecha;
import Exepciones.UsuarioNoEncontradoException;
import Marketplace.Oferta;

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

	public boolean login(String login, String contrasena) {
		return this.login.equals(login) && this.contrasena.equals(contrasena);
	}
	
	
	
	// funciona si la localidad vende tiquetes multiples
	public ArrayList<Tiquete> comprarTiquete(int cantidad, Evento evento, String localidad, boolean comprarConSaldo) throws Exception {
		ArrayList<Tiquete> log = new ArrayList<Tiquete>();
		Localidad l = evento.getLocalidadPorNombre(localidad);
		if (l.getTipoTiquete().equals("MULTIPLE")) {
			if(cantidad > TiqueteMultiple.getTiquetesMaximosPorTransaccion()) {
				throw new CantidadTiquetesExcedidaException(TiqueteMultiple.getTiquetesMaximosPorTransaccion());
			}
		}
		if (l.getCantidadTiquetesDisponibles() > cantidad) {
			throw new CapacidadLocalidadExcedidaException(cantidad);
		}
		Tiquete ti = l.obtenerTiqueteDisponible();
		log.add(ti);
		if (comprarConSaldo) {
			if (ti.getPrecioReal()*cantidad > this.saldoVirtual) {
				throw new SaldoInsuficienteException(this);
			}
			this.saldoVirtual = this.saldoVirtual - ti.getPrecioReal()*cantidad;
		}
		for (int i = 0; i < cantidad; i++) {
			Tiquete t = l.obtenerTiqueteDisponible();
			log.add(t);
			tiquetes.put(t.getId(), t);
			t.setComprado(true);
			t.setCliente(this);
		}
		return log;
	}
	
	public ArrayList<Tiquete> comprarTiquete(int cantidad, Evento evento, String localidad, ArrayList<Integer> idSillas, boolean comprarConSaldo) throws Exception {
		ArrayList<Tiquete> log = new ArrayList<Tiquete>();
		Localidad l = evento.getLocalidadPorNombre(localidad);
		if(cantidad > Tiquete.getTiquetesMaximosPorTransaccion()) {
			throw new CantidadTiquetesExcedidaException(Tiquete.getTiquetesMaximosPorTransaccion());
		}
		if (l.getCantidadTiquetesDisponibles() > cantidad) {
			throw new CapacidadLocalidadExcedidaException(cantidad);
		}
		Tiquete ti = l.obtenerTiqueteDisponible(idSillas.get(0));
		log.add(ti);
		if (comprarConSaldo) {
			if (ti.getPrecioReal() * cantidad > this.saldoVirtual) {
				throw new SaldoInsuficienteException(this);
			}
			this.saldoVirtual = this.saldoVirtual - ti.getPrecioReal() * cantidad;
		}
		for (int i = 0; i < cantidad; i++) {
			Tiquete t = l.obtenerTiqueteDisponible(i);
			log.add(ti);
			tiquetes.put(t.getId(), t);
			t.setComprado(true);
			t.setCliente(this);
		}
		return log;
	}
	
	public TiqueteMultiEvento comprarTiqueteMultiEvento(HashMap<Evento, String> eventos, boolean comprarConSaldo) throws Exception {
		TiqueteMultiEvento t = new TiqueteMultiEvento(eventos, this);
		if (eventos.size() > TiqueteMultiple.getTiquetesMaximosPorTransaccion()) {
			throw new CantidadTiquetesExcedidaException(TiqueteMultiple.getTiquetesMaximosPorTransaccion());
		}
		
		if (comprarConSaldo) {
			if (t.getPrecioReal() > this.saldoVirtual) {
				throw new SaldoInsuficienteException(this);
			}
			this.saldoVirtual = this.saldoVirtual - t.getPrecioReal();
		}
		tiquetes.put(t.getId(), t);
		t.setComprado(true);
		t.setCliente(this);
		return t;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}
	
	public PaqueteDeluxe comprarPaqueteDeluxe(Evento evento, String localidad, boolean comprarConSaldo) throws Exception {
		PaqueteDeluxe pd = new PaqueteDeluxe(evento, localidad);
		if (comprarConSaldo) {
			if (pd.getTiquetePrincipal().getPrecioReal() > this.saldoVirtual) {
				throw new SaldoInsuficienteException(this);
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
		return pd;
		
	}
	
	public void transferirTiquete(Tiquete tiquete, String login, String contrasena ) throws Exception {
		if (!contrasena.equals(this.contrasena)) {
			throw new PasswordIncorrectoException(this);
		}
		
		if (!tiquete.isTransferible()) {
			throw new TiqueteNoTransferibleException(tiquete);
		}
		
		if (tiquete.getFecha().isBefore(LocalDate.now())){
			throw new TiqueteVencidoFecha(tiquete);
		}
		eliminarTiquete(tiquete);
		clientes.get(login).agregarTiquete(tiquete);
		if (clientes.get(login).equals(null)) {
			throw new UsuarioNoEncontradoException(login);
		}
		tiquete.setCliente(clientes.get(login));
	}
	
	public void transferirTiquete(TiqueteMultiple tiqueteMultiple, Tiquete tiquete, String login, String contrasena ) throws Exception {
		Tiquete t = tiqueteMultiple.getTiquete(tiquete);
		if (t.equals(null)) {
			throw new TiqueteNoEncontradoException(t.getId());
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

public void acceptarOferta(Oferta oferta, boolean usarSaldo) throws Exception {
	if (usarSaldo == true && this.saldoVirtual < oferta.getPrecio()){
		throw new SaldoInsuficienteException(this);
	}
	this.setSaldoVirtual(this.getSaldoVirtual()-oferta.getPrecio());
    oferta.setVendida(true);
    //transferirTiquete
    Cliente vendedorOferta= oferta.getVendedor();
    vendedorOferta.setSaldoVirtual(vendedorOferta.getSaldoVirtual()+oferta.getPrecio());
    vendedorOferta.transferirTiquete(oferta.getTiquete(),this.login, this.contrasena );
    
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



public double getSaldoVirtual() {
	return this.saldoVirtual;
}

public void setSaldoVirtual(double saldo) {
	this.saldoVirtual=saldo;
}
}

