package logica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class PaqueteDeluxe {
	private int id;
	private static int contadorPaquetes = 0;
	private Tiquete tiquetePrincipal;
	private ArrayList<String> beneficios;
	private ArrayList<Tiquete> cortesias;
	private static double porcentajeExtra;
	
	public PaqueteDeluxe(Evento evento, String nombreLocalidad) throws Exception {
		Localidad localidad = evento.getLocalidadPorNombre(nombreLocalidad);
		if (localidad == null) {
			throw new Exception();		
		}
		Tiquete tiquete = localidad.obtenerTiqueteDisponible();
		if (tiquete == null) {
            throw new Exception("No hay tiquetes disponibles en la localidad " + nombreLocalidad);
        }
		this.tiquetePrincipal = tiquete;
		this.tiquetePrincipal.actualizarPrecios(this.tiquetePrincipal.getPrecioBase() * (1 + porcentajeExtra));
		this.tiquetePrincipal.setTransferible(false);
		this.beneficios = new ArrayList<String>();
		this.cortesias = new ArrayList<Tiquete>();
		this.id = ++contadorPaquetes;
	}
	
	//El organizador definirá los beneficios y cortesías fijas por temporadas, pueden cambiar.
	public void setBeneficios(ArrayList<String> beneficios) {
		this.beneficios = beneficios;
	}

	public void setCortesias(ArrayList<Tiquete> cortesias) {
		this.cortesias = cortesias;
	}
	
	public void agregarBeneficio(String beneficio) {
	    beneficios.add(beneficio);
	}

	public void agregarCortesia(Tiquete cortesia) {
	    cortesias.add(cortesia);
	}

	public Tiquete getTiquetePrincipal() {
		return tiquetePrincipal;
	}

	public ArrayList<String> getBeneficios() {
		return beneficios;
	}

	public ArrayList<Tiquete> getCortesias() {
		return cortesias;
	}
	
}
