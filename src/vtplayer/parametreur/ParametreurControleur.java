package vtplayer.parametreur;

import vtplayer.VTPlayerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vtplayer.VTPlayerInterface;

/**
 *
 * @author godeau
 */
public class ParametreurControleur extends MouseAdapter implements ActionListener {

    ParametreurModele parametreurModele;
    ParametreurVue parametreurVue;
    VTPlayerInterface vtp = null;

    /**
     * Constructeur.
     * 
     * @param parametreurModele modèle.
     * @param parametreurVue vue.
     * @see #ParametreurControleur(vtplayer.parametreur.ParametreurModele, vtplayer.parametreur.ParametreurVue, vtplayer.VTPlayer) 
     */
    public ParametreurControleur(ParametreurModele parametreurModele, ParametreurVue parametreurVue) {
        this.parametreurModele = parametreurModele;
        this.parametreurVue = parametreurVue;
    }

    /**
     * Constructeur.
     * 
     * @param parametreurModele modèle.
     * @param parametreurVue vue.
     * @param vtp référence vers la souris.
     * @see #ParametreurControleur(vtplayer.parametreur.ParametreurModele, vtplayer.parametreur.ParametreurVue) 
     */
    public ParametreurControleur(ParametreurModele parametreurModele, ParametreurVue parametreurVue, VTPlayerInterface vtp) {
        this.vtp = vtp;
        this.parametreurModele = parametreurModele;
        this.parametreurVue = parametreurVue;
        vtpSet(parametreurModele.getPicots(parametreurVue.getMenuSelectedItem()));
    }

    /**
     * Vérifie la présence de la souris est lève les bytes en conséquence
     * 
     * @param bytes bytes représentatif des picots a lever.
     */
    protected final void vtpSet(Byte bytes[]) {
        if (vtp != null && vtp.isOpen()) {
            try {
                vtp.set(bytes);
            } catch (VTPlayerException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Communication error", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(ParametreurControleur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            // L'utilisteur souhaite sauvegarder
            case ParametreurVue.ACTION_COMMAND_SAVE:
                try {
                    // on demande au modele de se sauvegarder
                    parametreurModele.save();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "The file can not be find or create. See the log for details.", "File error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(ParametreurControleur.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error on read/write file. See the log for details.", "File error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(ParametreurControleur.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            // si l'utilisateur a changer d'index a changer
            case ParametreurVue.ACTION_COMMAND_INDEX_CHANGE:
                // on récupère le nouvelle index
                int index = parametreurVue.getMenuSelectedItem();

                // on récupère les bytes associé a cette index
                Byte bytes[] = parametreurModele.getPicots(index);

                // on met a jour la vue
                parametreurVue.setMenuText(parametreurModele.getDescription(index));
                parametreurVue.setBytes(bytes);

                // si la souris est brancher on léve les picots
                vtpSet(bytes);

                // on demande a la vue de se redessiner
                parametreurVue.repaint();
                break;

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();

        if (o instanceof ParametreurGridVue.Picot) {
            // mise a jour de la vue
            ((ParametreurGridVue.Picot) o).setBit();

            // on récupère les bytes
            Byte bytes[] = parametreurVue.getBytes();

            // mise a jour du modele
            parametreurModele.setPicots(parametreurVue.getMenuSelectedItem(), bytes);

            // Il la souris est brancher on met les picots comme sur l'écran
            vtpSet(bytes);
        }

        // on redessine la vue.
        parametreurVue.repaint();
    }
}
