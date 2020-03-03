package cz.petrfaltus.filesfinder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Gui extends JFrame {
    private JMenuItem menuItemAbout;

    private class MenuItemsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == menuItemAbout) {
                AboutApplication();
            }
        }
    }

    private void AboutApplication() {
        String title = "About the " + this.getTitle();

        String message = "Author: Petr Faltus © March 2020";
        message += System.lineSeparator();
        message += System.lineSeparator();

        message += "Java version: " + System.getProperty("java.specification.version");
        message += " (" + System.getProperty("java.version") + ")";
        message += System.lineSeparator();
        message += "Operating system: " + System.getProperty("os.name");
        message += System.lineSeparator();

        message += " ";

        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void Menu() {
        MenuItemsListener menuItemsListener = new MenuItemsListener();

        menuItemAbout = new JMenuItem("About");
        menuItemAbout.addActionListener(menuItemsListener);

        JMenu menuInfo = new JMenu("Info"); 
        menuInfo.add(menuItemAbout);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuInfo);

        this.setJMenuBar(menuBar);
    }

    public Gui(String title) {
        super(title);

        Menu();
    }
}
