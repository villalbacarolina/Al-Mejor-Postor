package logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//Solver=Solucionador
public class SolverGoloso {
	
	private Instancia _instancia; 
	private Comparator<Oferta> _comparador;
	
	public SolverGoloso(Instancia instancia, Comparator<Oferta> comparador){
		_instancia = instancia;
		_comparador = comparador;
	}
	
	private ArrayList<Oferta> ofertasDeManianaOrdenadasPorComparador(){
		ArrayList<Oferta> ofertasOrdenadas = _instancia.getOfertas();
		Collections.sort(ofertasOrdenadas, _comparador);
		
		return ofertasOrdenadas;
	}
	
	public Solucion resolver(){
		Solucion ofertasSeleccionadas = new Solucion();
		
		for(Oferta oferta : ofertasDeManianaOrdenadasPorComparador())
			if(ofertasSeleccionadas.noSeSuperponeConNingunaOferta(oferta))
				ofertasSeleccionadas.agregar(oferta);
		
		return ofertasSeleccionadas;
	}

}