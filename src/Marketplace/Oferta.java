package Marketplace;

import java.util.ArrayList;

import logica.Tiquete;
import logica.Cliente;

public class Oferta {
	private Tiquete tiquete;
    private Cliente vendedor;
    private String descripcion;
    private double precio;
    private boolean vendida;
    private ArrayList<ContraOferta> contraOfertas;

    public Oferta(Tiquete tiquete,Cliente vendedor, String descripcion, double precio) {
    	this.tiquete=tiquete;
        this.vendedor = vendedor;
        this.descripcion = descripcion;
        this.precio = precio;
        this.vendida = false;
        this.contraOfertas=new ArrayList<ContraOferta>();
    }

    public void agregarContraOferta(ContraOferta contra) {
    	contraOfertas.add(contra);
    }
    
    public String getVendedor() { 
    	return vendedor.getLogin(); 
    	}
    
    public void setVendida(boolean vendida) { 
    	this.vendida = vendida; 
    	}

    @Override
    public String toString() {
    	return " ";
    }

	public Tiquete getTiquete() {
		return tiquete;
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
	
	private void setPrecio(double precio) {
		this.precio = precio;
	}
	
	private void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}

