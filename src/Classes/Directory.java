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
public class Directory {
    private String name;
    private Directory parent;    
    private List<File> files;
    private List<Directory> directories;    

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        this.files = new List();
        this.directories = new List();        
    }
    
    public Directory(String name) {
        this.name = name;
        this.parent = null;
        this.files = new List();
        this.directories = new List();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }
    
    public void addFile(File file){
        this.files.append(file);
    }
    
    public void addDirectory(Directory directory){
        this.directories.append(directory);
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }
    
    
    
    
}
