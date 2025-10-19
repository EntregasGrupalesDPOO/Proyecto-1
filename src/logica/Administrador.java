package logica;

import java.util.ArrayList;



import java.util.ArrayList;
import java.util.Collection;

public class Administrador {
	private String usuario;
	private String contrasena;
	private ArrayList<Solicitud> solicitudes;

	public boolean login(String usuario, String contrasena) {
		return usuario.equals(this.usuario) && contrasena.equals(this.contrasena);
	}

	public double obtenerGananciasGenerales(ArrayList<Evento> eventos) {
		double ganancias = 0;
		for (Evento evento : eventos) {
			Collection<Localidad> localidades = evento.getLocalidades().values();
			for (Localidad localidad : localidades) {
				for (Tiquete tiq : localidad.getTiquetesUsados().values()) {
					ganancias += tiq.getPrecioReal() - tiq.getPrecioBase();
				}
				for (TiqueteMultiple tiqM : localidad.getTiquetesMultiplesUsados().values()) {
					if (tiqM instanceof TiqueteMultipleUnicoEvento) {
						TiqueteMultipleUnicoEvento tiqueteMultipleUE = (TiqueteMultipleUnicoEvento) tiqM;
						for (Tiquete tiq : tiqueteMultipleUE.getTiquetes()) {
							ganancias += tiq.getPrecioReal() - tiq.getPrecioBase();
						}
					} else if (tiqM instanceof TiqueteMultipleVariosEventos) {
						TiqueteMultipleVariosEventos tiqueteMultipleVE = (TiqueteMultipleVariosEventos) tiqM;
						for (Tiquete tiq : tiqueteMultipleVE.getTiquetes().values()) {
							ganancias += tiq.getPrecioReal() - tiq.getPrecioBase();
						}
					}
				}
			}
		}
		return ganancias;
	}

	public double obtenerGananciasEvento(Evento evento) {
		double ganancias = 0;
		Set<Localidad> localidades = (Set<Localidad>) evento.getLocalidades().values();
		for (Localidad localidad:localidades) {
			for(Tiquete tiq: localidad.getTiquetesUsados().values()) {
				ganancias += tiq.getPrecioReal() - tiq.getPrecioBase();
			}
			for (TiqueteMultiple tiqM : localidad.getTiquetesMultiplesUsados().values()) {
				if (tiqM instanceof TiqueteMultipleUnicoEvento) {
					TiqueteMultipleUnicoEvento tiqueteMultipleUE = (TiqueteMultipleUnicoEvento) tiqM;
					for (Tiquete tiq: tiqueteMultipleUE.getTiquetes()) {
						ganancias += tiq.getPrecioReal() - tiq.getPrecioBase();
					}
				} else if (tiqM instanceof TiqueteMultipleVariosEventos) {
					TiqueteMultipleVariosEventos tiqueteMultipleVE = (TiqueteMultipleVariosEventos) tiqM;
					for (Tiquete tiq: tiqueteMultipleVE.getTiquetes().values()) {
						ganancias += tiq.getPrecioReal() - tiq.getPrecioBase();
					}
				}
			}
		}
		return ganancias;
	}
	
	public void realizarReembolsoEvento(Evento evento) {
		Set<Localidad> localidades = (Set<Localidad>) evento.getLocalidades().values();
		for (Localidad localidad:localidades) {
			for(Tiquete tiq: localidad.getTiquetesUsados().values()) {
				Usuario usuario = tiq.getUsuario();
				usuario.setSaldoVirtual(usuario.getSaldoVirtual() + tiq.getPrecioBase());
				tiq.setPrecioBase(0);
				tiq.setPrecioReal(tiq.getPrecioReal()- tiq.getPrecioBase());
			}
			for (TiqueteMultiple tiqM: localidad.getTiquetesMultiplesUsados().values()) {
				if (tiqM instanceof TiqueteMultipleUnicoEvento) {
					TiqueteMultipleUnicoEvento tiqueteMultipleUE = (TiqueteMultipleUnicoEvento) tiqM;
					for (Tiquete tiq: tiqueteMultipleUE.getTiquetes()) {
						Usuario usuario = tiq.getUsuario();
						usuario.setSaldoVirtual(usuario.getSaldoVirtual() + tiq.getPrecioBase());
						tiq.setPrecioBase(0);
						tiq.setPrecioReal(tiq.getPrecioReal()- tiq.getPrecioBase());
					}
				} else if (tiqM instanceof TiqueteMultipleVariosEventos) {
					TiqueteMultipleVariosEventos tiqueteMultipleVE = (TiqueteMultipleVariosEventos) tiqM;
					for (Tiquete tiq: tiqueteMultipleVE.getTiquetes().values()) {
						Usuario usuario = tiq.getUsuario();
						usuario.setSaldoVirtual(usuario.getSaldoVirtual() + tiq.getPrecioBase());
						tiq.setPrecioBase(0);
						tiq.setPrecioReal(tiq.getPrecioReal()- tiq.getPrecioBase());
					}
				}
			}
		}
	}
}


