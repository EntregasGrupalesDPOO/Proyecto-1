package logica;
import java.io.Serializable;


public class TiqueteEnumerado extends Tiquete implements Serializable{
	private int idSilla;
	public static final String ENUMERADO = "ENUMERADO";
	
	public TiqueteEnumerado(Integer idLocalidad, Evento evento, Usuario usuario, int idSilla) {
		super(idLocalidad, evento, usuario);
		this.tipo = ENUMERADO;
		this.idSilla = idSilla;
	}
	
	public int getIdSilla() {
		return idSilla;
	}

	/* 
	@Override
	public String toString() {
		return "TiqueteEnumerado [idSilla=" + idSilla + ", localidad=" + localidad + ", fecha="
				+ fecha + ", hora=" + hora + ", id=" + id + ", precioBase=" + precioBase
				+ ", usado=" + usado + ", evento=" + evento + ", tipo=" + tipo + "]";
	}

	*/

	

}
