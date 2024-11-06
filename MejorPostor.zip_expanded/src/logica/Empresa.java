package logica;

import java.util.ArrayList;

public class Empresa {
	
	private ArrayList<Oferta> _ofertas;
	
	public Empresa () {
		_ofertas = new ArrayList<Oferta>();
	}

	
	public void agregar(Oferta oferta)
	{
		_ofertas.add(oferta);
	}
	
	
	
//	public int getTamano()
//	{
//		return _ofertas.size();
//	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Oferta> getOfertas()
	{
		return (ArrayList<Oferta>) _ofertas.clone();
	}

	public Oferta getOferta(int i) {
		
		return _ofertas.get(i);
	}
}