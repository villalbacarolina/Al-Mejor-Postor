package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import logica.ComparadorMonto;
import logica.Empresa;
import logica.Oferta;
import logica.Solucion;
import logica.SolverGoloso;

public class SolverGolosoTest {

	@Test
	public void resolverPorMonto() {
		
		SolverGoloso solver = new SolverGoloso(ejemplo(), new ComparadorMonto());
		
		Solucion sol = solver.resolver();
		
		assertEquals(4, sol.tamanio());
		assertEquals(4100, (int)sol.montoTotal());
	}
	
	
	private Empresa ejemplo() {
		
		Empresa ret = new Empresa();
		ret.agregar(new Oferta("Juan", 0, 2, 300));
		ret.agregar(new Oferta("Pablo", 3, 5, 700));
		ret.agregar(new Oferta("Pedro", 2, 6, 1000));
		ret.agregar(new Oferta("Pepe", 7, 10, 800));
		ret.agregar(new Oferta("Carlos", 12, 15, 100));
		ret.agregar(new Oferta("Fran", 14, 16, 2000));
		
		return ret;
	}
}
