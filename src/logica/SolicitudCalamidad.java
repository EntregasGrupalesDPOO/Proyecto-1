package logica;

import Exepciones.ReembolsoNoPermitidoException;

public class SolicitudCalamidad extends Solicitud  {

    
    private Tiquete tiqueteAsociado;
    private TiqueteMultiple tiqueteAsociadoM;
    
    public SolicitudCalamidad(Usuario solicitante, String descripcion, Tiquete tiqueteAsociado) {
        super(solicitante, descripcion);
        this.tipo = "Calamidad";
        this.tiqueteAsociado = tiqueteAsociado;


    }
        public SolicitudCalamidad(Usuario solicitante, String descripcion, TiqueteMultiple tiqueteAsociado) {
        super(solicitante, descripcion);
        this.tipo = "Calamidad";
        this.tiqueteAsociadoM = tiqueteAsociado;


    }



    public void aceptarSolicitud() {


        solicitante.realizarReembolso(this.tiqueteAsociado);
        System.out.println("Solicitud de calamidad aceptada. Se ha realizado el reembolso del tiquete asociado a" + this.solicitante.getLogin());
        
        
    }


    public void rechazarSolicitud() throws ReembolsoNoPermitidoException {
        System.out.println("Solicitud de calamidad rechazada para el usuario " + this.solicitante.getLogin());
        throw new ReembolsoNoPermitidoException(solicitante);
    }

}
