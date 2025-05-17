/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment_6;

import java.text.DecimalFormat;

/**
 *
 * @author Cwhit
 */
public class Assignment_6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UserInputManagment uim = new UserInputManagment();
        TicketManagement Ticket = uim.CreateTicketFromUserInput();
        double price = Ticket.getPrice();
        System.out.printf("$%.2f\n",price);
        uim.closeScanner();
    }
    
}
