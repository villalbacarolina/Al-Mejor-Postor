package logica;

import java.util.Calendar;

public class Oferta {
	
	private String _oferente;
	private int _horaDesde;
	private int _horaHasta;
	private int cantidadDeHoras;
	private double _monto;
	private String _tipoShow;
	private Calendar _fechaManiana;
	
	public Oferta(String oferente, int horaDesde, int horaHasta, double monto, String tipoShow) {
		this._oferente = oferente;
		this._horaDesde = horaDesde;
		this._horaHasta = horaHasta;
		this.cantidadDeHoras=horaHasta-horaDesde;
		this._monto = monto;
		this._tipoShow = tipoShow;
		obtenerFechaManiana();
	}
		
	private void obtenerFechaManiana() {
		 _fechaManiana = Calendar.getInstance();     
		 _fechaManiana.add(Calendar.DATE,1);
	}
	
	public String getOferente() {
		return _oferente;
	}
	
	public int getHoraDesde() {
		return _horaDesde;
	}

	public int getHoraHasta() {
		return _horaHasta;
	}

	public double getMonto() {
		return _monto;
	}

	public Calendar getFechaManiana() {
		return _fechaManiana;
	}
	
	public String getTipoShow() {
		return _tipoShow;
	}
	
	public int getCantidadDeHoras() {
		return cantidadDeHoras;
	}
}