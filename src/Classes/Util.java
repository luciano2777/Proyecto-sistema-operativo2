/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import Assets.ColorTypeAdapter;
import DataStructures.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.lang.reflect.Type;
import java.util.Random;
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
    
    
    public static boolean inRange(int value, int options){        
        return (value >= 1 && value <= options);
    }
    
    public static Integer[] getRandomColor() {
        Random random = new Random();
        int rojo = random.nextInt(256); // Genera un valor aleatorio entre 0 y 255
        int verde = random.nextInt(256);
        int azul = random.nextInt(256);
        Integer[] color = {rojo, verde, azul}; 
        
        return color;
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
            
            Block[] SD = gson.fromJson(reader, Block[].class);
            return SD;

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    private static void saveFiles(List<JFile> files) throws IOException{
        Gson gson = new Gson();
        
        System.out.println(files);
        String SDJson = gson.toJson(files);
        
        String sp = File.separator;
        String path = Paths.get("src"+sp+"DB"+sp+"files.json").normalize().toString();
        
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(SDJson);
            
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
    
    
    private static List<JFile> loadFiles() {
        Gson gson = new Gson();
        String sp = File.separator;
        String path = Paths.get("src" + sp + "DB" + sp + "files.json").normalize().toString();
        try (FileReader reader = new FileReader(path)) {
            Type fileListType = new TypeToken<List<JFile>>() {}.getType();
            List<JFile> files = gson.fromJson(reader, fileListType);
            
            List<JFile> copyFiles = files.copy();
            
            return copyFiles;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    
    public static FileSystem load(){
        Directory root = loadDir();
        Block[] SD = loadSD();    
        List<JFile> files = loadFiles();
        
        if(root == null || SD == null){
            return null;
        }
        
        FileSystem fs = new FileSystem(SD, root, files, true);
        return fs;
    }
    
    
    public static void save(FileSystem fs){
        try {
            saveDir(fs.getRoot());
            saveSD(fs.getSD());  
            saveFiles(fs.getFiles());
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
