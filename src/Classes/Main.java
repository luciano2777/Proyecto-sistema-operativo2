/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Classes;

import GUI.MainView;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Juan
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
//        FileSystem fs = new FileSystem(10);
//        fs.createDirectory("d1", "root");
//        fs.createFile("File1", 3, "root/d1");
//        System.out.println(fs.getRoot().getDirectories().get(0).getFiles().get(0).getName());
        
        
        
        MainView mv = new MainView(10);
        mv.setVisible(true);

        

        
    }
    
}
