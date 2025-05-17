/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paintprogram;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
/**
 *
 * @author Cwhit
 */
public class MyFrame extends JFrame {
   private static MyFrame inst;
   
    public static MyFrame getInstance() {
        if (inst == null) {
            inst = new MyFrame();
        }
        return inst;
    }

    private MyFrame() {
        super("Paint program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(200, 200);
        setSize(600, 400);
        
        MyPanel paintPanel = MyPanel.getInstance();
        getContentPane().add(paintPanel, BorderLayout.CENTER);

        ButtonPanel buttonPanel = ButtonPanel.getInstance();
        getContentPane().add(buttonPanel, BorderLayout.WEST);
        
        Menu menu = Menu.getInstance();
        getContentPane().add(menu, BorderLayout.EAST);
        
        setVisible(true);
    }
}

