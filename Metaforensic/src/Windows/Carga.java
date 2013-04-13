/*JFrame encargado de la visualización del estado de Carga o generación dentro de la aplicación.
 *Autor: Andrés de Jesús Hernádez Martínez
 *Version: 0.1
 *Fecha de creación: 18 de enero de 2012
 *Fecha de ultima modificación: 30 de enero de 2012
 */
package Windows;

//import com.sun.awt.AWTUtilities;
import java.awt.Dialog.ModalExclusionType;

public class Carga extends javax.swing.JFrame {

    private static final long serialVersionUID = 3535809072139401362L;
    private final FrameIcons ic;

    public Carga(String a) {
        ic = FrameIcons.getInstance();
        WindowsStyle.SetStyle();
        ic.SetIcon();
        this.setIconImages(ic.GetIcon());
        initComponents();
        this.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        this.setDefaultCloseOperation(Carga.DO_NOTHING_ON_CLOSE);
        lbText.setText(a);
        //      AWTUtilities.setWindowOpaque(this, false);
        this.setLocationRelativeTo(null);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pbCarga = new javax.swing.JProgressBar();
        lbText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        pbCarga.setBorderPainted(false);
        pbCarga.setIndeterminate(true);
        pbCarga.setOpaque(true);

        lbText.setText("Generando....");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pbCarga, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbText))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbCarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbText;
    private javax.swing.JProgressBar pbCarga;
    // End of variables declaration//GEN-END:variables
}
