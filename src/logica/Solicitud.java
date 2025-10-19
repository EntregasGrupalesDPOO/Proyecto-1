package logica;

public abstract class Solicitud {

    
    protected Usuario solicitante;
	protected String tipo;
    protected String descripcion;
    public Solicitud(Usuario usuario, String descripcion) {
    	this.solicitante = usuario;
    	this.descripcion = descripcion;
    	
    }
	

}
