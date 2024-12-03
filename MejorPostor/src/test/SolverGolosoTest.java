package test;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;

import logica.Instancia;
import logica.Oferta;
import logica.Solucion;
import logica.SolverGoloso;

public class SolverGolosoTest {
	
	private Comparator<Oferta> _comparadorPorMonto;
	private Comparator<Oferta> _comparadorPorBeneficio;
	
	@Before
	public void iniciarComparadores() {
		_comparadorPorBeneficio = (o1, o2) -> {
		    double cocienteDos = o2.getMonto() / o2.getCantidadDeHoras();
		    double cocienteUno = o1.getMonto() / o1.getCantidadDeHoras();
		    return Double.compare(cocienteDos, cocienteUno);
		};
		
		_comparadorPorMonto = (o1, o2) -> Double.compare(o2.getMonto(), o1.getMonto());
	}
	
	public Instancia inicializar() {
		Instancia empresa = new Instancia();
		
		empresa.agregar(new Oferta("Juan", 0, 2, 300,"Comedia"));
		empresa.agregar(new Oferta("Pablo", 3, 5, 700,"Musical"));
		empresa.agregar(new Oferta("Pedro", 2, 6, 1000,"Teatro"));
		empresa.agregar(new Oferta("Pepe", 7, 10, 800,"Comedia"));
		empresa.agregar(new Oferta("Carlos", 12, 15, 100,"Charla"));
		empresa.agregar(new Oferta("Fran", 14, 16, 2000,"Teatro"));
		
		return empresa;
	}

	@Test
	public void resolverPorMontoTest() {	
		SolverGoloso solver = new SolverGoloso(inicializar(), _comparadorPorMonto);
		Solucion sol = solver.resolver();
		
		
		assertEquals(4, sol.tamanio());
		assertEquals(4100, (int)sol.getMontoTotal());
	}
	
	@Test
	public void resolverPorBeneficioTest() {
		SolverGoloso solver = new SolverGoloso(inicializar(), _comparadorPorBeneficio);
		Solucion sol = solver.resolver();
				
		assertEquals(4, sol.tamanio());
		assertEquals(3800, (int)sol.getMontoTotal());
	}
	
	//Casos borde
	
	@Test
	public void hayUnaSolaOfertaTest() {
	    Instancia empresa = new Instancia();
	    empresa.agregar(new Oferta("Ana", 0, 3, 500, "Drama"));
	    
	    SolverGoloso solver = new SolverGoloso(empresa, _comparadorPorMonto);
	    Solucion solucion = solver.resolver();
	    
	    assertEquals(1, solucion.tamanio());
	    assertEquals(500, (int) solucion.getMontoTotal());
	}

	@Test
	public void ofertasConMismasCondicionesTest() {
	    Instancia empresa = new Instancia();
	    empresa.agregar(new Oferta("Jose", 1, 4, 400, "Cine"));
	    empresa.agregar(new Oferta("Maria", 1, 4, 400, "Cine"));
	    empresa.agregar(new Oferta("Luis", 1, 4, 400, "Cine"));
	    
	    SolverGoloso solver = new SolverGoloso(empresa, _comparadorPorMonto);
	    Solucion sol = solver.resolver();
	    
	    //Solo debería elegir una oferta ya que todas se superponen.
	    assertEquals(1, sol.tamanio());
	    //La oferta elegida será siempre la primera en la lista ordenada.
	    assertEquals(400, (int) sol.getMontoTotal());
	}

	@Test
	public void noHayOfertasTest() {
	    Instancia empresa = new Instancia();
	    SolverGoloso solver = new SolverGoloso(empresa, _comparadorPorMonto);
	    Solucion sol = solver.resolver();
	    
	    assertEquals(0, sol.tamanio());
	    assertEquals(0, (int) sol.getMontoTotal());
	}
	

}
