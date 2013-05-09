/*
 * *****************************************************************************
 *    
 * Metaforensic version 1.1 - Análisis forense de metadatos en archivos
 * electrónicos Copyright (C) 2012-2013 TSU. Andrés de Jesús Hernández Martínez,
 * TSU. Idania Aquino Cruz, All Rights Reserved, https://github.com/andy737   
 * 
 * This file is part of Metaforensic.
 *
 * Metaforensic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Metaforensic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Metaforensic.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * E-mail: andy1818ster@gmail.com
 * 
 * *****************************************************************************
 */
package Process;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 * Valida tamaño de strings
 *
 * @author andy737-1
 * @version 1.1
 */
public class ValTam {

    /**
     * Valida textfield
     *
     * @param evt keypressed
     * @param txt texto introducido
     * @param tam limite
     */
    public static void val(java.awt.event.KeyEvent evt, JTextField txt, int tam) {
        String carac = txt.getText();
        if (carac.length() >= tam) {
            evt.consume();
        }

    }

    /**
     * Valida textpane
     *
     * @param evt keypressed
     * @param txt texto introducido
     * @param tam limite
     */
    public static void valExtP(java.awt.event.KeyEvent evt, JTextPane txt, int tam) {
        String carac = txt.getText();
        if (carac.length() >= tam) {
            evt.consume();
        }

    }

    /**
     * Valida textarea
     *
     * @param evt keypressed
     * @param txt texto introducido
     * @param tam limite
     */
    public static void valExtA(java.awt.event.KeyEvent evt, JTextArea txt, int tam) {
        String carac = txt.getText();
        if (carac.length() >= tam) {
            evt.consume();
        }

    }
}
