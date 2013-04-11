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

import Process.NewValues;
import Process.ValidateInfo;
import Windows.Clean;
import Windows.ModalDialog;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author andy737-1
 */
public class New_ extends javax.swing.JPanel {

    private ModalDialog md;
    private ValidateInfo valinfo;
    private Thread t;
    private NewValues values;

    public New_() {
        initComponents();
        md = null;
        t = null;
        values = NewValues.getInstance();
    }

    private boolean SelectDir(JTextField txt) throws IOException {

        boolean ciclo = false;
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo Metaforensic (.afa)", "afa");
        fchSeleccion.setFileFilter(filtro);
        int rseleccion = fchSeleccion.showDialog(this, "Seleccionar");
        if (rseleccion == JFileChooser.APPROVE_OPTION) {
            File directorio = new File(fchSeleccion.getSelectedFile().toPath().toString());
            if (directorio.isFile() && directorio.exists()) {
                txt.setText(directorio.getPath());
                txt.setForeground(Color.black);
                ciclo = false;
            } else {
                txt.setText("");
                md = new ModalDialog();
                md.setDialogo("El archivo no existe.");
                md.setTitulo("Error de ruta");
                md.setFrame(this);
                md.DialogErrFix();
                txt.requestFocus(true);
                ciclo = true;
            }
        }
        return ciclo;

    }

    private void SeleccionAfa() {
        boolean ciclo = true;
        while (ciclo) {
            try {
                ciclo = SelectDir(txtRuta);
            } catch (IOException ex) {
                Logger.getLogger(New_.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void CleanGUI() throws IOException {
        CleanDialog("Limpiar", "¿Deseas limpiar los campos y opciones?");
    }

    private void SetValues() {
        values.setAutor(txtAutor.getText());
        values.setDescripcion(txtDescripcion.getText());
        values.setNombre(txtNombre.getText());
        values.setRuta(txtRuta.getText());
    }

    private int CleanDialog(String titulo, String dialogo) throws IOException {

        valinfo = new ValidateInfo();
        int estado = 0;
        SetValues();
        valinfo.ValidateNew();
        if (valinfo.getEstado()) {
            md = new ModalDialog();
            md.setDialogo(dialogo);
            md.setTitulo(titulo);
            md.Dialog();
            if (this != null && md.getSeleccion() == 0) {
                Clean.getAllComponents(this);
                Clean.CleanTxt();
                Clean.CleanCombo();
                Clean.CleanCheck();
                Clean.CleanRadio();
                Clean.CleanPaneTxt();
                estado = 1;
            }
            estado = estado + 1;
        } else {
            Clean.getAllComponents(this);
            Clean.CleanTxt();
            Clean.CleanCombo();
            Clean.CleanCheck();
            Clean.CleanRadio();
            Clean.CleanPaneTxt();
            estado = 1;
        }
        return estado;
    }

    private void TxtError(JTextField txt) {
        if (!txt.getText().equals("")) {
            File directorio = new File(txt.getText());
            if (directorio.exists() && directorio.isFile()) {
                txt.setForeground(Color.black);
            } else {
                txt.setForeground(Color.red);
                txt.setText("ERROR");
            }
        }
    }

    private void VerifyErrorTxt(JTextField txt) {
        if (txt.getText().equals("ERROR")) {
            txt.setForeground(Color.black);
            txt.setText("");
        }
    }

    private void ExitApp() {
        int seleccion = JOptionPane.showOptionDialog(this, "¿Deseas salir de la aplicación?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Aceptar", "Cancelar"}, "Cancelar");
        if (seleccion == 0) {
            System.exit(0);
        }
    }

    private Boolean InputDir(JTextField txt) {

        if (!txt.getText().equals("")) {
            File directorio = new File(txt.getText());
            if (directorio.exists() && directorio.isFile()) {
                txt.setText(directorio.getPath());
                return true;
            } else {
                md = new ModalDialog();
                md.setDialogo("El archivo no existe.");
                md.setTitulo("Error de ruta");
                md.setFrame(this);
                t = md.DialogErr();
                t.start();
                txt.requestFocus();
                txt.setText("");
                return false;
            }
        } else {
            return true;
        }

    }
    /*
     * Validación para inicar recolección
     */

    private void ValidateForm() {

        if (InputDir(txtRuta)) {
            valinfo = new ValidateInfo();
            SetValues();
            try {
                valinfo.ValidateNew();
            } catch (IOException ex) {
                Logger.getLogger(New_.class.getName()).log(Level.SEVERE, null, ex);
            }
            int err = valinfo.getError();
            switch (err) {
                case 1:
                    md = new ModalDialog();
                    md.setDialogo("Ingresa un nombre para el proyecto.");
                    md.setFrame(this);
                    md.setTitulo("Error de validación");
                    md.DialogErrFix();
                    txtNombre.requestFocus();
                    break;
                case 2:
                    md = new ModalDialog();
                    md.setDialogo("Ingresa tu nombre.");
                    md.setFrame(this);
                    md.setTitulo("Error de validación");
                    md.DialogErrFix();
                    txtAutor.requestFocus();
                    break;
                case 3:
                    md = new ModalDialog();
                    md.setDialogo("Ingresa una breve descripción del proyecto.");
                    md.setFrame(this);
                    md.setTitulo("Error de validación");
                    md.DialogErrFix();
                    txtDescripcion.requestFocus();
                    break;
                case 4:
                    md = new ModalDialog();
                    md.setDialogo("Ingresa la ruta y nombre del archivo .afa validos.");
                    md.setFrame(this);
                    md.setTitulo("Error de validación");
                    md.DialogErrFix();
                    txtRuta.requestFocus();
                    break;
                case 5:
                    try {
                        md = new ModalDialog();
                        md.setDialogo("¿Deseas continuar con la creación del proyecto?");
                        md.setFrame(this);
                        md.setTitulo("Confirmación");
                        md.Dialog();
                        if (md.getSeleccion() == 0) {
                            //CollectMetadata();
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(New_.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                default:
                    System.exit(0);
            }

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

        fchSeleccion = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtAutor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextPane();
        txtRuta = new javax.swing.JTextField();
        btnCarga = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        fchSeleccion.setApproveButtonText("Seleccionar");
        fchSeleccion.setApproveButtonToolTipText("");
        fchSeleccion.setCurrentDirectory(null);
        fchSeleccion.setDialogTitle("Selecciona un archivo");

        setPreferredSize(new java.awt.Dimension(453, 298));

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel1.setText("Nombre [Proyecto]:");

        txtNombre.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel2.setText("Autor:");

        txtAutor.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel3.setText("Descripción:");

        txtDescripcion.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(txtDescripcion);

        txtRuta.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        txtRuta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRutaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRutaFocusLost(evt);
            }
        });

        btnCarga.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnCarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/inbox.png"))); // NOI18N
        btnCarga.setMnemonic('c');
        btnCarga.setText("Cargar");
        btnCarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel4.setText("Cargar archivo .afa [ruta+nombre]:");

        btnCrear.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plus.png"))); // NOI18N
        btnCrear.setMnemonic('p');
        btnCrear.setText("Crear");
        btnCrear.setMaximumSize(new java.awt.Dimension(93, 25));
        btnCrear.setMinimumSize(new java.awt.Dimension(93, 25));
        btnCrear.setPreferredSize(new java.awt.Dimension(93, 25));
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnLimpiar.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/paper-roll-ripped.png"))); // NOI18N
        btnLimpiar.setMnemonic('l');
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAutor))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre))
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLimpiar)
                .addGap(18, 18, 18)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCarga)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCarga))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        ExitApp();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        ValidateForm();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnCargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargaActionPerformed
        SeleccionAfa();
    }//GEN-LAST:event_btnCargaActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        try {
            CleanGUI();
        } catch (IOException ex) {
            Logger.getLogger(New_.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void txtRutaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRutaFocusLost
        TxtError(txtRuta);
    }//GEN-LAST:event_txtRutaFocusLost

    private void txtRutaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRutaFocusGained
        VerifyErrorTxt(txtRuta);
    }//GEN-LAST:event_txtRutaFocusGained
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCarga;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JFileChooser fchSeleccion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtAutor;
    private javax.swing.JTextPane txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRuta;
    // End of variables declaration//GEN-END:variables
}
