package tests;

import Marketplace.*;
import logica.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MarketPlaceTest {

    private MarketPlace market;
    private Cliente vendedor;
    private Cliente comprador;
    private Tiquete tiquete;
    private Oferta oferta;

    @BeforeEach
    public void setUp() throws Exception {
        market = new MarketPlace();
        vendedor = new Cliente("vendedor", "123");
        comprador = new Cliente("comprador", "456");

        tiquete = new TiqueteBasico(100.0, 0.1, java.time.LocalDate.now().plusDays(5), java.time.LocalTime.now());
        tiquete.setTransferible(true); // ✅ permet la revente

        oferta = new Oferta(tiquete, vendedor, "Entrada VIP", 120.0);
    }

    @Test
    public void testPublicarOferta() {
        market.publicarOferta(oferta);
        assertTrue(market.getOfertas().contains(oferta), "La oferta debería estar en la lista del marketplace");
    }

    @Test
    public void testEliminarOferta() {
        market.publicarOferta(oferta);
        market.eliminarOferta(oferta, vendedor);
        assertFalse(market.getOfertas().contains(oferta), "La oferta debería haberse eliminado");
    }

    @Test
    public void testPublicarContraOferta() {
        market.publicarOferta(oferta);
        ContraOferta contra = new ContraOferta(comprador, oferta, 110.0, false);
        market.publicarContraOferta(contra);
        assertTrue(oferta.getContraOfertas().contains(contra), "La contraoferta debería haberse agregado");
    }

    @Test
    public void testAceptarContraOferta() {
        market.publicarOferta(oferta);
        ContraOferta contra = new ContraOferta(comprador, oferta, 110.0, false);
        market.publicarContraOferta(contra);
 
        assertDoesNotThrow(() -> market.aceptarContraOferta(contra));
        assertTrue(market.getLog().getEventos().stream().anyMatch(e -> e.contains("aceptada")),
                   "Debería registrarse en el log la aceptación de la contraoferta");
    }

    @Test
    public void testRechazarContraOferta() {
        market.publicarOferta(oferta);
        ContraOferta contra = new ContraOferta(comprador, oferta, 105.0, false);
        market.publicarContraOferta(contra);

        assertDoesNotThrow(() -> market.rechazarContraOferta(contra));
        assertTrue(market.getLog().getEventos().stream().anyMatch(e -> e.contains("rechazada")),
                   "Debería registrarse en el log el rechazo de la contraoferta");
    }
}
