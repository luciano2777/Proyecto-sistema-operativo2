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
    private Directory root;
    private List<JFile> files;
    private boolean adminMode;

    public FileSystem(int SDsize) {
        this.SD = new Block[SDsize];       
        this.root = new Directory("root");
        this.files = new List();
        this.adminMode = false;
        
        for (int i = 0; i < SD.length; i++) {
            SD[i] = new Block();           
        }
    }

    public FileSystem(Block[] SD, Directory root, boolean adminMode) {
        this.SD = SD;        
        this.root = root;
        this.files = new List();
        this.adminMode = adminMode;
    }
    
    public FileSystem(Block[] SD, Directory root, List<JFile> files, boolean adminMode) {
        this.SD = SD;        
        this.root = root;
        this.files = files;
        this.adminMode = adminMode;
    }
    
    

    public Block[] getSD() {
        return SD;
    }

    public void setSD(Block[] SD) {
        this.SD = SD;
    }

    public Directory getRoot() {
        return root;
    }

    public void setRoot(Directory root) {
        this.root = root;
    }

    public boolean isAdminMode() {
        return adminMode;
    }

    public void setAdminMode(boolean adminMode) {
        this.adminMode = adminMode;
    }

    public List<JFile> getFiles() {
        return files;
    }

    public void setFiles(List<JFile> files) {
        this.files = files;
    }        
    
    
    
    
    public Integer assignBlock(int size, Integer[] color){
        int remainingBlocks = size;
        Integer firstBlock = null;
        
        //Selecciona el primer bloque que este desocupado
        for (int i = 0; i < SD.length; i++) {
            Block block = SD[i];
            if (block.isAvaible()) {
                firstBlock = i; 
                block.setAvaible(false); //Marca el primer bloque como no disponible
                block.setColor(color);
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
        Block currentBlock = SD[firstBlock];
        for (int i = 0; i < SD.length; i++) {
            Block block = SD[i];
            if (block.isAvaible()) {
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
            SD[firstBlock].setAvaible(true); //Libera el primer bloque
            Integer[] defaultColor = {255, 255, 255};
            SD[firstBlock].setColor(defaultColor);
            return null;
        }  
        
        //Buscar los siguientes bloques para el fichero
        remainingBlocks = size-1;
        currentBlock = SD[firstBlock];
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
        
        //Marca los bloques del disco como no disponibles        
        Block pointer = SD[firstBlock];
        while(pointer.getNext() != null){
            pointer.setColor(color);
            int idx = pointer.getNext();
            Block block = SD[idx];
            block.setAvaible(false);            
            pointer = SD[idx];
        }
        pointer.setColor(color);        
        
        return firstBlock;
    }  
    
    
    
    public String createFile(String name, int size, String parentPathDirectory){
        Directory parentDirectory = getDirectory(parentPathDirectory);
        
        Integer[] color = Util.getRandomColor();
        Integer firstBlock = assignBlock(size, color);
        
        String path = parentPathDirectory + name + ".file/" ;
        JFile newFile = new JFile(name, path, size, color, firstBlock);  
        
        if(firstBlock == null){            
            return "No hay espacio disponible en el dispositivo de almacenamiento";
        }
        
        List<JFile> parentFiles = parentDirectory.getFiles();
        for (int i = 0; i < parentFiles.getSize(); i++) {
            JFile auxFile = parentFiles.get(i);            
            if(auxFile.getName().equals(name + ".file")){
                return "Ya existe un archivo con el nombre '" + name + "'";
            }
        }
                
        parentDirectory.addFile(newFile);        
        files.append(newFile);          
        
        
        return "Archivo creado exitosamente";
    }
    
    
    public String deleteFile(String path){          
        if(!path.contains(".file")){            
            return "Ruta no valida";
        }
        
        String[] arrayPath = null;
        String parentPath = "";
        try{
            arrayPath = path.split("/");
            
            for (int i = 0; i < arrayPath.length-1; i++) {
                parentPath += arrayPath[i] + "/";
            }
        }
        catch(Exception e){
            return "Ruta no valida";
        }
                
        JFile file = getFile(path);        
        Block firstBlock = SD[file.getFirstBlock()];
        
        //Liberar el archivo de todos los bloques
        Integer[] defaultColor = {255, 255, 255};
        while(firstBlock != null){
            firstBlock.setAvaible(true);
            firstBlock.setColor(defaultColor);
            
            if(firstBlock.getNext() == null){
                break;
            }
            
            Block nextBlock = SD[firstBlock.getNext()];
            firstBlock.setNext(null);
            firstBlock = nextBlock;
        }
        
        //Eliminar el archivo del directorio                           
        Directory parentDirectory = getDirectory(parentPath);           
        
        for (int i = 0; i < parentDirectory.getFiles().getSize(); i++) {
            JFile auxFile = parentDirectory.getFiles().get(i);
            if(auxFile.getName().equals(file.getName())){                
                parentDirectory.getFiles().pop(i);
            }
        }
                
        //Eliminar de la lista de archivos
        for (int i = 0; i < files.getSize(); i++) {
            JFile auxFile = files.get(i);
            if(auxFile.getPath().equals(file.getPath())){
                files.pop(i);
            }
        }
        
        return "Archivo borrado exitosamente";
    }
    
    
    public String editFile(String name, String path){
        String fileToDelete = path.split("/")[path.split("/").length-1];
        if(!fileToDelete.contains(".file") || path.equals("root/")){            
            return "Ruta no valida";
        }
        
        String[] arrayPath = path.split("/");
        String parentPath = "";
        for (int i = 0; i < arrayPath.length-1; i++) {
            parentPath += arrayPath[i] + "/";
        }
        
        JFile file = getFile(path);
        Directory directory = getDirectory(parentPath);
        List<JFile> files = directory.getFiles();
        
        for (int i = 0; i < files.getSize(); i++) {
            JFile auxFile = files.get(i);
            if(auxFile.getName().equals(name)){
                return "Ya existe un archivo con el nombre '" + name + "'";
            }
        }
        
        file.getBackup().append(file.getName());
        file.setName(name);
        return "Archivo editado exitosamente";
    }
    
    
    
    public JFile getFile(String path){
        String[] arrayPath = null;                
        try{            
            arrayPath = path.split("/");
        }
        catch(Exception e){
            System.out.println("Ruta de directorio no valida");
            return null;            
        }        
        
        String parentPath = "";
        for (int i = 0; i < arrayPath.length-1; i++) {
            parentPath += arrayPath[i] + "/";
        }
        
        Directory parentDirectory = getDirectory(parentPath);
        List<JFile> files = parentDirectory.getFiles();
        
        for (int i = 0; i < files.getSize(); i++) {
            JFile file = files.get(i);
            
            if(file.getName().equals(arrayPath[arrayPath.length-1])){
                return file;
            }
        }
        
        return null;
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
        
        Directory newDirectory = new Directory(name, parentPathDirectory);         
        parentDirectory.getDirectories().append(newDirectory);
        
        return "Directorio creado exitosamente";
    }
    

    
    public Directory getDirectory(String path){
        String[] arrayPath = null;
        
        //System.out.println(path);
        
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
        if(path.equals("root/")){            
            return "No se puede eliminar el directorio raiz";
        }
        
        Directory directory = getDirectory(path);                        
        if(directory == null){
            return "Ruta de directorio no valida";            
        }
        
        
        List<JFile> directoryFiles = directory.getFiles();        
        
        //Borrar todos los archivos del directorio
        Integer[] defaultColor = {255, 255, 255};
        for (int i = 0; i < directoryFiles.getSize(); i++) {
            JFile file = directoryFiles.get(i);            
                           
            //Eliminar de la lista de archivos
            for (int j = 0; j < files.getSize(); j++) {                
                JFile auxFile = files.get(j);
                if(auxFile.getPath().equals(file.getPath())){
                    files.pop(j);
                }
            }
            
            //Eliminar de los bloques
            Block block = SD[file.getFirstBlock()];            
            while(block != null){
                block.setAvaible(true);
                block.setColor(defaultColor);

                if(block.getNext() == null){
                    block = null;
                }
                else{
                    Integer next = block.getNext();
                    block.setNext(null);
                    block = SD[next];                        
                }                    
            }                        
        }
        
        
        directoryFiles.delete();
        
        
        //Borrar subdirectorios
        directory.getDirectories().delete();
        
        
        //Borrar directorio
        Directory parentDirectory = getDirectory(directory.getParentPath());
        List<Directory> parentDirectories = getDirectory(directory.getParentPath()).getDirectories();
        
        
        
        for (int i = 0; i < parentDirectories.getSize(); i++) {
            if(parentDirectories.get(i).getName().equals(directory.getName())){
                parentDirectories.pop(i);                
                break;
            }
        }
        
        return "Directorio borrado exitosamente";                        
    }
    
    
    public String editDirectory(String name, String path){
        String fileToDelete = path.split("/")[path.split("/").length-1];
        if(fileToDelete.contains(".file") ){            
            return "Ruta no valida";
        }
        
        if(path.equals("root/")){
            return "No se puede editar el directorio raiz";
        }
                
        Directory directory = getDirectory(path);
        List<Directory> directories = getDirectory(directory.getParentPath()).getDirectories();
        
        for (int i = 0; i < directories.getSize(); i++) {
            Directory auxDirectory = directories.get(i);
            if(auxDirectory.getName().equals(name)){
                return "Ya existe un archivo con el nombre '" + name + "'";
            }
        }
        
        directory.setName(name);        
        return "Archivo editado exitosamente";
    }
    
    
    public String restoreFile(String path, int option){
        
        if(!path.contains(".file")){
            return "Ruta no valida";
        }
        
        JFile file = getFile(path);
        
        List<String> backup = file.getBackup();
        String newName = backup.pop(option-1);
        
        file.getBackup().append(file.getName());
        file.setName(newName);
        return "Restauracion completada";
        
        
    }
    
        
    
    
    
    
    
    
    
    
    
    
    
    
}
