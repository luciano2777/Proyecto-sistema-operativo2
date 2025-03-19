/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import DataStructures.List;
import java.awt.Color;

/**
 *
 * @author Juan
 */
public class JFile {
    private String name;     
    private String path;
    private int size;    
    private Integer firstBlock;  
    private Integer[] color;
    private List<JFile> backup;

    public JFile(String name, int size, Integer[] color, Integer firstBlock) {
        this.name = name + ".file";        
        this.size = size;
        this.firstBlock = firstBlock;
        this.color = color;
        this.backup = new List();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(!name.contains(".file")){
            this.name = name + ".file";            
        }
        else{
            this.name = name;            
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<JFile> getBackup() {
        return backup;
    }

    public void setBackup(List<JFile> backup) {
        this.backup = backup;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getFirstBlock() {
        return firstBlock;
    }

    public void setFirstBlock(Integer firstBlock) {
        this.firstBlock = firstBlock;
    }

    public Integer[] getColor() {
        return color;
    }

    public void setColor(Integer[] color) {
        this.color = color;
    }
    
    
    
    
    
    @Override
    public String toString() {
        return name;
    }
    
    
    
    
    
    
}
