/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package driver.lab.pkg5;

/**
 *
 * @author Cwhit
 */

public class Node {
    //Instance variable to store integer

    protected String studentName;

    //Node class object created to store its own reference

    //Instance variable to point to a node

    protected Node next;

    //Default constructor to initialize

    public Node()

    {

    //Object is initialized as null

    next = null;

    //studentName is initialized to null

    studentName = "";

    }

    // initialize with specific value

    public Node(String name, Node n)

    {
    studentName = name;

    next = n;

    }

    //Method to set link to next Node

    public void setNext(Node n)

    {

    next = n;

    }

    //Method to get link to next node

    public Node getNext()

    {
    return next;
    }

    //Method to get data from current Node

    public String getName()

    {

    //Return the current node data

    return studentName;

    }

}

