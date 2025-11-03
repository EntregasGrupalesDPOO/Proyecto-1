package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exepciones.SaldoInsuficienteException;
import Marketplace.Oferta;
import logica.*;

public class ClienteTest {

    private Cliente comprador;
    private Cliente vendedor;
    private Administrador admin;
    private Organizador organizador;
    private Evento evento;
    private Localidad localidad;
    private Venue venue;
    private Tiquete tiquete;

    @BeforeEach
    void setUp() throws Exception {
        admin = new Administrador("admin", "1234");
        organizador = new Organizador("org", "pass", admin);
        comprador = new Cliente("bastien", "1234");
        vendedor = new Cliente("pierre", "5678"); 

        venue = new Venue(5000,"Movistar Arena", "Bogotá" );
        evento = new Evento(venue, organizador, "MUSICAL", LocalDate.now().plusDays(5), LocalTime.of(19, 30));

        localidad = new Localidad("VIP", 10, 100.0, "BASICO", evento);
        tiquete = localidad.getTiquetes().get(0);
        tiquete.setTransferible(true);
        this.admin.fijarTarifaImpresion(1);
        this.admin.fijarMaximosPorTransaccion(5, 10);
    }
    
    

    // === TEST ACHAT TIQUETE SIMPLE ===
    @Test
    void testComprarTiquete() throws Exception { //es igual a comprar tiquetes multiples
        comprador.actualizarSaldoVirtual(500);
        ArrayList<Tiquete> comprados = comprador.comprarTiquete(2, evento, "VIP", true);

        assertEquals(2, comprados.size());
        assertTrue(comprados.get(0).isComprado());
        assertEquals(238, comprador.getSaldoVirtual(), 0.01);
    }


    @Test
    void testComprarTiqueteMultiEvento() throws Exception {
        comprador.actualizarSaldoVirtual(500);

        HashMap<Evento, String> eventos = new HashMap<>();
        eventos.put(evento, "VIP");

        TiqueteMultiEvento multi = comprador.comprarTiqueteMultiEvento(eventos, true);
        assertNotNull(multi);
        assertTrue(multi.isComprado());
        assertEquals(multi.getTiquetes().size(), 1);
    }

    // === TEST TRANSFERT DE TIQUETE SIMPLE ===
    @Test
    void testTransferirTiquete() throws Exception {
        comprador.agregarTiquete(tiquete);
        assertEquals(comprador, tiquete.getCliente());

        comprador.transferirTiquete(tiquete, vendedor.getLogin(), comprador.getContrasena());
        assertEquals(vendedor, tiquete.getCliente());
    }

    
    @Test
    void testAgregarYEliminarTiquete() {
        comprador.agregarTiquete(tiquete);
        assertEquals(1, comprador.getTiquetes().size());

        comprador.eliminarTiquete(tiquete);
        assertEquals(0, comprador.getTiquetes().size());
    }

    @Test
    void testActualizarSaldoVirtual() {
        comprador.actualizarSaldoVirtual(100.0);
        assertEquals(100.0, comprador.getSaldoVirtual(), 0.01);
    }

    @Test
    void testAceptarOferta() throws Exception {
        comprador.setSaldoVirtual(200);
        Oferta oferta = new Oferta(tiquete, vendedor, "Entrada VIP", 150);
        oferta.getTiquete().setTransferible(true);

        comprador.acceptarOferta(oferta, true);

        assertTrue(oferta.isVendida());
        assertEquals(50, comprador.getSaldoVirtual(), 0.01);
        assertEquals(150, vendedor.getSaldoVirtual(), 0.01);
        assertEquals(comprador, tiquete.getCliente());
    }

    @Test
    void testAceptarOfertaSaldoInsuficiente() throws Exception {
        comprador.setSaldoVirtual(50);
        Oferta oferta = new Oferta(tiquete, vendedor, "VIP", 150);
        oferta.getTiquete().setTransferible(true);

        assertThrows(SaldoInsuficienteException.class, () -> comprador.acceptarOferta(oferta, true));
    }

    @Test
    void testTransferirTiqueteContrasenaInvalida() throws Exception {
        comprador.agregarTiquete(tiquete);
        assertThrows(Exception.class, () -> comprador.transferirTiquete(tiquete, vendedor.getLogin(), "mauvais"));
    }
    
    @Test
    public void comprarTiqueteMultipleEventoTest() {
    	Venue venue1 = admin.crearVenue(10, "barsito", "uniandes");
		Venue venue2 = admin.crearVenue(40, "salon comunal", "zona norte");
		Venue venue3 = admin.crearVenue(30, "magola", "la pola");
		this.admin.anadirTarifaTipoEvento("religioso", 0.2);
		this.admin.anadirTarifaTipoEvento("deportivo", 0.1);
		this.admin.anadirTarifaTipoEvento("cultural", 0.3);
		this.admin.fijarMaximosPorTransaccion(15, 10);
		this.admin.fijarTarifaImpresion(5000);
		
		Cliente miCliente = new Cliente("un parcero", "dpoo");
		try {
			Evento evento1 = this.organizador.crearEvento(venue1, "religioso", LocalDate.of(2026, 1, 1), LocalTime.of(20, 30));
			Evento evento2 = this.organizador.crearEvento(venue2, "deportivo", LocalDate.of(2026, 1, 2), LocalTime.of(20, 30));
			Evento evento3 = this.organizador.crearEvento(venue3, "cultural", LocalDate.of(2026, 1, 3), LocalTime.of(20, 30));
			this.organizador.anadirLocalidadAEvento("loc1", 10, 10000, "BASICO", evento1);
			this.organizador.anadirLocalidadAEvento("loc2", 40, 15000, "BASICO", evento2);
			this.organizador.anadirLocalidadAEvento("loc3", 30, 20000, "BASICO", evento3);
			
			HashMap<Evento, String> paseEventos = new HashMap<Evento, String>();
			paseEventos.put(evento1, "loc1");
			paseEventos.put(evento2, "loc2");
			paseEventos.put(evento3, "loc3");
			 
			TiqueteMultiple tMultievento = miCliente.comprarTiqueteMultiEvento(paseEventos, false);
			
			assertEquals(62550, tMultievento.getPrecioReal());
			for (Tiquete t: tMultievento.getTiquetes()) {
				assertTrue(t.isComprado());
				assertEquals(miCliente,t.getCliente());
			}
			assertTrue(miCliente.getTiquetes().get(tMultievento.getId()).equals(tMultievento));
			assertEquals(1, miCliente.getTiquetes().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void transferirTiqueteIndividualDeMutlipleTest() {
    	Venue venue1 = admin.crearVenue(10, "barsito", "uniandes");
		Venue venue2 = admin.crearVenue(40, "salon comunal", "zona norte");
		Venue venue3 = admin.crearVenue(30, "magola", "la pola");
		this.admin.anadirTarifaTipoEvento("religioso", 0.2);
		this.admin.anadirTarifaTipoEvento("deportivo", 0.1);
		this.admin.anadirTarifaTipoEvento("cultural", 0.3);
		this.admin.fijarMaximosPorTransaccion(15, 10);
		this.admin.fijarTarifaImpresion(5000);
		
		Cliente miCliente = new Cliente("un parcero", "dpoo");
		Cliente miAmigo = new Cliente("otro parcero", "aaaa");
		
		try {
			Evento evento1 = this.organizador.crearEvento(venue1, "religioso", LocalDate.of(2026, 1, 1), LocalTime.of(20, 30));
			Evento evento2 = this.organizador.crearEvento(venue2, "deportivo", LocalDate.of(2026, 1, 2), LocalTime.of(20, 30));
			Evento evento3 = this.organizador.crearEvento(venue3, "cultural", LocalDate.of(2026, 1, 3), LocalTime.of(20, 30));
			this.organizador.anadirLocalidadAEvento("loc1", 10, 10000, "BASICO", evento1);
			this.organizador.anadirLocalidadAEvento("loc2", 40, 15000, "BASICO", evento2);
			this.organizador.anadirLocalidadAEvento("loc3", 30, 20000, "BASICO", evento3);
			
			HashMap<Evento, String> paseEventos = new HashMap<Evento, String>();
			paseEventos.put(evento1, "loc1");
			paseEventos.put(evento2, "loc2");
			paseEventos.put(evento3, "loc3");
			 
			TiqueteMultiple tMultievento = miCliente.comprarTiqueteMultiEvento(paseEventos, false);
			Tiquete unTiquete = tMultievento.getTiquetes().get(0);
			
			miCliente.transferirTiquete(tMultievento, unTiquete, "otro parcero", "dpoo");
			assertFalse(tMultievento.isTransferible());
			assertEquals(miAmigo, unTiquete.getCliente());
			assertEquals(2, tMultievento.getTiquetes().size());
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
