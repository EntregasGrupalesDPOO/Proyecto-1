package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import logica.*;
import Marketplace.*;
import Exepciones.*;

public class BoletasMasterTest {

    private BoletasMaster sistema;
    private Administrador admin;
    private Cliente vendedor;
    private Cliente comprador;
    private Evento evento;
    private Venue venue;
    private Organizador organizador;
    private Localidad localidad;
    private Tiquete tiquete;

    @BeforeEach
    public void setUp() throws Exception {
        sistema = new BoletasMaster();

        // Crear usuarios
        sistema.agregarAdministrador("admin", "123");
        sistema.agregarOrganizador("org", "org123");
        sistema.agregarCliente("vendedor", "pass");
        sistema.agregarCliente("comprador", "pass");

        //Iniciar sesión como administrador
        sistema.loginAdministrador("admin", "123");
        admin = sistema.getAdministrador();

        //Obtener referencias 
        organizador = sistema.getOrganizadores().get("org");
        vendedor = sistema.getClientes().get("vendedor");
        comprador = sistema.getClientes().get("comprador");

        vendedor.setSaldoVirtual(1000);
        comprador.setSaldoVirtual(1000);

        //Iniciar sesión como organizador y crear evento
        sistema.loginOrganizador("org", "org123");

        venue = new Venue(100, "Teatro Central", "Bogotá");
        sistema.agregarVenue(venue);

        sistema.agendarEvento(venue, organizador, Evento.MUSICAL, LocalDate.now().plusDays(5), LocalTime.of(20, 0));
        evento = sistema.getEventos().get(0);

        localidad = sistema.crearLocalidadEvento("VIP", 5, 100.0, "BASICO", evento);

        // Obtener un tiquete transferible
        tiquete = localidad.getTiquetes().get(0);
    }

    @Test
    public void testPublicarOfertaValida() throws Exception {
        // Cliente publica una oferta válida
        sistema.loginCliente("vendedor", "pass");

        assertDoesNotThrow(() -> {
            sistema.publicarOferta(tiquete, "Reventa VIP", 200.0);
        });

        ArrayList<Oferta> ofertas = sistema.verOfertas();
        assertEquals(1, ofertas.size());
        assertEquals(200.0, ofertas.get(0).getPrecio());
    }

    @Test
    public void testEliminarOfertaPorPropietario() throws Exception {
        // El vendedor elimina su propia oferta
        sistema.loginCliente("vendedor", "pass");
        sistema.publicarOferta(tiquete, "Ticket VIP", 150.0);
        Oferta oferta = sistema.verOfertas().get(0);

        sistema.eliminarOferta(oferta);
        assertTrue(sistema.verOfertas().isEmpty(), "El vendedor debe poder eliminar su oferta.");
    }

    @Test
    public void testEliminarOfertaPorOtroCliente() throws Exception {
        // Otro cliente intenta eliminar la oferta
        sistema.loginCliente("vendedor", "pass");
        sistema.publicarOferta(tiquete, "Ticket VIP", 150.0);
        Oferta oferta = sistema.verOfertas().get(0);

        sistema.loginCliente("comprador", "pass");
        sistema.eliminarOferta(oferta);

        assertFalse(sistema.verOfertas().isEmpty(), "Otro cliente no debe poder eliminar la oferta.");
    }

    @Test
    public void testHacerContraOferta() throws Exception {
        // Cliente hace una contraoferta
        sistema.loginCliente("vendedor", "pass");
        sistema.publicarOferta(tiquete, "VIP para concierto", 300.0);
        Oferta oferta = sistema.verOfertas().get(0);

        sistema.loginCliente("comprador", "pass");
        sistema.hacerContraOferta(oferta, 250.0, true);

        ArrayList<ContraOferta> contraOfertas = oferta.getContraOfertas();
        assertEquals(1, contraOfertas.size());
        assertEquals(250.0, contraOfertas.get(0).getNuevoPrecio());
    }

    @Test
    public void testAceptarContraOfertaPorVendedor() throws Exception {
        // Vendedor acepta una contraoferta
        sistema.loginCliente("vendedor", "pass");
        sistema.publicarOferta(tiquete, "Entrada VIP", 400.0);
        Oferta oferta = sistema.verOfertas().get(0);

        sistema.loginCliente("comprador", "pass");
        sistema.hacerContraOferta(oferta, 350.0, true);
        ContraOferta contra = oferta.getContraOfertas().get(0);

        sistema.loginCliente("vendedor", "pass");
        sistema.aceptarContraOferta(contra);

        assertTrue(oferta.isVendida(), "La oferta debe marcarse como vendida.");
        assertTrue(contra.isAceptada(), "La contraoferta debe marcarse como aceptada.");
    }

    @Test
    public void testRechazarContraOferta() throws Exception {
        // Vendedor rechaza una contraoferta
        sistema.loginCliente("vendedor", "pass");
        sistema.publicarOferta(tiquete, "Entrada VIP", 400.0);
        Oferta oferta = sistema.verOfertas().get(0);

        sistema.loginCliente("comprador", "pass");
        sistema.hacerContraOferta(oferta, 350.0, true);
        ContraOferta contra = oferta.getContraOfertas().get(0);

        sistema.loginCliente("vendedor", "pass");
        sistema.rechazarContraOferta(contra);

        assertFalse(contra.isAceptada(), "La contraoferta debe estar rechazada.");
        assertFalse(oferta.isVendida(), "La oferta no debe marcarse como vendida después de un rechazo.");
    }

    @Test
    public void testVerLogMarketplaceComoAdmin() throws Exception {
        // El administrador puede ver el log
        sistema.loginCliente("vendedor", "pass");
        sistema.publicarOferta(tiquete, "Ticket VIP", 250.0);

        sistema.loginAdministrador("admin", "123");

        assertDoesNotThrow(() -> {
            sistema.verLogMarketplace();
        });
    }
}
