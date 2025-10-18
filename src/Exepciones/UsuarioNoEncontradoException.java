package Exepciones;

import logica.Tiquete;

public class UsuarioNoEncontradoException extends Exception{
	
	public UsuarioNoEncontradoException(String login) {
        super("El usuario con el login " + login +  " no existe");
    }


}
