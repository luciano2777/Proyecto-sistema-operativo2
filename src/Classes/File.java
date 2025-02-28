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
    private String content;
    private String TipoCambio;
    //Backup será una lista que contendra los anteriores snapshots del file
    private List<File> backup;
    
    public File(String name, String content) {
        this.name = name;
        this.content = content;
        this.backup = new List();
        
                
    }
    
    public String getName() {
        return name;
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
        File archivo = new File(this.name,this.content);
        archivo.TipoCambio = Tipo;
        backup.append(archivo);
    }
    
    public String getTipoCambio() {
        return TipoCambio;
    }
    
}
