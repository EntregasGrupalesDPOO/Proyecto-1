package tests;

import logica.Cliente;
import Marketplace.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class MarketPlaceTest {

    private MarketPlace marketPlace;
    private Cliente vendedor;
    private Cliente comprador;
    private Oferta oferta;
    private ContraOferta contra;

    @BeforeEach
    public void setUp() throws Exception {
        marketPlace = new MarketPlace();
        vendedor = new Cliente("juan", "1234");
        comprador = new Cliente("maria", "abcd");

        logica.Tiquete tiqueteMock = new logica.TiqueteBasico(100, 0.1, java.time.LocalDate.now(), java.time.LocalTime.now());
        tiqueteMock.setTransferible(true);
        oferta = new Oferta(tiqueteMock, vendedor, "Tiquete para concierto", 150.0);
        contra = new ContraOferta(comprador, oferta, 140.0, false);
    }

    @Test
    public void testPublicarOferta() {
        marketPlace.publicarOferta(oferta);
        List<Oferta> ofertas = marketPlace.getOfertas();

        assertEquals(1, ofertas.size(), "Debe haber una oferta publicada");
        assertTrue(marketPlace.getLog().getEventos().get(0).contains("Nueva oferta publicada"),
                "El log debe registrar la publicación de la oferta");
    }

    @Test
    public void testEliminarOferta() {
        marketPlace.publicarOferta(oferta);
        marketPlace.eliminarOferta(oferta, vendedor);

        assertTrue(marketPlace.getOfertas().isEmpty(), "La oferta debe ser eliminada");
        assertTrue(marketPlace.getLog().getEventos().get(1).contains("Oferta eliminada"),
                "El log debe registrar la eliminación de la oferta");
    }

    @Test
    public void testPublicarContraOferta() {
        marketPlace.publicarOferta(oferta);
        marketPlace.publicarContraOferta(contra);

        assertEquals(1, oferta.getContraOfertas().size(), "La oferta debe tener una contraoferta");
        assertTrue(marketPlace.getLog().getEventos().get(1).contains("Nueva contra oferta publicada"),
                "El log debe registrar la creación de la contraoferta");
    }

    @Test
    public void testAceptarContraOferta() {
        marketPlace.publicarOferta(oferta);
        marketPlace.publicarContraOferta(contra);
        marketPlace.aceptarContraOferta(contra);

        List<String> logs = marketPlace.getLog().getEventos();
        assertTrue(logs.get(logs.size() - 1).contains("Contraoferta aceptada"),
                "El log debe registrar la aceptación de la contraoferta");
    }

    @Test
    public void testRechazarContraOferta() {
        marketPlace.publicarOferta(oferta);
        marketPlace.publicarContraOferta(contra);
        marketPlace.rechazarContraOferta(contra);

        List<String> logs = marketPlace.getLog().getEventos();
        assertTrue(logs.get(logs.size() - 1).contains("Contraoferta rechazada"),
                "El log debe registrar el rechazo de la contraoferta");
    }
}
