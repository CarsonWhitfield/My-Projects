/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guisum23;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Cwhit
 */
public class CreatButtonIcon {
    BufferedImage ib[];
    Color col;
    CreatButtonIcon(int a)
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
        g2.drawRect(5, 5, 20, 20);
        
        return ib;
    }
}
