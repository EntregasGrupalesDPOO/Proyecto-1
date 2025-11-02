package logica;

public abstract class Solicitud {

    
    protected Cliente solicitante;
	protected String tipo;
    protected String descripcion;
    protected Administrador admin;
    static Administrador adm;
    protected String estado; // puede ser "PENDIENTE", "ACEPTADA", "RECHAZADA"
    public static final String ESTADO_PENDIENTE = "PENDIENTE";
    public static final String ESTADO_ACEPTADA = "ACEPTADA";
    public static final String ESTADO_RECHAZADA = "RECHAZADA";


    public abstract void aceptarSolicitud();
    public abstract  void rechazarSolicitud() throws Exception;
    ;
    public Cliente getSolicitante() {
        return this.solicitante;
    }
    public void setSolicitante(Cliente solicitante) {
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
    public String getEstado() {
        return estado;
    }
    public Solicitud(Cliente usuario, String descripcion ) {
    	this.solicitante = usuario;
    	this.descripcion = descripcion;
        this.estado = ESTADO_PENDIENTE;
    	
    }

    @Override
    public String toString() {
        return "Solicitud [solicitante=" + solicitante.getLogin() + ", tipo=" + tipo + ", descripcion=" + descripcion
                + ", estado=" + estado + "]";
    }
	

}
