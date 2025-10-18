package logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Exepciones.TiqueteUsadoException;

public abstract class TiqueteMultiple {
	protected Integer id;
	protected boolean transferible = true;
	protected String tipo;
	private static Integer currentID = 0;
	public static int tiquetesMax;
	
	public TiqueteMultiple() {
		this.id = currentID + 1;
	}


	public Integer getId() {
		return id;
	}
	
}
