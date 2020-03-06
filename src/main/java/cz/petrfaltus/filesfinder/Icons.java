package cz.petrfaltus.filesfinder;

import java.lang.Class;

import java.net.URL;

import javax.swing.ImageIcon;

public class Icons {
    public static ImageIcon getResource(String urlName) {
        Class thisClass = Icons.class;

        URL url = thisClass.getResource(urlName);
        ImageIcon imageIcon = new ImageIcon(url);

        return imageIcon;
    }
}
