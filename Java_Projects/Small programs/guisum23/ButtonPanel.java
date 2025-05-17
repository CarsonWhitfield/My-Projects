/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guisum23;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *
 * @author Cwhit
 */
public class ButtonPanel extends JPanel implements ActionListener,ChangeListener {
private static ButtonPanel inst;
private final int AMOUNT = 4;
private CreatButtonIcon buttonIcon;

    public static ButtonPanel getInstance() {
        if (inst == null) {
            inst = new ButtonPanel();
        }
        return inst;
    }
    
    public ButtonPanel() {
        //Font myFont = new Font("Serif", Font.BOLD, 15);
        buttonIcon = new CreatButtonIcon(AMOUNT);
        Image[] iconArray = buttonIcon.getIcons();
 
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton clear = new JButton("Clear");
        clear.setMaximumSize(new Dimension(Integer.MAX_VALUE, clear.getMinimumSize().height));
        JButton rect = new JButton("");
        rect.setIcon(new ImageIcon(iconArray[0]));
        rect.setActionCommand("Rectangle");
        rect.setMaximumSize(new Dimension(Integer.MAX_VALUE, rect.getMinimumSize().height));
        
        JButton oval = new JButton("");
        oval.setIcon(new ImageIcon(iconArray[1]));
        oval.setActionCommand("Oval");
        oval.setMaximumSize(new Dimension(Integer.MAX_VALUE, oval.getMinimumSize().height));
        
        JButton emprect = new JButton("");
        emprect.setIcon(new ImageIcon(iconArray[2]));
        emprect.setActionCommand("Empty Rectangle");
        emprect.setMaximumSize(new Dimension(Integer.MAX_VALUE, oval.getMinimumSize().height));
        
        
        JButton empoval = new JButton("");
        empoval.setIcon(new ImageIcon(iconArray[3]));
        empoval.setActionCommand("Empty Oval");
        empoval.setMaximumSize(new Dimension(Integer.MAX_VALUE, oval.getMinimumSize().height));
        
        JButton freeHand = new JButton("Free Hand");
        //freeHand.setFont(myFont);
         freeHand.setMaximumSize(new Dimension(Integer.MAX_VALUE, oval.getMinimumSize().height));
        JLabel label = new JLabel();
        label.setText("Pen Size");
        JSlider sizeSlider = new JSlider(0,10,0);
        sizeSlider.setPreferredSize(new Dimension(35, 1));
        sizeSlider.setName("slider");
        JLabel secondlabel = new JLabel();
        secondlabel.setText("Color");
        JSlider colorSlider = new JSlider(0,13,0);
        colorSlider.setPreferredSize(new Dimension(35, 1));
        colorSlider.setName("secondslider");
        
        
        
        clear.addActionListener(this);
        rect.addActionListener(this);
        oval.addActionListener(this);
        emprect.addActionListener(this);
        empoval.addActionListener(this);
        freeHand.addActionListener(this);
        sizeSlider.addChangeListener(this);
        colorSlider.addChangeListener(this);
        
        
        
        add(clear);
        add(rect);
        add(oval);
        add(emprect);
        add(empoval);
        add(freeHand);
        add(label);
        add(sizeSlider);
        add(secondlabel);
        add(colorSlider);
        
        
          
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println(evt);
        String command = evt.getActionCommand();
        if (command.equals("Clear")) {
            MyPanel.getInstance().clearCanvas();
        } else if (command.equals("Rectangle")) {
            MyPanel.getInstance().changeShape(2);
        } else if (command.equals("Oval")) {
            MyPanel.getInstance().changeShape(3);
        } else if (command.equals("Empty Rectangle")) {
            MyPanel.getInstance().changeShape(4);
        } else if (command.equals("Empty Oval")) {
            MyPanel.getInstance().changeShape(5);
        } else if (command.equals("Free Hand")) {
            MyPanel.getInstance().changeShape(6);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
         // Handle slider value change
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            int value = source.getValue();
            JSlider sizeSlider = (JSlider)source;
            String name = sizeSlider.getName();
            if ("slider".equals(name)) {
            MyPanel.getInstance().changeStrokeThickness(value);
        }else if("secondslider".equals(name)){
            MyPanel.getInstance().changeColortest(value);
        }
        }
    }
}
