/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author andy737-1
 */
public class ValTam {

    public static void val(java.awt.event.KeyEvent evt, JTextField txt, int tam) {
        String carac = txt.getText();
        if (carac.length() >= tam) {
            evt.consume();
        }

    }

    public static void valExtP(java.awt.event.KeyEvent evt, JTextPane txt, int tam) {
        String carac = txt.getText();
        if (carac.length() >= tam) {
            evt.consume();
        }

    }

    public static void valExtA(java.awt.event.KeyEvent evt, JTextArea txt, int tam) {
        String carac = txt.getText();
        if (carac.length() >= tam) {
            evt.consume();
        }

    }
}
