package logica;

import Exepciones.ReembolsoNoPermitidoException;

public interface IAprobable {

    void aceptarSolicitud();
    void rechazarSolicitud() throws Exception;


}
