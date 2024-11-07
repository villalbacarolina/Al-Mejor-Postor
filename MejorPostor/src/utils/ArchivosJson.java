package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class ArchivosJson {

	private static File _carpeta;
	private static String _rutaArchivo;
	
	public static <T> void guardarComoJSON(String nombreArchivo, Set<T> set) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(set);

        String nombreCarpeta = "src/jsons";
        _carpeta = new File(nombreCarpeta);
        if (!_carpeta.exists()) 
            _carpeta.mkdirs();
        String _rutaArchivo = nombreCarpeta + File.separator + nombreArchivo;

        try (FileWriter writer = new FileWriter(_rutaArchivo, true)) {
            writer.write(json);
            writer.close();
            //SACAR ESTO
            System.out.println("Archivo JSON guardado en: " + _rutaArchivo);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar el archivo JSON.");
        }
    }
	
	public static File getCarpeta() {
		return _carpeta;
	}
	public static String getRutaArchivo() {
		return _rutaArchivo;
	}
	
}
