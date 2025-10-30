package Marketplace;

import java.util.ArrayList;

import Exepciones.SaldoInsuficienteException;
import Exepciones.TiqueteNoTransferibleException;
import logica.Tiquete;
import logica.TiqueteMultiple;
import logica.Cliente;

public class Oferta {

    private Tiquete tiquete;
    private TiqueteMultiple tiqueteMultiple;
    private Cliente vendedor;
    private String descripcion;
    private double precio;
    private boolean vendida;
    private ArrayList<ContraOferta> contraOfertas;

    public Oferta(Tiquete tiquete, TiqueteMultiple tiqueteMultiple, Cliente vendedor, String descripcion, double precio) throws TiqueteNoTransferibleException {
        if (tiquete != null && tiqueteMultiple != null) {
            throw new IllegalArgumentException("Una oferta no puede tener ambos tipos de tiquete.");
        }
        if (tiquete == null && tiqueteMultiple == null) {
            throw new IllegalArgumentException("Debe tener al menos un tiquete.");
        }
        if (tiqueteMultiple!=null) {
        	if (tiqueteMultiple.isTransferible()) {
        	throw new TiqueteNoTransferibleException(tiqueteMultiple);
        }
        }
        

        this.tiquete = tiquete;
        this.tiqueteMultiple = tiqueteMultiple;
        this.vendedor = vendedor;
        this.descripcion = descripcion;
        this.precio = precio;
        this.vendida = false;
        this.contraOfertas = new ArrayList<>();
    }

    public void agregarContraOferta(ContraOferta contra) {
        contraOfertas.add(contra);
    }

    public boolean esMultiple() {
        return tiqueteMultiple != null;
    }

    public Cliente getVendedor() { 
        return vendedor; 
    }

    public void setVendida(boolean vendida) { 
        this.vendida = vendida; 
    }

    public Tiquete getTiquete() {
        return tiquete;
    }

    public TiqueteMultiple getTiqueteMultiple() {
        return tiqueteMultiple;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public ArrayList<ContraOferta> getContraOfertas() {
        return contraOfertas;
    }

    public boolean isVendida() {
        return vendida;
    }
    


    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void removeContraOferta(ContraOferta contra) {
    	this.contraOfertas.remove(contra);
    }
    
    //a hacer : AcceptarOferta
    public void acceptarOferta(boolean usarSaldo) {
    	
    }
    

    @Override
    public String toString() {
        String tipo = esMultiple() ? "Tiquete Múltiple" : "Tiquete Simple";
        Integer idTiquete = esMultiple() ? tiqueteMultiple.getId() : tiquete.getId();
        return "Oferta: " + idTiquete + " (" + tipo + ") - Precio: $" + precio + " - " + descripcion;
    }
}
