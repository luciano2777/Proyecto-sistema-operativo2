/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import DataStructures.List;

/**
 *
 * @author Juan
 */
public class File {
    private String name;     
    private String path;
    private int size;    
    private Block firstBlock;    
    private List<File> backup;

    public File(String name, int size, Block firstBlock) {
        this.name = name;        
        this.size = size;
        this.firstBlock = firstBlock;
        this.backup = new List();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<File> getBackup() {
        return backup;
    }

    public void setBackup(List<File> backup) {
        this.backup = backup;
    }

    public Block getFirstBlock() {
        return firstBlock;
    }

    public void setFirstBlock(Block firstBlock) {
        this.firstBlock = firstBlock;
    }
    
    
    
    
    
    
    
    
}
