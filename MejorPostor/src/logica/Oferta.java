package logica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Oferta {
	
	private String _oferente;
	private int _horaDesde;
	private int _horaHasta;
	private int cantidadDeHoras;
	private double _monto;
	private String _tipoShow;
	private String _fechaManiana;
	
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
		 LocalDate fecha = LocalDate.now();
		 fecha = fecha.plusDays(1);
		 _fechaManiana = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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

	public String getFechaManiana() {
		return _fechaManiana;
	}
	
	public String getTipoShow() {
		return _tipoShow;
	}
	
	public int getCantidadDeHoras() {
		return cantidadDeHoras;
	}
}
