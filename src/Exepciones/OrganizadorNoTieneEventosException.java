package Exepciones;

import logica.Organizador;

public class OrganizadorNoTieneEventosException extends Exception {
	public OrganizadorNoTieneEventosException(Organizador organizador) {
		super("El organizador con login: " + organizador.getLogin() +" no ha agendado aún eventos");
	}
}
