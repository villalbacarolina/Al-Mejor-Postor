package logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SolverGoloso {
	
	private Empresa _empresa;
	private Comparator<Oferta> _comparador;
	
	public SolverGoloso(Empresa empresa, Comparator<Oferta> comparador)
	{
		_empresa = empresa;
		_comparador = comparador;
	}
	
	public Solucion resolver()
	{
		Solucion ret = new Solucion();
		for(Oferta oferta : ofertasOrdenadas()) {
			
			if(ret.validarSuperposicion(oferta)) {
				ret.agregar(oferta);
			}
		}
		return ret;
	}

	private ArrayList<Oferta> ofertasOrdenadas()
	{
		ArrayList<Oferta> ret = _empresa.getOfertas();
		Collections.sort(ret, _comparador);
		
		return ret;
	}

}