package cz.petrfaltus.filesfinder;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.lang.Thread;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javax.swing.border.Border;

public class GuiSearching extends JDialog {
    private static final int GAP_INNER = 8;
    private static final int GAP_BORDER = 18;

    private JButton cancelButton;

    private Search search = null;

    private class ButtonsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Object source = event.getSource();

            if (source == cancelButton) {
                if (search != null) {
                    search.cancel();
                }
            }
        }
    }

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

    private String cancelingGetTitle() {
        String title = "Canceling of the files searching";
        return title;
    }

    private void body() {
        Dimension gapInner = new Dimension(GAP_INNER, GAP_INNER);

        ButtonsListener buttonsListener = new ButtonsListener();

        // cancel button line
        cancelButton = new JButton("Cancel");
        cancelButton.setToolTipText(cancelingGetTitle());
        cancelButton.setMnemonic(KeyEvent.VK_C);
        cancelButton.setIcon(Icons.getResource("/ico/cancel.png"));
        cancelButton.addActionListener(buttonsListener);

        Container cancel = Box.createHorizontalBox();
        cancel.add(cancelButton);

        // final panel
        JPanel panel = new JPanel();

        Border panelBorder = BorderFactory.createEmptyBorder(GAP_BORDER, GAP_BORDER, GAP_BORDER, GAP_BORDER);
        panel.setBorder(panelBorder);

        BoxLayout panelLayout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(panelLayout);

        panel.add(Box.createRigidArea(gapInner));
        panel.add(cancel);

        // final container
        Container container = getContentPane();
        container.add(panel);
    }

    public GuiSearching(Frame parent, String title) {
        super(parent, title, true);

        // application icon
        ImageIcon imageIcon = Icons.getResource("/ico/filesfinder.png");
        Image image = imageIcon.getImage();
        this.setIconImage(image);

        body();
    }
}
