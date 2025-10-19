package logica;

public class SolicitudVenue extends Solicitud {

    private Venue venue;
    private String justificacion;

    public SolicitudVenue(Usuario solicitante, String descripcion, Venue venue ) {
        super(solicitante, descripcion);
        this.venue = venue;
        
        this.tipo = "PropuestaVenue";
    }

}
