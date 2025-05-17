/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_6;

/**
 *
 * @author Cwhit
 */
public class MilitaryTicket implements Price{
    private boolean Adult;
    private boolean Senior;
    
    public MilitaryTicket(boolean Adult, boolean Senior)
    {
        this.Adult = Adult;
        this.Senior = Senior;
    }
    @Override
    public double calculateThePrice() {
        
        double price = 0.0; 
        if (Senior) {
            price = 15.0; // Senior price
            price *= 0.9; // 10% off for military
        }
        if (Adult) {
            price = 20.0;// Adult
            price *= 0.9; // 10% off for military
        }
        return price;
    }
}
