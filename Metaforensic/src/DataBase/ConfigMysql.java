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
package DataBase;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 * Carga de parametros para conexción a BD
 *
 * @author andy737-1
 * @version 1.1
 */
public class ConfigMysql {

    private String nombreFichero;
    private String ip;
    private String port;
    private String user;
    private String password;
    private File archivo;
    private FileReader fr;
    private BufferedReader br;
    private boolean flag;
    private boolean sta;
    private static ConfigMysql instance = new ConfigMysql();

    private ConfigMysql() {
        archivo = null;
        password = "";
        flag = false;
        fr = null;
        sta = false;
        br = null;
        ip = "";
        port = "";
        user = "";
        String dir = System.getProperty("user.home");
        nombreFichero = dir + "\\documents\\Metaforensic\\configMysql.ini";
    }

    /**
     * Lee fichero con parametros de conexión
     */
    @SuppressWarnings("UseSpecificCatch")
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

                }
            } else {

                JOptionPane.showMessageDialog((Component) null, "El archivo de configuración global no existe.", "Error de Configuración", JOptionPane.ERROR_MESSAGE, null);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog((Component) null, "Error de archivo de configuración global.", "Error de E/S", JOptionPane.ERROR_MESSAGE, null);

        } finally {

            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog((Component) null, "Error de cierre del archivo de configuración global.", "Error de E/S", JOptionPane.ERROR_MESSAGE, null);
                System.exit(0);

            }
        }
    }

    /**
     *
     * @return dirección de server BD
     */
    public String getIp() {

        return ip;
    }

    /**
     *
     * @return puerto de conexión a BD
     */
    public String getPort() {

        return port;
    }

    /**
     *
     * @return usuario de BD
     */
    public String getUser() {

        return user;
    }

    /**
     * Seteo de password para conexión a BD
     */
    public void setPass() {
        String tmp1;
        JPanel pn = new JPanel();
        JPasswordField pwd = new JPasswordField(25);
        JLabel lb = new JLabel("Ingresa el password de la BD: \n");
        pn.add(lb);
        pn.add(pwd);

        if (JOptionPane.showConfirmDialog(null, pn, "Conexión a la base de datos.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
            tmp1 = new String(pwd.getPassword());
            password = tmp1;
            sta = true;
            if (tmp1 == null || tmp1.equals("")) {
                sta = false;

            }
        } else {
            System.exit(0);
        }
    }

    /**
     *
     * @param password reset de password interno
     */
    public void ErrPass(String password) {
        this.password = password;
    }

    /**
     *
     * @return password de conexión
     */
    public String getPass() {

        return password;
    }

    /**
     *
     * @return instancia de la clase
     */
    public static ConfigMysql getInstance() {
        return instance;
    }

    /**
     *
     * @return estado del password
     */
    public boolean PassSta() {
        return sta;
    }
}
