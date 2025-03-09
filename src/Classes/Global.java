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
public class Global {
    public List<String>ListaSD;
    public List<File>ListaFile;
    public Global(){
        this.ListaSD = new List();
        this.ListaFile = new List();
    }
    
    //Implementar lo de guardar Archivo y demas luego.
    public static void CargarArchivo(){
    
}
    
    public static void CargarListaSD(){
    
}
    
    public static void CargarJtree(){
}
    public List<File> GetListaFile(){
        return ListaFile;
    }
    
    public List<String> GetListaSD(){
        return ListaSD;
    }
    
    public void SetListaFile(){
        this.ListaFile =ListaFile;
    }
    
    public void SetListaSD(){
        this.ListaSD= ListaSD;
    }
}
