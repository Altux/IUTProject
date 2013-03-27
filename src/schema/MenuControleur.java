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
     * SchemaMenu correspond à l'inerface du menu
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
     * @param v constructeur du contr�leur du menu permet de contr�ler tous
     * les activit� des op�rations du menu
     */
    public MenuControleur(SchemaMenu m, SchemaVue v) {
        this.m = m;
        this.v = v;
    }

    /**
     *
     * @param e prend un �v�nement Permet de savoir la valeur du bouton du
     * menu s�lectionn� et ainsi faire une action en cons�quent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            //on vérifie la valeur du bouton 
            case SchemaMenu.AC_OPEN:
                open();
                break;

            case SchemaMenu.AC_EXIT:
                System.exit(0);
        }

    }

    protected void open() {
        //on d�finit le r&pertoire par défaut pour ouvrir notre sl�lection de fichier
        JFileChooser f = new JFileChooser(SchemaVue.REP_SAVE);
        int returnVal = f.showOpenDialog(m);
        //Si le jFileChooser à fonctionné alors on peut continuer	
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            savePath = f.getSelectedFile().getAbsolutePath();

            try {
                //on affiche alors le nouveau sch�ma
                v.newSchema(GestionaireFichier.loadFileShemat(savePath));
                // permet de redessiner l'affichage sinon l'ancienne image reste présente 
                // et la  nouvelle apparait au dessus de l'autre
                v.repaint();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
}
