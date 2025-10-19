package logica;

import java.util.ArrayList;

public class SolicitudCancelacionEvento extends Solicitud {

    private Evento evento;

    public SolicitudCancelacionEvento(Usuario solicitante, String descripcion, Evento evento) {
        super(solicitante, descripcion);
        this.evento = evento;
    }

}
