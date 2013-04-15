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
package Process;

import DataBase.ConectionBD;
import DataBase.ConfigMysql;
import java.awt.Component;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

/**
 * Define todas las operaciones sobre la BD
 *
 * @author andy737-1
 * @version 1.0
 */
public class OperationBD {

    private File tmp;
    private ConectionBD conn;
    private ConfigMysql conf;
    private NewValues new_;
    private OpenValues ov;
    private DeleteValues dl;
    private Connection con;
    private CallableStatement call;
    private String idtmp;
    private FileName fn;
    private boolean erroglob;
    private int op;
    private ArrayList combo;
    private ArrayList info;
    private ArrayList all;
    private ArrayList eve;
    private ArrayList eveAll;
    private IdVal iv;
    private File file;

    /**
     * Constructor inicia variables
     *
     * @param op tipo de operación sobre la BD
     */
    public OperationBD(int op) {
        ov = OpenValues.getInstance();
        iv = IdVal.getInstance();
        conn = new ConectionBD();
        file = null;
        idtmp = "";
        erroglob = false;
        eve = null;
        fn = new FileName();
        conf = ConfigMysql.getInstance();
        new_ = NewValues.getInstance();
        call = null;
        combo = null;
        info = null;
        all = null;
        tmp = null;
        eveAll = null;
        dl = DeleteValues.getInstance();
        this.op = op;
        SelectOpPane(op);

    }

    private void SelectOpPane(int op) {
        switch (op) {
            case 1:
                con = conn.getConexion();
                if (con != null) {
                    CreatePro();
                    CreateProEven();
                    InsertFile();
                    CreateEven("Se cargo el archivo \"" + fn.filename(tmp) + "\".afa en el proyecto ");

                } else {
                    CreateEven("Error al cargar el archivo \"" + fn.filename(tmp) + "\".afa en el proyecto ");
                    erroglob = true;
                    conf.ErrPass("");
                }
                break;
            case 2:
                con = conn.getConexion();
                if (con != null) {
                    LoadCombo();
                } else {
                    erroglob = true;
                    conf.ErrPass("");
                }
                break;
            case 3:
                con = conn.getConexion();
                if (con != null) {
                    DeletePro();
                    //GeneraDelEven();
                } else {
                    erroglob = true;
                    conf.ErrPass("");
                }
                break;
            case 4:
                con = conn.getConexion();
                if (con != null) {
                    LoadInfo();
                    CreateEven("Se cargo visualización de información del proyecto ");
                } else {
                    CreateEven("Error al cargar información del proyecto ");
                    erroglob = true;
                    conf.ErrPass("");
                }
                break;
            case 5:
                con = conn.getConexion();
                if (con != null) {
                    LoadAll();
                } else {
                    erroglob = true;
                    conf.ErrPass("");
                }
                break;
            case 6:
                con = conn.getConexion();
                if (con != null) {
                    LoadEve();
                    CreateEven("Se cargo visualización de eventos proyecto ");
                } else {
                    CreateEven("Error al cargar información de eventos del proyecto ");
                    erroglob = true;
                    conf.ErrPass("");
                }
                break;
            case 7:
                con = conn.getConexion();
                if (con != null) {
                    LoadEveAll();
                } else {
                    erroglob = true;
                    conf.ErrPass("");
                }
                break;
            case 8:
                con = conn.getConexion();
                if (con != null) {
                    OpenFile();
                    CreateEven("Se descifro el archivo " + getFile().getName() + " del proyecto ");
                } else {
                    CreateEven("Error al descifrar el archivo " + getFile().getName() + " del proyecto ");
                    erroglob = true;
                    conf.ErrPass("");
                }
                break;
        }

    }

    /**
     *
     * @return archivo para descrifrado
     */
    public File getFile() {
        return file;
    }

    private void OpenFile() {
        idtmp = ov.getId();
        try {
            if (conn.BDStatus()) {
                call = con.prepareCall("{call ruta_archivo(?)}");
                call.setString(1, ov.getId());
                try (ResultSet rs = call.executeQuery()) {
                    if (rs.next()) {
                        file = new File(rs.getObject(1).toString());
                    }
                    rs.close();
                }
                call.close();
                con.close();
            }
        } catch (SQLException ex) {
            erroglob = true;
            JOptionPane.showMessageDialog((Component) null, "La base de datos no puede cargar los eventos.", "Error de procedimiento", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    private void LoadEveAll() {
        eveAll = new ArrayList();
        int i;
        try {
            if (conn.BDStatus()) {
                call = con.prepareCall("{call consulta_proyecto_evenAll()}");
                try (ResultSet rs = call.executeQuery()) {
                    while (rs.next()) {
                        i = 1;
                        while (i < 5) {
                            eveAll.add(rs.getObject(i));
                            i++;
                        }
                    }
                    rs.close();
                }
                call.close();
                con.close();
            }
        } catch (SQLException ex) {
            erroglob = true;
            JOptionPane.showMessageDialog((Component) null, "La base de datos no puede cargar los eventos.", "Error de procedimiento", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    private void LoadEve() {
        idtmp = iv.getId();
        eve = new ArrayList();
        int i;
        try {
            if (conn.BDStatus()) {
                call = con.prepareCall("{call consulta_proyecto_even(?)}");
                call.setString(1, iv.getId());
                try (ResultSet rs = call.executeQuery()) {
                    while (rs.next()) {
                        i = 1;
                        while (i < 5) {
                            eve.add(rs.getObject(i));
                            i++;
                        }
                    }
                    rs.close();
                }
                call.close();
                con.close();
            }
        } catch (SQLException ex) {
            erroglob = true;
            JOptionPane.showMessageDialog((Component) null, "La base de datos no puede cargar los eventos.", "Error de procedimiento", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    private void LoadAll() {
        all = new ArrayList();
        int i;
        try {
            if (conn.BDStatus()) {
                call = con.prepareCall("{call consulta_proyecto_gen()}");
                try (ResultSet rs = call.executeQuery()) {
                    while (rs.next()) {
                        i = 1;
                        while (i < 17) {
                            all.add(rs.getObject(i));
                            i++;
                        }
                    }
                    rs.close();
                }
                call.close();
                con.close();
            }
        } catch (SQLException ex) {
            erroglob = true;
            JOptionPane.showMessageDialog((Component) null, "La base de datos no puede cargar la información.", "Error de procedimiento", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    private void LoadInfo() {
        idtmp = iv.getId();
        info = new ArrayList();
        int i = 1;
        try {
            if (conn.BDStatus()) {
                call = con.prepareCall("{call consulta_proyecto(?)}");
                call.setString(1, iv.getId());
                try (ResultSet rs = call.executeQuery()) {
                    if (rs.next()) {
                        while (i < 17) {
                            info.add(rs.getObject(i));
                            i++;
                        }
                    }
                    rs.close();
                }
                call.close();
                con.close();
            }
        } catch (SQLException ex) {
            erroglob = true;
            JOptionPane.showMessageDialog((Component) null, "La base de datos no puede cargar la información.", "Error de procedimiento", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    private void GeneraDelEven() {
        try {
            con = conn.getConexion();
            if (conn.BDStatus()) {
                call = con.prepareCall("{call genera_even_deleteproy(?)}");
                call.setString(1, dl.getId());
                call.executeUpdate();
                call.close();
                con.close();
            }
        } catch (SQLException ex) {
            erroglob = true;
            JOptionPane.showMessageDialog((Component) null, "La base de datos no puede generar el evento universal.", "Error de procedimiento", JOptionPane.ERROR_MESSAGE, null);
        }

    }

    private void DeletePro() {
        try {
            con = conn.getConexion();
            if (conn.BDStatus()) {
                call = con.prepareCall("{call eliminar_proyecto(?)}");
                call.setString(1, dl.getId());
                call.executeUpdate();
                call.close();
                con.close();
            }
        } catch (SQLException ex) {
            erroglob = true;
            JOptionPane.showMessageDialog((Component) null, "La base de datos no puede generar el evento delete.", "Error de procedimiento", JOptionPane.ERROR_MESSAGE, null);
        }

    }

    private double SizeFile(File archivo) {
        double bytes = archivo.length();
        double kb = bytes / 1024;
        return kb;
    }

    private void InsertFile() {
        tmp = new File(new_.getRuta());
        long ms = tmp.lastModified();
        int hora, minutos, segundos;
        Date d = new Date(ms);
        Calendar c = new GregorianCalendar();
        c.setTime(d);
        java.sql.Date sqlDate = new java.sql.Date(c.getTime().getTime());

        hora = c.get(Calendar.HOUR);
        minutos = c.get(Calendar.MINUTE);
        segundos = c.get(Calendar.SECOND);
        Time sqlTime = Time.valueOf(hora + ":" + minutos + ":" + segundos);

        try {
            con = conn.getConexion();
            if (conn.BDStatus()) {
                call = con.prepareCall("{call insertar_archivo (?,?,?,?,?,?,?,?)}");
                call.setString(1, "afa");
                call.setString(2, fn.filename(tmp));
                call.setString(3, SizeFile(tmp) + " KB");
                call.setString(4, "AES");
                call.setString(5, fn.path(tmp) + "\\");
                call.setDate(6, sqlDate);
                call.setTime(7, sqlTime);
                call.setString(8, idtmp);
                call.executeUpdate();
                call.close();
                con.close();
            }
        } catch (SQLException ex) {
            erroglob = true;
            JOptionPane.showMessageDialog((Component) null, "La base de datos no puede cargar el archivo.", "Error de procedimiento", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    /**
     *
     * @return error de la BD
     */
    public boolean ErroSta() {
        return erroglob;
    }

    private void CreateEven(String msg) {
        try {
            con = conn.getConexion();
            if (conn.BDStatus()) {
                call = con.prepareCall("{call genera_even_universal(?,?)}");
                call.setString(1, idtmp);
                call.setString(2, msg);
                call.executeUpdate();
                call.close();
                con.close();
            }
        } catch (SQLException ex) {
            erroglob = true;
            JOptionPane.showMessageDialog((Component) null, "La base de datos no puede generar el evento.", "Error de procedimiento", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    private void CreateProEven() {
        try {
            con = conn.getConexion();
            if (conn.BDStatus()) {
                call = con.prepareCall("{call genera_even_insertproy(?)}");
                call.setString(1, idtmp);
                call.executeUpdate();
                call.close();
                con.close();
            }
        } catch (SQLException ex) {
            erroglob = true;
            JOptionPane.showMessageDialog((Component) null, "La base de datos no puede generar el evento creación.", "Error de procedimiento", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    private void CreatePro() {
        try {
            conn.getConexion();
            if (conn.BDStatus()) {
                call = con.prepareCall("{call insertar_proyecto(?,?,?)}");
                call.setString(1, new_.getNombre());
                call.setString(2, new_.getDescripcion());
                call.setString(3, new_.getAutor());
                ResultSet rs = call.executeQuery();
                if (rs.next()) {
                    idtmp = rs.getString(1);
                }
                call.close();
                con.close();
            }
        } catch (SQLException ex) {
            erroglob = true;
            JOptionPane.showMessageDialog((Component) null, "La base de datos no puede crear el proyecto.", "Error de procedimiento", JOptionPane.ERROR_MESSAGE, null);
        }
    }

    /**
     *
     * @return array para carga de combobox
     */
    public ArrayList getCombo() {
        return combo;
    }

    /**
     *
     * @return eventos generales
     */
    public ArrayList getEvenAll() {
        return eveAll;
    }

    /**
     *
     * @return todos los proyectos
     */
    public ArrayList getAll() {
        return all;
    }

    /**
     *
     * @return info de proyecto
     */
    public ArrayList getInfo() {
        return info;
    }

    /**
     *
     * @return evento particular
     */
    public ArrayList getEven() {
        return eve;
    }

    private void LoadCombo() {
        combo = new ArrayList();
        try {
            if (conn.BDStatus()) {
                call = con.prepareCall("{call llenar_combo_proyecto()}");
                ResultSet rs = call.executeQuery();
                while (rs.next()) {
                    combo.add(rs.getString(1));
                }
                call.close();
                con.close();
            }
        } catch (SQLException ex) {
            erroglob = true;
            JOptionPane.showMessageDialog((Component) null, "La base de datos no puede llenar los combos.", "Error de procedimiento", JOptionPane.ERROR_MESSAGE, null);
        }
    }
}
