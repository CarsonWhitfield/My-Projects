/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paintprogram;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Cwhit
 */
public class CreatButtonIcon 
{
    BufferedImage ib[];
    Color col;
    private static CreatButtonIcon  inst;
    
    public static CreatButtonIcon getInstance() {
        if (inst == null) {
            inst = new CreatButtonIcon();
        }
        return inst;
    }
    
    
    public CreatButtonIcon()
    {
    
    }
    public void changecolor(int intColor)
    {
    Color newcolor;
        if(intColor == 1)
        {
            newcolor = Color.BLACK;
            col = newcolor;
        }else if(intColor == 2){
            newcolor = Color.BLUE;
            col = newcolor;
        }else if(intColor == 3){
            newcolor = Color.CYAN;
            col = newcolor;
        }else if(intColor == 4){
            newcolor = Color.DARK_GRAY;
            col = newcolor;
        }else if(intColor == 5){
            newcolor = Color.GRAY;
            col = newcolor;
        }else if(intColor == 6){
            newcolor = Color.GREEN;
            col = newcolor;
        }else if(intColor == 7){
            newcolor = Color.LIGHT_GRAY;
            col = newcolor;
        }else if(intColor == 8){
            newcolor = Color.MAGENTA;
            col = newcolor;
        }else if(intColor == 9){
            newcolor = Color.ORANGE;
            col = newcolor;
        }else if(intColor == 10){
            newcolor = Color.PINK;
            col = newcolor;
        }else if(intColor == 11){
            newcolor = Color.RED;
            col = newcolor;
        }else if(intColor == 12){
            newcolor = Color.WHITE;
            col = newcolor;
        }else if(intColor == 13){
            newcolor = Color.YELLOW;
            col = newcolor;
        }
    }
    public CreatButtonIcon(int a)
    {
        
        ib = new BufferedImage[a];
        
        for(int i = 0; i<a; i++)
        {
            ib[i] = new BufferedImage(30,30,2);
        }
        col = new Color(0,0,0);
    }
    
    public Image[] getIcons()
    {
        Graphics2D g2 = ib[0].createGraphics();
        g2.setColor(col);
        g2.fillRect(5, 5, 20, 20);
        
        g2 = ib[1].createGraphics();
        g2.setColor(col);
        g2.fillOval(5, 5, 20, 20);
        
        g2 = ib[2].createGraphics();
        g2.setColor(col);
        g2.drawRect(5, 5, 20, 20);
        
        g2 = ib[3].createGraphics();
        g2.setColor(col);
        g2.drawOval(5, 5, 20, 20);
        
        return ib;
    }
}
