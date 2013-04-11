/**
 * *****************************************************************************
 *
 * Metaforensic version 1.1 - Análisis forense de metadatos en archivos
 * electrónicos Copyright (C) 2012-2013 TSU. Andrés de Jesús Hernández Martínez,
 * All Rights Reserved, https://github.com/andy737
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * E-mail: andy1818ster@gmail.com
 *
 *******************************************************************************
 */
package Windows;

import GUI.New_;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JViewport;

/**
 * Clase encargada de limpiar formularios (textbox, combobox, checkbox,
 * radiobutton)
 *
 * @author andy737-1
 * @version 1.1
 */
public class Clean {

    private static Component[] arr;

    /**
     * Constructor, incializa array de componentes
     */
    public Clean() {
        arr = null;
    }

    /**
     * Metodo que recupera los controles de un contenedor JFrame
     *
     * @param c que recibe de la clase padre
     */
    public static void getAllComponents(New_ c) {

        if (c.getComponents() != null) {
            arr = c.getComponents();
        }
    }

    /**
     * Limpia JTextBox
     */
    public static void CleanTxt() {

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] instanceof JTextField) {
                ((JTextField) arr[i]).setText("");
            }
        }
    }

    /**
     * Limpia JTextPane
     */
    public static void CleanPaneTxt() {
        Component[] c;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] instanceof JScrollPane) {
                c = ((JScrollPane) arr[i]).getComponents();
                for (i = 0; i < c.length; i++) {
                    if (c[i] instanceof JViewport) {
                        c = ((JViewport) c[i]).getComponents();
                        for (i = 0; i < c.length; i++) {
                            if (c[i] instanceof JTextPane) {
                                ((JTextPane) c[i]).setText("");
                                i = 2;
                            }
                        }
                    }
                }

            }
        }
    }

    /**
     * Limpia JComboBox
     */
    public static void CleanCombo() {

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] instanceof JComboBox) {
                ((JComboBox) arr[i]).setSelectedIndex(-1);
            }
        }
    }

    /**
     * Limpia JCheckBox
     */
    public static void CleanCheck() {

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] instanceof JCheckBox) {
                ((JCheckBox) arr[i]).setSelected(false);
            }
        }
    }

    /**
     * Limpia JRadioButton
     */
    public static void CleanRadio() {

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] instanceof JRadioButton) {
                ((JRadioButton) arr[i]).setEnabled(false);
                ((JRadioButton) arr[i]).setSelected(false);
            }
        }
    }
}
