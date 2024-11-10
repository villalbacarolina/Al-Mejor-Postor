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
	
	private Empresa inicializar() {
		Empresa emp = new Empresa();
		
		emp.agregar(new Oferta("Juan", 0, 2, 300,"Comedia"));
		emp.agregar(new Oferta("Pablo", 3, 5, 700,"Musical"));
		emp.agregar(new Oferta("Pedro", 2, 6, 1000,"Teatro"));
		emp.agregar(new Oferta("Pepe", 7, 10, 800,"Comedia"));
		emp.agregar(new Oferta("Carlos", 12, 15, 100,"Charla"));
		emp.agregar(new Oferta("Fran", 14, 16, 2000,"Teatro"));
		
		return emp;
	}

	@Test
	public void resolverPorMontoTest() {	
		SolverGoloso solver = new SolverGoloso(inicializar(), new ComparadorMonto());
		Solucion sol = solver.resolver();
		
		assertEquals(4, sol.tamanio());
		assertEquals(4100, (int)sol.montoTotal());
	}
	
	@Test
	public void resolverPorBeneficioTest() {
		SolverGoloso solver = new SolverGoloso(inicializar(), new ComparadorPorBeneficio());
		
		Solucion sol = solver.resolver();
				
		assertEquals(4, sol.tamanio());
		assertEquals(3800, (int)sol.montoTotal());
	}
	
	//Casos borde
	
	@Test
	public void hayUnaSolaOfertaTest() {
	    Empresa emp = new Empresa();
	    emp.agregar(new Oferta("Ana", 0, 3, 500, "Drama"));
	    
	    SolverGoloso solver = new SolverGoloso(emp, new ComparadorMonto());
	    Solucion solucion = solver.resolver();
	    
	    assertEquals(1, solucion.tamanio());
	    assertEquals(500, (int) solucion.montoTotal());
	}

	@Test
	public void ofertasConMismasCondicionesTest() {
	    Empresa emp = new Empresa();
	    emp.agregar(new Oferta("Jose", 1, 4, 400, "Cine"));
	    emp.agregar(new Oferta("Maria", 1, 4, 400, "Cine"));
	    emp.agregar(new Oferta("Luis", 1, 4, 400, "Cine"));
	    
	    SolverGoloso solver = new SolverGoloso(emp, new ComparadorMonto());
	    Solucion sol = solver.resolver();
	    
	    //Solo debería elegir una oferta ya que todas se superponen.
	    assertEquals(1, sol.tamanio());
	    //La oferta elegida será siempre la primera en la lista ordenada.
	    assertEquals(400, (int) sol.montoTotal());
	}

	@Test
	public void noHayOfertasTest() {
	    Empresa emp = new Empresa();
	    SolverGoloso solver = new SolverGoloso(emp, new ComparadorMonto());
	    Solucion sol = solver.resolver();
	    
	    assertEquals(0, sol.tamanio());
	    assertEquals(0, (int) sol.montoTotal());
	}
	

}
