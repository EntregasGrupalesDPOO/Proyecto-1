package Marketplace;

import java.util.ArrayList;
import java.util.List;

import logica.Cliente;
import logica.Usuario;

public class MarketPlace {
    private List<Oferta> ofertas;
    private LogReventa log;

    public MarketPlace() {
        this.ofertas = new ArrayList<>();
        this.log = new LogReventa();
    }
    

    public void publicarOferta(Oferta oferta) {
        ofertas.add(oferta);
        log.registrarEvento("Nueva oferta publicada: " + oferta.getDescripcion());
    }

    public void eliminarOferta(Oferta oferta, Cliente autor) {
        if (ofertas.remove(oferta)) {
            log.registrarEvento("Oferta eliminada por " + autor.getLogin() + ": " + oferta.getDescripcion());
        }
    }
    
    public void publicarContraOferta(ContraOferta contra) {
        contra.getOfertaOriginal().agregarContraOferta(contra);
    }

    public void registrarContraOferta(ContraOferta contra) {
        log.registrarEvento("Contraoferta registrada: " + contra.getDescripcion());
    }

    public void aceptarContraOferta(ContraOferta contra) {
        log.registrarEvento("Contraoferta aceptada: " + contra.getDescripcion());
        //transferir tiquete y modificar saldo
    }

    public List<Oferta> getOfertas() {
        return ofertas;
    }

    public LogReventa getLog() {
        return log;
    }
}
