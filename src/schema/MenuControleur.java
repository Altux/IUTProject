package schema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;

/**
 *
 * @author groupe interface
 */
public class MenuControleur implements ActionListener {

    /**
     * SchemaMenu correspond Ã  l'inerface du menu
     *
     * ({
     *
     * @see SchemaMenu})
     */
    protected SchemaMenu m;
    /**
     * SchemaVue l'interface de notre application ({
     *
     * @see SchemaMenu})
     */
    protected SchemaVue v;
    /**
     * savePath: le chemin que nous prenons pour ouvrir le fichier contient des
     * codes
     */
    protected String savePath = null;

    /**
     *
     * @param m
     * @param v constructeur du contrôleur du menu permet de contrôler tous
     * les activité des opérations du menu
     */
    public MenuControleur(SchemaMenu m, SchemaVue v) {
        this.m = m;
        this.v = v;
    }

    /**
     *
     * @param e prend un événement Permet de savoir la valeur du bouton du
     * menu sélectionné et ainsi faire une action en conséquent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            //on vÃ©rifie la valeur du bouton 
            case SchemaMenu.AC_OPEN:
                open();
                break;

            case SchemaMenu.AC_EXIT:
                System.exit(0);
        }

    }

    protected void open() {
        //on définit le r&pertoire par dÃ©faut pour ouvrir notre slélection de fichier
        JFileChooser f = new JFileChooser(SchemaVue.REP_SAVE);
        int returnVal = f.showOpenDialog(m);
        //Si le jFileChooser Ã  fonctionnÃ© alors on peut continuer	
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            savePath = f.getSelectedFile().getAbsolutePath();

            try {
                //on affiche alors le nouveau schêma
                v.newSchema(GestionaireFichier.loadFileShemat(savePath));
                // permet de redessiner l'affichage sinon l'ancienne image reste prÃ©sente 
                // et la  nouvelle apparait au dessus de l'autre
                v.repaint();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
}
