/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minheap;

/**
 *
 * @author Cwhit
 */
public class Heap {
    private int[] heapArray;
    private int size;

    public Heap() {
        heapArray = new int[25];
        size = 0;
    }

    public void insert(int item) {
        if (size >= heapArray.length) {
            System.out.println("Heap is full. Cannot insert item: " + item);
            return;
        }

        heapArray[size] = item;
        heapifyUp(size);
        size++;
    }

    public int remove() {
        if (size <= 0) {
            System.out.println("Heap is empty. Cannot remove item.");
            return -1;
        }

        int root = heapArray[0];
        heapArray[0] = heapArray[size - 1];
        size--;
        heapifyDown(0);

        return root;
    }

    private void heapifyUp(int index) {
        int parentIndex = (index - 1) / 2;

        if (parentIndex >= 0 && heapArray[index] < heapArray[parentIndex]) {
            swap(index, parentIndex);
            heapifyUp(parentIndex);
        }
    }

    private void heapifyDown(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int smallestIndex = index;

        if (leftChildIndex < size && heapArray[leftChildIndex] < heapArray[smallestIndex])
            smallestIndex = leftChildIndex;

        if (rightChildIndex < size && heapArray[rightChildIndex] < heapArray[smallestIndex])
            smallestIndex = rightChildIndex;

        if (smallestIndex != index) {
            swap(index, smallestIndex);
            heapifyDown(smallestIndex);
        }
    }

    private void swap(int i, int j) {
        int temp = heapArray[i];
        heapArray[i] = heapArray[j];
        heapArray[j] = temp;
    }

    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(heapArray[i] + " ");
        }
        System.out.println();
    }
}
