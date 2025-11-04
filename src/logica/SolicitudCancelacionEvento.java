package logica;


import java.io.Serializable;

import Exepciones.ReembolsoNoPermitidoException;

public class SolicitudCancelacionEvento extends Solicitud implements IAprobable,Serializable    {

    private Evento evento;
    private Organizador solicitante;
    public SolicitudCancelacionEvento(Organizador solicitante, String descripcion, Evento evento) {
        super (solicitante, descripcion );
        this.evento = evento;
        this.tipo = "CancelacionEvento";
    }

    @Override
    public void aceptarSolicitud() {
        adm.cancelarEventoInsolvencia(evento);
        this.estado = Solicitud.ESTADO_ACEPTADA;
        System.out.println("Solicitud de cancelación de evento por insolvencia aceptada. Se ha realizado el reembolso para el evento: " + evento.getNombre());
    }

    @Override
    public void rechazarSolicitud() throws ReembolsoNoPermitidoException {
        System.out.println("Solicitud de cancelación de evento rechazada para el usuario " + this.solicitante.getLogin());
        this.estado = Solicitud.ESTADO_RECHAZADA;
        throw new ReembolsoNoPermitidoException(solicitante);
    }

}
