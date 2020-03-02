package cz.petrfaltus.filesfinder;

import java.awt.Dimension;

public class FilesFinder {
    public static void main(String[] args) {
        Dimension preferredSize = new Dimension(600, 400);

        Gui window = new Gui("Files finder");
        window.setLocationRelativeTo(null); // place to the middle of the screen
        window.setPreferredSize(preferredSize);
        window.pack();
        window.setDefaultCloseOperation(Gui.EXIT_ON_CLOSE);

        window.setVisible(true);
    }
}
