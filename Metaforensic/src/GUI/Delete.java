/*
 * To change this template, choose Tools | Templates
 * and Open the template in the editor.
 */
package GUI;

import Process.DeleteValues;
import Process.OperationBD;
import Windows.Clean;
import Windows.ModalDialog;
import java.awt.event.ItemEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author andy737-1
 */
public class Delete extends javax.swing.JPanel {

    private ModalDialog md;
    private OperationBD lc;
    private DeleteValues dv;

    /**
     * Creates new form Open
     */
    public Delete() {
        dv = DeleteValues.getInstance();
        lc = null;
        initComponents();
        LoadCombo();
        cmbProyecto.setSelectedIndex(-1);
    }

    private void LoadCombo() {
        lc = new OperationBD(5);
        lc.getCombo();
        if (!lc.ErroSta()) {
            for (int j = 0; j < lc.getCombo().size(); j++) {
                cmbProyecto.addItem(lc.getCombo().get(j));
            }
        }
    }

    public void CleanGUIDirect() {
        if (this != null) {
            Clean.getAllComponents(this);
            Clean.CleanTxt();
            Clean.CleanCombo();
            Clean.CleanAreaTxt();
        }
    }

    private void InitProcess() {
        md = new ModalDialog();
        md.setDialogo("¿Deseas eliminar el proyecto seleccionado?");
        md.setFrame(this);
        md.setTitulo("Confirmación");
        md.Dialog();
        if (md.getSeleccion() == 0) {
            dv.setId(cmbProyecto.getSelectedItem().toString());
            lc = new OperationBD(3);
            if (!lc.ErroSta()) {
                cmbProyecto.removeAllItems();
                LoadCombo();
                cmbProyecto.setSelectedIndex(-1);
                CleanGUIDirect();
                md = new ModalDialog();
                md.setDialogo("El proyecto fue eliminado con exito.");
                md.setFrame(this);
                md.setTitulo("Confirmación");
                md.Dialog();
            }

        }
    }

    private void ViewInfo(java.awt.event.ItemEvent evt) {
        String[] atrib = {"Id. Proyecto: ", "Nombre: ", "Descripción: ", "Autor: ", "Fecha de Creación: ", "Hora de Creación: ", "Id. Archivo cargado: ", "tipo: ", "Tamaño: ", "Tipo de Cifrado: ", "Directorio: ", "Fecha de Recolección: ", " Hora de Recolección: ", "Fecha de Carga: ", "Hora de Carga: "};
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            dv.setId(cmbProyecto.getSelectedItem().toString());
            lc = new OperationBD(4);
            for (int i = 1; i < lc.getInfo().size() - 1; i++) {
                txtaCon.append(atrib[i]);
                txtaCon.append(lc.getInfo().get(i).toString() + "\n");
            }
        }

    }

    private void ValidaCombo() {
        if (cmbProyecto.getSelectedIndex() == -1) {
            md = new ModalDialog();
            md.setDialogo("Selecciona un proyecto.");
            md.setFrame(this);
            md.setTitulo("Error de validación");
            md.DialogErrFix();
            cmbProyecto.requestFocus();
        } else {
            InitProcess();
        }
    }

    private void ExitApp() {
        int seleccion = JOptionPane.showOptionDialog(this, "¿Deseas salir de la aplicación?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Aceptar", "Cancelar"}, "Cancelar");
        if (seleccion == 0) {
            System.exit(0);
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
        cmbProyecto = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtaCon = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        btnSalir = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setPreferredSize(new java.awt.Dimension(453, 298));

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei", 0, 12)); // NOI18N
        jLabel1.setText("Selecciona un proyecto:");

        cmbProyecto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbProyectoItemStateChanged(evt);
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

        btnEliminar.setFont(new java.awt.Font("Microsoft YaHei", 1, 11)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/minus.png"))); // NOI18N
        btnEliminar.setMnemonic('d');
        btnEliminar.setText("Eliminar");
        btnEliminar.setMaximumSize(new java.awt.Dimension(93, 25));
        btnEliminar.setMinimumSize(new java.awt.Dimension(93, 25));
        btnEliminar.setPreferredSize(new java.awt.Dimension(93, 25));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/spam-2.png"))); // NOI18N
        jLabel3.setText("Esta acción no es reversible");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(cmbProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 208, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(42, 42, 42))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        ValidaCombo();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        ExitApp();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void cmbProyectoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbProyectoItemStateChanged
        ViewInfo(evt);
    }//GEN-LAST:event_cmbProyectoItemStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cmbProyecto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea txtaCon;
    // End of variables declaration//GEN-END:variables
}
