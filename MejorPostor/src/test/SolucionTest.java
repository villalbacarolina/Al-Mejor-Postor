package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import logica.Oferta;
import logica.Solucion;

public class SolucionTest {

    private Solucion solucion;

    @Before
    public void inicializar() {
        solucion = new Solucion();
    }

    @Test
    public void horariosSuperpuestosTest() {
        Oferta mismoHorario1 = new Oferta("Pedro", 2, 6, 1000, "Teatro");
        Oferta mismoHorario2 = new Oferta("Lucas", 2, 6, 800, "Teatro");
        Oferta horarioDentroDelOtro1 = new Oferta("Pedro", 2, 8, 1, "Teatro");
        Oferta horarioDentroDelOtro2 = new Oferta("Lucas", 4, 6, 1, "Teatro");
        Oferta horarioTerminaDentroDelOtro1 = new Oferta("Pedro", 6, 8, 1, "Teatro");
        Oferta horarioTerminaDentroDelOtro2 = new Oferta("Lucas", 1, 7, 1, "Teatro");
        Oferta horarioEmpiezaDentroDelOtro1 = new Oferta("Pedro", 1, 10, 1, "Teatro");
        Oferta horarioEmpiezaDentroDelOtro2 = new Oferta("Lucas", 5, 15, 1, "Teatro");

        solucion.agregar(mismoHorario1);
        assertFalse(solucion.validarSuperposicion(mismoHorario2));
        solucion.agregar(horarioDentroDelOtro1);
        assertFalse(solucion.validarSuperposicion(horarioDentroDelOtro2));
        solucion.agregar(horarioTerminaDentroDelOtro1);
        assertFalse(solucion.validarSuperposicion(horarioTerminaDentroDelOtro2));
        solucion.agregar(horarioEmpiezaDentroDelOtro1);
        assertFalse(solucion.validarSuperposicion(horarioEmpiezaDentroDelOtro2));
    }


    @Test
    public void sinSuperposicionesTest() {
        Oferta oferta1 = new Oferta("Carlos", 0, 5, 1000, "Teatro");
        Oferta oferta2 = new Oferta("Laura", 6, 8, 600, "Danza");

        assertTrue(solucion.validarSuperposicion(oferta1));
        solucion.agregar(oferta1);
        assertTrue(solucion.validarSuperposicion(oferta2));
        solucion.agregar(oferta2);

        assertEquals(2, solucion.tamanio());
        assertEquals(1600, solucion.montoTotal(), 0.01);
    }
    
    @Test
    public void toStringVacioTest() {
        assertEquals(" Solucion [ofertas= ]", solucion.toString());
    }
    
}
