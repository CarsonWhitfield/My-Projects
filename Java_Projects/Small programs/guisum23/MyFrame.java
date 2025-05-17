/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisum23;

/**
 *
 * @author Cwhit
 */
import java.awt.BorderLayout;
import javax.swing.JFrame;

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
        setSize(100, 600);

        MyPanel paintPanel = MyPanel.getInstance();
        getContentPane().add(paintPanel, BorderLayout.CENTER);

        ButtonPanel buttonPanel = ButtonPanel.getInstance();
        getContentPane().add(buttonPanel, BorderLayout.WEST);
        
        
        setVisible(true);
    }
}
