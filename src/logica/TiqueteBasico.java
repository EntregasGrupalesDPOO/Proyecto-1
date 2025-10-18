package logica;

public class TiqueteBasico extends Tiquete{
	public static final String BASICO = "BASICO";
	public TiqueteBasico(Integer idLocalidad, Evento evento, Usuario usuario) {
		super(idLocalidad, evento, usuario);
		this.tipo = BASICO;
	}

}
