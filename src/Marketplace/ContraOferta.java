package Marketplace;

import logica.Cliente;

public class ContraOferta {
    private Cliente comprador;       
    private Oferta ofertaOriginal;     
    private double nuevoPrecio;
    private boolean aceptada;

    public ContraOferta(Cliente comprador, Oferta ofertaOriginal, double nuevoPrecio) {
        this.comprador = comprador;
        this.ofertaOriginal = ofertaOriginal;
        this.nuevoPrecio = nuevoPrecio;
        this.aceptada = false;
    }

    public void aceptar(String login, String contrasena) throws Exception {
        // Solo el usuario que creio la oferta puede aceptar
        if (!login.equals(ofertaOriginal.getVendedor()) || !contrasena.equals(ofertaOriginal.getVendedor())) {
            throw new Exception("Solo el vendedor original puede aceptar esta contraoferta.");
        }

        this.aceptada = true;
        ofertaOriginal.setVendida(true);

        // Notifiar el comprador ?
    }

    public void rechazar(String login, String contrasena) throws Exception {
        // Solo el usuario que creio la oferta puede rechazar
        if (!login.equals(ofertaOriginal.getVendedor()) || !contrasena.equals(ofertaOriginal.getVendedor())) {
            throw new Exception("Solo el vendedor original puede rechazar esta contraoferta.");
        }

        this.aceptada = false;
        //notifiar el comprador ?
    }

    // Getters
    public String getComprador() { 
    	return comprador.getLogin(); 
    	}
    
    public double getNuevoPrecio() { 
    	return nuevoPrecio; 
    	}
    
    public boolean isAceptada() { 
    	return aceptada; 
    	}
    
    public Oferta getOfertaOriginal() { 
    	return ofertaOriginal; 
    	}

    public String getDescripcion() {
    	return comprador.getLogin()+ " quiere comprar el tiquete para "+ ofertaOriginal.getTiquete().getEvento()+ " de " +ofertaOriginal.getVendedor()+
    			" al precio de "+ nuevoPrecio;
    }
    @Override
    public String toString() {
        return "Contraoferta{" +
                "comprador=" + comprador.getLogin() +
                ", precio=" + nuevoPrecio +
                ", aceptada=" + aceptada +
                '}';
    }
}

