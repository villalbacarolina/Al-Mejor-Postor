package logica;

import java.util.ArrayList;

public class Solucion {
	
	private ArrayList<Oferta> _ofertas;
	private double _montoTotal;
	
	public Solucion(){
		_ofertas = new ArrayList<Oferta>();
	}
	
	//___________________________//
	
	@SuppressWarnings("unchecked")
	public ArrayList<Oferta> getOfertas(){
		return (ArrayList<Oferta>) _ofertas.clone();
	}
	
	public double getMontoTotal() {
		return _montoTotal;
	}
	
	//___________________________//
	
	public void agregar(Oferta oferta){
		_ofertas.add(oferta);
		_montoTotal += oferta.getMonto();
	}
	
	public int tamanio(){
		return _ofertas.size();
	}
	
	public boolean noSeSuperponeConNingunaOferta(Oferta oferta) {
		if(_ofertas.isEmpty()) 
			return true;	
		
		for(Oferta otraOferta : _ofertas) 
			if (!oferta.noSeSuperpone(otraOferta))
				return false;
		
		return true;
	}

	@Override
	public String toString() {
		String ret = ""; 
		
		for (Oferta oferta: _ofertas)
			ret = ret + " " + oferta.getOferente() + " " + oferta.getHoraDesde()
			 + " " + oferta.getHoraHasta() + " " + oferta.getMonto() + "\n";
			
		return " Solucion [ofertas=" + ret + " ]";
	}

}