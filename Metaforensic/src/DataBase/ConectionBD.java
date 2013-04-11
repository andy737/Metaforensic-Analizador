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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author andy737-1
 */
public class ConectionBD {

    private Connection conn;
    private PreparedStatement sentenciaSQL;
    private String controlador;
    private String password;
    private String user;
    private String port;
    private String ip;
    private configMysql lee;

    public ConectionBD() {
        lee=configMysql.getInstance();
        password="";
        user="";
        port="";
        ip="";
        conn = null;
        sentenciaSQL = null;
        controlador = "com.mysql.jdbc.Driver";
    }

    public void conBD() throws SQLException {
        lee.leerFichero();
        ip = lee.getIp();
        port = lee.getPort();
        user = lee.getUser();
        password = lee.getPass();
        if (!ip.equals("") && !port.equals("") && !user.equals("") && !password.equals("")) {
            try {
                Class.forName(controlador).newInstance();
                conn = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/metaforensic", user, password);
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog((Component) null, "La aplicación no se pudo conectar a la base de datos.\nRevisa la configuración.", "Error de Conexión", JOptionPane.ERROR_MESSAGE, null);
                System.exit(0);
            } catch (SQLException e) {

                JOptionPane.showMessageDialog((Component) null, "La aplicación no se pudo conectar a la base de datos.\nRevisa la configuración.", "Error de Conexión", JOptionPane.ERROR_MESSAGE, null);
                System.exit(0);
            } catch (InstantiationException e) {

                JOptionPane.showMessageDialog((Component) null, "La aplicación no se pudo conectar a la base de datos.\nRevisa la configuración.", "Error de Conexión", JOptionPane.ERROR_MESSAGE, null);
                System.exit(0);
            } catch (IllegalAccessException e) {

                JOptionPane.showMessageDialog((Component) null, "La aplicación no se pudo conectar a la base de datos.\nRevisa la configuración.", "Error de Conexión", JOptionPane.ERROR_MESSAGE, null);
                System.exit(0);
            }
        } else {

            JOptionPane.showMessageDialog((Component) null, "El fichero global no contiene una configuración correcta o no existe.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE, null);
            System.exit(0);

        }

    }

    public void desconectar() {
        try {

            if (sentenciaSQL != null) {
                sentenciaSQL.close();
            }
            if (conn != null) {
                conn.close();

            }

        } catch (SQLException ignorada) {
        }
    }

    public Connection getConexion() {
        return conn;
    }
}
