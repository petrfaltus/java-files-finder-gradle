package cz.petrfaltus.filesfinder;

import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Gui extends JFrame {
    private JMenuItem menuItemExit;
    private JMenuItem menuItemAbout;

    private class MenuItemsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Object source = event.getSource();

            if (source == menuItemExit) {
                System.exit(0);
            }

            if (source == menuItemAbout) {
                AboutApplication();
            }
        }
    }

    private String AboutApplicationGetTitle() {
        String title = "About the " + this.getTitle();
        return title;
    }

    private void AboutApplication() {
        String message = "Author: Petr Faltus © March 2020";
        message += System.lineSeparator();
        message += System.lineSeparator();

        message += "Java version: " + System.getProperty("java.specification.version");
        message += " (" + System.getProperty("java.version") + ")";
        message += System.lineSeparator();
        message += "Operating system: " + System.getProperty("os.name");
        message += System.lineSeparator();

        message += " ";

        JOptionPane.showMessageDialog(this, message, AboutApplicationGetTitle(), JOptionPane.INFORMATION_MESSAGE);
    }

    private void Menu() {
        MenuItemsListener menuItemsListener = new MenuItemsListener();

        // File menu items
        menuItemExit = new JMenuItem("Exit");
        menuItemExit.setToolTipText("Exit the application");
        menuItemExit.setMnemonic(KeyEvent.VK_E);
        menuItemExit.addActionListener(menuItemsListener);

        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        menuFile.add(menuItemExit);

        // horizontal menu glue
        Component horizontalGlue = Box.createHorizontalGlue();

        // Info menu items
        menuItemAbout = new JMenuItem("About");
        menuItemAbout.setToolTipText(AboutApplicationGetTitle());
        menuItemAbout.setMnemonic(KeyEvent.VK_A);
        menuItemAbout.addActionListener(menuItemsListener);

        JMenu menuInfo = new JMenu("Info");
        menuInfo.setMnemonic(KeyEvent.VK_I);
        menuInfo.add(menuItemAbout);

        // final menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuFile);
        menuBar.add(horizontalGlue);
        menuBar.add(menuInfo);

        this.setJMenuBar(menuBar);
    }

    public Gui(String title) {
        super(title);

        Menu();
    }
}
