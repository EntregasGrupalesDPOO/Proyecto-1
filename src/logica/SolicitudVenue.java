package logica;

public class SolicitudVenue extends Solicitud {

    private Venue venue;
    private String justificacion;
    private BoletasMaster sistema;
    public SolicitudVenue(Usuario solicitante, String descripcion, Venue venue, BoletasMaster sistema) {
        super(solicitante, descripcion);
        this.venue = venue;
        this.sistema = sistema;
        this.tipo = "PropuestaVenue";
    }

    @Override
    public void aceptarSolicitud() {
        this.sistema.agregarVenue(venue);
        System.out.println("Solicitud de venue aceptada. El venue " + venue.getNombre() + " ha sido aprobado.");
        
    }

    // Método específico de esta subclase que devuelve el Venue aceptado

    public void rechazarSolicitud() {
        System.out.println("Solicitud de venue rechazada para el usuario " + this.solicitante.getLogin());
        
    }

}
