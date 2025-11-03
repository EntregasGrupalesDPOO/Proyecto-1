package tests;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import logica.Administrador;
import logica.Cliente;
import logica.Evento;
import logica.Organizador;
import logica.Venue;

public class AdministradorTest {
	private Administrador admin;
	private ArrayList<Venue> venues;
	private ArrayList<Organizador> organizadores;

	@BeforeEach
	public void setUp(){
		this.admin = new Administrador("hola", "chao");
		Venue venue1 = admin.crearVenue(10, "barsito", "uniandes");
		Venue venue2 = admin.crearVenue(40, "salon comunal", "zona norte");
		Venue venue3 = admin.crearVenue(30, "magola", "la pola");
		venues.add(venue1);
		venues.add(venue2);
		venues.add(venue3);
		
		Organizador org1 = new Organizador("santi", "1234", this.admin);
		Organizador org2 = new Organizador("juan", "abcd", this.admin);
		Organizador org3 = new Organizador("sebas", "aaaa", this.admin);
		
		organizadores.add(org1);
		organizadores.add(org2);
		organizadores.add(org3);
		
		this.admin.anadirTarifaTipoEvento("religioso", 0.2);
		this.admin.anadirTarifaTipoEvento("deportivo", 0.1);
		this.admin.anadirTarifaTipoEvento("culturar", 0.3);
		this.admin.fijarMaximosPorTransaccion(15, 10);
		this.admin.fijarTarifaImpresion(5000);
		
		try {
			Evento evento1 = org1.crearEvento(venue1, "religioso", LocalDate.of(2026, 1, 1), LocalTime.of(20, 30));
			org1.anadirLocalidadAEvento("loc1", 10, 15000, "ENUMERADO", evento1);
			Evento evento2 = org2.crearEvento(venue1, "deportivo", LocalDate.of(2026, 1, 2), LocalTime.of(20, 30));
			org2.anadirLocalidadAEvento("loc1", 8, 50000, "MULTIPLE", evento2, 5);
			Evento evento3 = org3.crearEvento(venue1, "cultural", LocalDate.of(2026, 1, 3), LocalTime.of(20, 30));
			org3.anadirLocalidadAEvento("loc1", 30, 20000, "BASICO", evento3);
			
			Cliente cliente1 = new Cliente("barto", "aycaramba");
			Cliente cliente2 = new Cliente("homero", "doh");
			Cliente cliente3 = new Cliente("nelson", "ja ja");
			
			ArrayList<Integer> sillas= new ArrayList<Integer>();
			sillas.add(5);
			sillas.add(6);
			sillas.add(7);
			sillas.add(8);
			sillas.add(9);
			sillas.add(10);
			cliente1.comprarTiquete(5, evento1, "loc1", sillas, false);
			cliente2.comprarTiquete(2, evento2, "loc1", false);
			cliente3.comprarTiquete(10, evento3, "loc1", false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void obtenerGananciasGlobalesTest() {
		assertequals
	}
	
}
