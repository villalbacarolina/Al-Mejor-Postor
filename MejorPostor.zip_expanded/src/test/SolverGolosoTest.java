package test;

import static org.junit.Assert.*;

import org.junit.Test;

import logica.ComparadorMonto;
import logica.ComparadorPorBeneficio;
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
	
	@Test
	public void resolverPorBeneficio() {
		
		SolverGoloso solver = new SolverGoloso(ejemplo(), new ComparadorPorBeneficio());
		
		Solucion sol = solver.resolver();
		
		
		assertEquals(4, sol.tamanio());
		assertEquals(3800, (int)sol.montoTotal());
	}
	
	private Empresa ejemplo() {
		
		Empresa ret = new Empresa();
		ret.agregar(new Oferta("Juan", 0, 2, 300,"Comedia"));
		ret.agregar(new Oferta("Pablo", 3, 5, 700,"Musical"));
		ret.agregar(new Oferta("Pedro", 2, 6, 1000,"Teatro"));
		ret.agregar(new Oferta("Pepe", 7, 10, 800,"Comedia"));
		ret.agregar(new Oferta("Carlos", 12, 15, 100,"Charla"));
		ret.agregar(new Oferta("Fran", 14, 16, 2000,"Teatro"));
		
		return ret;
	}
}
