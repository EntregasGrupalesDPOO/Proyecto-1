package logica;
import java.io.Serializable;


public class TiqueteBasico extends Tiquete implements Serializable{
	public static final String BASICO = "BASICO";
	public TiqueteBasico(Integer idLocalidad, Evento evento, Usuario usuario) {
		super(idLocalidad, evento, usuario);
		this.tipo = BASICO;
	}

}
