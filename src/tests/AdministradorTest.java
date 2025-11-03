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
import logica.Tiquete;
import logica.Venue;

public class AdministradorTest {
	private Administrador admin;
	private ArrayList<Venue> venues;
	private ArrayList<Organizador> organizadores;
	private ArrayList<Evento> eventos; 

	@BeforeEach
	public void setUp(){
		this.venues = new ArrayList<Venue>();
		this.organizadores = new ArrayList<Organizador>();
		this.eventos = new ArrayList<Evento>();
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
		this.admin.anadirTarifaTipoEvento("cultural", 0.3);
		this.admin.fijarMaximosPorTransaccion(15, 10);
		this.admin.fijarTarifaImpresion(5000);
		
		try {
			Evento evento1 = org1.crearEvento(venue1, "religioso", LocalDate.of(2026, 1, 1), LocalTime.of(20, 30));
			eventos.addLast(evento1);
			org1.anadirLocalidadAEvento("loc1", 10, 15000, "ENUMERADO", evento1);
			Evento evento2 = org2.crearEvento(venue2, "deportivo", LocalDate.of(2026, 1, 2), LocalTime.of(20, 30));
			eventos.addLast(evento2);
			org2.anadirLocalidadAEvento("loc2", 8, 50000, "MULTIPLE", evento2, 5);
			Evento evento3 = org3.crearEvento(venue3, "cultural", LocalDate.of(2026, 1, 3), LocalTime.of(20, 30));
			eventos.addLast(evento3);
			org3.anadirLocalidadAEvento("loc3", 30, 20000, "BASICO", evento3);
			
			
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
			cliente2.comprarTiquete(2, evento2, "loc2", false);
			cliente3.comprarTiquete(10, evento3, "loc3", false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void obtenerGananciasGlobalesTest() {
		assertEquals(178000, this.admin.gananciasGlobales());
	}
	
	@Test
	public void obtenerGananciasFecha() {
		assertEquals(48000,this.admin.gananciasPorFecha(LocalDate.of(2026, 1, 1)));
		assertEquals(20000,(int)this.admin.gananciasPorFecha(LocalDate.of(2026, 1, 2)));
		assertEquals(110000,this.admin.gananciasPorFecha(LocalDate.of(2026, 1, 3)));
	}
	 
	@Test
	public void obtenerGananciasEvento() {
		assertEquals(48000,this.admin.gananciasPorEvento(eventos.get(0)));
		assertEquals(20000,(int)this.admin.gananciasPorEvento(eventos.get(1)));
		assertEquals(110000,this.admin.gananciasPorEvento(eventos.get(2)));
	}
	
	@Test
	public void obtenerGananciasOrganizador() {
		assertEquals(48000, this.admin.gananciasPorOrganizador(organizadores.get(0)));
		assertEquals(20000, (int)this.admin.gananciasPorOrganizador(organizadores.get(1)));
		assertEquals(110000, this.admin.gananciasPorOrganizador(organizadores.get(2)));
	}
	
	@Test
	public void reembolsarTiqueteCalamidadTest() {
		Cliente cliente2 = Cliente.clientes.get("homero");
		Tiquete tiq = cliente2.getTiquetes().get(98);
		this.admin.reembolsarTiqueteCalamidad(cliente2, tiq);
		
		assertEquals(null, cliente2.getTiquetes().get(98));
		assertEquals(0, tiq.getPrecioBase());
		assertEquals(0, tiq.getPrecioReal());
		assertEquals(false, tiq.isTransferible());
	}
	
	@Test
	public void cancelarEventoTest() {
		Cliente cliente2 = Cliente.clientes.get("homero");
		Evento evento2 = eventos.get(1);
		this.admin.cancelarEvento(evento2);
		assertEquals("CANCELADO", evento2.getEstado());
		assertEquals(null, cliente2.getTiquetes().get(98));
		assertEquals(110000, (int)cliente2.getSaldoVirtual());
		assertEquals(10000, this.admin.gananciasPorEvento(evento2));
	}
	
	@Test
	public void cancelarEventoInsolvenciaTest() {
		Cliente cliente2 = Cliente.clientes.get("homero");
		Evento evento2 = eventos.get(1);
		this.admin.cancelarEventoInsolvencia(evento2);
		assertEquals("CANCELADO", evento2.getEstado());
		assertEquals(null, cliente2.getTiquetes().get(98));
		assertEquals(100000, (int)cliente2.getSaldoVirtual());
		assertEquals(20000, (int)this.admin.gananciasPorEvento(evento2));
	}
}
