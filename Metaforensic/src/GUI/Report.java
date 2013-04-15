/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Process.DateTime;
import Process.FileMeta;
import Process.WindowStat;
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
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 * Genera anlisis y reportes
 *
 * @author andy737-1
 * @version 1.0
 */
public class Report extends javax.swing.JFrame {

    private final FrameIcons ic;
    private String file;
    private FileReader in;
    private BufferedReader bin;
    private File fl;
    private ArrayList<FileMeta> files;
    private FileMeta minf;
    private String[] tooltips;
    private boolean cmbs;
    private ModalDialog md;
    private FileOutputStream logout;
    private OutputStreamWriter outlog;
    private BufferedWriter outfinal;
    private WindowStat stw;

    /**
     * Creates new form Report
     */
    public Report(String file) {
        stw = WindowStat.getWinInstance();
        this.file = file;
        in = null;
        cmbs = false;
        bin = null;
        fl = null;
        tooltips = null;
        ic = FrameIcons.getInstance();
        WindowsStyle.SetStyle();
        ic.SetIcon();
        this.setIconImages(ic.GetIcon());
        initComponents();
        this.setLocationRelativeTo(null);
        Clean();
        LoadInfo();
        cmbs = true;
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

                    if (linea.contains("fileName")) {
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
            JOptionPane.showMessageDialog(null, "El archivo no se puede leer.", "Error de lectura", JOptionPane.ERROR_MESSAGE);
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
                /*
                 resultado = Integer.compare(p1.getPosicion(), p2.getPosicion());
                 if (resultado != 0) {
                 return resultado;
                 }*/
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

    private void ViewAll() {
        txtView.setText("");
        String linea;
        try {
            fl = new File(file);
            in = new FileReader(fl);
            bin = new BufferedReader(in);
            while ((linea = bin.readLine()) != null) {

                if (linea.contains("*")) {
                    txtView.append("\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n\n");
                } else {
                    txtView.append(linea + "\n");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "El archivo no se puede leer.", "Error de lectura", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ViewFileInd(java.awt.event.ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.SELECTED && cmbs) {
            try {
                String linea;
                boolean flag = false;
                txtView.setText("");
                fl = new File(file);
                in = new FileReader(fl);
                bin = new BufferedReader(in);
                StringBuffer bf = new StringBuffer(100000000);
                bin.readLine();
                while ((linea = bin.readLine()) != null) {
                    if (linea.contains("*")) {
                        if (flag) {
                            return;
                        }
                        bf.setLength(0);
                    } else {
                        bf.append(linea).append("\n");
                        bf.append("\n");
                        if (linea.contains(cmbFile.getSelectedItem().toString())) {
                            flag = true;
                        }
                        if (flag) {
                            txtView.append(bf.toString());
                            bf.setLength(0);
                        }
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "El archivo no se puede leer.", "Error de lectura", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void ViewFiles() {
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
            txtView.append(files.get(i).getNombre() + "\n");
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
        txtView.append("\n");
        if (pdf > 0) {
            txtView.append("Número de archivos pdf: " + pdf + "\n");
        }
        if (jpg > 0) {
            txtView.append("Número de archivos jpg: " + jpg + "\n");
        }
        if (png > 0) {
            txtView.append("Número de archivos png: " + png + "\n");
        }
        if (docx > 0) {
            txtView.append("Número de archivos docx: " + docx + "\n");
        }
        if (xlsx > 0) {
            txtView.append("Número de archivos xlsx: " + xlsx + "\n");
        }
        if (pptx > 0) {
            txtView.append("Número de archivos pptx: " + pptx + "\n");
        }
        if (doc > 0) {
            txtView.append("Número de archivos doc: " + doc + "\n");
        }
        if (xls > 0) {
            txtView.append("Número de archivos xls: " + xls + "\n");
        }
        if (ppt > 0) {
            txtView.append("Número de archivos ppt: " + ppt + "\n");
        }
        if (odt > 0) {
            txtView.append("Número de archivos odt: " + odt + "\n");
        }
        if (ods > 0) {
            txtView.append("Número de archivos ods: " + ods + "\n");
        }
        if (odp > 0) {
            txtView.append("Número de archivos odp: " + odp + "\n");
        }
        txtView.append("Total de archivos: " + files.size() + "\n");
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

    private boolean SelectDir(JTextArea txt) {

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
                md.DialogCon();
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
                } catch (IOException ex) {
                    md.setDialogo("No se pudo cerrar corretacmente el archivo " + path + "\\" + DateTime.getDate().toString().replace("-", "") + "_" + DateTime.getTimeMilli().toString().replace(":", "") + "_" + "Reporte.txt");
                    md.setTitulo("Error de archivo");
                    md.setFrame(this);
                    md.DialogErr();
                }
            }
        }
    }

    private void FindUsers() {
        try {
            String linea;
            int inicio;
            txtView.setText("");
            fl = new File(file);
            in = new FileReader(fl);
            bin = new BufferedReader(in);
            StringBuffer bf = new StringBuffer(100000000);
            StringBuffer bf1 = new StringBuffer(10000);
            bin.readLine();
            while ((linea = bin.readLine()) != null) {

                if (linea.contains("fileName")) {
                    bf1.setLength(0);
                    bf1.append(linea).append("\n");
                }
                if (linea.contains("user") || linea.contains("users") || linea.contains("author") || linea.contains("users") || linea.contains("creator")) {
                    bf.append("\n");
                    bf.append(bf1);
                    bf.append(linea);

                }
            }
            txtView.append(bf.toString());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "El archivo no se puede leer.", "Error de lectura", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ViewTimes() {
        try {
            String linea;
            int inicio;
            txtView.setText("");
            fl = new File(file);
            in = new FileReader(fl);
            bin = new BufferedReader(in);
            StringBuffer bf = new StringBuffer(100000000);
            StringBuffer bf1 = new StringBuffer(10000);
            bin.readLine();
            while ((linea = bin.readLine()) != null) {

                if (linea.contains("fileName")) {
                    bf1.setLength(0);
                    bf1.append(linea).append("\n");
                }
                if (linea.contains("Creation") || linea.contains("creation") || linea.contains("Modified") || linea.contains("modified") || linea.contains("created") || linea.contains("Created") || linea.contains("Printed") || linea.contains("printed") || linea.contains("Save") || linea.contains("save") || linea.contains("Saved") || linea.contains("saved")) {
                    bf.append("\n");
                    bf.append(bf1);
                    bf.append(linea);

                }
            }
            txtView.append(bf.toString());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "El archivo no se puede leer.", "Error de lectura", JOptionPane.ERROR_MESSAGE);
        }
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
        jButton1 = new javax.swing.JButton();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        txtView = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        cmbFile = new javax.swing.JComboBox();
        btnUser = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnTime = new javax.swing.JButton();

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

        jButton1.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/newspaper-2.png"))); // NOI18N
        jButton1.setMnemonic('a');
        jButton1.setText("Archivos recolectados");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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

        txtView.setColumns(20);
        txtView.setForeground(new java.awt.Color(0, 0, 102));
        txtView.setRows(5);
        jScrollPane2.setViewportView(txtView);

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
        cmbFile.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbFileItemStateChanged(evt);
            }
        });

        btnUser.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/user-4.png"))); // NOI18N
        btnUser.setText("Usuarios");
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
        btnTime.setText("Timestamp");
        btnTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(cmbFile, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnUser)
                                        .addGap(27, 27, 27)
                                        .addComponent(btnTime))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addGap(24, 24, 24)
                                        .addComponent(btnGral))))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(jButton1)
                            .addComponent(btnGral))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUser)
                            .addComponent(btnTime))
                        .addGap(39, 39, 39)
                        .addComponent(jLabel2)
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(cmbFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalir)
                    .addComponent(btnGuardar))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGralActionPerformed
        ViewAll();
    }//GEN-LAST:event_btnGralActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        stw.setEstadoId("");
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ViewFiles();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmbFileItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbFileItemStateChanged
        ViewFileInd(evt);
    }//GEN-LAST:event_cmbFileItemStateChanged

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        stw.setEstadoId("");
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        ValidaTxt();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserActionPerformed
        FindUsers();
    }//GEN-LAST:event_btnUserActionPerformed

    private void btnTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimeActionPerformed
        ViewTimes();
    }//GEN-LAST:event_btnTimeActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGral;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTime;
    private javax.swing.JButton btnUser;
    private javax.swing.JComboBox cmbFile;
    private javax.swing.JFileChooser fchGuardar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtArq;
    private javax.swing.JTextField txtEquipo;
    private javax.swing.JTextField txtSO;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JTextField txtVer;
    private javax.swing.JTextArea txtView;
    // End of variables declaration//GEN-END:variables
}
