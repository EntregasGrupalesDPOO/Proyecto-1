package logica;

public class TiqueteEnumerado extends Tiquete {
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
		return "TiqueteEnumerado [idSilla=" + idSilla + ", localidad=" + localidad + ", idDueno=" + idDueno + ", fecha="
				+ fecha + ", hora=" + hora + ", id=" + id + ", dueno=" + dueno + ", precioBase=" + precioBase
				+ ", comision=" + comision + ", usado=" + usado + ", evento=" + evento + ", tipo=" + tipo + "]";
	}

	*/

	

}
