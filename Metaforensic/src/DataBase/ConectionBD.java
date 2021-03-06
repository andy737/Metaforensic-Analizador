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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Conexión a base de datos Mysql
 *
 * @author andy737-1
 * @version 1.1
 */
public class ConectionBD {

    private Connection conn;
    private boolean sta;
    private boolean stapass;
    private String controlador;
    private String password;
    private String user;
    private String port;
    private String ip;
    private ConfigMysql lee;

    /**
     * Inicializa variables constructor
     */
    public ConectionBD() {
        lee = ConfigMysql.getInstance();
        password = "";
        user = "";
        port = "";
        ip = "";
        conn = null;
        sta = true;
        stapass = true;
        controlador = "com.mysql.jdbc.Driver";
    }

    private void conBD() {
        lee.leerFichero();
        ip = lee.getIp();
        port = lee.getPort();
        user = lee.getUser();
        password = lee.getPass();
        if (!ip.equals("") && !port.equals("") && !user.equals("")) {
            try {
                Class.forName(controlador).newInstance();
                conn = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/metaforensic", user, password);
                sta = true;
            } catch (ClassNotFoundException e) {
                sta = false;
                JOptionPane.showMessageDialog((Component) null, "La aplicación no se pudo conectar a la base de datos.\nRevisa la configuración.", "Error de Conexión", JOptionPane.ERROR_MESSAGE, null);
            } catch (SQLException e) {
                sta = false;
                JOptionPane.showMessageDialog((Component) null, "La aplicación no se pudo conectar a la base de datos.\nRevisa la configuración.", "Error de Conexión", JOptionPane.ERROR_MESSAGE, null);
            } catch (InstantiationException e) {
                sta = false;
                JOptionPane.showMessageDialog((Component) null, "La aplicación no se pudo conectar a la base de datos.\nRevisa la configuración.", "Error de Conexión", JOptionPane.ERROR_MESSAGE, null);
            } catch (IllegalAccessException e) {
                sta = false;
                JOptionPane.showMessageDialog((Component) null, "La aplicación no se pudo conectar a la base de datos.\nRevisa la configuración.", "Error de Conexión", JOptionPane.ERROR_MESSAGE, null);
            }
        } else {
            sta = false;
        }

    }

    /**
     *
     * @return estado de la conexión actual
     */
    public boolean BDStatus() {
        return sta;
    }

    /**
     *
     * @return conexión a BD
     */
    public Connection getConexion() {
        conBD();
        return conn;
    }

    /**
     * Cerrar conexión a BD
     */
    public void Cerrar() {
        try {
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog((Component) null, "El cierre de la conexion a la base de datos falló.", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE, null);
        }
    }
}
