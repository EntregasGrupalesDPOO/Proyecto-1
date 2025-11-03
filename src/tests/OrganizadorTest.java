package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Administrador;
import logica.Cliente;
import logica.Evento;
import logica.Organizador;
import logica.Venue;

public class OrganizadorTest {
	private Organizador organizador;
	private Administrador admin;
	private ArrayList<Venue> venues;
	
	@BeforeEach
	public void setUp() {
		this.admin = new Administrador("admin", "abcd");
		this.organizador = new Organizador("santiago", "1234", this.admin);
		this.venues = new ArrayList<Venue>();		
		Venue venue1 = admin.crearVenue(10, "barsito", "uniandes");
		Venue venue2 = admin.crearVenue(40, "salon comunal", "zona norte");

		venues.add(venue1);
		venues.add(venue2);
		
		
		this.admin.anadirTarifaTipoEvento("religioso", 0.2);
		this.admin.anadirTarifaTipoEvento("deportivo", 0.1);

		this.admin.fijarMaximosPorTransaccion(15, 10);
		this.admin.fijarTarifaImpresion(5000);
		
		try {
			Evento evento1 = this.organizador.crearEvento(venue1, "religioso", LocalDate.of(2026, 1, 1), LocalTime.of(20, 30));
			Evento evento2 = this.organizador.crearEvento(venue2, "deportivo", LocalDate.of(2026, 1, 2), LocalTime.of(20, 30));
	
			
			this.organizador.anadirLocalidadAEvento("loc1", 8, 15000, "ENUMERADO", evento1);
			this.organizador.anadirLocalidadAEvento("loc2", 2, 15000, "ENUMERADO", evento1, 0.15);
			
			this.organizador.anadirLocalidadAEvento("loc3", 4, 150000, "MULTIPLE", evento2, 5);
			this.organizador.anadirLocalidadAEvento("loc4", 4, 150000, "MULTIPLE", evento2, 0.2, 5);
			
			Cliente cliente1 = new Cliente("barto", "aycaramba");
			Cliente cliente2 = new Cliente("homero", "doh");
			Cliente cliente3 = new Cliente("nelson", "ja ja");
			
			ArrayList<Integer> sillas= new ArrayList<Integer>();
			sillas.add(1);
			sillas.add(2);
			sillas.add(3);
			sillas.add(4);
			sillas.add(5);
			cliente1.comprarTiquete(5, evento1, "loc1", sillas, false);
			cliente2.comprarTiquete(2, evento1, "loc2", sillas, false);
			cliente3.comprarTiquete(3, evento2, "loc3", false);
			cliente3.comprarTiquete(4, evento2, "loc4", false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void consultarGananciasLocalidadTest() {
		assertEquals(75000, this.organizador.consultarGananciasLocalidad(this.organizador.getEventosCreados().get(0).getLocalidadPorNombre("loc1")));
		assertEquals(25500, this.organizador.consultarGananciasLocalidad(this.organizador.getEventosCreados().get(0).getLocalidadPorNombre("loc2")));
		assertEquals(450000, this.organizador.consultarGananciasLocalidad(this.organizador.getEventosCreados().get(1).getLocalidadPorNombre("loc3")));
		assertEquals(480000, this.organizador.consultarGananciasLocalidad(this.organizador.getEventosCreados().get(1).getLocalidadPorNombre("loc4")));
	}
	
	
	@Test
	public void consultarPorcentajeLocalidadTest() {
		assertEquals(0.625, this.organizador.consultarPorcentajeLocalidad(this.organizador.getEventosCreados().get(0).getLocalidadPorNombre("loc1")));
		assertEquals(1, this.organizador.consultarPorcentajeLocalidad(this.organizador.getEventosCreados().get(0).getLocalidadPorNombre("loc2")));
		assertEquals(0.75, this.organizador.consultarPorcentajeLocalidad(this.organizador.getEventosCreados().get(1).getLocalidadPorNombre("loc3")));
		assertEquals(1, this.organizador.consultarPorcentajeLocalidad(this.organizador.getEventosCreados().get(1).getLocalidadPorNombre("loc4")));
	}
	
	@Test
	public void consultarGananciasEventoTest() {
		assertEquals(100500, this.organizador.consultarGananciasEvento(this.organizador.getEventosCreados().get(0)));
		assertEquals(930000, this.organizador.consultarGananciasEvento(this.organizador.getEventosCreados().get(1)));
	}
	
	@Test
	public void consultarPorcentajeEventoTest() {
		assertEquals(0.7, this.organizador.consultarPorcentajeEvento(this.organizador.getEventosCreados().get(0)));
		assertEquals(0.875, this.organizador.consultarPorcentajeEvento(this.organizador.getEventosCreados().get(1)));
	}
	
	@Test
	public void consultarGananciasGlobalesTest() {
		assertEquals(1030500, this.organizador.consultarGananciasGlobales());
	}
	
	@Test
	public void consultarPorcentajeGlobalesTest() {
		assertEquals(42.0/50.0, this.organizador.consultarPorcentajeGlobales());
	}
}
