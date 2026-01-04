🧠 Java Min-Heap Implementation (Array-Based)

This project implements a min-heap data structure in Java using a fixed-size integer array. It supports efficient insertion and removal of the minimum element while maintaining the heap property through recursive heapify-up and heapify-down operations.

    What It Does

      Stores heap values in an int[] array (capacity 25)

      Tracks the current number of elements with size

      Maintains the min-heap rule: every parent node is smaller than (or equal to) its children

    Core Operations

      insert(int item)

      Adds a value to the heap

      Restores heap order using heapifyUp (bubble-up)

      remove()

      Removes and returns the minimum value (root at index 0)

      Replaces the root with the last element

      Restores heap order using heapifyDown (sink-down)

      printHeap()

      Prints the current heap array contents in level-order (array order)

    Demo Program (MinHeap)

      The MinHeap class provides a simple test run that:

      Inserts a set of values

      Prints the heap state
  
      Removes several minimum elements

      Inserts additional values

      Prints the heap after each operation set

      This demonstrates how the heap reorganizes itself after each insert/remove.

    Concepts Demonstrated

      Heap / Priority Queue fundamentals

      Array-based binary heap indexing

      Parent: (i - 1) / 2

      Left child: 2i + 1

      Right child: 2i + 2

    Recursion

      Swapping and in-place reordering
  
      Basic error handling for empty/full heap cases
