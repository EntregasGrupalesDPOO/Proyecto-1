package Exepciones;

public class TiqueteMultipleVENoExisteExcpetion extends Exception {

	public TiqueteMultipleVENoExisteExcpetion(int size) {
		super("No existen tiquetes múltiples para "+ size + "eventos");
	}

}
