/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package driver.lab.pkg5;

/**
 *
 * @author Cwhit
 */
public class linkedList 
{
    //Reference object to point to starting node

    protected Node start;

    //Reference object to point to last node

    protected Node end ;

    //To store the size of the list

    public int size;

    //Default constructor to initialize

    public linkedList()

    {

        //Start and last object is initialized to null

        start = null;

        end = null;

        //Size is initialized to zero

        size = 0;

    }

       //Method to check if list is empty

    public boolean isEmpty()

    {

        //Return true if the list is empty

        return start == null;

    }

        //Method to insert a node at the end

    public void insertAtEnd(String name)

    {

        //Creates a new node

        Node newNode = new Node(name, null);

        //Increases the size by one

        size++ ;

        //Checks if start is null

        if(start == null)

        {

            //Start is pointing to new node

            start = newNode;

            end = start;

        }else

        {

            end.setNext(newNode);

            end = newNode;

        }

    }

    //Method to delete a node at position

    public void deleteAtPos(int pos)

    {

        //Checks if the position is equal to one

        if (pos == 1)
        {
            start = start.getNext();
            //reduce size by one
            size--;
            return ;
        }

        //Checks if the position is equal to size for last node

        if (pos == size)
        {
            //Creates a reference to refer to start node
            Node startNode = start;
            //Creates a temporary reference to refer to start node
            Node tempNode = start;
            //Loop till end of the list
            while (startNode != end)
            {
                //Assigns start node reference to temporary node
                tempNode = startNode;
                //Points to next node
                startNode = startNode.getNext();
            }
            //Temporary nod is assigned to end node
            end = tempNode;
            //End points to null
            end.setNext(null);
            //Reduce size by one
            size --;
            return;
        }
   
        //Creates a reference to start node
        Node posStart = start;
        //Decrease the position by one because it starts from zero
        pos = pos - 1 ;
        //Loop till end of list
        for (int counter = 1; counter < size - 1; counter++)
        {
            //Checks if the counter value is equal to position specified
            if (counter == pos)
            {

                //Creates a temporary reference refers to next node

                Node tmpNode = posStart.getNext();

                //Temporary node refers to next node

                tmpNode = tmpNode.getNext();

                posStart.setNext(tmpNode);

                break;

            }

            posStart = posStart.getNext();

        }
        //Decrease the size by one
        size-- ;

    }

    //Method to display elements of the list

    public void print()

    {

        System.out.print("\n Linked List Contents ");

        //Checks if the size is zero
        if (size == 0)
        {
            //Displays the error message empty
            System.out.print("ERROR: Empty list\n");
            System.out.print("\n");
            return;
        }else if (start.getNext() == null)
        {
            //Displays the node value
            System.out.println(start.getName());
            return;
        }
            //Creates a temporary reference to refer to start node
            Node tempNode = start;
            //Display the value of the start node
            System.out.print(start.getName()+ ", ");
            //Points to the next node
            tempNode = start.getNext();
            //Loops till not null
            while (tempNode.getNext() != null)
            {
                //Display the value of the current node
                System.out.print(tempNode.getName()+ ",");
                tempNode = tempNode.getNext();
            }
            System.out.print(tempNode.getName()+ "\n");
            System.out.print("\n");
        }

        //Method to delete all nodes
        void deleteAll()
        {
            //Start is initialized to null
            start = null;
            //Set the size to zero
            size = 0;
        }

}

