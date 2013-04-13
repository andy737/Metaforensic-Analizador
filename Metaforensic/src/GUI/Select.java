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
package GUI;

import Process.DateTime;
import Process.IdVal;
import Process.OperationBD;
import Process.SelectValues;
import Windows.ModalDialog;
import java.awt.event.ItemEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Visualización de información sobre de proyectos y eventos en la BD
 *
 * @author andy737-1
 * @version 1.0
 */
public class Select extends javax.swing.JPanel {

    private ModalDialog md;
    private FileOutputStream logout;
    private OutputStreamWriter outlog;
    private BufferedWriter outfinal;
    private OperationBD lc;
    private boolean flag;
    private SelectValues sv;
    private IdVal iv;

    /**
     * Constructor Inicialización de variables
     */
    public Select() {
        flag = false;
        iv = IdVal.getInstance();
        sv = SelectValues.getInstance();
        lc = null;
        initComponents();
        LoadCombo();
        cmbProyectoS.setSelectedIndex(-1);
    }

    public final void LoadCombo() {
        cmbProyectoS.removeAllItems();
        cmbProyectoS.addItem(null);
        lc = new OperationBD(2);
        lc.getCombo();
        if (!lc.ErroSta()) {
            for (int i = 0; i < lc.getCombo().size(); i++) {
                cmbProyectoS.addItem(lc.getCombo().get(i));
                if (lc.getCombo() == null) {
                    break;
                }
            }
            flag = true;
        }
        cmbProyectoS.setSelectedIndex(-1);
    }

    private void ExitApp() {
        int seleccion = JOptionPane.showOptionDialog(this, "¿Deseas salir de la aplicación?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Aceptar", "Cancelar"}, "Cancelar");
        if (seleccion == 0) {
            System.exit(0);
        }
    }

    private void SaveFile() {
        boolean ciclo = true;
        while (ciclo) {
            ciclo = SelectDir(txtaCon);
        }
    }

    private void ViewInfo(java.awt.event.ItemEvent evt) {
        String[] atrib = {"Id. Proyecto: ", "Nombre: ", "Descripción: ", "Autor: ", "Fecha de Creación: ", "Hora de Creación: ", "Id. Archivo Cargado: ", "tipo: ", "Nombre de Archivo: ", "Tamaño: ", "Tipo de Cifrado: ", "Directorio: ", "Fecha de Recolección: ", "Hora de Recolección: ", "Fecha de Carga: ", "Hora de Carga: "};
        if (evt.getStateChange() == ItemEvent.SELECTED && flag) {
            rdbTodo.setSelected(false);
            txtaCon.setText("");
            sv.setId(cmbProyectoS.getSelectedItem().toString());
            iv.setId(sv.getId());
            lc = new OperationBD(4);
            for (int i = 0; i < lc.getInfo().size(); i++) {
                txtaCon.append(atrib[i]);
                txtaCon.append(lc.getInfo().get(i).toString() + "\n");
            }
            txtaCon.setCaretPosition(0);
        }

    }

    private void ViewEve() {
        int j = 0;
        String[] atrib = {"Id. Evento: ", "Descripción: ", "Fecha Evento: ", "Hora_evento: "};
        if (flag && !rdbTodo.isSelected()) {
            txtaCon.setText("");
            if (cmbProyectoS.getSelectedItem() == null || cmbProyectoS.getSelectedItem().equals("")) {
                md = new ModalDialog();
                md.setDialogo("Selecciona un proyecto.");
                md.setTitulo("Error de visualización");
                md.setFrame(this);
                md.DialogErrFix();
                cmbProyectoS.requestFocus();
            } else {
                sv.setId(cmbProyectoS.getSelectedItem().toString());
                iv.setId(sv.getId());
                lc = new OperationBD(6);
                for (int i = 0; i < lc.getEven().size(); i++) {
                    if (j == 4) {
                        j = 0;
                        txtaCon.append("******************************************************\n");
                    }
                    txtaCon.append(atrib[j]);
                    txtaCon.append(lc.getEven().get(i).toString() + "\n");
                    j++;
                }
                txtaCon.setCaretPosition(0);
            }
        } else {
            if (rdbTodo.isSelected()) {
                j = 0;
                txtaCon.setText("");
                lc = new OperationBD(7);
                for (int i = 0; i < lc.getEvenAll().size(); i++) {
                    if (j == 4) {
                        j = 0;
                        txtaCon.append("******************************************************\n");
                    }
                    txtaCon.append(atrib[j]);
                    txtaCon.append(lc.getEvenAll().get(i).toString() + "\n");
                    j++;
                }
                txtaCon.setCaretPosition(0);
            }
        }

    }

    private void ViewAll(java.awt.event.ItemEvent evt) {
        String[] atrib = {"Id. Proyecto: ", "Nombre: ", "Descripción: ", "Autor: ", "Fecha de Creación: ", "Hora de Creación: ", "Id. Archivo cargado: ", "tipo: ", "Nombre de Archivo: ", "Tamaño: ", "Tipo de Cifrado: ", "Directorio: ", "Fecha de Recolección: ", "Hora de Recolección: ", "Fecha de Carga: ", "Hora de Carga: "};
        int j = 0;
        if (evt.getStateChange() == ItemEvent.SELECTED && flag) {
            txtaCon.setText("");
            cmbProyectoS.setSelectedIndex(-1);
//            sv.setId(cmbProyectoS.getSelectedItem().toString());
            //          iv.setId(sv.getId());
            lc = new OperationBD(5);
            for (int i = 0; i < lc.getAll().size(); i++) {
                if (j == 16) {
                    j = 0;
                    txtaCon.append("******************************************************\n");
                }
                txtaCon.append(atrib[j]);
                txtaCon.append(lc.getAll().get(i).toString() + "\n");
                j++;
            }
            txtaCon.setCaretPosition(0);
        }

    }

    private void CreateFile(String path) {
        try {
            logout = new FileOutputStream(path + "\\" + DateTime.getDate().toString().replace("-", "") + "_" + DateTime.getTimeMilli().toString().replace(":", "") + "_" + cmbProyectoS.getSelectedItem() + ".log");
            outlog = new OutputStreamWriter(logout, "UTF-8");
            outfinal = new BufferedWriter(outlog);
            txtaCon.write(outfinal);
            outfinal.flush();
        } catch (IOException ex) {
            md.setDialogo("No se pudo crear el archivo " + path + "\\" + DateTime.getDate().toString().replace("-", "") + "_" + DateTime.getTimeMilli().toString().replace(":", "") + "_" + cmbProyectoS.getSelectedItem() + ".log" + " en la carpeta: \n" + path);
            md.setTitulo("Error de archivo");
            md.setFrame(this);
            md.DialogErr();
        } finally {
            if (outfinal != null) {
                try {
                    outfinal.close();
                } catch (IOException ex) {
                    md.setDialogo("No se pudo cerrar corretacmente el archivo " + path + "\\" + DateTime.getDate().toString().replace("-", "") + "_" + DateTime.getTimeMilli().toString().replace(":", "") + "_" + cmbProyectoS.getSelectedItem() + ".log");
                    md.setTitulo("Error de archivo");
                    md.setFrame(this);
                    md.DialogErr();
                }
            }
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
                txt.setText("");
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

    private void ValidaTxt() {
        if (txtaCon.getText().equals("") || txtaCon.getText() == null) {
            md = new ModalDialog();
            md.setDialogo("Selecciona un proyecto o evento.");
            md.setFrame(this);
            md.setTitulo("Error de validación");
            md.DialogErrFix();
            cmbProyectoS.requestFocus();
        } else {
            SaveFile();
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
        fchGuardar = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        cmbProyectoS = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtaCon = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        btnSalir = new javax.swing.JButton();
        btnEventos = new javax.swing.JButton();
        rdbTodo = new javax.swing.JRadioButton();
        btnGuarda = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        fchGuardar.setApproveButtonText("Guardar");
        fchGuardar.setCurrentDirectory(null);
        fchGuardar.setDialogTitle("Guardar información o eventos del proyecto");
        fchGuardar.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        setPreferredSize(new java.awt.Dimension(453, 298));

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel1.setText("Selecciona un proyecto:");

        cmbProyectoS.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbProyectoSItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel2.setText("Información:");

        txtaCon.setEditable(false);
        txtaCon.setColumns(20);
        txtaCon.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        txtaCon.setForeground(new java.awt.Color(0, 0, 153));
        txtaCon.setLineWrap(true);
        txtaCon.setRows(5);
        txtaCon.setDisabledTextColor(new java.awt.Color(0, 0, 153));
        txtaCon.setEnabled(false);
        jScrollPane2.setViewportView(txtaCon);

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

        btnEventos.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnEventos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/clipboard-2.png"))); // NOI18N
        btnEventos.setMnemonic('e');
        btnEventos.setText("Eventos");
        btnEventos.setMaximumSize(new java.awt.Dimension(93, 25));
        btnEventos.setMinimumSize(new java.awt.Dimension(93, 25));
        btnEventos.setPreferredSize(new java.awt.Dimension(93, 25));
        btnEventos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEventosActionPerformed(evt);
            }
        });

        rdbTodo.setText("Mostrar todo");
        rdbTodo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdbTodoItemStateChanged(evt);
            }
        });

        btnGuarda.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnGuarda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/stiffy.png"))); // NOI18N
        btnGuarda.setMnemonic('o');
        btnGuarda.setText("Guardar");
        btnGuarda.setMaximumSize(new java.awt.Dimension(93, 25));
        btnGuarda.setMinimumSize(new java.awt.Dimension(93, 25));
        btnGuarda.setPreferredSize(new java.awt.Dimension(93, 25));
        btnGuarda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnGuarda, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(cmbProyectoS, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addComponent(btnEventos, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(layout.createSequentialGroup()
                .addComponent(rdbTodo)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbProyectoS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEventos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbTodo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuarda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        ExitApp();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaActionPerformed
        ValidaTxt();
    }//GEN-LAST:event_btnGuardaActionPerformed

    private void cmbProyectoSItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbProyectoSItemStateChanged
        ViewInfo(evt);
    }//GEN-LAST:event_cmbProyectoSItemStateChanged

    private void rdbTodoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdbTodoItemStateChanged
        ViewAll(evt);
    }//GEN-LAST:event_rdbTodoItemStateChanged

    private void btnEventosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEventosActionPerformed
        ViewEve();
    }//GEN-LAST:event_btnEventosActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEventos;
    private javax.swing.JButton btnGuarda;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cmbProyectoS;
    private javax.swing.JFileChooser fchGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JRadioButton rdbTodo;
    private javax.swing.JTextArea txtaCon;
    // End of variables declaration//GEN-END:variables
}
