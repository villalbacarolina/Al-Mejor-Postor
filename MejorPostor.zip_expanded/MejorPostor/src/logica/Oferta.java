package logica;

import java.util.Calendar;

public class Oferta {
	
	private String oferente;
	private int horaDesde;
	private int horaHasta;
	private double monto;
	private Calendar fechaManiana;
	
	public Oferta(String oferente, int horaDesde, int horaHasta, double monto) {
		this.oferente = oferente;
		this.horaDesde = horaDesde;
		this.horaHasta = horaHasta;
		this.monto = monto;
		obtenerFechaManiana();
	}
	
	private void obtenerFechaManiana() {
		 fechaManiana = Calendar.getInstance();     
		 fechaManiana.add(Calendar.DATE,1);
	}
	
	public String getOferente() {
		return oferente;
	}
	
	public int getHoraDesde() {
		return horaDesde;
	}

	public int getHoraHasta() {
		return horaHasta;
	}

	public double getMonto() {
		return monto;
	}

	public Calendar getFechaManiana() {
		return fechaManiana;
	}
}
