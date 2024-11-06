package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import logica.Oferta;

public class ArchivosJson {


	public static <T> void guardarComoJSON(String nombreArchivo, Set<T> set) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(set);

        String nombreCarpeta = "/jsons";
        File carpeta = new File(nombreCarpeta);
        if (!carpeta.exists()) 
            carpeta.mkdirs();
        String rutaArchivo = nombreCarpeta + File.separator + nombreArchivo;

        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.write(json);
            writer.close();
            //SACAR ESTO
            System.out.println("Archivo JSON guardado en: " + rutaArchivo);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar el archivo JSON.");
        }
    }	
	
}