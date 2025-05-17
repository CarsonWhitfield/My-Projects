/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package selectpolitician;

/**
 *
 * @author Cwhit
 */
public class Node 
{
    private int politician;
    protected Node next;
    protected Node previous;

    public Node(int polNum){
        politician = polNum;
    }
    public void setNext(Node n){
        next = n;
    }
    public Node getNext(){
        return next;
    }
    public void setPrev(Node n){
        previous = n;
    }
    public Node getPrev(){
        return previous;
    }
    public int getPol(){
        return politician;
    }
}
