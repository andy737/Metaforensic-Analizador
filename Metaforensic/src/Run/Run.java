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
package Run;

import DataBase.ConectionBD;
import DataBase.ConfigMysql;
import GUI.Project;
import Windows.FrameIcons;
import Windows.WindowsStyle;
import javax.swing.JOptionPane;

/**
 * Clase encargada de iniciar la aplicación "Metaforensic"
 *
 *
 * @author andy737-1
 * @version 1.0
 */
public class Run {

    private static ConfigMysql my;
    private static FrameIcons ic;
    private static ConectionBD con;
    //static Logger logger = Logger.getLogger(Run.class);

    /**
     * Inicio de la aplicación
     *
     * @param args (valor default)
     */
    public static void main(String[] args) {
        my = ConfigMysql.getInstance();
        ic = FrameIcons.getInstance();
        con = new ConectionBD();
        WindowsStyle.SetStyle();
        AppInit();
        //BasicConfigurator.configure();
        //logger.info("Ingresando a la aplicación");
        //Splash.RunSplash(null, false);        
        //Run.AppInit();

    }

    private static void AppInit() {
        my.setPass();
        if (!my.PassSta()) {
            JOptionPane.showMessageDialog(null, "Debes ingresar el password de la base de datos para iniciar la aplicación.", "Fin de aplicación", JOptionPane.ERROR_MESSAGE, null);
            System.exit(0);
        } else {
            con.getConexion();
            if (con.BDStatus()) {
                con.Cerrar();
                Project pj = new Project();
                pj.setVisible(true);

            } else {

                JOptionPane.showMessageDialog(null, "Debes ingresar el password correcto de la base de datos para iniciar la aplicación.", "Fin de aplicación", JOptionPane.ERROR_MESSAGE, null);
                System.exit(0);
            }
        }
    }
}