package logica;

import java.util.ArrayList;

public class Instancia {
	
	private ArrayList<Oferta> _ofertas;
	
	public Instancia () {
		_ofertas = new ArrayList<Oferta>();
	}
	
	public void agregar(Oferta oferta){
		_ofertas.add(oferta);
	}
	
	public boolean estaRepetida(Oferta oferta) {	
		for (Oferta otraOferta : _ofertas)
			if (oferta.equals(otraOferta))
					return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Oferta> getOfertas(){
		return (ArrayList<Oferta>) _ofertas.clone();
	}

	public Oferta getOferta(int posicion) {
		return _ofertas.get(posicion);
	}
}