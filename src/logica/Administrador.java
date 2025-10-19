package logica;

import java.util.ArrayList;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class Administrador {
	private String usuario;
	private String contrasena;
	private ArrayList<Solicitud> solicitudes;

	public  ArrayList<Solicitud>  getSolicitudes() {
		return new ArrayList<Solicitud>(solicitudes);
	}
	public Administrador(String usuario, String contrasena) {
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.solicitudes = new ArrayList<Solicitud>();
	}

	public boolean login(String usuario, String contrasena) {
		return usuario.equals(this.usuario) && contrasena.equals(this.contrasena);
	}

	public void agregarSolicitud(Solicitud solicitud) {
		this.solicitudes.add(solicitud);
	}
	public void mostrarSolicitudesPendientes() {
		if (solicitudes.isEmpty()) {
			System.out.println("No hay solicitudes pendientes.");
		} else {
			for (int i = 0; i < solicitudes.size(); i++) {
				Solicitud solicitud = solicitudes.get(i);
				System.out.println((i) + ". " + solicitud.getTipo() + " - Solicitante: " + solicitud.getSolicitante().getLogin() + " - Descripción: " + solicitud.getDescripcion());
			}
		}
	}
	public void atenderSolicitud (Solicitud solicitud, boolean aceptar) throws Exception {
		if (aceptar) {
			solicitud.aceptarSolicitud();
		}  else {
			solicitud.rechazarSolicitud();
		}
		this.solicitudes.remove(solicitud);

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


