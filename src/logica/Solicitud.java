package logica;

public abstract class Solicitud {

    
    protected Usuario solicitante;
	protected String tipo;
    protected String descripcion;


    public abstract void aceptarSolicitud();
    public abstract  void rechazarSolicitud() throws Exception;
    ;
    public Usuario getSolicitante() {
        return solicitante;
    }
    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Solicitud(Usuario usuario, String descripcion) {
    	this.solicitante = usuario;
    	this.descripcion = descripcion;
    	
    }
	

}
