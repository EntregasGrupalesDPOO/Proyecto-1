package Exepciones;

import logica.Tiquete;

public class CantidadTiquetesExcedidaException extends Exception{
	public CantidadTiquetesExcedidaException(int max) {
        super("La cantidad de tiquetes supera el máximo permitido "+ max + ".");
    }
    
    public CantidadTiquetesExcedidaException(String mensaje) {
        super(mensaje);
    }
}
