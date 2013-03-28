package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import schema.ActionCommande;
import schema.GestionaireFichier;
import schema.SchemaVue;

/**
 *
 * @author groupe interface
 */
public class MenuBarreControleur implements ActionListener {

    /**
     * MenuBarre correspond � l'interface du menu.
     */
    protected MenuBarre m;
    /**
     * SchemaVue l'interface de notre application ({
     *
     * @see MenuBarre})
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
     * @param v constructeur du contr�leur du menu permet de contr�ler tous les
     * activit� des op�rations du menu
     */
    public MenuBarreControleur(MenuBarre m, SchemaVue v) {
        this.m = m;
        this.v = v;
    }

    /**
     *
     * @param e prend un �v�nement Permet de savoir la valeur du bouton du menu
     * s�lectionn� et ainsi faire une action en cons�quent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            //on v�rifie la valeur du bouton 
            case ActionCommande.AC_OPEN:
                open();
                break;

            case ActionCommande.AC_NEW:
                newS();
                break;

            case ActionCommande.AC_SAVE:
                save();
                break;

            case ActionCommande.AC_SAVEAS:
                saveAs();
                break;

            case ActionCommande.AC_EXIT:
                System.exit(0);


        }

    }

    protected void save() {
        // TODO implement
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    protected void saveAs() {
        // TODO implement
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected void newS() {
        // TODO implement
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
