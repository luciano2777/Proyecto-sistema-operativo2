/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

/**
 *
 * @author Juan
 */
public class Block {    
    private boolean avaible;
    private Integer next;

    public Block() {
        this.avaible = true;
        this.next = null;
    }

    public boolean isAvaible() {
        return avaible;
    }

    public void setAvaible(boolean avaible) {
        this.avaible = avaible;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }
    
    @Override
    public String toString(){
        return "avaible -> " + avaible + " | next -> " + next;
    }
    
    
    
}
