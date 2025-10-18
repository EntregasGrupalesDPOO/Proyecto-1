package logica;

import java.util.ArrayList;
import java.util.Set;

public class Administrador {
	public double obtenerGananciasGlobales(ArrayList<Evento> eventos) {
		double ganancias = 0;
		for (Evento evento:eventos) {
			Set<Localidad> localidades = (Set<Localidad>) evento.getLocalidades().values();
			for (Localidad localidad:localidades) {
				for(Tiquete tiq: localidad.getTiquetesUsados().values()) {
					ganancias += tiq.getPrecioReal() - tiq.getPrecioBase();
				}
				for (TiqueteMultiple tiqM: localidad.getTiquetesMultiplesUsados().values()) {
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
		}
		return ganancias;
	}
	
	public double obtenerGananciasEvento(Evento evento) {
		double ganancias = 0;
		Set<Localidad> localidades = (Set<Localidad>) evento.getLocalidades().values();
		for (Localidad localidad:localidades) {
			for(Tiquete tiq: localidad.getTiquetesUsados().values()) {
				ganancias += tiq.precioBase;
			}
			for (TiqueteMultiple tiqM: localidad.getTiquetesMultiplesUsados().values()) {
				if (tiqM instanceof TiqueteMultipleUnicoEvento) {
					TiqueteMultipleUnicoEvento tiqueteMultipleUE = (TiqueteMultipleUnicoEvento) tiqM;
					for (Tiquete tiq: tiqueteMultipleUE.getTiquetes()) {
						ganancias += tiq.precioBase;
					}
				} else if (tiqM instanceof TiqueteMultipleVariosEventos) {
					TiqueteMultipleVariosEventos tiqueteMultipleVE = (TiqueteMultipleVariosEventos) tiqM;
					for (Tiquete tiq: tiqueteMultipleVE.getTiquetes().values()) {
						ganancias += tiq.precioBase;
					}
				}
			}
		}
		return ganancias;
	}
}


