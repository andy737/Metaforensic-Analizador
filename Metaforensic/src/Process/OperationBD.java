/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author andy737-1
 */
public class OperationBD {

    private File tmp;
    private ConectionBD conn;
    private ConfigMysql conf;
    private NewValues new_;
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

    public OperationBD(int op) {
        iv = IdVal.getInstance();
        conn = new ConectionBD();
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
                    CreateEven("Se inserto el archivo \"" + fn.filename(tmp) + "\".afa en el proyecto ");

                } else {
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
                } else {
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
                } else {
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

    public ArrayList getCombo() {
        return combo;
    }

    public ArrayList getEvenAll() {
        return eveAll;
    }

    public ArrayList getAll() {
        return all;
    }

    public ArrayList getInfo() {
        return info;
    }

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
