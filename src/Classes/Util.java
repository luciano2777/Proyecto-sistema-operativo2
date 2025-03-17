/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @author Juan
 */
public class Util {
    
    
    public static boolean isNumeric(String str){
        try{
            int num = Integer.parseInt(str);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    
    
    public static void save(FileSystem fileSystem) throws IOException{
        Gson gson = new Gson();
        String fsJson = gson.toJson(fileSystem);
        
        String sp = File.separator;
        String path = Paths.get("src"+sp+"DB"+sp+"directories.json").normalize().toString();
        
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(fsJson);
            
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    
    public static FileSystem load(){
        Gson gson = new Gson();
        
        String sp = File.separator;
        String path = Paths.get("src"+sp+"DB"+sp+"directories.json").normalize().toString();
        try (FileReader reader = new FileReader(path)) {
            // Convierte el JSON a un objeto Persona
            FileSystem fileSystem = gson.fromJson(reader, FileSystem.class);
            return fileSystem;

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    
}
