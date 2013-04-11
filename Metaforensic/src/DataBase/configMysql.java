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
package DataBase;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class configMysql {

    private String nombreFichero;
    private String ip;
    private String port;
    private String user;
    private String password;
    private File archivo;
    private FileReader fr;
    private BufferedReader br;
    private static configMysql instance = new configMysql();

    private configMysql() {
        archivo = null;
        fr = null;
        br = null;
        ip = "";
        port = "";
        user = "";
        password = "";
        String dir = System.getProperty("user.home");
        nombreFichero = dir + "\\documents\\Metaforensic\\configMysql.ini";
    }

    public void leerFichero() {
        try {

            archivo = new File(nombreFichero);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            if (archivo.exists()) {
                StringTokenizer iP = new StringTokenizer(br.readLine(), ":");
                iP.nextToken();
                if ((ip = iP.nextToken().trim()) != null) {
                    StringTokenizer porT = new StringTokenizer(br.readLine(), ":");
                    porT.nextToken();
                    port = porT.nextToken().trim();
                    StringTokenizer useR = new StringTokenizer(br.readLine(), ":");
                    useR.nextToken();
                    user = useR.nextToken().trim();
                    StringTokenizer pass = new StringTokenizer(br.readLine(), ":");
                    pass.nextToken();
                    password = pass.nextToken().trim();
                }
            } else {

                JOptionPane.showMessageDialog((Component) null, "El archivo de configuración global no existe.", "Error de Configuración", JOptionPane.ERROR_MESSAGE, null);
                System.exit(0);
            }

        } catch (IOException | HeadlessException e) {
            JOptionPane.showMessageDialog((Component) null, "Error de archivo de fichero de configuración global.", "Error de E/S", JOptionPane.ERROR_MESSAGE, null);
            System.exit(0);

        } finally {

            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e2) {
                JOptionPane.showMessageDialog((Component) null, "Error de cierre del archivo de configuración global.", "Error de E/S", JOptionPane.ERROR_MESSAGE, null);
                System.exit(0);

            }
        }
    }

    public String getIp() {

        return ip;
    }

    public String getPort() {

        return port;
    }

    public String getUser() {

        return user;
    }

    private void setPass() {
        String tmp1, tmp2;
        JPanel pn = new JPanel();
        JPasswordField pwd = new JPasswordField(25);
        JLabel lb = new JLabel("Ingresa un password: \n");
        pn.add(lb);
        pn.add(pwd);

        if (JOptionPane.showConfirmDialog(null, pn, "Conexión a la base de datos.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
            tmp1 = new String(pwd.getPassword());
            pn = new JPanel();
            pwd = new JPasswordField(25);
            lb.setText("Confirma el password: \n");
            pn.add(lb);
            pn.add(pwd);
            if (JOptionPane.showConfirmDialog(null, pn, "Conexión a la base de datos.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
                tmp2 = new String(pwd.getPassword());

                if (tmp1.equals(tmp2)) {
                    password = tmp2;
                } else {
                    JOptionPane.showMessageDialog(null, "El password no es identico.", "Error de validación", JOptionPane.WARNING_MESSAGE);

                }
            }
        }
    }

    public String getPass() {

        return password;
    }

    public static configMysql getInstance() {
        return instance;
    }
}
