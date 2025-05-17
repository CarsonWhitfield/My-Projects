/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paintprogram;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel implements ActionListener {
    private static Menu inst;

    JMenuItem help = new JMenuItem("Help");
    JMenuBar menubar = new JMenuBar();

    public static Menu getInstance() {
        if (inst == null) {
            inst = new Menu();
        }
        return inst;
    }

    public Menu() {
        JMenu menu = new JMenu("Menu");
        menubar.add(menu);

        help.addActionListener(this);
        menu.add(help);

        add(menubar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == help) {
            HelpDialog helpDialog = new HelpDialog();
            helpDialog.setVisible(true);
        }
    }
}


