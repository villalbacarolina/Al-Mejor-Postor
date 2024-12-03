package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import logica.Oferta;

public class SuperposicionTest {
	
	Oferta mismoHorario1,
	mismoHorario2,
	horarioDentroDelOtro1,
	horarioDentroDelOtro2,
	horarioTerminaDentroDelOtro1,
	horarioTerminaDentroDelOtro2,
	horarioEmpiezaDentroDelOtro1,
	horarioEmpiezaDentroDelOtro2;
    
    @Before 
    public void inicializar() throws Exception {
        mismoHorario1 = new Oferta("Pedro", 2, 6, 1000, "Teatro");
        mismoHorario2 = new Oferta("Lucas", 2, 6, 800, "Teatro");
        
        horarioDentroDelOtro1 = new Oferta("Pedro", 4, 6, 1, "Teatro");
        horarioDentroDelOtro2 = new Oferta("Lucas", 2, 8, 1, "Teatro");
        
        horarioTerminaDentroDelOtro1 = new Oferta("Pedro", 5, 10, 1, "Teatro");
        horarioTerminaDentroDelOtro2 = new Oferta("Lucas", 1, 8, 1, "Teatro");
        
        horarioEmpiezaDentroDelOtro1 = new Oferta("Pedro", 1, 10, 1, "Teatro");
        horarioEmpiezaDentroDelOtro2 = new Oferta("Lucas", 5, 15, 1, "Teatro");
    }

    @Test
    public void mismoHorarioTest() {
        assertFalse(mismoHorario1.noSeSuperpone(mismoHorario2));
        assertFalse(mismoHorario2.noSeSuperpone(mismoHorario1));
    }
   
    @Test
    public void horarioDentroDeOtroTest() {
        assertFalse(horarioDentroDelOtro1.noSeSuperpone(horarioDentroDelOtro2));
        assertFalse(horarioDentroDelOtro2.noSeSuperpone(horarioDentroDelOtro1));
    }
    
    @Test
    public void horarioTerminaDentroDeOtroTest() {
        assertFalse(horarioTerminaDentroDelOtro1.noSeSuperpone(horarioTerminaDentroDelOtro2));
    }
    
    @Test
    public void horarioEmpiezaDentroDelOtroTest() {
        assertFalse(horarioEmpiezaDentroDelOtro1.noSeSuperpone(horarioEmpiezaDentroDelOtro2));
    }
    
    @Test
    public void sinSuperposicionTest() {
    	Oferta oferta1 = new Oferta("Carlos", 2, 5, 1000, "Teatro");
        Oferta oferta2 = new Oferta("Laura", 7, 9, 600, "Danza");
        
    	Oferta ofertaConFinIdenticoAInicio = new Oferta("Carlos", 1, 5, 1000, "Teatro");
        Oferta ofertaConInicioIdenticoAFin = new Oferta("Laura", 5, 10, 600, "Danza");
        
        
        assertTrue(oferta1.noSeSuperpone(oferta2));
        assertTrue(oferta2.noSeSuperpone(oferta1));
        
        assertTrue(ofertaConFinIdenticoAInicio.noSeSuperpone(ofertaConInicioIdenticoAFin));
        assertTrue(ofertaConInicioIdenticoAFin.noSeSuperpone(ofertaConFinIdenticoAInicio));
    }
}
