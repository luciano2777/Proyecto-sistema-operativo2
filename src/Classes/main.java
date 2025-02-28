/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Classes;

/**
 *
 * @author luciano
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { //Probando
        // TODO code application logic here
        File archivo = new File("Hola", "texto");
        System.out.println(archivo.getName() +"\n" + archivo.getContent());
        archivo.setName("Adios");
        archivo.setContent("nada");
        File previousFile = archivo.getbackup(0); //Saca el backup
        System.out.println(previousFile.getName() + " Version anterior del archivo antes del cambio de nombre"+ "\nTipo de cambio:" + previousFile.getTipoCambio());
        System.out.println(archivo.getName() + " Version actual");
    }
    
}
