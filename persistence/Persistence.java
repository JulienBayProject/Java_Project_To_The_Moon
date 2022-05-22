package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.game.Credits;
import model.player.GestionMember;

public class Persistence {

	/**
	 * @param <T>
	 * @param fileName
	 * @param classs
	 * lecture du fichier json, spécifié en argument + spécification de la classe
	 * en argument
	 * @return
	 */
	public static <T> T readingJson(String fileName, Class<T> classs) {
		Gson g = new GsonBuilder().create();
		try {
			JsonObject j = JsonParser.parseReader(new FileReader(fileName)).getAsJsonObject();
			T d = g.fromJson(j, classs);
			return d;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param fileName
	 * @param m
	 * écriture dans un fichier json, en spécifiant le nom du fichier + spécification de la classe
	 * en argument 
	 */
	public static void writeJson(String file, GestionMember gp ) {
        Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        PrintWriter pw;
        try  {
        	File f = new File(file);
            pw = new PrintWriter(new FileWriter(f));
       //     System.out.println(g.toJson(gp));	
            String json = g.toJson(gp);
            pw.println(json);
            pw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.err.println(e);
        }
        
    }
	
}
