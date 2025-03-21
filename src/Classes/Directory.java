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
    private String parentPath;    
    private List<JFile> files;
    private List<Directory> directories;    

    public Directory(String name, String parentPath) {
        this.name = name;
        this.parentPath = parentPath;
        this.files = new List();
        this.directories = new List();        
    }
    
    public Directory(String name) {
        this.name = name;
        this.parentPath = null;
        this.files = new List();
        this.directories = new List();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JFile> getFiles() {
        return files;
    }

    public void setFiles(List<JFile> files) {
        this.files = files;
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }
    
    public void addFile(JFile file){
        this.files.append(file);
        System.out.println(this.files);
    }
    
    public void addDirectory(Directory directory){
        this.directories.append(directory);
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    
    @Override
    public String toString() {
        return name;
    }
    
    
    
    
    
    
}
