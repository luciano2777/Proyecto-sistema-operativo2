/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataStructures;


/**
 *
 * @author Juan
 * @param <T>
 */
public class List<T> {
    //Atributos
    private Node<T> head;    
    private int size;

    //Constructor 1: Creacion de una lista vacia
    public List() {
        this.head = null;        
        this.size = 0;
    }
    
    //Constructor 2: Creacion de una lista con nodos
    public List(T... dataArray) {
        for(T data: dataArray){
            append(data);
        }
    }
    
    //-------------------Getters y Setters-------------------
    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public int getSize() {
        return size;
    }        
    
    //-------------------Procedimientos y Metodos-------------------   
    
    /***
     * Elimina todos los nodos de la lista 
     */
    public void delete(){
        this.head = null;
        this.size = 0;
    }    
    
    /***
     * Retorna si la lista esta vacia
     * @return 
     */
    public boolean isEmpty(){
        return this.head == null;
    }
        
    /***
     * Agrega un nodo al final de la lista
     * @param data Dato a agregar en la lista
     */
    public void append(T data){        
        Node<T> newNode = new Node(data);
        if(isEmpty()){
            this.head = newNode;
        }
        else{
            Node pointer = this.head;
            while(pointer.getNext() != null){
                pointer = pointer.getNext();
            }
            
            pointer.setNext(newNode);
        }
        this.size++;
    }
    
    /***
     * Agrega un nodo al inicio de la lista
     * @param data Dato a agregar en la lista
     */
    public void preappend(T data){
        Node<T> newNode = new Node(data);
        if(isEmpty()){
            this.head = newNode;
        }
        else{
            newNode.setNext(this.head);
            this.head = newNode;
        }
        this.size++;
    }
    
    /***
     * 
     * Agrega un nodo en el indice de la lista
     * @param data Dato a agregar en la lista
     * @param idx Indice de la lista
     */
    public void insert(T data, int idx){
        Node<T> newNode = new Node(data);
        if(idx >= 0 && idx < this.size){
            if(idx == 0){
                preappend(data);
            }
            else{
                Node<T> pointer = this.head;
                for (int i = 0; i < idx-1; i++) {
                    pointer = pointer.getNext();
                }
                newNode.setNext(pointer.getNext());
                pointer.setNext(newNode);
                this.size++;
            }
        }
        else{
            System.err.println("List error: Index " + Integer.toString(idx) + " out of range");
        }        
    }
    
    /***
     * Elimina el primer elemento de la lista
     * @return 
     */
    public T removeHead(){
        if(!isEmpty()){
            T data = this.head.getData();
            Node<T> newHead = this.head.getNext();
            this.head.setNext(null);
            this.head = newHead;   
            this.size--;
            return data;
        }
        else{
            System.err.println("List error: Empty list");
        }   
        return null;
    }
    
    /***
     * Elimina el ultimo elemento de la lista
     * @return 
     */
    public T removeTail(){
        if(!isEmpty()){
            
            Node<T> pointer = this.head;
            while(pointer.getNext().getNext() != null){
                pointer = pointer.getNext();
            }
            
            T data = (T) pointer.getNext().getData();
            pointer.setNext(null);
            
            this.size--;            
            return data;
        }
        else{
            System.err.println("List error: Empty list");
        } 
        return null;
    }
    
    /***
     * Elimina un elemento de la lista dado su indice
     * @param idx indice del elemento a eliminar
     * @return retorna el elemento eliminado                             
     */
    public T pop(int idx){
        if(isEmpty()){
            System.err.println("List error: Empty list");
        }
        else if(idx < 0 || idx > size-1){            
            System.err.println("List error: Index " + Integer.toString(idx) + " out of range");
        }
        else{
            if(idx == 0){
                return removeHead();
            }
            else if(idx == size-1){
                return removeTail();
            }
            else{
                Node<T> pointer = this.head;
                for (int i = 0; i < idx-1; i++) {
                    pointer = pointer.getNext();
                }
                Node<T> nodeToDelete = pointer.getNext();
                T data = nodeToDelete.getData();
                pointer.setNext(nodeToDelete.getNext());
                nodeToDelete.setNext(null);
                this.size--;
                return data;
            }                        
        } 
        return null;
    }
    


    
    /***
     * Obtiene el elemento de la lista dado el indice
     * @param idx indice del elemento a obtener
     * @return       
     */
    public T get(int idx){        
        if(isEmpty()){
            System.err.println("List error: Empty list");
        }
        else if(idx < 0 || idx > size-1){
            System.err.println("List error: Index " + Integer.toString(idx) + " out of range");
        }
        else{
            if(idx == 0){
                return this.head.getData();
            }
            else{
                Node<T> pointer = this.head;
                for (int i = 0; i < idx; i++) {                    
                    pointer = pointer.getNext();
                }
                return pointer.getData();
            }            
        }
        return null;
    }
    
    /***
     * Obtiene el indice dado un dato, en caso de que hallan varios elementos con el mismo dato retorna el primero
     * @param data dato dado para hallar su indice
     * @return 
     */
    public Integer getIndex(T data){
        if(!isEmpty()){
            Node<T> pointer = this.head;
            for (int i = 0; i < this.size; i++) {
                if(pointer.getData() == data){
                    return i;
                }                
            }
        }
        else{
            System.err.println("List error: Empty list");
        }
        return null;
    }
    
    /***
     * Reemplaza todos los datos A por datos B
     * @param dataA dato que sera reemplazado
     * @param dataB dato usado para reemplazar
     */
    public void reeplace(T dataA, T dataB){
        if(!isEmpty()){
            Node<T> pointer = this.head;
            for (int i = 0; i < size; i++) {
                if(pointer.getData() == dataA){
                    pointer.setData(dataB);
                }                
            }
        }
        else{
            System.err.println("List error: Empty list");
        }        
    }
    
    /***
     * Retorna una copia de la lista con todos sus nodos
     * @return 
     */
    public List<T> copy(){
        List<T> newList = new List();
        if(!isEmpty()){                        
            for (int i = 0; i < size; i++) {
                newList.append(get(i));                
            }
        }
        return newList;
    }
    
    /***
     * Permite imprimir la lista en consola usando System.out.println(list);
     * @return 
     */
    @Override
    public String toString() {        
        if(isEmpty()){
            return "[]";
        }
        
        String listStr = ""; 
        Node<T> pointer = this.head;        
        for (int i = 0; i < size; i++) {
            listStr += pointer.getData() + " ";
            pointer = pointer.getNext();
        }
        return listStr;
    }

    

}