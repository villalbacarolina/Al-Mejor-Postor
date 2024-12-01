package logica;

import java.util.Objects;

public class Oferta {
	
	private String _oferente;
	private int _horaDesde;
	private int _horaHasta;
	private int cantidadDeHoras;
	private double _monto;
	private String _tipoShow;
	private String _fechaManiana;
	
	public Oferta(String oferente, int horaDesde, int horaHasta, double monto, String tipoShow, String fechaManiana) {
		this._oferente = oferente;
		this._horaDesde = horaDesde;
		this._horaHasta = horaHasta;
		this.cantidadDeHoras=horaHasta-horaDesde;
		this._monto = monto;
		this._tipoShow = tipoShow;
		this._fechaManiana = fechaManiana;
	}
	
	public boolean noSeSuperpone(Oferta otraOferta) {
		return this._horaHasta <= otraOferta.getHoraDesde()  ||
			   this._horaDesde >= otraOferta.getHoraHasta();	
	}
	
	public boolean equals(Oferta otro) {
		if(this == otro) return true;
		
		return  this._oferente.equals(otro.getOferente()) &&
				this._tipoShow.equals(otro.getTipoShow()) &&
				this._fechaManiana.equals(otro.getFechaManiana()) &&
				Integer.compare(_horaHasta, otro.getHoraHasta())  == 0 &&
				Integer.compare(_horaDesde, otro.getHoraDesde())  == 0 &&
				Double.compare(_monto, otro.getMonto()) == 0;
	}
	
	public int hashCode() {
		return Objects.hash(_oferente, _tipoShow, _fechaManiana, _horaDesde, _horaHasta, _monto);
	}
	
	public Object[] toArray() {
	    return new Object[] {
	        _oferente,
	        _monto,
	        _horaDesde,
	        _horaHasta,
	        _tipoShow,
	        _fechaManiana
	    };
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
