package logica;

public class TiqueteEnumerado extends Tiquete {
	private int idSilla;
	public static final String ENUMERADO = "ENUMERADO";
	public TiqueteEnumerado(Localidad localidad, int id, Usuario dueno, double precioBase, double comision,  int idSilla) {
		super(localidad, id, dueno, precioBase, comision);
		this.idSilla = idSilla;
		this.tipo = TiqueteEnumerado.ENUMERADO;
	}
	public int getIdSilla() {
		return idSilla;
	}
	@Override
	public String toString() {
		return "TiqueteEnumerado [idSilla=" + idSilla + ", localidad=" + localidad + ", idDueno=" + idDueno + ", fecha="
				+ fecha + ", hora=" + hora + ", id=" + id + ", dueno=" + dueno + ", precioBase=" + precioBase
				+ ", comision=" + comision + ", usado=" + usado + ", evento=" + evento + ", tipo=" + tipo + "]";
	}
	

}
