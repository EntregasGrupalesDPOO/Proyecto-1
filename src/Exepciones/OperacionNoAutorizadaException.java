package Exepciones;



public class OperacionNoAutorizadaException extends Exception{
	
	public OperacionNoAutorizadaException() {
        super("Este tipo de usuario no puede comprar tiquete");
    }
    
    public OperacionNoAutorizadaException(String mensaje) {
        super(mensaje);
    }
}