/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisum23;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


/**
 *
 * @author Hunter
 */
public class MyPanel extends JPanel implements MouseListener,MouseMotionListener{

    private int startX, startY, endX, endY;
    private BufferedImage grid;
    private Graphics2D gc;
    private static MyPanel inst;
    private int shape;
    boolean freehand = false;
    private int strokeThickness;
    private Color currentColor;
    
            
            
    public static MyPanel getInstance() {
        if (inst == null) {
            inst = new MyPanel();
        }
        return inst;
    }

     private MyPanel() {
        startX = startY = endX = endY = 0;
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
        freehand = false;
        strokeThickness = 1;
        currentColor = Color.BLACK;
    }

    public void changeStrokeThickness(int thickness)
    {
        strokeThickness = thickness;
    }
    
    public void changeColortest(int intColor)
    {
        Color newcolor;
        if(intColor == 1)
        {
            newcolor = Color.BLACK;
            currentColor = newcolor;
        }else if(intColor == 2){
            newcolor = Color.BLUE;
            currentColor = newcolor;
        }else if(intColor == 3){
            newcolor = Color.CYAN;
            currentColor = newcolor;
        }else if(intColor == 4){
            newcolor = Color.DARK_GRAY;
            currentColor = newcolor;
        }else if(intColor == 5){
            newcolor = Color.GRAY;
            currentColor = newcolor;
        }else if(intColor == 6){
            newcolor = Color.GREEN;
            currentColor = newcolor;
        }else if(intColor == 7){
            newcolor = Color.LIGHT_GRAY;
            currentColor = newcolor;
        }else if(intColor == 8){
            newcolor = Color.MAGENTA;
            currentColor = newcolor;
        }else if(intColor == 9){
            newcolor = Color.ORANGE;
            currentColor = newcolor;
        }else if(intColor == 10){
            newcolor = Color.PINK;
            currentColor = newcolor;
        }else if(intColor == 11){
            newcolor = Color.RED;
            currentColor = newcolor;
        }else if(intColor == 12){
            newcolor = Color.WHITE;
            currentColor = newcolor;
        }else if(intColor == 13){
            newcolor = Color.YELLOW;
            currentColor = newcolor;
        }
    }
    public void changeColor(Color color)
    {
        currentColor = color;
    }
    public void paintComponent(Graphics g) {
       // super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = this.getWidth();
        int h = this.getHeight();
        if (grid == null) {
            grid = (BufferedImage) (this.createImage(w, h));
            gc = grid.createGraphics();
        }
        g2.drawImage(grid, null, 0, 0);
        g2.setStroke(new BasicStroke(strokeThickness));
        g2.setColor(currentColor);
        
       

    }

    public void changeShape(int temp) {
        shape = temp;
    }

    public void clearCanvas() {
        gc.clearRect(0, 0, getWidth(), getHeight());
        repaint();
    }

    public void freeHand(int x1, int y1, int x2, int y2)
    {
            
            gc.setColor(currentColor);
            BasicStroke stroke = new BasicStroke(strokeThickness);
            gc.setStroke(stroke);
            gc.drawLine(x1, y1, x2, y2);
            repaint();
            
    }
    public void drawLine() {
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        int width = Math.abs(startX - endX);
        int height = Math.abs(startY - endY);
        gc.setColor(currentColor);
        BasicStroke stroke = new BasicStroke(strokeThickness);
        gc.setStroke(stroke);   
        
        switch (shape) {
            case 1:
                break;
            case 2:
                gc.fillRect(x, y, width, height);
                break;
            case 3:
                gc.fillOval(x, y, width, height);
                break;
            case 4:
                gc.drawRect(x, y, width, height);
                break;
            case 5:
                gc.drawOval(x, y, width, height);
                System.out.println(freehand);
                break;
            case 6:
                System.out.println(freehand);
                break;
        }
        repaint();
    }

    public void mouseExited(MouseEvent evt) {
        System.out.println("Exited");
    }

    public void mouseEntered(MouseEvent evt) {
        System.out.println("Entered");
    }

    public void mousePressed(MouseEvent evt) {
        //System.out.println(evt);
        //System.out.println(evt.getX() + " " + evt.getY());
        if(freehand == false){
        startX = evt.getX();
        startY = evt.getY();
        }
    }

    public void mouseClicked(MouseEvent evt) {
        //System.out.println(evt);
        if (shape == 6) {
            freehand = true;
            System.out.println(freehand);
        }
        if (shape == 5) {
            freehand = false;
            System.out.println(freehand);
        }
        if (shape == 4) {
            freehand = false;
            System.out.println(freehand);
        }
        if (shape == 3) {
            freehand = false;
            System.out.println(freehand);
        }
        if (shape == 2) {
            freehand = false;
            System.out.println(freehand);
        }
    }

    public void mouseReleased(MouseEvent evt) {
        if(freehand == false){
        endX = evt.getX();
        endY = evt.getY();
        drawLine();
        }
    }
    
    public void mouseDragged(MouseEvent e) {
        if(freehand == true){
        int x = e.getX();
        int y = e.getY();
        freeHand(x, y, x, y);
        }
    }

    
    public void mouseMoved(MouseEvent e) {
    }
}