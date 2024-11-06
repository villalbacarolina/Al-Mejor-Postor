package logica;

import java.util.Comparator;

public class ComparadorMonto implements Comparator<Oferta>{

	@Override
	public int compare(Oferta o1, Oferta o2) {
		return (int)(o2.getMonto() - o1.getMonto());
	}

}