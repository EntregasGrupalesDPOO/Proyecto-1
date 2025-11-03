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
        evento = new Evento("concierto","concierto de maluma",venue, organizador, Evento.MUSICAL, LocalDate.now().plusDays(5), LocalTime.of(19, 30));

        localidad = new Localidad("VIP", 10, 100.0, "BASICO", evento);
        tiquete = localidad.getTiquetes().get(0);
        tiquete.setTransferible(true);
    }

    // === TEST ACHAT TIQUETE SIMPLE ===
    @Test
    void testComprarTiqueteSimple() throws Exception {
        comprador.actualizarSaldoVirtual(500);
        ArrayList<Tiquete> comprados = comprador.comprarTiquete(2, evento, "VIP", true);

        assertEquals(2, comprados.size());
        assertTrue(comprados.get(0).isComprado());
        assertEquals(100, comprador.getSaldoVirtual(), 0.01);
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
}
