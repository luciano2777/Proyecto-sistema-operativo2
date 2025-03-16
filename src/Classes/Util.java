/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

/**
 *
 * @author Juan
 */
public class Util {
    
    
    public static boolean isNumeric(String str){
        try{
            int num = Integer.parseInt(str);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
