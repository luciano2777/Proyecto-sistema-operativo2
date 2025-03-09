/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import DataStructures.List;

/**
 *
 * @author luciano
 */
public class File {
    
    private String name;
    private int size;
    private String content;
    //Tipo de cambio es para ver el tipo de cambio que se ha hecho a un file.
    private String TipoCambio;
    //Backup será una lista que contendra los anteriores snapshots del file
    private List<File> backup;
    
    public File(String name, int size) {
        this.name = name;
        this.backup = new List();
        this.size = size;
                
    }
    
    public String getName() {
        return name;
    }
    
        public int getSize() {
        return size;
    }

    public void setName(String name) {
        Savetobackup("Nombre");
        this.name = name;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        Savetobackup("Contenido");
        backup.append(this);
        this.content = content;
    }
    
    public File getbackup(int index) {
        return backup.get(index);
    }
    
    public void Savetobackup(String Tipo) {
        File archivo = new File(this.name,this.size);
        archivo.TipoCambio = Tipo;
        backup.append(archivo);
    }
    
    public String getTipoCambio() { 
        return TipoCambio;
    }
    
}
