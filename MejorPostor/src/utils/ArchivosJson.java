package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class ArchivosJson {

	private static File _carpeta;
	private static String _rutaArchivo;
	
	public static <T> void guardarComoJSON(String nombreArchivo, ArrayList<T> set) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(set);

        String nombreCarpeta = "src/jsons";
        _carpeta = new File(nombreCarpeta);
        if (!_carpeta.exists()) 
            _carpeta.mkdirs();
        String _rutaArchivo = nombreCarpeta + File.separator + nombreArchivo;

        try (FileWriter writer = new FileWriter(_rutaArchivo)) {
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static File getCarpeta() {
		return _carpeta;
	}
	public static String getRutaArchivo() {
		return _rutaArchivo;
	}
	
}
