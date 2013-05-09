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

import Crypto.AESCrypt;
import Crypto.SecurityFile;
import Process.IdVal;
import Process.OpenValues;
import Process.OperationBD;
import Process.WindowStat;
import Windows.Carga;
import Windows.ModalDialog;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 * Apertura de un proyecto existente
 *
 * @author andy737-1
 * @version 1.1
 */
public class Open extends javax.swing.JPanel {

    private ModalDialog md;
    private OperationBD lc;
    private OpenValues ov;
    private boolean flag;
    private IdVal iv;
    private SecurityFile sf;
    private AESCrypt aes;
    private WindowStat stid;

    /**
     * Constructor inicia variables
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Open() {
        stid = WindowStat.getWinInstance();
        sf = SecurityFile.getInstance();
        iv = IdVal.getInstance();
        ov = OpenValues.getInstance();
        lc = null;
        flag = false;
        initComponents();
        LoadCombo();
        cmbProyectoO.setSelectedIndex(-1);
    }

    private void OpenFile() {
        lc = new OperationBD(8);
        if (!lc.ErroSta() && lc.getFile().exists()) {
            if (!stid.getEstadoID().equals(lc.getFile().toString())) {
                stid.setEstadoId(lc.getFile().toString());
                String tmp1;
                JPanel pn = new JPanel();
                JPasswordField pwd = new JPasswordField(25);
                JLabel lb = new JLabel("Ingresa el password del archivo: \n");
                pn.add(lb);
                pn.add(pwd);

                if (JOptionPane.showConfirmDialog(null, pn, "Descrifrado temporal de archivo .afa.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
                    tmp1 = new String(pwd.getPassword());
                    sf.setPass(tmp1);
                    sf.setIn(lc.getFile().toString());
                    try {
                        aes = new AESCrypt(sf.getPass());
                    } catch (GeneralSecurityException ex) {
                        /*Ignore*/
                    } catch (UnsupportedEncodingException ex) {
                        /*Ignore*/
                    }

                    sf.setOut(lc.getFile().toString().replace("afa", "") + "txt");
                    final Carga cg = new Carga("Descifrando...");
                    final Thread load = new Thread() {
                        @Override
                        public void run() {
                            cg.setVisible(true);
                        }
                    };
                    load.start();
                    Thread ope = new Thread() {
                        @Override
                        public void run() {
                            try {
                                aes.ProcessDe();
                                Report rp = new Report(lc.getFile().toString().replace("afa", "") + "txt");
                                rp.setVisible(true);
                            } catch (IOException ex) {
                                stid.setEstadoId("");
                                md = new ModalDialog();
                                md.setDialogo("El archivo no puede ser leido.");
                                md.setTitulo("Error de lectuta/escritura");
                                md.DialogErrFix();
                            } catch (GeneralSecurityException ex) {
                                stid.setEstadoId("");
                                md = new ModalDialog();
                                md.setDialogo("El descrifrado falló.");
                                md.setTitulo("Error de algoritmo de cifrado");
                                md.DialogErrFix();
                            } finally {
                                cg.dispose();
                                load.stop();
                            }
                        }
                    };
                    ope.start();
                } else {
                    stid.setEstadoId("");
                }
            } else {
                md = new ModalDialog();
                md.setDialogo("El archivo seleccionado ya se encuentra abierto, elige un archivo diferente.");
                md.setTitulo("Error de validación");
                md.DialogErrFix();
            }
        } else {
            stid.setEstadoId("");
            md = new ModalDialog();
            md.setDialogo("El archivo no existe.\nAsegurate que se encuentre en la misma ruta cargada en la BD.");
            md.setTitulo("Error de lectuta");
            md.DialogErrFix();
        }
    }

    /**
     * Carga combobox
     */
    public void LoadCombo() {
        cmbProyectoO.removeAllItems();
        cmbProyectoO.addItem(null);
        lc = new OperationBD(2);
        lc.getCombo();
        if (!lc.ErroSta()) {
            for (int j = 0; j < lc.getCombo().size(); j++) {
                cmbProyectoO.addItem(lc.getCombo().get(j));
                if (lc.getCombo() == null) {
                    break;
                }
            }
            flag = true;
        }
        cmbProyectoO.setSelectedIndex(-1);
    }

    private void InitProcess() {
        md = new ModalDialog();
        md.setDialogo("¿Deseas continuar con la apertura del proyecto seleccionado?");
        md.setTitulo("Confirmación");
        md.DialogQues();
        if (md.getSeleccion() == 0) {
            OpenFile();
        }
    }

    private void ValidaCombo() {
        if (cmbProyectoO.getSelectedIndex() == -1) {
            md = new ModalDialog();
            md.setDialogo("Selecciona un proyecto.");
            md.setTitulo("Error de validación");
            md.DialogErrFix();
            cmbProyectoO.requestFocus();
        } else {
            InitProcess();
        }
    }

    private void ExitApp() {
        md = new ModalDialog();
        md.setDialogo("¿Deseas salir de la aplicación?");
        md.setTitulo("Salir");
        md.DialogQues();
        if (md.getSeleccion() == 0) {
            File fl = new File(Report.file);
            if (fl.exists()) {
                fl.delete();
            }
            System.exit(0);
        }
    }

    private void ViewInfo(java.awt.event.ItemEvent evt) {
        String[] atrib = {"Id. Proyecto: ", "Nombre: ", "Descripción: ", "Autor: ", "Fecha de Creación: ", "Hora de Creación: ", "Id. Archivo Cargado: ", "Extensión: ", "Nombre de Archivo: ", "Tamaño: ", "Tipo de Cifrado: ", "Directorio: ", "Fecha de Recolección: ", "Hora de Recolección: ", "Fecha de Carga: ", "Hora de Carga: "};
        if (evt.getStateChange() == ItemEvent.SELECTED && flag) {
            txtaInfo.setText("");
            ov.setId(cmbProyectoO.getSelectedItem().toString());
            iv.setId(ov.getId());
            lc = new OperationBD(4);
            for (int i = 0; i < lc.getInfo().size(); i++) {
                txtaInfo.append(atrib[i]);
                txtaInfo.append(lc.getInfo().get(i).toString() + "\n");
            }
            txtaInfo.setCaretPosition(0);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        cmbProyectoO = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtaInfo = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        btnSalir = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setPreferredSize(new java.awt.Dimension(453, 298));

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel1.setText("Selecciona un proyecto:");

        cmbProyectoO.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbProyectoOItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel2.setText("Información:");

        txtaInfo.setEditable(false);
        txtaInfo.setColumns(20);
        txtaInfo.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        txtaInfo.setForeground(new java.awt.Color(0, 0, 153));
        txtaInfo.setLineWrap(true);
        txtaInfo.setRows(5);
        txtaInfo.setDisabledTextColor(new java.awt.Color(0, 0, 153));
        txtaInfo.setEnabled(false);
        jScrollPane2.setViewportView(txtaInfo);

        btnSalir.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/out.png"))); // NOI18N
        btnSalir.setMnemonic('x');
        btnSalir.setText("Salir");
        btnSalir.setMaximumSize(new java.awt.Dimension(93, 25));
        btnSalir.setMinimumSize(new java.awt.Dimension(93, 25));
        btnSalir.setPreferredSize(new java.awt.Dimension(93, 25));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnOpen.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book-lines-2.png"))); // NOI18N
        btnOpen.setMnemonic('o');
        btnOpen.setText("Abrir");
        btnOpen.setMaximumSize(new java.awt.Dimension(93, 25));
        btnOpen.setMinimumSize(new java.awt.Dimension(93, 25));
        btnOpen.setPreferredSize(new java.awt.Dimension(93, 25));
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(cmbProyectoO, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(0, 208, Short.MAX_VALUE))
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbProyectoO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        ValidaCombo();
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        ExitApp();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void cmbProyectoOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbProyectoOItemStateChanged
        ViewInfo(evt);
    }//GEN-LAST:event_cmbProyectoOItemStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cmbProyectoO;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea txtaInfo;
    // End of variables declaration//GEN-END:variables
}
