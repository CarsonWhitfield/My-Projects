/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bunnies;

/**
 *
 * @author Cwhit
 */
public class Bunny {
    
    private int weight;
    private String name;
    private static int count = 0;
    
    public Bunny(String n, int w)
    {
        weight = w;
        name = n;
        count++;
    }
    
    public static int getCount(){return count;}
    
    public int getWeight()
    {
        return weight;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setWeight(int w)
    {
        weight = w;
    }
}
