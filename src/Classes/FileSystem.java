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
public class FileSystem {
    private Block[] SD;
    private List<File> Files;
    private Directory root;

    public FileSystem(int SDsize) {
        this.SD = new Block[SDsize];
        this.Files = new List();
        this.root = new Directory("root");
        
        for (int i = 0; i < SD.length; i++) {
            SD[i] = new Block();           
        }
    }

    public Block[] getSD() {
        return SD;
    }

    public void setSD(Block[] SD) {
        this.SD = SD;
    }

    public List<File> getFiles() {
        return Files;
    }

    public void setFiles(List<File> Files) {
        this.Files = Files;
    }

    public Directory getRoot() {
        return root;
    }

    public void setRoot(Directory root) {
        this.root = root;
    }
    
    
    
    public Block assignBlock(int size){
        int remainingBlocks = size;
        Block firstBlock = null;
        
        //Selecciona el primer bloque que este desocupado
        for (Block block : SD) {
            if (block.isAvaible()) {
                firstBlock = block; 
                firstBlock.setAvaible(false); //Marca el primer bloque como no disponible
                remainingBlocks--;
                break;
            }
        }
        
        //Si no hay bloques disponibles el SD esta lleno
        if(firstBlock == null){
            System.out.println("El dispositivo no posee espacio");
            return null;
        }
        
        //Buscar los siguientes bloques para el fichero
        Block currentBlock = firstBlock;
        for (int i = 0; i < SD.length; i++) {
            Block block = SD[i];
            if (block.isAvaible()) {
                currentBlock.setNext(i);
                currentBlock = block;                
                remainingBlocks--;                                
            }            
            
            if(remainingBlocks == 0){
                break;
            }
        }
        
        //Si no hay bloques suficientes
        if(remainingBlocks > 0){
            System.out.println("El size del fichero sobrepasa la capacidad del dispositivo");
            firstBlock.setAvaible(true); //Libera el primer bloque
            return null;
        }  
        
        //Marca los bloques del disco como no disponibles        
        Block pointer = firstBlock;
        while(pointer.getNext() != null){
            int idx = pointer.getNext();
            Block block = SD[idx];
            block.setAvaible(false);
            pointer = SD[idx];
        }
        
        return firstBlock;
    }  
    
    
    
    public void createFile(String name, int size, String parentPathDirectory){
        Directory parentDirectory = getDirectory(parentPathDirectory);
        
        Block firstBlock = assignBlock(size);
        
        if(firstBlock == null){
            System.out.println("No se pudo crear el bloque");
            return;
        }
                
        File newFile = new File(name, size, firstBlock);  
        parentDirectory.addFile(newFile);
    }
    
    
    
    public void createDirectory(String name, String parentPathDirectory){
        Directory parentDirectory = getDirectory(parentPathDirectory);
        
        if(parentDirectory == null){
            System.out.println("Ruta no valida");
            return;
        }
        
        Directory newDirectory = new Directory(name);                
        parentDirectory.getDirectories().append(newDirectory);
    }
    

    
    public Directory getDirectory(String path){
        String[] arrayPath = null;
        try{
            arrayPath = path.split("/");
        }
        catch(Exception e){
            System.out.println("Ruta de directorio no valida");
            return null;            
        }
                   
        Directory currentDirectory = this.root;
        for (int i = 1; i < arrayPath.length; i++) {            
            List<Directory> directories = currentDirectory.getDirectories();
            
            for (int j = 0; j < directories.getSize(); j++) {
                Directory directory = directories.get(j);
                                
                if(directory.getName().equals(arrayPath[i])){
                    currentDirectory = directory;                    
                }
            }                        
        }
        
        return currentDirectory;         
    }
    
    
    
    
    
    
    
    
    
    
}
