/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bunnies;

/**
 *
 * @author Cwhit
 */
public class Bunnies {

    
    public static void main(String[] args) {
        final int NUM = 15;
        
        Bunny b = new Bunny("bugs", 10);
        Bunny bees[] = new Bunny[NUM];
        
        
        for(int i = 0; i <NUM; i++)
        {
             bees[i] = new Bunny(new Integer(i).toString(),  i*5);
        }
        
         System.out.println(bees[4].getCount() + " " + bees[13].getCount() );
        
        for(int i = 0; i<NUM; i++)
        {
            System.out.println(bees[i].getName() + " : " + bees[i].getWeight());
            
        }
        System.out.println(Bunny.getCount());
    }
    
}
