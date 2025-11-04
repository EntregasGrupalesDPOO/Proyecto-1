package logica;

import java.io.Serializable;

import Exepciones.ReembolsoNoPermitidoException;

public class SolicitudCalamidad extends Solicitud implements IAprobable,Serializable{

    
    private Tiquete tiqueteAsociado;
    
    public SolicitudCalamidad(Cliente solicitante, String descripcion, Tiquete tiqueteAsociado) {
        super(solicitante, descripcion);
        this.tipo = "Calamidad";
        this.tiqueteAsociado = tiqueteAsociado;


    }




    public void aceptarSolicitud() {

        adm.reembolsarTiqueteCalamidad(this.solicitante,this.tiqueteAsociado);
        this.estado = Solicitud.ESTADO_ACEPTADA;
        System.out.println("Solicitud de calamidad aceptada. Se ha realizado el reembolso para el tiquete ID: " + tiqueteAsociado.getId());

        
    }


    public void rechazarSolicitud() throws ReembolsoNoPermitidoException {
        System.out.println("Solicitud de calamidad rechazada para el usuario " + this.solicitante.getLogin());
        this.estado = Solicitud.ESTADO_RECHAZADA;
        throw new ReembolsoNoPermitidoException(solicitante);

    }

}
