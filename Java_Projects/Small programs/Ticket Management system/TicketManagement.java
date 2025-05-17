/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment_6;

/**
 *
 * @author Cwhit
 */
public class TicketManagement {
    private Price price;

    public TicketManagement(Price price) {
        this.price = price;
    }

    public void setPriceStrategy(Price price) {
        this.price = price;
    } 

    public double getPrice() {
        return price.calculateThePrice();
    }
}
