package logica;

import java.util.Comparator;

public class ComparadorPorBeneficio implements Comparator<Oferta>{

	@Override
	public int compare(Oferta o1, Oferta o2) {
		double cocienteDos = o2.getMonto()/o2.getCantidadDeHoras();
		double cocienteUno = o1.getMonto()/o1.getCantidadDeHoras();
		
		if (cocienteUno <= cocienteDos)
			return 1;
		else if (cocienteUno >= cocienteDos)
			return -1;
		else
			return 0;
		
	}

}