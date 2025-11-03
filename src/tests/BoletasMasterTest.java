package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import logica.*;
import Marketplace.*;
import Exepciones.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import logica.*;
import Marketplace.*;
import Exepciones.*;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Exepciones.OperacionNoAutorizadaException;
import Exepciones.PasswordIncorrectoException;
import Exepciones.UsuarioNoEncontradoException;
import logica.*;

// Suite única: SIN Marketplace.
public class BoletasMasterTest {

    private BoletasMaster bm;
    private Administrador admin;
    private Organizador org;
    private Cliente c1, c2;
    private Venue venue;
    private Evento evento;

    @BeforeEach
    void setUp() {
        bm = new BoletasMaster();

        bm.agregarAdministrador("admin", "1234");
        admin = bm.getAdministrador();

        bm.agregarOrganizador("org", "pass");
        bm.agregarCliente("alice", "111");
        bm.agregarCliente("bob", "222");

        org = new Organizador("org", "pass", admin);
        c1  = new Cliente("alice", "111");
        c2  = new Cliente("bob", "222");

        venue  = new Venue(5000, "Movistar Arena", "Bogotá");
        evento = new Evento(venue, org, "MUSICAL",
                LocalDate.now().plusDays(5), LocalTime.of(19, 30));
    }



    @Test
    void constructor_estadoInicial_ok() {
        assertFalse(bm.isEsAdministrador());
        assertFalse(bm.isEsCliente());
        assertFalse(bm.isEsOrganizador());

        assertNotNull(bm.getEventos());
        assertNotNull(bm.getEventosPorFecha());
        assertNotNull(bm.getVenues());
        assertNotNull(bm.getTiquetes());
    }

    @Test
    void agregarEvento_agregaAListasYMapa() {
        bm.agregarEvento(evento);

        assertEquals(1, bm.getEventos().size());
        assertTrue(bm.getEventosPorFecha().containsKey(evento.getFecha()));
        assertTrue(bm.getEventosPorFecha().get(evento.getFecha()).contains(evento));
    }

    @Test
    void agregarVenueYTiquete_ok() {
        bm.agregarVenue(venue);
        assertEquals(1, bm.getVenues().size());

        TiqueteBasico tb = new TiqueteBasico(
                100.0, 0.10,
                LocalDate.now().plusDays(5), LocalTime.now());
        bm.agregarTiquete(tb);
        assertTrue(bm.getTiquetes().containsKey(tb.getId()));
    }



    @Test
    void loginCliente_ok() throws Exception {
        bm.loginCliente("alice", "111");
        assertTrue(bm.isEsCliente());
        assertFalse(bm.isEsOrganizador());
        assertFalse(bm.isEsAdministrador());
        assertEquals("alice", bm.getUsuarioActual().getLogin());
    }

    @Test
    @DisplayName("Login organizador correcto activa flags y usuarioActual")
    void loginOrganizador_ok() throws Exception {
        bm.loginOrganizador("org", "pass");
        assertTrue(bm.isEsOrganizador());
        assertFalse(bm.isEsCliente());
        assertFalse(bm.isEsAdministrador());
        assertEquals("org", bm.getUsuarioActual().getLogin());
    }

    @Test
    @DisplayName("Login admin correcto activa flag admin y permite que Solicitud.adm se setee")
    void loginAdmin_ok() throws Exception {
        bm.loginAdministrador("admin", "1234");
        assertTrue(bm.isEsAdministrador());
        assertFalse(bm.isEsCliente());
        assertFalse(bm.isEsOrganizador());
        // No hay getter para Solicitud.adm; validamos que no lance excepción en métodos que exigen admin.
        assertDoesNotThrow(() -> bm.obtenerGananciasGenerales());
    }

    @Test
    @DisplayName("Login cliente: usuario inexistente -> UsuarioNoEncontradoException")
    void loginCliente_usuarioInexistente() {
        assertThrows(UsuarioNoEncontradoException.class,
                () -> bm.loginCliente("nope", "x"));
    }

    @Test
    @DisplayName("Login cliente: password malo -> PasswordIncorrectoException")
    void loginCliente_passwordMalo() {
        assertThrows(PasswordIncorrectoException.class,
                () -> bm.loginCliente("alice", "bad"));
    }

    @Test
    @DisplayName("Login organizador: usuario inexistente y password malo")
    void loginOrganizador_errores() {
        assertThrows(UsuarioNoEncontradoException.class,
                () -> bm.loginOrganizador("none", "x"));
        assertThrows(PasswordIncorrectoException.class,
                () -> bm.loginOrganizador("org", "bad"));
    }

    @Test
    void loginAdmin_passwordMalo() {
        assertThrows(PasswordIncorrectoException.class,
                () -> bm.loginAdministrador("admin", "bad"));
    }

  

    @Test
    void crearLocalidad_sinOrganizador_falla() {
        assertThrows(OperacionNoAutorizadaException.class, () ->
                bm.crearLocalidadEvento("VIP", 10, 100.0, "BASICO", evento));
    }

    @Test
    void crearLocalidad_comoOrganizador_ok() throws Exception {
        // Loguearse como organizador (dispara la validación interna de getOrganizadorActual)
        bm.loginOrganizador("org", "pass");
        Localidad loc = bm.crearLocalidadEvento("VIP", 10, 100.0, "BASICO", evento);
        assertNotNull(loc);
        assertEquals("VIP", loc.getNombre());
    }

    @Test
    void agendarEvento_comoOrganizador_agrega() throws Exception {
        bm.loginOrganizador("org", "pass");
        int antes = bm.getEventos().size();
        bm.agendarEvento(venue, org, "MUSICAL",
                LocalDate.now().plusDays(10), LocalTime.of(20, 0));
        assertTrue(bm.getEventos().size() >= antes + 1);
    }

    @Test
    void agendarEvento_sinOrganizador_falla() {
        assertThrows(OperacionNoAutorizadaException.class, () ->
                bm.agendarEvento(venue, org, "MUSICAL",
                        LocalDate.now().plusDays(10), LocalTime.of(20, 0)));
    }

    @Test
    void asegurarAdmin_enConsultas_lanzaSinAdmin() {
        assertThrows(IllegalStateException.class, () -> bm.obtenerGananciasGenerales());
        assertThrows(IllegalStateException.class, () -> bm.imprimirGananciasPorOrganizador(org));
        assertThrows(IllegalStateException.class, () -> bm.obtenerGananciasEvento(evento));
        assertThrows(IllegalStateException.class, () -> bm.imprimirGananciasPorFecha(LocalDate.now()));
        assertThrows(IllegalStateException.class, () -> bm.imprimirGananciasPorTodasLasFechas());
        assertThrows(IllegalStateException.class, () -> bm.imprimirGananciasPorTodosLosOrganizadores());
    }

    @Test
    void consultasGanancias_comoAdmin_noLanzan() throws Exception {
        bm.loginAdministrador("admin", "1234");
        assertDoesNotThrow(() -> bm.obtenerGananciasGenerales());
        assertDoesNotThrow(() -> bm.imprimirGananciasPorOrganizador(org));
        assertDoesNotThrow(() -> bm.obtenerGananciasEvento(evento));
        assertDoesNotThrow(() -> bm.imprimirGananciasPorFecha(null)); // rama de 'fecha nula'
        assertDoesNotThrow(() -> bm.imprimirGananciasPorTodasLasFechas());
        assertDoesNotThrow(() -> bm.imprimirGananciasPorTodosLosOrganizadores());
    }


    @Test
    void solicitarReembolso_cliente_ok() throws Exception {
        bm.loginCliente("alice", "111");

        TiqueteBasico tb = new TiqueteBasico(
                100.0, 0.10,
                LocalDate.now().plusDays(5), LocalTime.now());
        bm.agregarTiquete(tb);

        int antes = bm.getAdministrador().getSolicitudes().size();
        bm.solicitarReembolso(tb.getId(), "Enfermedad");
        assertEquals(antes + 1, bm.getAdministrador().getSolicitudes().size());
    }

    @Test
    void solicitarCancelacionEvento_org_ok() throws Exception {
        bm.loginOrganizador("org", "pass");
        bm.agregarEvento(evento);
        int antes = bm.getAdministrador().getSolicitudes().size();
        bm.solicitarCancelacionEvento(evento, "Insolvencia");
        assertEquals(antes + 1, bm.getAdministrador().getSolicitudes().size());
    }

    @Test
    void proponerVenue_org_ok() throws Exception {
        // Debe existir admin y usuario actual (usamos organizador)
        bm.loginOrganizador("org", "pass");
        int antes = bm.getAdministrador().getSolicitudes().size();
        bm.proponerVenue(3000, "Teatro ABC", "Chapinero");
        assertEquals(antes + 1, bm.getAdministrador().getSolicitudes().size());
    }


    @Test
    void fijarComision_porTipoEvento_seteaMapa() {
        bm.fijarComisionPorTipoEvento(0.1, 0.2, 0.3, 0.4);
        assertEquals(0.1, Evento.tiposDeEventos.get(Evento.CULTURAL), 1e-9);
        assertEquals(0.2, Evento.tiposDeEventos.get(Evento.DEPORTIVO), 1e-9);
        assertEquals(0.3, Evento.tiposDeEventos.get(Evento.MUSICAL), 1e-9);
        assertEquals(0.4, Evento.tiposDeEventos.get(Evento.RELIGIOSO), 1e-9);
    }



    @Test
    void comprarTiquetes_sinUsuario_noExcepcion() throws Exception {
        // No hay usuario logueado: método sale sin operar -> no lanza
        assertDoesNotThrow(() -> bm.comprarTiquetes(1, evento, "VIP"));
        // Nada que afirmar sobre mapa si la compra subyacente no ocurre
    }

    @Test
    void comprarTiquetes_comoCliente_noFalla() throws Exception {
        bm.loginCliente("alice", "111");
        Localidad loc = new Localidad("VIP", 10, 100.0, "BASICO", evento);

        bm.comprarTiquetes(1, evento, "VIP");
    }

    @Test
    void comprarTiquetes_multiplesMultiEvento_ok() throws Exception {
        bm.loginCliente("alice", "111");
        HashMap<Evento,String> evs = new HashMap<>();
        evs.put(evento, "VIP");
        assertDoesNotThrow(() -> bm.comprarTiquetesMultiplesMultiEvento(evs));
    }

    @Test
    void comprarPaqueteDeluxe_ok() throws Exception {
        bm.loginCliente("alice", "111");
        Localidad loc = new Localidad("VIP", 10, 100.0, "BASICO", evento);
        assertDoesNotThrow(() -> bm.comprarPaqueteDeluxe(evento, "VIP"));
    }
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
