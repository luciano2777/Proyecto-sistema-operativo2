/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import DataStructures.List;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    
    private static void saveDir(Directory root) throws IOException{
        Gson gson = new Gson();
        String dirJson = gson.toJson(root);
        
        String sp = File.separator;
        String path = Paths.get("src"+sp+"DB"+sp+"directories.json").normalize().toString();
        
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(dirJson);
            
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    
    private static Directory loadDir(){
        Gson gson = new Gson();
        
        String sp = File.separator;
        String path = Paths.get("src"+sp+"DB"+sp+"directories.json").normalize().toString();
        try (FileReader reader = new FileReader(path)) {
            // Convierte el JSON a un objeto Persona
            Directory root = gson.fromJson(reader, Directory.class);
            return root;

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    private static void saveSD(Block[] SD) throws IOException{
        Gson gson = new Gson();
        String SDJson = gson.toJson(SD);
        
        String sp = File.separator;
        String path = Paths.get("src"+sp+"DB"+sp+"SD.json").normalize().toString();
        
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(SDJson);
            
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    
    private static Block[] loadSD(){
        Gson gson = new Gson();
        
        String sp = File.separator;
        String path = Paths.get("src"+sp+"DB"+sp+"SD.json").normalize().toString();
        try (FileReader reader = new FileReader(path)) {
            // Convierte el JSON a un objeto Persona
            Block[] SD = gson.fromJson(reader, Block[].class);
            return SD;

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    
    
    public static FileSystem load(){
        Directory root = loadDir();
        Block[] SD = loadSD();        
        
        if(root == null || SD == null){
            return null;
        }
        
        FileSystem fs = new FileSystem(SD, root, true);
        return fs;
    }
    
    
    public static void save(FileSystem fs){
        try {
            saveDir(fs.getRoot());
            saveSD(fs.getSD());            
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
