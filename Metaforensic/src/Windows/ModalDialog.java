/*
 * *****************************************************************************
 *    
 * Metaforensic version 1.0 - Análisis forense de metadatos en archivos
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
package Windows;

import GUI.Delete;
import GUI.New_;
import GUI.Open;
import GUI.Report;
import GUI.Select;
import javax.swing.JOptionPane;

/**
 * Clase encargada de mostrar dialogos modales
 *
 * @author andy737-1
 * @version 1.0
 */
public class ModalDialog {

    private int seleccion;
    private String dialogo;
    private String titulo;
    private New_ j;
    private Open k;
    private Delete h;
    private Select i;
    private Report m;

    /**
     * Constructor que inicia variables
     */
    public ModalDialog() {
        seleccion = -1;
        dialogo = "";
        titulo = "";
        j = null;
        k = null;
        h = null;
        i = null;
    }

    /**
     * Muestra dialogo de confirmación
     */
    public void Dialog() {

        seleccion = JOptionPane.showOptionDialog(j,
                dialogo, titulo, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Aceptar", "Cancelar"}, "Cancelar");
    }

    /**
     * Muestra dialogo de advertencia
     */
    public void DialogAd() {

        seleccion = JOptionPane.showOptionDialog(j,
                dialogo, titulo, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Aceptar", "Cancelar"}, "Cancelar");
    }

    /**
     * Muestra dialogo de confirmación
     */
    public void DialogCon() {

        seleccion = JOptionPane.showOptionDialog(j,
                dialogo, titulo, JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");
    }

    /**
     *
     * @return hilo que maneja al joptionpane para evitar errores de ejecucion
     * visual
     */
    public Thread DialogErr() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showOptionDialog(j,
                        dialogo, titulo, JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");
            }
        });
        return t;
    }

    /**
     * Metodo que muestra un joptionpane sin uso de hilos, utilizado errores en
     * para validaciones especificas
     */
    public void DialogErrFix() {

        JOptionPane.showOptionDialog(j,
                dialogo, titulo, JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, new Object[]{"Aceptar"}, "Aceptar");

    }

    /**
     *
     * @param dialogo de la ventana modal
     */
    public void setDialogo(String dialogo) {
        this.dialogo = dialogo;
    }

    /**
     *
     * @param titulo de la ventana modal
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     *
     * @param j frame padre
     */
    public void setFrame(New_ j) {
        this.j = j;
    }

    /**
     *
     * @return resultado de la seleccion (0-aceptar,1-cancelar)
     */
    public int getSeleccion() {
        return seleccion;
    }

    public void setFrame(Open k) {
        this.k = k;
    }

    public void setFrame(Delete h) {
        this.h = h;
    }

    public void setFrame(Select i) {
        this.i = i;
    }

    public void setFrame(Report m) {
        this.m = m;
    }
}
