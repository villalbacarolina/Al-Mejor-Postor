package logica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

import utils.InfoOfertas;

public class Empresa {
	
	private static Instancia instancia = new Instancia();
	private static Solucion solucion = new Solucion();
	
	public static void guardar(Oferta oferta) {
		instancia.agregar(oferta);
	}

	public static boolean estaRepetida(Oferta oferta) {
		return instancia.estaRepetida(oferta);
	}
	
	public static void seleccionarOfertasPorMonto() {
		Comparator<Oferta> comparadorPorMonto = (o1, o2) -> Double.compare(o2.getMonto(), o1.getMonto());
		SolverGoloso solver = new SolverGoloso(instancia, comparadorPorMonto);
		solucion = solver.resolver();
	}
	
	public static void seleccionarOfertasPorBeneficio() {
		Comparator<Oferta> comparadorPorBeneficio = (o1, o2) -> {
		    double cocienteDos = o2.getMonto() / o2.getCantidadDeHoras();
		    double cocienteUno = o1.getMonto() / o1.getCantidadDeHoras();
		    return Double.compare(cocienteDos, cocienteUno);
		};
		SolverGoloso solver = new SolverGoloso(instancia, comparadorPorBeneficio);
		solucion = solver.resolver();
	}
	
	public static boolean diaTerminado() {
		LocalDate ret = LocalDate.now();
		ret = ret.plusDays(1);
		String fechaVerificar = ret.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		for(Oferta of : InfoOfertas.getOfertasSeleccionadas()) 
			if(of.getFechaManiana().equals(fechaVerificar))
				return true;
		
		return false;
	}

	public static ArrayList<Oferta> getOfertaSolucion() {
		return solucion.getOfertas();
	}

}
