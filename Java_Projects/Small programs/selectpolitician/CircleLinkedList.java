/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package selectpolitician;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
/**
 *
 * @author Cwhit
 */
public class CircleLinkedList 
{
    Node first = new Node(1);
    Node current = first;
    Node last = first;
    int candidates;
    PrintWriter writer;

    // Creates the circular doubly linked list with N nodes
    public CircleLinkedList(int n, PrintWriter inWriter) throws FileNotFoundException, UnsupportedEncodingException {
        writer = inWriter;
        for (int x = 2; x <= n; x++) {
            addItem(x);
        }
        candidates = n;
    }
    public void addItem(int inPol) { // adds an item to the end of the doubly linked list
        Node temp = new Node(inPol);
        last.setNext(temp);
        temp.setPrev(last);
        temp.setNext(first); // Creates a circularly linked list
        last = temp;
        first.setPrev(last);
    }
    public void deleteItem(int pol) { // Deletes an item with the politician value
        current = first;
        if (first.getNext() != null) {
            while (pol != current.getPol()) {
            current = current.getNext();
        }
        if (current.getPol() == last.getPol()) {
            last = current.getPrev();
            current.getPrev().setNext(first);
        }
        if (current.getPol() == first.getPol()) {
            first = current.getNext();
            current.getNext().setPrev(last);
        }
            current.getPrev().setNext(current.getNext());
            current.getNext().setPrev(current.getPrev());
        } else {
            first = null;
            last = null;
        }
        candidates -= 1;
    }
    public int getCandidates() {
        return candidates;
    }
    public void closeWriter(){ //Closes the file writer
        writer.close();
    }
    // Carries out the candidate selection process
    public void candidateSelection(int n, int k, int m) {
        Node selectorK, selectorM;
        selectorK = first;
        selectorM = last;
        if (m == 0) {
        m = 1;
        }
        if (m > n) {
        m = m % n;
        }
        int counter = 0;
        while (candidates >= 0) {
            counter += 1;
            for (int x = 1; x < k; x++) {// Move the list to kth value
                selectorK = selectorK.getNext();
            }

            for (int x = n; x > (n-m); x--) {// Move the list to m value
                selectorM = selectorM.getPrev();
            }

            if (selectorK.getPol() == selectorM.getPol()) {
                System.out.println(selectorK.getPol());
                writer.println(selectorK.getPol());
                deleteItem(selectorK.getPol());// Remove K value
            } else {
                System.out.println(selectorK.getPol() + " " + selectorM.getPol());
                writer.println(selectorK.getPol() + " " + selectorM.getPol());
                deleteItem(selectorK.getPol());// Remove K value
                deleteItem(selectorM.getPol());// Remove M value
            }
        }
    }
}
