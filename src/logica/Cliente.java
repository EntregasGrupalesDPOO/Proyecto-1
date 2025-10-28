package logica;

import java.util.ArrayList;
import java.util.HashMap;

import Marketplace.ContraOferta;
import Marketplace.Oferta;

import java.io.Serializable;

public class Cliente extends Usuario implements Serializable {
	
	private ArrayList<Oferta> ofertas;

	public Cliente(String login, String contrasena) {
		super(login, contrasena);
	}
	
	public ArrayList<Oferta> getOfertas(){
		return ofertas;
	}
	
	//para ver todas las contraofertas que nos proponen
	public HashMap<Oferta,ArrayList<ContraOferta>> getContraOfertas(){
		HashMap<Oferta,ArrayList<ContraOferta>> contraOfertas =new HashMap<Oferta,ArrayList<ContraOferta>>();
		for (Oferta oferta:ofertas){
			contraOfertas.put(oferta, oferta.getContraOfertas());
		}
		return contraOfertas;
	}
	

}
