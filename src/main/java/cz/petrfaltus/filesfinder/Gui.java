package cz.petrfaltus.filesfinder;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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

import javax.swing.border.Border;

public class Gui extends JFrame {
    private static final String APPLICATION_DATE = "March 2020 - January 2021";
    private static final String APPLICATION_VERSION = "2.0";

    private static final int GAP_INNER = 8;
    private static final int GAP_BORDER = 18;

    private static final String STARTING_DIRECTORY_LABEL = "Starting directory: ";
    private static final String FILE_MASK_LABEL = "File mask: ";

    private static final String DEFAULT_FILE_MASK = Const.EMPTY_STRING;

    private JMenuItem menuItemExit;
    private JMenuItem menuItemBrowse;
    private JMenuItem menuItemSetDirectory;
    private JMenuItem menuItemDefault;
    private JMenuItem menuItemSetFileMask;
    private JMenuItem menuItemSearch;
    private JMenuItem menuItemCopyToClipboard;
    private JMenuItem menuItemAbout;

    private JLabel directoryLabelValue;
    private JLabel fileMaskLabelValue;

    private JButton browseButton;
    private JButton setDirectoryButton;
    private JButton defaultButton;
    private JButton setFileMaskButton;
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
                aboutApplication();
            }

            if ((source == browseButton) || (source == menuItemBrowse)) {
                browseDirectories();
            }

            if ((source == setDirectoryButton) || (source == menuItemSetDirectory)) {
                settingDirectory();
            }

            if ((source == defaultButton) || (source == menuItemDefault)) {
                defaultFileMask();
            }

            if ((source == setFileMaskButton) || (source == menuItemSetFileMask)) {
                settingFileMask();
            }

            if ((source == searchButton) || (source == menuItemSearch)) {
                searching();
            }

            if (source == menuItemCopyToClipboard) {
                copyingToClipboard();
            }
        }
    }

    private class TextsMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent event) {
            Object source = event.getSource();

            if (source == resultTextArea) {
                copyingToClipboard();
            }
        }

        @Override
        public void mousePressed(MouseEvent event) {
        }

        @Override
        public void mouseReleased(MouseEvent event) {
        }

        @Override
        public void mouseEntered(MouseEvent event) {
        }

        @Override
        public void mouseExited(MouseEvent event) {
        }
    }

    private String aboutApplicationGetTitle() {
        String title = "About the " + this.getTitle();
        return title;
    }

    private void aboutApplication() {
        String message = "Author: Petr Faltus © " + APPLICATION_DATE;
        message += System.lineSeparator();
        message += "Version: " + APPLICATION_VERSION;
        message += System.lineSeparator();
        message += System.lineSeparator();

        message += "Java version: " + System.getProperty("java.specification.version");
        message += " (" + System.getProperty("java.version") + ")";
        message += System.lineSeparator();
        message += "Operating system: " + System.getProperty("os.name");
        message += System.lineSeparator();

        message += " ";

        JOptionPane.showMessageDialog(this, message, aboutApplicationGetTitle(), JOptionPane.INFORMATION_MESSAGE);
    }

    private String browseDirectoriesGetTitle() {
        String title = "Browsing of the starting directory";
        return title;
    }

    private void browseDirectories() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(browseDirectoriesGetTitle());
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

    private String settingDirectoryGetTitle() {
        String title = "Manual setting of the starting directory";
        return title;
    }

    private void settingDirectory() {
        String message = STARTING_DIRECTORY_LABEL;
        String title = settingDirectoryGetTitle();
        String directoryName = directoryLabelValue.getText();
        directoryName = (String)JOptionPane.showInputDialog(this, message, title, JOptionPane.PLAIN_MESSAGE, null, null, directoryName);

        if (directoryName != null) {
            directoryLabelValue.setText(directoryName);
        }
    }

    private String defaultFileMaskGetTitle() {
        String title = "Default file mask setting";
        return title;
    }

    private void defaultFileMask() {
        String question = "Really set the default file mask '" + DEFAULT_FILE_MASK + "' ?";
        String title = defaultFileMaskGetTitle();
        int n = JOptionPane.showConfirmDialog(this, question, title, JOptionPane.YES_NO_OPTION);

        if (n == 0) {
            fileMaskLabelValue.setText(DEFAULT_FILE_MASK);
        }
    }

    private String settingFileMaskGetTitle() {
        String title = "Manual setting of the file mask";
        return title;
    }

    private void settingFileMask() {
        String message = FILE_MASK_LABEL;
        String title = settingFileMaskGetTitle();
        String fileMaskName = fileMaskLabelValue.getText();
        fileMaskName = (String)JOptionPane.showInputDialog(this, message, title, JOptionPane.PLAIN_MESSAGE, null, null, fileMaskName);

        if (fileMaskName != null) {
            fileMaskLabelValue.setText(fileMaskName);
        }
    }

    private String searchingGetTitle() {
        String title = "Files searching";
        return title;
    }

    private Point searchingWindowGetLeftUpperCorner() {
        Point leftUpperCorner = getLocation();

        int dx = (int)((getWidth() - Const.APP_WINDOW_SEARCH_WIDTH) / 2);
        int dy = (int)((getHeight() - Const.APP_WINDOW_SEARCH_HEIGHT) / 2);
        leftUpperCorner.translate(dx, dy);

        return leftUpperCorner;
    }

    private void searchingWindow(Search search) {
        Point leftUpperCorner = searchingWindowGetLeftUpperCorner();
        Dimension preferredSize = new Dimension(Const.APP_WINDOW_SEARCH_WIDTH, Const.APP_WINDOW_SEARCH_HEIGHT);

        GuiSearching window = new GuiSearching(this, searchingGetTitle());
        window.setLocation(leftUpperCorner);
        window.setPreferredSize(preferredSize);
        window.pack();
        window.setDefaultCloseOperation(GuiSearching.DO_NOTHING_ON_CLOSE);

        window.startWork(search);
        window.setVisible(true);
    }

    private void searching() {
        String question = "Really start the searching ?";
        String title = searchingGetTitle();
        int n = JOptionPane.showConfirmDialog(this, question, title, JOptionPane.YES_NO_OPTION);

        if (n != 0) {
            return;
        }

        String directoryName = directoryLabelValue.getText().trim();
        if (directoryName.equals(Const.EMPTY_STRING)) {
            String message = "The starting directory has not been set";
            JOptionPane.showMessageDialog(this, message, searchingGetTitle(), JOptionPane.ERROR_MESSAGE);
            return;
        }

        File directory = new File(directoryName);
        if (!directory.isDirectory()) {
            String message = "The directory '" + directoryName + "' does not exist";
            JOptionPane.showMessageDialog(this, message, searchingGetTitle(), JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fileMask = fileMaskLabelValue.getText();

        Search search = new Search(directory, fileMask);
        searchingWindow(search);
        String result = search.getResult();
        if (result == null) {
            String message = search.getError();
            JOptionPane.showMessageDialog(this, message, searchingGetTitle(), JOptionPane.ERROR_MESSAGE);
            return;
        }

        resultTextArea.setText(result);
        resultTextArea.setCaretPosition(0);

        int count = search.getCount();
        String message = "Number of found files: " + count;
        JOptionPane.showMessageDialog(this, message, searchingGetTitle(), JOptionPane.INFORMATION_MESSAGE);
    }

    private String copyingToClipboardGetTitle() {
        String title = "Copying of the result";
        return title;
    }

    private void copyingToClipboard() {
        String question = "Really copy the result to the clipboard ?";
        String title = copyingToClipboardGetTitle();
        int n = JOptionPane.showConfirmDialog(this, question, title, JOptionPane.YES_NO_OPTION);

        if (n != 0) {
            return;
        }

        String resultString = resultTextArea.getText();
        if (resultString.equals(Const.EMPTY_STRING)) {
            String message = "There is no result yet";
            JOptionPane.showMessageDialog(this, message, copyingToClipboardGetTitle(), JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringSelection stringSelection = new StringSelection(resultString);

        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = defaultToolkit.getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        String message = "Result copied to the clipboard";
        JOptionPane.showMessageDialog(this, message, copyingToClipboardGetTitle(), JOptionPane.INFORMATION_MESSAGE);
    }

    private void menu() {
        MenuItemsButtonsListener menuItemsListener = new MenuItemsButtonsListener();

        // File menu items
        menuItemExit = new JMenuItem("Exit");
        menuItemExit.setToolTipText("Exit the application");
        menuItemExit.setMnemonic(KeyEvent.VK_X);
        menuItemExit.setIcon(Icons.getResource("/ico/exit.png"));
        menuItemExit.addActionListener(menuItemsListener);

        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        menuFile.setIcon(Icons.getResource("/ico/file.png"));
        menuFile.add(menuItemExit);

        // Run menu items
        menuItemBrowse = new JMenuItem("Browse directory");
        menuItemBrowse.setToolTipText(browseDirectoriesGetTitle());
        menuItemBrowse.setMnemonic(KeyEvent.VK_B);
        menuItemBrowse.setIcon(Icons.getResource("/ico/browse.png"));
        menuItemBrowse.addActionListener(menuItemsListener);

        menuItemSetDirectory = new JMenuItem("Set directory");
        menuItemSetDirectory.setToolTipText(settingDirectoryGetTitle());
        menuItemSetDirectory.setMnemonic(KeyEvent.VK_S);
        menuItemSetDirectory.setIcon(Icons.getResource("/ico/set.png"));
        menuItemSetDirectory.addActionListener(menuItemsListener);

        menuItemDefault = new JMenuItem("Default file mask");
        menuItemDefault.setToolTipText(defaultFileMaskGetTitle());
        menuItemDefault.setMnemonic(KeyEvent.VK_D);
        menuItemDefault.setIcon(Icons.getResource("/ico/default.png"));
        menuItemDefault.addActionListener(menuItemsListener);

        menuItemSetFileMask = new JMenuItem("Set file mask");
        menuItemSetFileMask.setToolTipText(settingFileMaskGetTitle());
        menuItemSetFileMask.setMnemonic(KeyEvent.VK_E);
        menuItemSetFileMask.setIcon(Icons.getResource("/ico/set.png"));
        menuItemSetFileMask.addActionListener(menuItemsListener);

        menuItemSearch = new JMenuItem("Search");
        menuItemSearch.setToolTipText(searchingGetTitle());
        menuItemSearch.setMnemonic(KeyEvent.VK_A);
        menuItemSearch.setIcon(Icons.getResource("/ico/search.png"));
        menuItemSearch.addActionListener(menuItemsListener);

        menuItemCopyToClipboard = new JMenuItem("Copy to clipboard");
        menuItemCopyToClipboard.setToolTipText(copyingToClipboardGetTitle());
        menuItemCopyToClipboard.setMnemonic(KeyEvent.VK_C);
        menuItemCopyToClipboard.setIcon(Icons.getResource("/ico/copy.png"));
        menuItemCopyToClipboard.addActionListener(menuItemsListener);

        JMenu menuRun = new JMenu("Run");
        menuRun.setMnemonic(KeyEvent.VK_R);
        menuRun.setIcon(Icons.getResource("/ico/run.png"));
        menuRun.add(menuItemBrowse);
        menuRun.add(menuItemSetDirectory);
        menuRun.add(new JSeparator());
        menuRun.add(menuItemDefault);
        menuRun.add(menuItemSetFileMask);
        menuRun.add(new JSeparator());
        menuRun.add(menuItemSearch);
        menuRun.add(new JSeparator());
        menuRun.add(menuItemCopyToClipboard);

        // horizontal menu glue
        Component horizontalGlue = Box.createHorizontalGlue();

        // Info menu items
        menuItemAbout = new JMenuItem("About");
        menuItemAbout.setToolTipText(aboutApplicationGetTitle());
        menuItemAbout.setMnemonic(KeyEvent.VK_A);
        menuItemAbout.setIcon(Icons.getResource("/ico/about.png"));
        menuItemAbout.addActionListener(menuItemsListener);

        JMenu menuInfo = new JMenu("Info");
        menuInfo.setMnemonic(KeyEvent.VK_I);
        menuInfo.setIcon(Icons.getResource("/ico/info.png"));
        menuInfo.add(menuItemAbout);

        // final menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuFile);
        menuBar.add(menuRun);
        menuBar.add(horizontalGlue);
        menuBar.add(menuInfo);

        this.setJMenuBar(menuBar);
    }

    private void body() {
        Dimension gapInner = new Dimension(GAP_INNER, GAP_INNER);

        MenuItemsButtonsListener buttonsListener = new MenuItemsButtonsListener();
        TextsMouseListener textsMouseListener = new TextsMouseListener();

        // directory line
        JLabel directoryLabel = new JLabel(STARTING_DIRECTORY_LABEL);
        directoryLabelValue = new JLabel(Const.EMPTY_STRING);

        browseButton = new JButton("Browse");
        browseButton.setToolTipText(browseDirectoriesGetTitle());
        browseButton.setMnemonic(KeyEvent.VK_B);
        browseButton.setIcon(Icons.getResource("/ico/browse.png"));
        browseButton.addActionListener(buttonsListener);

        setDirectoryButton = new JButton("Set");
        setDirectoryButton.setToolTipText(settingDirectoryGetTitle());
        setDirectoryButton.setMnemonic(KeyEvent.VK_S);
        setDirectoryButton.setIcon(Icons.getResource("/ico/set.png"));
        setDirectoryButton.addActionListener(buttonsListener);

        Container directory = Box.createHorizontalBox();
        directory.add(directoryLabel);
        directory.add(directoryLabelValue);
        directory.add(Box.createHorizontalGlue());
        directory.add(browseButton);
        directory.add(Box.createRigidArea(gapInner));
        directory.add(setDirectoryButton);

        // file mask line
        JLabel fileMaskLabel = new JLabel(FILE_MASK_LABEL);
        fileMaskLabelValue = new JLabel(DEFAULT_FILE_MASK);

        defaultButton = new JButton("Default");
        defaultButton.setToolTipText(defaultFileMaskGetTitle());
        defaultButton.setMnemonic(KeyEvent.VK_D);
        defaultButton.setIcon(Icons.getResource("/ico/default.png"));
        defaultButton.addActionListener(buttonsListener);

        setFileMaskButton = new JButton("Set");
        setFileMaskButton.setToolTipText(settingFileMaskGetTitle());
        setFileMaskButton.setMnemonic(KeyEvent.VK_E);
        setFileMaskButton.setIcon(Icons.getResource("/ico/set.png"));
        setFileMaskButton.addActionListener(buttonsListener);

        Container fileMask = Box.createHorizontalBox();
        fileMask.add(fileMaskLabel);
        fileMask.add(fileMaskLabelValue);
        fileMask.add(Box.createHorizontalGlue());
        fileMask.add(defaultButton);
        fileMask.add(Box.createRigidArea(gapInner));
        fileMask.add(setFileMaskButton);

        // search button line
        searchButton = new JButton("Search");
        searchButton.setToolTipText(searchingGetTitle());
        searchButton.setMnemonic(KeyEvent.VK_A);
        searchButton.setIcon(Icons.getResource("/ico/search.png"));
        searchButton.addActionListener(buttonsListener);

        Container search = Box.createHorizontalBox();
        search.add(Box.createHorizontalGlue());
        search.add(searchButton);

        // result text area
        resultTextArea = new JTextArea(90, 300);
        resultTextArea.setEditable(false);
        resultTextArea.setLineWrap(false);
        resultTextArea.addMouseListener(textsMouseListener);

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

        // application icon
        ImageIcon imageIcon = Icons.getResource("/ico/filesfinder.png");
        Image image = imageIcon.getImage();
        this.setIconImage(image);

        menu();
        body();
    }
}
