package cz.petrfaltus.filesfinder;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.border.Border;

public class Gui extends JFrame {
    private static final int GAP_INNER = 8;
    private static final int GAP_BORDER = 18;

    private JMenuItem menuItemExit;
    private JMenuItem menuItemAbout;

    private JTextField directoryTextField;
    private JTextField fileMaskTextField;

    private JButton browseButton;
    private JButton searchButton;

    private JTextArea resultTextArea;

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

    private void Body() {
        Dimension gapInner = new Dimension(GAP_INNER, GAP_INNER);

        // directory line
        JLabel directoryLabel = new JLabel("Starting directory: ");
        directoryTextField = new JTextField();
        browseButton = new JButton("Browse");

        Container directory = Box.createHorizontalBox();
        directory.add(directoryLabel);
        directory.add(directoryTextField);
        directory.add(Box.createRigidArea(gapInner));
        directory.add(browseButton);

        // file mask line
        JLabel fileMaskLabel = new JLabel("File mask: ");
        fileMaskTextField = new JTextField();

        Container fileMask = Box.createHorizontalBox();
        fileMask.add(fileMaskLabel);
        fileMask.add(fileMaskTextField);

        // search button line
        searchButton = new JButton("Search");

        Container search = Box.createHorizontalBox();
        search.add(Box.createHorizontalGlue());
        search.add(searchButton);

        // result text area
        resultTextArea = new JTextArea(90, 300);
        resultTextArea.setEditable(false);
        resultTextArea.setLineWrap(false);

        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);

        // final panel
        JPanel panel = new JPanel();

        Border panelBorder = BorderFactory.createEmptyBorder(GAP_BORDER, GAP_BORDER, GAP_BORDER, GAP_BORDER);
        panel.setBorder(panelBorder);

        BoxLayout panelLayout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(panelLayout);

        panel.add(directory);
        panel.add(Box.createRigidArea(gapInner));
        panel.add(fileMask);
        panel.add(Box.createRigidArea(gapInner));
        panel.add(Box.createRigidArea(gapInner));
        panel.add(search);
        panel.add(Box.createRigidArea(gapInner));
        panel.add(Box.createRigidArea(gapInner));
        panel.add(resultScrollPane);

        // final container
        Container container = getContentPane();
        container.add(panel);
    }

    public Gui(String title) {
        super(title);

        Menu();
        Body();
    }
}
