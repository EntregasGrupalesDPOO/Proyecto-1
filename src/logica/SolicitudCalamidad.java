package logica;

import Exepciones.ReembolsoNoPermitidoException;

public class SolicitudCalamidad extends Solicitud  {

    
    private Tiquete tiqueteAsociado;
    private TiqueteMultiple tiqueteAsociadoM;
    private boolean esMultiple;
    
    public SolicitudCalamidad(Usuario solicitante, String descripcion, Tiquete tiqueteAsociado) {
        super(solicitante, descripcion);
        this.tipo = "Calamidad";
        this.tiqueteAsociado = tiqueteAsociado;
        this.esMultiple = false;


    }
        public SolicitudCalamidad(Usuario solicitante, String descripcion, TiqueteMultiple tiqueteAsociado) {
        super(solicitante, descripcion);
        this.tipo = "Calamidad";
        this.tiqueteAsociadoM = tiqueteAsociado;
        this.esMultiple = true;


    }



    public void aceptarSolicitud() {

        if (esMultiple) {
            solicitante.realizarReembolso(this.tiqueteAsociadoM);
            System.out.println("Solicitud de calamidad aceptada. Se ha realizado el reembolso del tiquete multiple asociado a" + this.solicitante.getLogin());
            return;
        }
        else{
            solicitante.realizarReembolso(this.tiqueteAsociado);
            System.out.println("Solicitud de calamidad aceptada. Se ha realizado el reembolso del tiquete asociado a" + this.solicitante.getLogin());
            
        }

        
    }


    public void rechazarSolicitud() throws ReembolsoNoPermitidoException {
        System.out.println("Solicitud de calamidad rechazada para el usuario " + this.solicitante.getLogin());
        throw new ReembolsoNoPermitidoException(solicitante);
    }

}
