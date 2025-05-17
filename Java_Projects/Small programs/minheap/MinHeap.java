/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package minheap;

/**
 *
 * @author Cwhit
 */
public class MinHeap {
public static void main(String[] args) {
        Heap heap = new Heap();

        // Add items to the heap
        heap.insert(15);
        heap.insert(5);
        heap.insert(8);
        heap.insert(4);
        heap.insert(9);
        heap.insert(22);
        heap.insert(17);
        heap.insert(6);
        heap.insert(14);
        heap.insert(1);

        // Print the heap
        System.out.println("Heap after inserting items:");
        heap.printHeap();

        // Remove two items from the heap
        heap.remove();
        heap.remove();

        // Print the heap
        System.out.println("Heap after removing two items:");
        heap.printHeap();

        // Add more items to the heap
        heap.insert(18);
        heap.insert(12);

        // Print the heap
        System.out.println("Heap after adding two more items:");
        heap.printHeap();

        // Remove three items from the heap
        heap.remove();
        heap.remove();
        heap.remove();

        // Print the heap
        System.out.println("Heap after removing three items:");
        heap.printHeap();
    }
}
