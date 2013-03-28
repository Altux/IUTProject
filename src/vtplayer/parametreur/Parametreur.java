package vtplayer.parametreur;

import java.awt.Frame;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import vtplayer.VTPlayerException;
import vtplayer.VTPlayerInterface;
import vtplayer.VTPlayerManager;

/**
 *
 * @author
 */
public class Parametreur extends JDialog {

    ParametreurVue vue;
    ParametreurModele modele;
    ParametreurControleur controleur;

    public Parametreur(Frame owner, VTPlayerInterface vtp) {
        super(owner);
        setSize(800, 600);

        try {
            modele = new ParametreurModele();
            vue = new ParametreurVue(modele);
            controleur = new ParametreurControleur(modele, vue);
            vue.addMouseListener(controleur);
            vue.addActionListener(controleur);

            setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
            setVisible(true);
        } catch (IOException | VTPlayerException ex) {
            dispose();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) throws VTPlayerException {
        VTPlayerInterface vtp = VTPlayerManager.getInstance();
        VTPlayerManager.open(vtp);
        new Parametreur(null, vtp);
    }
}
