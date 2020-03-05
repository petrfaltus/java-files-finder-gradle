package cz.petrfaltus.filesfinder;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.border.Border;

public class Gui extends JFrame {
    private static final int GAP_INNER = 8;
    private static final int GAP_BORDER = 18;

    private static final String STARTING_DIRECTORY_LABEL = "Starting directory: ";

    private JMenuItem menuItemBrowse;
    private JMenuItem menuItemExit;
    private JMenuItem menuItemAbout;

    private JLabel directoryLabelValue;
    private JTextField fileMaskTextField;

    private JButton browseButton;
    private JButton setDirectoryButton;
    private JButton searchButton;

    private JTextArea resultTextArea;

    private class MenuItemsButtonsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Object source = event.getSource();

            if (source == menuItemExit) {
                System.exit(0);
            }

            if (source == menuItemAbout) {
                AboutApplication();
            }

            if ((source == browseButton) || (source == menuItemBrowse)) {
                BrowseDirectories();
            }

            if (source == setDirectoryButton) {
                SettingDirectory();
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

    private String BrowseDirectoriesGetTitle() {
        String title = "Browse the starting directory";
        return title;
    }

    private void BrowseDirectories() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(BrowseDirectoriesGetTitle());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        String directoryName = directoryLabelValue.getText();
        File directory = new File(directoryName);
        fileChooser.setCurrentDirectory(directory);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            directory = fileChooser.getSelectedFile();
            directoryName = directory.getPath();
            directoryLabelValue.setText(directoryName);
        }
    }

    private String SettingDirectoryGetTitle() {
        String title = "Set manualy the starting directory";
        return title;
    }

    private void SettingDirectory() {
        String message = STARTING_DIRECTORY_LABEL;
        String title = SettingDirectoryGetTitle();
        String directoryName = directoryLabelValue.getText();
        directoryName = (String)JOptionPane.showInputDialog(this, message, title, JOptionPane.PLAIN_MESSAGE, null, null, directoryName);

        if (directoryName != null) {
            directoryLabelValue.setText(directoryName);
        }
    }

    private void Menu() {
        MenuItemsButtonsListener menuItemsListener = new MenuItemsButtonsListener();

        // File menu items
        menuItemBrowse = new JMenuItem("Browse directory");
        menuItemBrowse.setToolTipText(BrowseDirectoriesGetTitle());
        menuItemBrowse.setMnemonic(KeyEvent.VK_B);
        menuItemBrowse.addActionListener(menuItemsListener);

        menuItemExit = new JMenuItem("Exit");
        menuItemExit.setToolTipText("Exit the application");
        menuItemExit.setMnemonic(KeyEvent.VK_E);
        menuItemExit.addActionListener(menuItemsListener);

        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        menuFile.add(menuItemBrowse);
        menuFile.add(new JSeparator());
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

        MenuItemsButtonsListener buttonsListener = new MenuItemsButtonsListener();

        // directory line
        JLabel directoryLabel = new JLabel(STARTING_DIRECTORY_LABEL);
        directoryLabelValue = new JLabel("");

        browseButton = new JButton("Browse");
        browseButton.setToolTipText(BrowseDirectoriesGetTitle());
        browseButton.addActionListener(buttonsListener);

        setDirectoryButton = new JButton("Set");
        setDirectoryButton.setToolTipText(SettingDirectoryGetTitle());
        setDirectoryButton.addActionListener(buttonsListener);

        Container directory = Box.createHorizontalBox();
        directory.add(directoryLabel);
        directory.add(directoryLabelValue);
        directory.add(Box.createHorizontalGlue());
        directory.add(browseButton);
        directory.add(Box.createRigidArea(gapInner));
        directory.add(setDirectoryButton);

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
