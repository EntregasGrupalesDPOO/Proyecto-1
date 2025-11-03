package logica;

import java.io.Serializable;

public class SolicitudVenue extends Solicitud implements IAprobable,Serializable {

    private Venue venue;
    private BoletasMaster sistema;
    public SolicitudVenue(Cliente solicitante, String descripcion, Venue venue, BoletasMaster sistema) {
        super(solicitante, descripcion);
        this.venue = venue;
        this.sistema = sistema;
        this.tipo = "PropuestaVenue";
        
    }

    @Override
    public void aceptarSolicitud() {
        this.sistema.agregarVenue(venue);
        this.estado = Solicitud.ESTADO_ACEPTADA;
        System.out.println("Solicitud de venue aceptada. El venue " + venue.getNombre() + " ha sido aprobado.");
        
    }


    public void rechazarSolicitud() {
        this.estado = Solicitud.ESTADO_RECHAZADA;
        System.out.println("Solicitud de venue rechazada para el usuario " + this.solicitante.getLogin());
        
    }

}
