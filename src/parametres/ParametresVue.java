package parametres;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ParametresVue extends javax.swing.JDialog {
    
    ParametresModele parametresModele;

    /**
     * Creates new form ParametresVue
     */
    public ParametresVue(java.awt.Frame parent, ParametresModele parametresModele) {
        super(parent, true);
        this.parametresModele = parametresModele;
        setResizable(false);
        
        
        initComponents();
        
        if ("EU".equals(parametresModele.getNorme())) {
            btnNormeEU.setSelected(true);
        } else {
            btnNormeUS.setSelected(true);
        }

        sliderFrequence.setValue(parametresModele.getFrequencePicots());

        tableControle.setValueAt(java.awt.event.KeyEvent.getKeyText(parametresModele.getKeySpatialization()), 0, 1);
        tableControle.setValueAt(java.awt.event.KeyEvent.getKeyText(parametresModele.getKeyRotation()), 1, 1);
        tableControle.setValueAt(java.awt.event.KeyEvent.getKeyText(parametresModele.getKeyChangeBit()), 2, 1);
        //tableControle.setValueAt(KeyEvent.getKeyText(parametresModele.getKeySuppresion()), X, 1);
        //tableControle.setValueAt(KeyEvent.getKeyText(parametresModele.getKeyEchapement()), X, 1);

        checkboxSonActif.setSelected(parametresModele.getSon());
    }
    
    protected static int showKeyChooser(javax.swing.JDialog frame) {
        javax.swing.JDialog dialog = new javax.swing.JDialog(frame, true);
        dialog.setUndecorated(true);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(frame);

        javax.swing.JLabel jLabel = new javax.swing.JLabel("Hello world");
        jLabel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK));
        dialog.add(jLabel);

        KeyInput keyInput = new KeyInput();
        dialog.addKeyListener(keyInput);

        dialog.pack();
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

        javax.swing.JLabel lblTitre = new javax.swing.JLabel();
        javax.swing.JPanel pnlNorme = new javax.swing.JPanel();
        javax.swing.JSeparator jSeparator1 = new javax.swing.JSeparator();
        javax.swing.JLabel lblTitreNorme = new javax.swing.JLabel();
        btnNormeEU = new javax.swing.JRadioButton();
        btnNormeUS = new javax.swing.JRadioButton();
        javax.swing.JPanel pnlSon = new javax.swing.JPanel();
        checkboxSonActif = new javax.swing.JCheckBox();
        javax.swing.JLabel lblTitreSon = new javax.swing.JLabel();
        javax.swing.JSeparator jSeparator4 = new javax.swing.JSeparator();
        javax.swing.JPanel pnlFrequence = new javax.swing.JPanel();
        javax.swing.JSeparator jSeparator3 = new javax.swing.JSeparator();
        sliderFrequence = new javax.swing.JSlider();
        javax.swing.JLabel lblTitreFrequence = new javax.swing.JLabel();
        lblValFrequence = new javax.swing.JLabel();
        javax.swing.JPanel pnlControle = new javax.swing.JPanel();
        javax.swing.JLabel lblControles = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        tableControle = new javax.swing.JTable();
        javax.swing.JSeparator jSeparator2 = new javax.swing.JSeparator();
        Annuler = new javax.swing.JButton();
        Appliquer = new javax.swing.JButton();
        Ok = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitre.setText("Parametres");

        lblTitreNorme.setText("Norme");

        btnNormeEU.setText("EU");
        btnNormeEU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNormeEUActionPerformed(evt);
            }
        });

        btnNormeUS.setText("US");
        btnNormeUS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNormeUSActionPerformed(evt);
            }
        });

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNormeLayout.setVerticalGroup(
            pnlNormeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNormeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitreNorme)
                .addGap(5, 5, 5)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNormeEU)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNormeUS)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        checkboxSonActif.setText("Actif");
        checkboxSonActif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxSonActifActionPerformed(evt);
            }
        });

        lblTitreSon.setText("Son");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
                .addContainerGap()
                .addGroup(pnlFrequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTitreFrequence)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(sliderFrequence, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
            .addGroup(pnlFrequenceLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(lblValFrequence, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlFrequenceLayout.setVerticalGroup(
            pnlFrequenceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFrequenceLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitreFrequence)
                .addGap(7, 7, 7)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sliderFrequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblValFrequence, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

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

        Annuler.setText("Annuler");
        Annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnnulerActionPerformed(evt);
            }
        });

        Appliquer.setText("Appliquer");
        Appliquer.setToolTipText("");
        Appliquer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AppliquerActionPerformed(evt);
            }
        });

        Ok.setText("Ok");
        Ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlFrequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Ok)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Annuler)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Appliquer))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitre)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pnlNorme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pnlControle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnlSon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 20, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pnlNorme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlSon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnlControle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlFrequence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Annuler)
                            .addComponent(Appliquer)
                            .addComponent(Ok))
                        .addGap(5, 5, 5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNormeEUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNormeEUActionPerformed
        parametresModele.setNorme("EU");
    }//GEN-LAST:event_btnNormeEUActionPerformed

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
        tableControle.setValueAt(java.awt.event.KeyEvent.getKeyText(showKeyChooser(this)), tableControle.getSelectedRow(), 1);
    }//GEN-LAST:event_tableControleMouseClicked

    private void AnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnnulerActionPerformed
        try {
            // On recharges les anciens param�tre
            parametresModele.setProprietes(ParametresModele.load(parametresModele.getPathConfig()));
        } catch (IOException ex) {
        }
        dispose();
    }//GEN-LAST:event_AnnulerActionPerformed

    private void AppliquerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AppliquerActionPerformed
        try {
            parametresModele.sauvegarder();
        } catch (java.io.IOException ex) {
            // TODO informer l'utilisateur qu'il y a eu une erreur
            java.util.logging.Logger.getLogger(Parametres.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AppliquerActionPerformed

    private void OkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OkActionPerformed
        AppliquerActionPerformed(evt);
        dispose();
    }//GEN-LAST:event_OkActionPerformed

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