/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_6;

import java.util.Scanner;

/**
 *
 * @author Cwhit
 */
public class UserInputManagment {
    private Scanner scanner;

    public UserInputManagment() {
        this.scanner = new Scanner(System.in);
    }

    public int promptForAge() {
        System.out.print("Enter your age: ");
        return scanner.nextInt();
    }

    public boolean CheckForAARPMembership(int age) {
        if(age >= 65){
        System.out.print("Do you have AARP membership? (yes/no): ");
        String input = scanner.next().toLowerCase();
        return input.equals("yes");
        }
        return false;
    }

    public boolean CheckForMilitaryStatus(int age,  boolean AARPMembership) {
        if(age >= 17 && !AARPMembership){
        System.out.print("Are you in the military? (yes/no): ");
        String input = scanner.next().toLowerCase();
        return input.equals("yes");
        }
        return false;
    }

    public TicketManagement CreateTicketFromUserInput() {
        int age = promptForAge();
        boolean AARPMembership = CheckForAARPMembership(age);
        boolean Military = CheckForMilitaryStatus(age,AARPMembership);
        
        if (age < 18) {
            System.out.print("There is no discount for this ticket\n");
            System.out.print("Your child's ticket will cost: ");
            return new TicketManagement(new ChildTicket());
        } else if (age > 18 && age < 65){
            if(Military){
                boolean Adult = true;
                boolean Senior = true;
                System.out.print("This ticket has 10% discount\n");
                System.out.print("Your Adult ticket will cost: ");
                return new TicketManagement(new MilitaryTicket(Adult,Senior));
            }else{
                System.out.print("There is no discount for this ticket\n");
                System.out.print("Your Adult ticket will cost: ");
                return new TicketManagement(new AdultTicket());
            }
        }else{
            if(age >= 65 && Military){
                boolean Adult = false;
                boolean Senior = true;
                System.out.print("This ticket has 10% discount\n");
                System.out.print("Your Senior ticket will cost: ");
                return new TicketManagement(new MilitaryTicket(Adult,Senior));
            }else if(age >= 65 && AARPMembership){
                System.out.print("This ticket has 20% discount\n");
                System.out.print("Your Adult ticket will cost: ");
                return new TicketManagement(new AARPTicket());
            }else{
                System.out.print("There is no discount for this ticket\n");
                System.out.print("Your Adult ticket will cost: ");
                return new TicketManagement(new SeniorsTicket());
            }
        }
    }

    public void closeScanner() {
        scanner.close();
    }
}
