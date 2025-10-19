package logica;

public class SolicitudCalamidad extends Solicitud {

    private  String tipoSolicitud;
    public SolicitudCalamidad(Usuario solicitante, String descripcion) {
        super(solicitante, descripcion);
        this.tipo = "Calamidad";


    }

}
