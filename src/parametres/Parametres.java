package parametres;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 * @deprecated 
 */
public class Parametres extends JDialog {

    ParametresModele parametresModele;

    public Parametres(JFrame parent, ParametresModele parametresModele) {
        super(parent, "Param�tre", true);
        this.parametresModele = parametresModele;

        initComponents();

        if ("EU".equals(parametresModele.getNorme())) {
            btnNormeEU.setSelected(true);
        } else {
            btnNormeUS.setSelected(true);
        }

        sliderFrequence.setValue(parametresModele.getFrequencePicots());

        tableControle.setValueAt(KeyEvent.getKeyText(parametresModele.getKeySpatialization()), 0, 1);
        tableControle.setValueAt(KeyEvent.getKeyText(parametresModele.getKeyRotation()), 1, 1);
        tableControle.setValueAt(KeyEvent.getKeyText(parametresModele.getKeyChangeBit()), 2, 1);
        //tableControle.setValueAt(KeyEvent.getKeyText(parametresModele.getKeySuppresion()), X, 1);
        //tableControle.setValueAt(KeyEvent.getKeyText(parametresModele.getKeyEchapement()), X, 1);

        checkboxSonActif.setSelected(parametresModele.getSon());
    }

    protected static int showKeyChooser(JDialog frame) {
        JDialog dialog = new JDialog(frame, true);
        dialog.setUndecorated(true);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(frame);

        JLabel jLabel = new JLabel("Hello world");
        jLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dialog.add(jLabel);

        KeyInput keyInput = new KeyInput();
        dialog.addKeyListener(keyInput);

        dialog.pack();
        //dialog.setModal(true);
        dialog.setVisible(true);

        return keyInput.getKey();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.ButtonGroup buttonGroup1 = new javax.swing.ButtonGroup();
        Appliquer = new javax.swing.JButton();
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel lblTitre = new javax.swing.JLabel();
        javax.swing.JPanel pnlNorme = new javax.swing.JPanel();
        javax.swing.JSeparator jSeparator1 = new javax.swing.JSeparator();
        javax.swing.JLabel lblTitreNorme = new javax.swing.JLabel();
        btnNormeEU = new javax.swing.JRadioButton();
        btnNormeUS = new javax.swing.JRadioButton();
        javax.swing.JPanel pnlControle = new javax.swing.JPanel();
        javax.swing.JLabel lblControles = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        tableControle = new javax.swing.JTable();
        javax.swing.JSeparator jSeparator2 = new javax.swing.JSeparator();
        javax.swing.JPanel pnlSon = new javax.swing.JPanel();
        checkboxSonActif = new javax.swing.JCheckBox();
        javax.swing.JLabel lblTitreSon = new javax.swing.JLabel();
        javax.swing.JSeparator jSeparator4 = new javax.swing.JSeparator();
        javax.swing.JPanel pnlFrequence = new javax.swing.JPanel();
        javax.swing.JSeparator jSeparator3 = new javax.swing.JSeparator();
        sliderFrequence = new javax.swing.JSlider();
        javax.swing.JLabel lblTitreFrequence = new javax.swing.JLabel();
        lblValFrequence = new javax.swing.JLabel();
        Ok = new javax.swing.JButton();
        Annuler = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(565, 369));
        setPreferredSize(new java.awt.Dimension(800, 600));

        Appliquer.setText("Appliquer");
        Appliquer.setToolTipText("");
        Appliquer.setActionCommand("Appliquer");
        Appliquer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AppliquerActionPerformed(evt);
            }
        });

        lblTitre.setText("Parametres");

        lblTitreNorme.setText("Norme");

        buttonGroup1.add(btnNormeEU);
        btnNormeEU.setText("EU");
        btnNormeEU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNormeEUActionPerformed(evt);
            }
        });

        buttonGroup1.add(btnNormeUS);
        btnNormeUS.setText("US");
        btnNormeUS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNormeUSActionPerformed(evt);
            }
        });

        lblControles.setText("Controles");

        tableControle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Spatialisation", null},
                {"Rotation", null},
                {"Changer etat du fil", null}
            },
            new String [] {
                "Controle", "Touche"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableControle.setPreferredSize(new java.awt.Dimension(87, 50));
        tableControle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableControleMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableControle);

        javax.swing.GroupLayout pnlControleLayout = new javax.swing.GroupLayout(pnlControle);
        pnlControle.setLayout(pnlControleLayout);
        pnlControleLayout.setHorizontalGroup(
            pnlControleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlControleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblControles)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlControleLayout.setVerticalGroup(
            pnlControleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblControles)
                .addGap(5, 5, 5)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlNormeLayout = new javax.swing.GroupLayout(pnlNorme);
        pnlNorme.setLayout(pnlNormeLayout);
        pnlNormeLayout.setHorizontalGroup(
            pnlNormeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNormeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNormeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNormeUS)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitreNorme)
                    .addComponent(btnNormeEU))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlControle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlNormeLayout.setVerticalGroup(
            pnlNormeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNormeLayout.createSequentialGroup()
                .addGroup(pnlNormeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNormeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitreNorme)
                        .addGap(5, 5, 5)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNormeEU)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNormeUS))
                    .addComponent(pnlControle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        checkboxSonActif.setText("Actif");
        checkboxSonActif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxSonActifActionPerformed(evt);
            }
        });

        lblTitreSon.setText("Son");

        sliderFrequence.setMaximum(2000);
        sliderFrequence.setMinimum(100);
        sliderFrequence.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderFrequenceStateChanged(evt);
            }
        });

        lblTitreFrequence.setText("Frequence ");

        javax.swing.GroupLayout pnlFrequenceLayout = new javax.swing.GroupLayout(pnlFrequence);
        pnlFrequence.setLayout(pnlFrequenceLayout);
        pnlFrequenceLayout.setHorizontalGroup(
            pnlFrequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFrequenceLayout.createSequentialGroup()
                .addGroup(pnlFrequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFrequenceLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlFrequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTitreFrequence)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(sliderFrequence, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(pnlFrequenceLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(lblValFrequence, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFrequenceLayout.setVerticalGroup(
            pnlFrequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFrequenceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitreFrequence)
                .addGap(7, 7, 7)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sliderFrequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblValFrequence, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Ok.setText("Ok");
        Ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OkActionPerformed(evt);
            }
        });

        Annuler.setText("Annuler");
        Annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnulerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSonLayout = new javax.swing.GroupLayout(pnlSon);
        pnlSon.setLayout(pnlSonLayout);
        pnlSonLayout.setHorizontalGroup(
            pnlSonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitreSon)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkboxSonActif))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlSonLayout.createSequentialGroup()
                .addComponent(pnlFrequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(Ok)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Annuler)
                .addGap(122, 122, 122))
        );
        pnlSonLayout.setVerticalGroup(
            pnlSonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitreSon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkboxSonActif)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlSonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlSonLayout.createSequentialGroup()
                        .addComponent(pnlFrequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSonLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnlSonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Ok)
                            .addComponent(Annuler))
                        .addGap(29, 29, 29))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTitre)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pnlNorme, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNorme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(451, 451, 451)
                .addComponent(Appliquer)
                .addContainerGap(247, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Appliquer)
                .addGap(451, 451, 451))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNormeUSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNormeUSActionPerformed
        parametresModele.setNorme("US");
    }//GEN-LAST:event_btnNormeUSActionPerformed

    private void checkboxSonActifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxSonActifActionPerformed
        parametresModele.setSon(String.valueOf(checkboxSonActif.isSelected()));
    }//GEN-LAST:event_checkboxSonActifActionPerformed

    private void sliderFrequenceStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderFrequenceStateChanged
        String text = Integer.toString(sliderFrequence.getValue());
        parametresModele.setFreqPicots(text);
        lblValFrequence.setText(text);
    }//GEN-LAST:event_sliderFrequenceStateChanged

    private void tableControleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableControleMouseClicked
        tableControle.setValueAt(KeyEvent.getKeyText(showKeyChooser(this)), tableControle.getSelectedRow(), 1);
    }//GEN-LAST:event_tableControleMouseClicked

    private void btnNormeEUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNormeEUActionPerformed
        parametresModele.setNorme("EU");
    }//GEN-LAST:event_btnNormeEUActionPerformed

    private void AppliquerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AppliquerActionPerformed
        try {
            parametresModele.sauvegarder();
        } catch (IOException ex) {
            // TODO informer l'utilisateur qu'il y a eu une erreur
            Logger.getLogger(Parametres.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AppliquerActionPerformed

    private void OkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OkActionPerformed
        AppliquerActionPerformed(evt);
        dispose();
    }//GEN-LAST:event_OkActionPerformed

    private void AnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnnulerActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Annuler;
    private javax.swing.JButton Appliquer;
    private javax.swing.JButton Ok;
    private javax.swing.JRadioButton btnNormeEU;
    private javax.swing.JRadioButton btnNormeUS;
    private javax.swing.JCheckBox checkboxSonActif;
    private javax.swing.JLabel lblValFrequence;
    private javax.swing.JSlider sliderFrequence;
    private javax.swing.JTable tableControle;
    // End of variables declaration//GEN-END:variables
}