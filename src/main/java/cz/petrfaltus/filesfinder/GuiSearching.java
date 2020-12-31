package cz.petrfaltus.filesfinder;

import java.awt.Frame;
import java.awt.Image;

import java.lang.Thread;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

public class GuiSearching extends JDialog {
    private Search search;

    private void work() {
        search.run();

        Runnable runnableUI = new Runnable() {
            public void run() {
                setVisible(false);
                dispose();
            }
        };

        // add to the UI thread
        SwingUtilities.invokeLater(runnableUI);
    }

    public void startWork(Search search) {
        this.search = search;

        Thread workingThread = new Thread() {
            public void run(){
                work();
            }
        };

        // start in the working thread
        workingThread.start();
    }

    public GuiSearching(Frame parent, String title) {
        super(parent, title, true);

        // application icon
        ImageIcon imageIcon = Icons.getResource("/ico/filesfinder.png");
        Image image = imageIcon.getImage();
        this.setIconImage(image);
    }
}
