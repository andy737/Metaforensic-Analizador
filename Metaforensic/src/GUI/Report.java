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
package GUI;

import Process.DateTime;
import Process.FileMeta;
import Process.WindowStat;
import Windows.Carga;
import Windows.FrameIcons;
import Windows.ModalDialog;
import Windows.WindowsStyle;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.BadLocationException;

/**
 * Genera anlisis y reportes
 *
 * @author andy737-1
 * @version 1.1
 */
public class Report extends javax.swing.JFrame {

    private final FrameIcons ic;
    /**
     * Nombre del archivo .afa -> txt
     */
    public static String file = "";
    private FileReader in;
    private BufferedReader bin;
    private File fl;
    private ArrayList<FileMeta> files;
    private FileMeta minf;
    private String[] tooltips;
    private ModalDialog md;
    private FileOutputStream logout;
    private OutputStreamWriter outlog;
    private BufferedWriter outfinal;
    private WindowStat stw;
    private boolean salir;

    /**
     * Inicializa variables
     *
     * @param file nombre del archivo txt
     */
    public Report(String file) {
        stw = WindowStat.getWinInstance();
        Report.file = file;
        in = null;
        bin = null;
        fl = null;
        tooltips = null;
        salir = false;
        ic = FrameIcons.getInstance();
        WindowsStyle.SetStyle();
        ic.SetIcon();
        this.setIconImages(ic.GetIcon());
        initComponents();
        this.setLocationRelativeTo(null);
        Clean();
        LoadInfo();
    }

    private void Clean() {
        txtEquipo.setText("");
        txtUsuario.setText("");
        txtSO.setText("");
        txtVer.setText("");
        txtArq.setText("");
        cmbFile.setSelectedIndex(-1);
        txtView.setText("");
    }

    private void LoadInfo() {
        String linea;
        int i = 0;
        int inicio = 0;
        try {
            fl = new File(file);
            in = new FileReader(fl);
            bin = new BufferedReader(in);
            files = new ArrayList<>();
            while ((linea = bin.readLine()) != null) {
                if (!linea.contains("*")) {
                    inicio = linea.indexOf(":") + 1;
                }
                if (i > 0 && i < 6) {
                    switch (i) {
                        case 1:
                            txtEquipo.setText(linea.substring(inicio));
                            txtEquipo.setToolTipText(linea.substring(inicio));
                            break;
                        case 2:
                            txtUsuario.setText(linea.substring(inicio));
                            txtUsuario.setToolTipText(linea.substring(inicio));
                            break;
                        case 3:
                            txtSO.setText(linea.substring(inicio));
                            txtSO.setToolTipText(linea.substring(inicio));
                            break;
                        case 4:
                            txtVer.setText(linea.substring(inicio));
                            txtVer.setToolTipText(linea.substring(inicio));
                            break;
                        case 5:
                            txtArq.setText(linea.substring(inicio));
                            txtArq.setToolTipText(linea.substring(inicio));
                            break;
                    }
                } else {

                    if (linea.contains("[fileName]")) {
                        minf = new FileMeta();
                        minf.setNombre(linea.substring(inicio));
                        minf.setPosicion(i - 6);
                        files.add(minf);
                    }
                }
                i++;
            }
            files.size();
            Sort(files);
        } catch (IOException ex) {
            ErrorLectura();
        } finally {
            try {
                bin.close();
                in.close();
            } catch (IOException ex) {
                ErrorCierre();
            }
        }

    }

    class MyComboBoxRenderer extends BasicComboBoxRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
                if (-1 < index) {
                    list.setToolTipText(tooltips[index]);
                }
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setFont(list.getFont());
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    /**
     *
     * @param fm lista de archivo a ordenar
     */
    public void Sort(ArrayList<FileMeta> fm) {
        tooltips = new String[files.size()];
        int i = 0;
        Comparator<FileMeta> comparador = new Comparator<FileMeta>() {
            @Override
            public int compare(FileMeta p1, FileMeta p2) {

                int resultado = p1.getNombre().compareTo(p2.getNombre());
                if (resultado != 0) {
                    return resultado;
                }
                return resultado;
            }
        };
        Collections.sort(fm, comparador);
        while (i < files.size()) {
            tooltips[i] = files.get(i).getNombre();
            cmbFile.addItem(files.get(i).getNombre());
            i++;
        }
        cmbFile.setRenderer(new MyComboBoxRenderer());
        cmbFile.setSelectedIndex(-1);

    }

    private void ViewAll() throws BadLocationException {
        cmbFile.setSelectedIndex(-1);
        txtView.setText("");
        String linea;
        try {
            fl = new File(file);
            in = new FileReader(fl);
            bin = new BufferedReader(in);
            while ((linea = bin.readLine()) != null) {

                if (linea.contains("*")) {
                    AddText("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n\n");
                } else {
                    AddText(linea + "\n");
                }
            }
        } catch (IOException ex) {
            ErrorLectura();
        } finally {
            try {
                bin.close();
                in.close();
            } catch (IOException ex) {
                ErrorCierre();
            }
        }
    }

    private void ViewFileInd(java.awt.event.ItemEvent evt) throws BadLocationException {
        rbTodo.setSelected(false);
        if (evt.getStateChange() == ItemEvent.SELECTED && cmbFile.getSelectedIndex() >= 0) {
            try {
                String linea;
                boolean flag1 = false;
                txtView.setText("");
                fl = new File(file);
                in = new FileReader(fl);
                bin = new BufferedReader(in);
                @SuppressWarnings("StringBufferMayBeStringBuilder")
                StringBuffer bf = new StringBuffer(100000000);
                bin.readLine();
                while ((linea = bin.readLine()) != null) {
                    if (linea.contains("*")) {
                        if (flag1) {
                            return;
                        }
                        bf.setLength(0);
                    } else {
                        bf.append(linea).append("\n\n");
                        if (linea.contains(cmbFile.getSelectedItem().toString())) {
                            flag1 = true;
                        }
                        if (flag1) {
                            AddText(bf.toString());
                            bf.setLength(0);
                        }
                    }
                }
            } catch (IOException ex) {
                ErrorLectura();
            } finally {
                try {
                    bin.close();
                    in.close();
                } catch (IOException ex) {
                    ErrorCierre();
                }
            }
        }

    }

    private void ViewFiles() throws BadLocationException {
        cmbFile.setSelectedIndex(-1);
        txtView.setText("");
        int i = 0;
        int pdf = 0;
        int doc = 0;
        int xls = 0;
        int ppt = 0;
        int xlsx = 0;
        int pptx = 0;
        int docx = 0;
        int jpg = 0;
        int png = 0;
        int odt = 0;
        int odp = 0;
        int ods = 0;
        while (i < files.size()) {
            AddText(files.get(i).getNombre() + "\n");
            int dot = files.get(i).getNombre().lastIndexOf(".");
            switch (files.get(i).getNombre().substring(dot + 1)) {
                case "pdf":
                    pdf++;
                    break;
                case "jpg":
                    jpg++;
                    break;
                case "png":
                    png++;
                    break;
                case "docx":
                    docx++;
                    break;
                case "xlsx":
                    xlsx++;
                    break;
                case "pptx":
                    pptx++;
                    break;
                case "doc":
                    doc++;
                    break;
                case "xls":
                    xls++;
                    break;
                case "ppt":
                    ppt++;
                    break;
                case "odt":
                    odt++;
                    break;
                case "ods":
                    ods++;
                    break;
                case "odp":
                    odp++;
                    break;
            }
            i++;
        }
        AddText("\n");
        if (pdf > 0) {
            AddText("Número de archivos pdf: " + pdf + "\n");
        }
        if (jpg > 0) {
            AddText("Número de archivos jpg: " + jpg + "\n");
        }
        if (png > 0) {
            AddText("Número de archivos png: " + png + "\n");
        }
        if (docx > 0) {
            AddText("Número de archivos docx: " + docx + "\n");
        }
        if (xlsx > 0) {
            AddText("Número de archivos xlsx: " + xlsx + "\n");
        }
        if (pptx > 0) {
            AddText("Número de archivos pptx: " + pptx + "\n");
        }
        if (doc > 0) {
            AddText("Número de archivos doc: " + doc + "\n");
        }
        if (xls > 0) {
            AddText("Número de archivos xls: " + xls + "\n");
        }
        if (ppt > 0) {
            AddText("Número de archivos ppt: " + ppt + "\n");
        }
        if (odt > 0) {
            AddText("Número de archivos odt: " + odt + "\n");
        }
        if (ods > 0) {
            AddText("Número de archivos ods: " + ods + "\n");
        }
        if (odp > 0) {
            AddText("Número de archivos odp: " + odp + "\n");
        }
        AddText("Total de archivos: " + files.size() + "\n");
    }

    private void ValidaTxt() {
        if (txtView.getText().equals("") || txtView.getText() == null) {
            md = new ModalDialog();
            md.setDialogo("Selecciona un proyecto o evento.");
            md.setFrame(this);
            md.setTitulo("Error de validación");
            md.DialogErrFix();
        } else {
            SaveFile();
        }
    }

    private void SaveFile() {
        boolean ciclo = true;
        while (ciclo) {
            ciclo = SelectDir(txtView);
        }
    }

    private boolean SelectDir(JTextPane txt) {

        boolean ciclo = false;
        int rseleccion = fchGuardar.showDialog(this, "Guardar");
        if (rseleccion == JFileChooser.APPROVE_OPTION) {
            File directorio = new File(fchGuardar.getSelectedFile().toPath().toString());
            if (directorio.isDirectory()) {
                CreateFile(directorio.toString());
                ciclo = false;
                md = new ModalDialog();
                md.setDialogo("El archivo fue creado con éxito.");
                md.setTitulo("Confirmación");
                md.setFrame(this);
                md.DialogInfo();
            } else {
                md = new ModalDialog();
                md.setDialogo("El directorio no existe.");
                md.setTitulo("Error de ruta");
                md.setFrame(this);
                md.DialogErrFix();
                txt.requestFocus(true);
                ciclo = true;
            }
        }
        return ciclo;
    }

    private void CreateFile(String path) {
        try {
            logout = new FileOutputStream(path + "\\" + DateTime.getDate().toString().replace("-", "") + "_" + DateTime.getTimeMilli().toString().replace(":", "") + "_" + "Reporte.txt");
            outlog = new OutputStreamWriter(logout, "UTF-8");
            outfinal = new BufferedWriter(outlog);
            txtView.write(outfinal);
            outfinal.flush();
        } catch (IOException ex) {
            md.setDialogo("No se pudo crear el archivo " + path + "\\" + DateTime.getDate().toString().replace("-", "") + "_" + DateTime.getTimeMilli().toString().replace(":", "") + "_" + "Reporte.txt" + " en la carpeta: \n" + path);
            md.setTitulo("Error de archivo");
            md.setFrame(this);
            md.DialogErr();
        } finally {
            if (outfinal != null) {
                try {
                    outfinal.close();
                    outlog.close();
                } catch (IOException ex) {
                    md.setDialogo("No se pudo cerrar corretacmente el archivo " + path + "\\" + DateTime.getDate().toString().replace("-", "") + "_" + DateTime.getTimeMilli().toString().replace(":", "") + "_" + "Reporte.txt");
                    md.setTitulo("Error de archivo");
                    md.setFrame(this);
                    md.DialogErr();
                }
            }
        }
    }

    private void FindUsers() throws BadLocationException {
        String linea;
        int st = 0;
        boolean flst = false;
        txtView.setText("");
        try {
            fl = new File(file);
            in = new FileReader(fl);
            bin = new BufferedReader(in);
            @SuppressWarnings("StringBufferMayBeStringBuilder")
            StringBuffer bf = new StringBuffer(100000000);
            StringBuffer bf1 = new StringBuffer(10000);
            bin.readLine();
            if (rbTodo.isSelected()) {
                while ((linea = bin.readLine()) != null) {
                    if (linea.contains("fileName")) {
                        StringTokenizer tkn = new StringTokenizer(linea, ":");
                        if (tkn.nextToken().toString().contains("fileName")) {
                            bf1.setLength(0);
                            st = 0;
                            bf1.append(linea);
                        }
                    }
                    StringTokenizer tkn_ = new StringTokenizer(linea, "]");
                    if (tkn_.hasMoreTokens()) {
                        String tmp = tkn_.nextToken();
                        if (tmp.contains("user") || tmp.contains("users") || tmp.contains("author") || tmp.contains("users") || tmp.contains("creator")) {
                            if (!bf1.toString().equals("")) {
                                if (st == 0) {
                                    bf.append("\n").append(bf1).append("\n");
                                }
                                bf.append(linea).append("\n");
                                st++;
                            }
                        }
                    }
                }
                AddText(bf.toString());
            } else {
                if (!rbTodo.isSelected() && cmbFile.getSelectedIndex() != -1) {
                    while ((linea = bin.readLine()) != null) {
                        if (linea.contains("fileName")) {
                            StringTokenizer tkn = new StringTokenizer(linea, ":");
                            if (tkn.nextToken().toString().contains("fileName") && flst) {
                                break;
                            }
                        }
                        if (linea.contains(cmbFile.getSelectedItem().toString())) {
                            st = 0;
                            flst = true;
                            bf1.setLength(0);
                            bf1.append(linea);
                        }
                        StringTokenizer tkn_ = new StringTokenizer(linea, "]");
                        if (tkn_.hasMoreTokens()) {
                            String tmp = tkn_.nextToken();
                            if (tmp.contains("user") || tmp.contains("users") || tmp.contains("author") || tmp.contains("users") || tmp.contains("creator")) {
                                if (!bf1.toString().equals("")) {
                                    if (st == 0) {
                                        bf.append("\n").append(bf1).append("\n");
                                    }
                                    bf.append(linea).append("\n");
                                    st++;
                                }
                            }
                        }
                    }
                    AddText(bf.toString());
                } else {
                    AddText("Pulsa el botón en combinación de alguna de las opción.");
                }
            }
        } catch (IOException ex) {
            ErrorLectura();
        } finally {
            try {
                if ((txtView.getDocument().getLength() <= 0 && rbTodo.isSelected()) || (txtView.getDocument().getLength() <= 0 && cmbFile.getSelectedIndex() > -1)) {
                    AddText("No hay información disponible...");
                }
                bin.close();
                in.close();
            } catch (IOException ex) {
                ErrorCierre();
            }
        }
    }

    private void ViewSoft() throws BadLocationException {
        String linea;
        int st = 0;
        boolean flst = false;
        txtView.setText("");
        try {
            fl = new File(file);
            in = new FileReader(fl);
            bin = new BufferedReader(in);
            @SuppressWarnings("StringBufferMayBeStringBuilder")
            StringBuffer bf = new StringBuffer(100000000);
            StringBuffer bf1 = new StringBuffer(10000);
            bin.readLine();
            if (rbTodo.isSelected()) {
                while ((linea = bin.readLine()) != null) {
                    if (linea.contains("fileName")) {
                        StringTokenizer tkn = new StringTokenizer(linea, ":");
                        if (tkn.nextToken().toString().contains("fileName")) {
                            bf1.setLength(0);
                            st = 0;
                            bf1.append(linea);
                        }
                    }
                    StringTokenizer tkn_ = new StringTokenizer(linea, "]");
                    if (tkn_.hasMoreTokens()) {
                        String tmp = tkn_.nextToken();
                        if (tmp.contains("tool") || tmp.contains("Tool") || tmp.contains("producer") || tmp.contains("Producer") || tmp.contains("software") || tmp.contains("Software")) {
                            if (!bf1.toString().equals("")) {
                                if (st == 0) {
                                    bf.append("\n").append(bf1).append("\n");
                                }
                                bf.append(linea).append("\n");
                                st++;
                            }
                        }
                    }
                }
                AddText(bf.toString());
            } else {
                if (!rbTodo.isSelected() && cmbFile.getSelectedIndex() != -1) {
                    while ((linea = bin.readLine()) != null) {
                        if (linea.contains("fileName")) {
                            StringTokenizer tkn = new StringTokenizer(linea, ":");
                            if (tkn.nextToken().toString().contains("fileName") && flst) {
                                break;
                            }
                        }
                        if (linea.contains(cmbFile.getSelectedItem().toString())) {
                            st = 0;
                            flst = true;
                            bf1.setLength(0);
                            bf1.append(linea);
                        }
                        StringTokenizer tkn_ = new StringTokenizer(linea, "]");
                        if (tkn_.hasMoreTokens()) {
                            String tmp = tkn_.nextToken();
                            if (tmp.contains("tool") || tmp.contains("Tool") || tmp.contains("producer") || tmp.contains("Producer") || tmp.contains("software") || tmp.contains("Software")) {
                                if (!bf1.toString().equals("")) {
                                    if (st == 0) {
                                        bf.append("\n").append(bf1).append("\n");
                                    }
                                    bf.append(linea).append("\n");
                                    st++;
                                }
                            }
                        }
                    }
                    AddText(bf.toString());
                } else {
                    AddText("Pulsa el botón en combinación de alguna de las opción.");
                }
            }
        } catch (IOException ex) {
            ErrorLectura();
        } finally {
            try {
                if ((txtView.getDocument().getLength() <= 0 && rbTodo.isSelected()) || (txtView.getDocument().getLength() <= 0 && cmbFile.getSelectedIndex() > -1)) {
                    AddText("No hay información disponible...");
                }
                bin.close();
                in.close();
            } catch (IOException ex) {
                ErrorCierre();
            }
        }
    }

    private void ViewTimes() throws BadLocationException {
        String linea;
        int st = 0;
        boolean flst = false;
        txtView.setText("");
        try {
            fl = new File(file);
            in = new FileReader(fl);
            bin = new BufferedReader(in);
            @SuppressWarnings("StringBufferMayBeStringBuilder")
            StringBuffer bf = new StringBuffer(100000000);
            StringBuffer bf1 = new StringBuffer(10000);
            bin.readLine();
            if (rbTodo.isSelected()) {
                while ((linea = bin.readLine()) != null) {
                    if (linea.contains("fileName")) {
                        StringTokenizer tkn = new StringTokenizer(linea, ":");
                        if (tkn.nextToken().toString().contains("fileName")) {
                            bf1.setLength(0);
                            st = 0;
                            bf1.append(linea);
                        }
                    }
                    StringTokenizer tkn_ = new StringTokenizer(linea, "]");
                    if (tkn_.hasMoreTokens()) {
                        String tmp = tkn_.nextToken();
                        if (tmp.contains("Creation") || tmp.contains("creation") || tmp.contains("Modified") || tmp.contains("modified") || tmp.contains("created") || tmp.contains("Created") || tmp.contains("Printed") || tmp.contains("printed") || tmp.contains("Save") || tmp.contains("save") || tmp.contains("Saved") || tmp.contains("saved") || tmp.contains("Date/Time") || tmp.contains("date") || tmp.contains("time") || tmp.contains("Date") || tmp.contains("Time")) {
                            if (!bf1.toString().equals("")) {
                                if (st == 0) {
                                    bf.append("\n").append(bf1).append("\n");
                                }
                                bf.append(linea).append("\n");
                                st++;
                            }
                        }
                    }
                }
                AddText(bf.toString());
            } else {
                if (!rbTodo.isSelected() && cmbFile.getSelectedIndex() != -1) {
                    while ((linea = bin.readLine()) != null) {
                        if (linea.contains("fileName")) {
                            StringTokenizer tkn = new StringTokenizer(linea, ":");
                            if (tkn.nextToken().toString().contains("fileName") && flst) {
                                break;
                            }
                        }
                        if (linea.contains(cmbFile.getSelectedItem().toString())) {
                            st = 0;
                            flst = true;
                            bf1.setLength(0);
                            bf1.append(linea);
                        }

                        StringTokenizer tkn_ = new StringTokenizer(linea, "]");
                        if (tkn_.hasMoreTokens()) {
                            String tmp = tkn_.nextToken();
                            if (tmp.contains("Creation") || tmp.contains("creation") || tmp.contains("Modified") || tmp.contains("modified") || tmp.contains("created") || tmp.contains("Created") || tmp.contains("Printed") || tmp.contains("printed") || tmp.contains("Save") || tmp.contains("save") || tmp.contains("Saved") || tmp.contains("saved") || tmp.contains("Date/Time") || tmp.contains("date") || tmp.contains("time") || tmp.contains("Date") || tmp.contains("Time")) {
                                if (!bf1.toString().equals("")) {
                                    if (st == 0) {
                                        bf.append("\n").append(bf1).append("\n");
                                    }
                                    bf.append(linea).append("\n");
                                    st++;
                                }
                            }
                        }
                    }
                    AddText(bf.toString());
                } else {
                    AddText("Pulsa el botón en combinación de alguna de las opción.");
                }
            }
        } catch (IOException ex) {
            ErrorLectura();
        } finally {
            try {
                if ((txtView.getDocument().getLength() <= 0 && rbTodo.isSelected()) || (txtView.getDocument().getLength() <= 0 && cmbFile.getSelectedIndex() > -1)) {
                    AddText("No hay información disponible...");
                }
                bin.close();
                in.close();
            } catch (IOException ex) {
                ErrorCierre();
            }
        }
    }

    private void ViewOtro() throws BadLocationException {
        String linea;
        int st = 0;
        boolean flst = false;
        txtView.setText("");
        try {
            fl = new File(file);
            in = new FileReader(fl);
            bin = new BufferedReader(in);
            @SuppressWarnings("StringBufferMayBeStringBuilder")
            StringBuffer bf = new StringBuffer(100000000);
            StringBuffer bf1 = new StringBuffer(10000);
            bin.readLine();
            if (rbTodo.isSelected()) {
                while ((linea = bin.readLine()) != null) {
                    if (linea.contains("fileName")) {
                        StringTokenizer tkn = new StringTokenizer(linea, ":");
                        if (tkn.nextToken().toString().contains("fileName")) {
                            bf1.setLength(0);
                            st = 0;
                            bf1.append(linea);
                        }
                    }
                    StringTokenizer tkn_ = new StringTokenizer(linea, "]");
                    if (tkn_.hasMoreTokens()) {
                        String tmp = tkn_.nextToken();
                        if (tmp.contains("Exif Version") || tmp.contains("Company") || tmp.contains("Application") || tmp.contains("title") || tmp.contains("Content-Type") || tmp.contains("Copyright") || tmp.contains("copyright") || tmp.contains("gps") || tmp.contains("GPS") || tmp.contains("Gps") || tmp.contains("Primary Platform") || tmp.contains("Exif Version") || tmp.contains("Version Info") || tmp.contains("comments") || tmp.contains("Device") || tmp.contains("Viewing Conditions Description")) {
                            if (!bf1.toString().equals("")) {
                                if (st == 0) {
                                    bf.append("\n").append(bf1).append("\n");
                                }
                                bf.append(linea).append("\n");
                                st++;
                            }
                        }
                    }
                }
                AddText(bf.toString());
            } else {
                if (!rbTodo.isSelected() && cmbFile.getSelectedIndex() != -1) {
                    while ((linea = bin.readLine()) != null) {
                        if (linea.contains("fileName")) {
                            StringTokenizer tkn = new StringTokenizer(linea, ":");
                            if (tkn.nextToken().toString().contains("fileName") && flst) {
                                break;
                            }
                        }
                        if (linea.contains(cmbFile.getSelectedItem().toString())) {
                            st = 0;
                            flst = true;
                            bf1.setLength(0);
                            bf1.append(linea);
                        }
                        StringTokenizer tkn_ = new StringTokenizer(linea, "]");
                        if (tkn_.hasMoreTokens()) {
                            String tmp = tkn_.nextToken();
                            if (tmp.contains("Exif Version") || tmp.contains("Company") || tmp.contains("Application") || tmp.contains("title") || tmp.contains("Content-Type") || tmp.contains("Copyright") || tmp.contains("copyright") || tmp.contains("gps") || tmp.contains("GPS") || tmp.contains("Gps") || tmp.contains("Primary Platform") || tmp.contains("Exif Version") || tmp.contains("Version Info") || tmp.contains("comments") || tmp.contains("Device") || tmp.contains("Viewing Conditions Description")) {
                                if (!bf1.toString().equals("")) {
                                    if (st == 0) {
                                        bf.append("\n").append(bf1).append("\n");
                                    }
                                    bf.append(linea).append("\n");
                                    st++;
                                }
                            }
                        }
                    }
                    AddText(bf.toString());
                } else {
                    AddText("Pulsa el botón en combinación de alguna de las opción.");
                }
            }
        } catch (IOException ex) {
            ErrorLectura();
        } finally {
            try {
                if ((txtView.getDocument().getLength() <= 0 && rbTodo.isSelected()) || (txtView.getDocument().getLength() <= 0 && cmbFile.getSelectedIndex() > -1)) {
                    AddText("No hay información disponible...");
                }
                bin.close();
                in.close();
            } catch (IOException ex) {
                ErrorCierre();
            }
        }
    }

    private void AddText(String txt) throws BadLocationException {
        txtView.getStyledDocument().insertString(txtView.getDocument().getLength(), txt, null);
    }

    private void Exit() {
        fl = new File(file);
        fl.delete();
        stw.setEstadoId("");
        this.dispose();
    }

    private void Todo() {
        txtView.setText("");
        if (rbTodo.isSelected()) {
            cmbFile.setSelectedIndex(-1);
        }
    }

    private void ErrorCierre() {
        md = new ModalDialog();
        md.setDialogo("Error al cerrar archivo.");
        md.setTitulo("Error de cierre");
        md.DialogErrFix();
    }

    private void ErrorLectura() {
        md = new ModalDialog();
        md.setDialogo("El archivo no se puede leer.");
        md.setTitulo("Error de lectura");
        md.DialogErrFix();
    }

    private void Accion(final int opc, final java.awt.event.ItemEvent evt) {
        final Carga cg = new Carga("Generando...");
        final Thread load = new Thread() {
            @Override
            public void run() {
                cg.setVisible(true);
            }
        };
        btnGuardar.setEnabled(false);
        btnSalir.setEnabled(false);
        btnReco.setEnabled(false);
        btnGral.setEnabled(false);
        btnTime.setEnabled(false);
        btnUser.setEnabled(false);
        btnSoft.setEnabled(false);
        btnOtro.setEnabled(false);
        cmbFile.setEnabled(false);
        rbTodo.setEnabled(false);
        salir = false;
        load.start();
        Thread ope = new Thread() {
            @Override
            public void run() {
                try {
                    switch (opc) {
                        case 1:
                            ViewFiles();
                            break;
                        case 2:
                            ViewAll();
                            break;
                        case 3:
                            ViewTimes();
                            break;
                        case 4:
                            FindUsers();
                            break;
                        case 5:
                            ViewSoft();
                            break;
                        case 6:
                            ViewOtro();
                            break;
                        case 7:
                            ViewFileInd(evt);
                            break;
                    }
                } catch (BadLocationException ex) {
                    /*Ignore*/
                } finally {
                    cg.dispose();
                    load.stop();
                    btnGuardar.setEnabled(true);
                    btnSalir.setEnabled(true);
                    btnReco.setEnabled(true);
                    btnGral.setEnabled(true);
                    btnTime.setEnabled(true);
                    btnUser.setEnabled(true);
                    btnSoft.setEnabled(true);
                    btnOtro.setEnabled(true);
                    cmbFile.setEnabled(true);
                    rbTodo.setEnabled(true);
                    salir = true;
                }
            }
        };
        ope.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fchGuardar = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        btnReco = new javax.swing.JButton();
        btnGral = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtEquipo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSO = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtVer = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtArq = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        cmbFile = new javax.swing.JComboBox();
        btnUser = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnTime = new javax.swing.JButton();
        btnSoft = new javax.swing.JButton();
        btnOtro = new javax.swing.JButton();
        rbTodo = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtView = new javax.swing.JTextPane();

        fchGuardar.setDialogTitle("Selecciona un directorio");
        fchGuardar.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Metaforensic [Analizador]");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 1, 12)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/paper-ripped.png"))); // NOI18N
        jLabel1.setText("Análisis");

        btnReco.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnReco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/newspaper-2.png"))); // NOI18N
        btnReco.setMnemonic('a');
        btnReco.setText("Archivos recolectados");
        btnReco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecoActionPerformed(evt);
            }
        });

        btnGral.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnGral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/newspaper-2.png"))); // NOI18N
        btnGral.setMnemonic('r');
        btnGral.setText("Recolección general");
        btnGral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGralActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel2.setText("Ver archivo individual:");

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel3.setText("Equipo objetivo:");
        jLabel3.setToolTipText("");

        txtEquipo.setEditable(false);
        txtEquipo.setBackground(new java.awt.Color(255, 255, 255));
        txtEquipo.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        txtEquipo.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel4.setText("Usuario del equipo:");

        txtUsuario.setEditable(false);
        txtUsuario.setBackground(new java.awt.Color(255, 255, 255));
        txtUsuario.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        txtUsuario.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel5.setText("Sistema operativo:");

        txtSO.setEditable(false);
        txtSO.setBackground(new java.awt.Color(255, 255, 255));
        txtSO.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        txtSO.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel6.setText("Versión SO:");

        txtVer.setEditable(false);
        txtVer.setBackground(new java.awt.Color(255, 255, 255));
        txtVer.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        txtVer.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel7.setText("Arquitectura del SO:");

        txtArq.setEditable(false);
        txtArq.setBackground(new java.awt.Color(255, 255, 255));
        txtArq.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        txtArq.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        cmbFile.setFont(new java.awt.Font("Microsoft YaHei", 0, 10)); // NOI18N
        cmbFile.setAutoscrolls(true);
        cmbFile.setMaximumSize(new java.awt.Dimension(28, 20));
        cmbFile.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbFileItemStateChanged(evt);
            }
        });

        btnUser.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/user-4.png"))); // NOI18N
        btnUser.setMnemonic('u');
        btnUser.setText("Usuarios");
        btnUser.setMaximumSize(new java.awt.Dimension(73, 23));
        btnUser.setMinimumSize(new java.awt.Dimension(73, 23));
        btnUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserActionPerformed(evt);
            }
        });

        btnSalir.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/out.png"))); // NOI18N
        btnSalir.setMnemonic('x');
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/stiffy.png"))); // NOI18N
        btnGuardar.setMnemonic('g');
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnTime.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/clock.png"))); // NOI18N
        btnTime.setMnemonic('t');
        btnTime.setText("Date/Time");
        btnTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimeActionPerformed(evt);
            }
        });

        btnSoft.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnSoft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/floppy.png"))); // NOI18N
        btnSoft.setMnemonic('f');
        btnSoft.setText("Sofware");
        btnSoft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSoftActionPerformed(evt);
            }
        });

        btnOtro.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnOtro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/star-2.png"))); // NOI18N
        btnOtro.setMnemonic('o');
        btnOtro.setText("Otros");
        btnOtro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOtroActionPerformed(evt);
            }
        });

        rbTodo.setFont(new java.awt.Font("Microsoft YaHei", 0, 11)); // NOI18N
        rbTodo.setMnemonic('l');
        rbTodo.setText("Mostrar todo");
        rbTodo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbTodoItemStateChanged(evt);
            }
        });

        txtView.setEditable(false);
        txtView.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        txtView.setForeground(new java.awt.Color(0, 0, 153));
        txtView.setDisabledTextColor(new java.awt.Color(0, 0, 153));
        txtView.setPreferredSize(new java.awt.Dimension(8, 6));
        jScrollPane1.setViewportView(txtView);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(4, 4, 4)
                                        .addComponent(txtEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(4, 4, 4)
                                        .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(4, 4, 4)
                                        .addComponent(txtSO, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(4, 4, 4)
                                        .addComponent(txtVer, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(4, 4, 4)
                                        .addComponent(txtArq, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(27, 27, 27)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(cmbFile, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnReco)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnUser, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnGral, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnSoft, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(btnTime, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(rbTodo)
                                                    .addComponent(btnOtro, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel3))
                            .addComponent(txtEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel4))
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel5))
                            .addComponent(txtSO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel6))
                            .addComponent(txtVer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel7))
                            .addComponent(txtArq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnReco)
                            .addComponent(btnUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGral)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(btnSoft)))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTime)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(btnOtro)))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(1, 1, 1)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rbTodo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnSalir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGralActionPerformed
        Accion(2, null);
    }//GEN-LAST:event_btnGralActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (salir) {
            Exit();
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnRecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecoActionPerformed
        Accion(1, null);
    }//GEN-LAST:event_btnRecoActionPerformed

    private void cmbFileItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbFileItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            Accion(7, evt);
        }
    }//GEN-LAST:event_cmbFileItemStateChanged

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        Exit();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        ValidaTxt();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserActionPerformed
        Accion(4, null);
    }//GEN-LAST:event_btnUserActionPerformed

    private void btnTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimeActionPerformed
        Accion(3, null);
    }//GEN-LAST:event_btnTimeActionPerformed

    private void rbTodoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbTodoItemStateChanged
        Todo();
    }//GEN-LAST:event_rbTodoItemStateChanged

    private void btnSoftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSoftActionPerformed
        Accion(5, null);
    }//GEN-LAST:event_btnSoftActionPerformed

    private void btnOtroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOtroActionPerformed
        Accion(6, null);
    }//GEN-LAST:event_btnOtroActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGral;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnOtro;
    private javax.swing.JButton btnReco;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSoft;
    private javax.swing.JButton btnTime;
    private javax.swing.JButton btnUser;
    private javax.swing.JComboBox cmbFile;
    private javax.swing.JFileChooser fchGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton rbTodo;
    private javax.swing.JTextField txtArq;
    private javax.swing.JTextField txtEquipo;
    private javax.swing.JTextField txtSO;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JTextField txtVer;
    private javax.swing.JTextPane txtView;
    // End of variables declaration//GEN-END:variables
}
