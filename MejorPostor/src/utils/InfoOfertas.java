package utils;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import logica.Oferta;

public class InfoOfertas {
	
	private static 	ArrayList<Oferta> ofertasTotales = new ArrayList<Oferta>();
	private static ArrayList<Oferta> ofertasSeleccionadas = new ArrayList<Oferta>();
	
	@SuppressWarnings("deprecation")
	public static void obtenerOfertasTotalesJson() {
		Gson gson = new Gson();
        try {
            FileReader reader = new FileReader("src/jsons/ofertasTotales");

            JsonParser parser = new JsonParser();
            
            JsonElement jsonElement = parser.parse(reader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
         
            for (JsonElement element : jsonArray) {
               
                Oferta oferta = gson.fromJson(element, Oferta.class);
                ofertasTotales.add(oferta);
            	}
            
            if (!ofertasTotales.isEmpty()) {
            	
            	Calendar fecha = Calendar.getInstance();
            	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            	Oferta of = ofertasTotales.get(0);
            	fecha.setTime(format.parse(of.getFechaManiana()));
   		 		
            	Calendar fechaHoy = Calendar.getInstance();
            	
            	if (fechaHoy.equals(fecha) || fechaHoy.after(fecha)) {
            		ofertasTotales.clear();
            	}
   		 		
   		 	}
            reader.close();
         
        }catch (Exception e) {
            e.printStackTrace();
        }
        
	}
	
	@SuppressWarnings("deprecation")
	public static void obtenerOfertasSeleccionadasJson() {
		Gson gson = new Gson();
        try {
            FileReader reader = new FileReader("src/jsons/ofertasSeleccionadas");
         
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(reader);
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            for (JsonElement element : jsonArray) {
               
                Oferta oferta = gson.fromJson(element, Oferta.class);
                ofertasSeleccionadas.add(oferta);
            }
          
            reader.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void guardarOfertasTotales (Oferta oferta) {
		
		ofertasTotales.add(oferta);
	}
	
	public static void guardarOfertasSeleccionadas (Oferta oferta) {
		
		ofertasSeleccionadas.add(oferta);
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Oferta> getOfertasTotales()
	{
		return (ArrayList<Oferta>) ofertasTotales.clone();
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Oferta> getOfertasSeleccionadas()
	{
		return (ArrayList<Oferta>) ofertasSeleccionadas.clone();
	}
	
	
}
