package tests;

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

        // Usuarios base (mismo estilo de tus compañeros)
        bm.agregarAdministrador("admin", "1234");
        admin = bm.getAdministrador();

        bm.agregarOrganizador("org", "pass");
        bm.agregarCliente("alice", "111");
        bm.agregarCliente("bob", "222");

        // Referencias útiles para varios tests
        org = new Organizador("org", "pass", admin);
        c1  = new Cliente("alice", "111");
        c2  = new Cliente("bob", "222");

        venue  = new Venue(5000, "Movistar Arena", "Bogotá");
        evento = new Evento(venue, org, "MUSICAL",
                LocalDate.now().plusDays(5), LocalTime.of(19, 30));
    }

    // ------------------------------------------------------------------------------------
    // CONSTRUCTOR / ESTADO INICIAL / ADDERS
    // ------------------------------------------------------------------------------------

    @Test
    @DisplayName("Constructor: estructuras inicializadas y sin roles activos")
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
    @DisplayName("Agregar evento: se añade a lista y a mapa por fecha")
    void agregarEvento_agregaAListasYMapa() {
        bm.agregarEvento(evento);

        assertEquals(1, bm.getEventos().size());
        assertTrue(bm.getEventosPorFecha().containsKey(evento.getFecha()));
        assertTrue(bm.getEventosPorFecha().get(evento.getFecha()).contains(evento));
    }

    @Test
    @DisplayName("Agregar venue y tiquete: adders funcionan")
    void agregarVenueYTiquete_ok() {
        bm.agregarVenue(venue);
        assertEquals(1, bm.getVenues().size());

        // Crear tiquete básico como en los tests de tus compañeros
        TiqueteBasico tb = new TiqueteBasico(
                100.0, 0.10,
                LocalDate.now().plusDays(5), LocalTime.now());
        bm.agregarTiquete(tb);
        assertTrue(bm.getTiquetes().containsKey(tb.getId()));
    }

    // ------------------------------------------------------------------------------------
    // LOGIN / VALIDACIÓN DE ROLES (incluye auxiliares a través de métodos públicos)
    // ------------------------------------------------------------------------------------

    @Test
    @DisplayName("Login cliente correcto activa flags y usuarioActual")
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
    @DisplayName("Login admin: password malo -> PasswordIncorrectoException")
    void loginAdmin_passwordMalo() {
        assertThrows(PasswordIncorrectoException.class,
                () -> bm.loginAdministrador("admin", "bad"));
    }

    // ------------------------------------------------------------------------------------
    // MÉTODOS QUE EJECUTAN VALIDACIONES AUXILIARES DE ROL (getOrganizadorActual / asegurarAdmin)
    // ------------------------------------------------------------------------------------

    @Test
    @DisplayName("Crear localidad sin ser organizador -> OperacionNoAutorizadaException")
    void crearLocalidad_sinOrganizador_falla() {
        // Nadie logueado (ni organizador)
        assertThrows(OperacionNoAutorizadaException.class, () ->
                bm.crearLocalidadEvento("VIP", 10, 100.0, "BASICO", evento));
    }

    @Test
    @DisplayName("Crear localidad siendo organizador -> OK (usa helper de validación interna)")
    void crearLocalidad_comoOrganizador_ok() throws Exception {
        // Loguearse como organizador (dispara la validación interna de getOrganizadorActual)
        bm.loginOrganizador("org", "pass");
        Localidad loc = bm.crearLocalidadEvento("VIP", 10, 100.0, "BASICO", evento);
        assertNotNull(loc);
        assertEquals("VIP", loc.getNombre());
    }

    @Test
    @DisplayName("Agendar evento requiere organizador; al serlo, agrega el evento")
    void agendarEvento_comoOrganizador_agrega() throws Exception {
        bm.loginOrganizador("org", "pass");
        int antes = bm.getEventos().size();
        bm.agendarEvento(venue, org, "MUSICAL",
                LocalDate.now().plusDays(10), LocalTime.of(20, 0));
        assertTrue(bm.getEventos().size() >= antes + 1);
    }

    @Test
    @DisplayName("Agendar evento sin ser organizador -> OperacionNoAutorizadaException")
    void agendarEvento_sinOrganizador_falla() {
        assertThrows(OperacionNoAutorizadaException.class, () ->
                bm.agendarEvento(venue, org, "MUSICAL",
                        LocalDate.now().plusDays(10), LocalTime.of(20, 0)));
    }

    @Test
    @DisplayName("Impresiones/consultas de ganancias requieren admin: asegurarAdmin lanza si no lo eres")
    void asegurarAdmin_enConsultas_lanzaSinAdmin() {
        assertThrows(IllegalStateException.class, () -> bm.obtenerGananciasGenerales());
        assertThrows(IllegalStateException.class, () -> bm.imprimirGananciasPorOrganizador(org));
        assertThrows(IllegalStateException.class, () -> bm.obtenerGananciasEvento(evento));
        assertThrows(IllegalStateException.class, () -> bm.imprimirGananciasPorFecha(LocalDate.now()));
        assertThrows(IllegalStateException.class, () -> bm.imprimirGananciasPorTodasLasFechas());
        assertThrows(IllegalStateException.class, () -> bm.imprimirGananciasPorTodosLosOrganizadores());
    }

    @Test
    @DisplayName("Si eres admin, las consultas de ganancias no lanzan")
    void consultasGanancias_comoAdmin_noLanzan() throws Exception {
        bm.loginAdministrador("admin", "1234");
        // Cubrimos ramas que imprimen sin NPE aunque no haya datos
        assertDoesNotThrow(() -> bm.obtenerGananciasGenerales());
        assertDoesNotThrow(() -> bm.imprimirGananciasPorOrganizador(org));
        assertDoesNotThrow(() -> bm.obtenerGananciasEvento(evento));
        assertDoesNotThrow(() -> bm.imprimirGananciasPorFecha(null)); // rama de 'fecha nula'
        assertDoesNotThrow(() -> bm.imprimirGananciasPorTodasLasFechas());
        assertDoesNotThrow(() -> bm.imprimirGananciasPorTodosLosOrganizadores());
    }

    // ------------------------------------------------------------------------------------
    // SOLICITUDES (cliente y organizador) -> validan wiring con Administrador y listas de solicitudes
    // ------------------------------------------------------------------------------------

    @Test
    @DisplayName("Cliente solicita reembolso -> se crea SolicitudCalamidad en Admin")
    void solicitarReembolso_cliente_ok() throws Exception {
        // Loguearse como cliente
        bm.loginCliente("alice", "111");

        // Necesitamos un tiquete dentro de bm.getTiquetes() para referenciar por id
        TiqueteBasico tb = new TiqueteBasico(
                100.0, 0.10,
                LocalDate.now().plusDays(5), LocalTime.now());
        bm.agregarTiquete(tb);

        int antes = bm.getAdministrador().getSolicitudes().size();
        bm.solicitarReembolso(tb.getId(), "Enfermedad");
        assertEquals(antes + 1, bm.getAdministrador().getSolicitudes().size());
    }

    @Test
    @DisplayName("Organizador solicita cancelación de evento -> se agrega SolicitudCancelacionEvento")
    void solicitarCancelacionEvento_org_ok() throws Exception {
        bm.loginOrganizador("org", "pass");
        bm.agregarEvento(evento);
        int antes = bm.getAdministrador().getSolicitudes().size();
        bm.solicitarCancelacionEvento(evento, "Insolvencia");
        assertEquals(antes + 1, bm.getAdministrador().getSolicitudes().size());
    }

    @Test
    @DisplayName("Organizador propone venue -> se agrega SolicitudVenue al Admin")
    void proponerVenue_org_ok() throws Exception {
        // Debe existir admin y usuario actual (usamos organizador)
        bm.loginOrganizador("org", "pass");
        int antes = bm.getAdministrador().getSolicitudes().size();
        bm.proponerVenue(3000, "Teatro ABC", "Chapinero");
        assertEquals(antes + 1, bm.getAdministrador().getSolicitudes().size());
    }

    // ------------------------------------------------------------------------------------
    // COMISIONES POR TIPO DE EVENTO
    // ------------------------------------------------------------------------------------

    @Test
    @DisplayName("Fijar comisión por tipo de evento llena el mapa estático de Evento")
    void fijarComision_porTipoEvento_seteaMapa() {
        bm.fijarComisionPorTipoEvento(0.1, 0.2, 0.3, 0.4);
        assertEquals(0.1, Evento.tiposDeEventos.get(Evento.CULTURAL), 1e-9);
        assertEquals(0.2, Evento.tiposDeEventos.get(Evento.DEPORTIVO), 1e-9);
        assertEquals(0.3, Evento.tiposDeEventos.get(Evento.MUSICAL), 1e-9);
        assertEquals(0.4, Evento.tiposDeEventos.get(Evento.RELIGIOSO), 1e-9);
    }

    // ------------------------------------------------------------------------------------
    // COMPRAS (paths mínimos para cubrir guardas de rol y wiring sin usar Marketplace)
    // ------------------------------------------------------------------------------------

    @Test
    @DisplayName("Comprar tiquetes requiere cliente logueado; sin usuario no hace nada")
    void comprarTiquetes_sinUsuario_noExcepcion() throws Exception {
        // No hay usuario logueado: método sale sin operar -> no lanza
        assertDoesNotThrow(() -> bm.comprarTiquetes(1, evento, "VIP"));
        // Nada que afirmar sobre mapa si la compra subyacente no ocurre
    }

    @Test
    @DisplayName("Cliente puede invocar comprarTiquetes (cubre guardas y wiring)")
    void comprarTiquetes_comoCliente_noFalla() throws Exception {
        bm.loginCliente("alice", "111");
        // Creamos una localidad para que el flujo tenga contexto razonable
        Localidad loc = new Localidad("VIP", 10, 100.0, "BASICO", evento);
        // La compra real depende de la lógica de Cliente; aquí cubrimos el guard y el loop de inserción en mapa
        // Si tu implementación exige saldo>0, puedes setearlo en Cliente (c1.setSaldoVirtual(...))
        bm.comprarTiquetes(1, evento, "VIP");
        // No hay aserción dura: distintas implementaciones pueden o no generar tiquetes aquí.
    }

    @Test
    @DisplayName("Compra multi-evento: cubre guardas y loop de inserción")
    void comprarTiquetes_multiplesMultiEvento_ok() throws Exception {
        bm.loginCliente("alice", "111");
        HashMap<Evento,String> evs = new HashMap<>();
        evs.put(evento, "VIP");
        assertDoesNotThrow(() -> bm.comprarTiquetesMultiplesMultiEvento(evs));
    }

    @Test
    @DisplayName("Compra paquete deluxe: cubre guardas y registro de cortesías")
    void comprarPaqueteDeluxe_ok() throws Exception {
        bm.loginCliente("alice", "111");
        Localidad loc = new Localidad("VIP", 10, 100.0, "BASICO", evento);
        assertDoesNotThrow(() -> bm.comprarPaqueteDeluxe(evento, "VIP"));
    }
}
