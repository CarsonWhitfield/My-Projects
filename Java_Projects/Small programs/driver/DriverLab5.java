/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package driver.lab.pkg5;

import java.util.Scanner;

/**
 *
 * @author Cwhit
 */
public class DriverLab5 {
    // Displays menu
    static void menu()

    {

        System.out.println(" What would you like to do?" +

        "\n Press 1 to add an item to the back of the list " +

        "\n Press 2 to print the list" +

        "\n Press 3 to delete an item" +

        "\n Press 4 to clear the whole list" +

        "\n Press -1 to exit");

        System.out.println("\n Enter your choice: ");

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        //Scanner class 

        Scanner sc = new Scanner(System.in);

        //Creating object of class linkedList

        linkedList list = new linkedList();

        //Loops till user inputs -1 is entered

         do
         {
            //Displays menu
             menu();
            //Accepts user choice
            int userchoice = sc.nextInt();
            //Switch case begins
            switch(userchoice)
         {
                case 1:
                    System.out.print("\n Enter a name to insert: ");
                    list.insertAtEnd(sc.next());
                    break;
                    case 2:
                    list.print();
                    break;
                case 3:
                    System.out.print("\n Enter the position to delete: ");
                    list.deleteAtPos(sc.nextInt());
                    break;
                case 4:
                    list.deleteAll();
                    break;
                case -1:
                    System.exit(0);
                    default:
                    System.out.println("Invalid choice.");
          }

        }while(true);
    }
}
    

