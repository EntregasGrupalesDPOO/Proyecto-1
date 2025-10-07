package Exepciones;


import logica.Tiquete;

public class TiqueteUsadoException extends Exception {
    


	public TiqueteUsadoException(Tiquete tiquete) {
        super("El tiquete con id" + tiquete.getId() +  "del tipo" + tiquete.getTipo() +  "ya ha sido utilizado y no puede volver a usarse.");
    }
    
    public TiqueteUsadoException(String mensaje) {
        super(mensaje);
    }
}
