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
    private List<JFile> Files;
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

    public List<JFile> getFiles() {
        return Files;
    }

    public void setFiles(List<JFile> Files) {
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
    
    
    
    public String createFile(String name, int size, String parentPathDirectory){
        Directory parentDirectory = getDirectory(parentPathDirectory);
        
        Block firstBlock = assignBlock(size);
        
        if(firstBlock == null){            
            return "No hay espacio disponible en el dispositivo de almacenamiento";
        }
        
        List<JFile> files = parentDirectory.getFiles();
        for (int i = 0; i < files.getSize(); i++) {
            JFile auxFile = files.get(i);            
            if(auxFile.getName().equals(name + ".file")){
                return "Ya existe un archivo con el nombre '" + name + "'";
            }
        }
                
        JFile newFile = new JFile(name, size, firstBlock);  
        parentDirectory.addFile(newFile);
        return "Archivo creado exitosamente";
    }
    
    
    public String deleteFile(String path){
        String fileToDelete = path.split("/")[path.split("/").length-1];
        if(!fileToDelete.contains(".file")){            
            return "Ruta no valida";
        }
                
        Directory directory = getDirectory(path);
        
        List<JFile> files = directory.getFiles();
        
        for (int i = 0; i < files.getSize(); i++) {
            JFile file = files.get(i);
            if(file.getName().equals(fileToDelete)){
                Block block = file.getFirstBlock();
                
                while(block != null){
                    block.setAvaible(true);
                    
                    if(block.getNext() == null){
                        block = null;
                    }
                    else{
                        block = SD[block.getNext()];                        
                    }                    
                }
                
                files.pop(i);
            }
        }
        return "Archivo borrado exitosamente";
    }
    
    
    public String createDirectory(String name, String parentPathDirectory){
        Directory parentDirectory = getDirectory(parentPathDirectory);
        
        if(parentDirectory == null){            
            return "Ruta no valida";
        }
        
        List<Directory> directories = parentDirectory.getDirectories();
        for (int i = 0; i < directories.getSize(); i++) {
            Directory auxDirectory = directories.get(i);
            
            if(auxDirectory.getName().equals(name)){
                return "Ya existe un directorio con el nombre '" + name + "'";
            }
        }
        
        Directory newDirectory = new Directory(name, parentDirectory);         
        parentDirectory.getDirectories().append(newDirectory);
        
        return "Directorio creado exitosamente";
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
    
    
    public String deleteDirectory(String path){
        Directory directory = getDirectory(path);
        
        System.out.println(directory.getName());
        if(directory.getParent() == null){
            return "No se puede eliminar el directorio raiz";
        }
        
        if(directory == null){
            return "Ruta de directorio no valida";            
        }
        
        List<JFile> files = directory.getFiles();
        
        //Borrar todos los archivos del directorio
        for (int i = 0; i < files.getSize(); i++) {
            JFile file = files.pop(i);
            
            Block block = file.getFirstBlock();
            
            while(block != null){
                block.setAvaible(true);

                if(block.getNext() == null){
                    block = null;
                }
                else{
                    block = SD[block.getNext()];                        
                }                    
            }                        
        }
        
        
        //Borrar subdirectorios
        directory.getDirectories().delete();
        
        //Borrar directorio
        List<Directory> parentDirectories = directory.getParent().getDirectories();
        
        for (int i = 0; i < parentDirectories.getSize(); i++) {
            if(parentDirectories.get(i).getName().equals(directory.getName())){
                parentDirectories.pop(i);
                break;
            }
        }
        
        return "Directorio borrado exitosamente";
        
        
        
    }
    
        
    
    
    
    
    
    
    
    
    
    
    
    
}
