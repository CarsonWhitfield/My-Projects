/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package selectpolitician;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
/**
 *
 * @author Cwhit
 */
public class riffRaff 
{
    PrintWriter writer; // write to file
    Scanner scanner = new Scanner(System.in);
    String inFile;
    int line;
    
    // Read N,K, and M values from input file
    public void readIn() throws UnsupportedEncodingException, FileNotFoundException
    {
        writer = new PrintWriter("LinkedListProgram.txt", "UTF-8");//Create output file
        
        System.out.println("Enter name of Input file (Example: input.txt):");
        inFile = scanner.nextLine();
        System.out.println("\n");
        try {
            System.out.println("Program 4\r\n---------\r\n" );
            //open the file that the user inputs
            FileReader fileName = new FileReader(inFile);
            Scanner fileRead = new Scanner(fileName);
            int sum = 1;
            int n, k, m;
            n = 1;
            k = 1;
            m = 1;
            while (sum != 0) {
            sum = 0;
            n = fileRead.nextInt(); // Read n
            k = fileRead.nextInt(); // Read k
            m = fileRead.nextInt(); // Read m
            sum = n + k + m; // sum n, k and m. if sum is 0, then it is end of file
            CircleLinkedList LL = new CircleLinkedList(n,writer); // Create an object of LinkedList
            if (sum != 0) { // if the sum is not 0 (its not the end of the file), call LinkedList method
                 System.out.println("N= " + n + " k= " + k + " m= " + m);
                 writer.write("N= " + n + " k= " + k + " m= " + m);
                 writer.write("\n");
                 System.out.println("\r\nOutput\r\n---------\r\n" );
                 writer.write("\r\nOutput\r\n---------\r\n");
                 LL.candidateSelection(n, k, m);
                 System.out.println("\n");
                 writer.write("\n");
            }else{
                writer.write("\n");
                writer.write("End of Project");
                LL.closeWriter();//Close file writer
            }
            }
            fileRead.close();//Close file reader
              System.out.println("End of Project");
        } catch (FileNotFoundException exception) {
            // error is thrown if file cannot be found.
            System.out.println("File Not Found!");
        }
    }

    
}
