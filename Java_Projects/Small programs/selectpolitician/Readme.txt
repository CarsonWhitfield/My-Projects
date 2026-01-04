🗳️ Circular Doubly Linked List Candidate Selection (Java)

This project implements a circular doubly linked list to simulate a candidate elimination/selection process (similar to a Josephus-style problem). Candidates are numbered 1..N and removed from the list based on two counters: one moving forward by k steps and one moving backward by m steps. Each elimination round prints the selected candidate(s) to the console and writes the same output to a file.

    What It Does

      Builds a circular doubly linked list of N candidates (politicians)

      Repeatedly selects:

      The k-th candidate moving forward (next)

      The m-th candidate moving backward (prev)

      Eliminates one candidate if both selectors land on the same node, otherwise eliminates two

      Continues until all candidates are removed

      Writes results to LinkedListProgram.txt

    Main Components

      Node

      Stores a candidate ID (politician)

      Tracks next and previous links for circular traversal

      CircleLinkedList

      Creates and maintains the circular doubly linked list

    Supports:

      addItem() to append candidates

      deleteItem() to remove a candidate and relink neighbors

      candidateSelection(n, k, m) to run the elimination logic

      riffRaff / SelectPolitician

      Reads N, K, and M values from a user-provided input file

      Runs the selection process for each line until a terminating 0 0 0

      Logs output to both the console and an output text file

  Key Concepts Demonstrated

    Custom data structures (circular doubly linked list)

    Pointer manipulation (next/prev updates during deletion)

    Algorithmic simulation of elimination/selection

  File I/O

    Reads test cases from a file

    Writes formatted results to an output report
