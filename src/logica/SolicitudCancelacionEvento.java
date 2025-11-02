package logica;

import java.util.ArrayList;

public class SolicitudCancelacionEvento extends Solicitud {

    private Evento evento;
    private Administrador administrador;
    public SolicitudCancelacionEvento(Usuario solicitante, String descripcion, Evento evento, Administrador admin) {
        super(solicitante, descripcion);
        this.evento = evento;
        this.tipo = "CancelacionEvento";
        this.administrador = admin;
    }

    @Override
    public void aceptarSolicitud() {
        administrador.(evento);
        System.out.println("Solicitud de cancelación de evento aceptada. Se ha realizado el reembolso para el evento: " + evento.getNombre());
    }

    @Override
    public void rechazarSolicitud() throws Exception {
        System.out.println("Solicitud de cancelación de evento rechazada para el usuario " + this.solicitante.getLogin());
        throw new UnsupportedOperationException("Unimplemented method 'rechazarSolicitud'");
    }

}
