/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paintprogram;

import javax.swing.*;

public class HelpDialog extends JDialog {
    public HelpDialog() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocation(200, 200);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setTitle("Help");

        JLabel label = new JLabel("# Welcom to the help page #");
        add(label);
        
    }
}


