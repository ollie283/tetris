package utilities;

// code copied from Simon Lucas
// code copied by Udo Kruschwitz
// further commented by Oliver Holland

import javax.swing.*;
import java.awt.*;

/**
 * Utility frame extension that handles trival method calls.
 */
public class JEasyFrame extends JFrame {
    public Component comp;

    /**
     * Construct frame and call methods with arguments that are most likely to be desired.
     */
    public JEasyFrame(String title, Component comp) {
        super(title);
        this.comp = comp;
        getContentPane().add(BorderLayout.CENTER, comp);
        pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }

}
